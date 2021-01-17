package demo.agent.bytebuddy;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.implementation.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * 拦截处理的方法
 */
@Slf4j
public class MonitorDemo {

    public static ThreadLocal<Track> trackThreadLocal = new InheritableThreadLocal<>();
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
            log.info("tree:{}", trackThreadLocal.get().getTreeStr());
            trackThreadLocal.remove();
        } else {
            //节点向前移动一位
            trackThreadLocal.set(track.getFatherTrack());
        }
        return result;
    }

    @Data
    public static class Track {

        private String className;//名称对象
        private String methodName;//方法名称
        private Integer parameterCount;//入参个数
        private Class<?>[] parameterTypes;//入参类型
        private Object[] args;//入参内容
        private Class<?> returnType;//出参类型
        private Object result;//出参内容
        private long start;//开始时间
        private long end;//结束时间

        //        private int
        private Track fatherTrack;//上层
        private List<Track> childTracks;//子层

        private Object sourceObject;//原始代理对象
        private Method sourceMethod;//原始方法

        public static Track build(Object thisObject,//当前对象
                                  Method method,//拦截方法
                                  Object[] args//参数
        ) {
            Track track = new Track();
            track.sourceMethod = method;
            track.sourceObject = thisObject;
            //
            track.className = thisObject.getClass().getName();
            track.methodName = method.getName();
            track.parameterCount = method.getParameterCount();
            track.args = args;
            track.returnType = method.getReturnType();

            track.start = System.currentTimeMillis();
            return track;
        }

        public void exit(Object result,//执行结果
                         long end
        ) {
            this.result = result;
            this.end = end;
        }

        public synchronized void addChild(Track child) {
            if (null == this.childTracks) {
                this.childTracks = new ArrayList<>();
            }
            this.childTracks.add(child);
        }

        public synchronized List<Track> getChild() {
            if (null == this.childTracks) {
                return new ArrayList<>();
            } else {
                return this.childTracks;
            }
        }

        public String getTreeStr() {
            return getTreeStr(this);
        }

        public static String getTreeStr(Track track) {
            StringBuilder str = new StringBuilder();
            print(str, track, 0);
            return str.toString();
        }

        private static void print(StringBuilder str, Track track, int level) {
            for (int i = 0; i < level; i++) {
                str.append("\t");
            }
            str.append(track.className + "#" + track.methodName).append("\n");
            track.getChild().forEach(childTrack -> {
                int levelNext = level + 1;
                print(str, childTrack, levelNext);
            });
        }


    }


}
