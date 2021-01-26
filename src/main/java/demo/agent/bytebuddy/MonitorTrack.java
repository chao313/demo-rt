package demo.agent.bytebuddy;

import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.*;
import java.util.concurrent.Callable;

/**
 * 拦截处理的方法
 */
@Slf4j
public class MonitorTrack {

    public static Collection<Track> tracks = new ArrayList<>();//收集全部的追踪信息
    public static Map<String, List<Track>> mapTracks = new LinkedHashMap<>();//收集全部的追踪信息
    public static ThreadLocal<Track> trackThreadLocal = new InheritableThreadLocal<>();
    private static OutputStream trackLog;

    static {
        try {
            trackLog = new FileOutputStream("trackLog");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
//    public static ThreadLocal<Boolean> ThreadLocal = new InheritableThreadLocal<>();

    /**
     * @Argument 绑定单个参数
     * @AllArguments 绑定所有参数的数组
     * @This 当前被拦截的、动态生成的那个对象
     * @Super 当前被拦截的、动态生成的那个对象的父类对象
     * @Origin 可以绑定到以下类型的参数:Method 被调用的原始方法 Constructor 被调 用的原始构造器 Class 当前动态创建的类 MethodHandle MethodType String 动态类的toString()的返回值 int 动态方法的修饰符
     * @DefaultCall 调用默认方法而非super的方法
     * @SuperCall 用于调用父类版本的方法
     * @Super 注入父类型对象，可以是接口，从而调用它的任何方法
     * @RuntimeType 可以用在返回值、参数上，提示ByteBuddy禁用严格的类型检查
     * @Empty 注入参数的类型的默认值
     * @StubValue 注入一个存根值。对于返回引用、void的方法，注入null;对于返回原始类型 的方法，注入0
     * @FieldValue 注入被拦截对象的一个字段的值
     * @Morph 类似于@SuperCall，但是允许指定调用参数
     */
    @RuntimeType
    public static Object intercept(@This Object thisObject,//当前对象
                                   @Super Object superObject,//父类对象
                                   @Origin Method method,//拦截原有方法
                                   @AllArguments Object[] args,//拦截原有参数
                                   @SuperCall Callable<?> callable) throws Exception {

        boolean flag = false;//是否是头
        Track track = Track.build(thisObject, method, args);
        if (null == trackThreadLocal.get()) {
            flag = true;//代表是头节点
            trackThreadLocal.set(track);
            //存储
            tracks.add(track);
            String key = track.getClassName() + "#" + track.getMethodName();
            if (mapTracks.containsKey(key)) {
                mapTracks.get(key).add(track);
            } else {
                List<Track> tmp = new ArrayList<>();
                tmp.add(track);
                mapTracks.put(key, tmp);
            }
        } else {
            trackThreadLocal.get().addChild(track);
            track.setFatherTrack(trackThreadLocal.get());//父子节点拼接
            trackThreadLocal.set(track);//节点下移
        }
        Object result = null;
        try {
            result = callable.call();
        } catch (Exception e) {
            log.error("e:{}", e.toString(), e);
        }
        track.exit(result, System.currentTimeMillis());

        if (flag == true) {
            trackLog = trackThreadLocal.get().getTreeStr(trackLog);
            trackLog.flush();
            trackThreadLocal.remove();
        } else {
            //节点向前移动一位
            trackThreadLocal.set(track.getFatherTrack());
        }
        return result;
    }


}
