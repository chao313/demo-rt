package demo.agent.bytebuddy;

import com.fasterxml.jackson.databind.json.JsonMapper;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.*;

@Data
@Slf4j
public class TrackInfo {
    private TrackInfo fatherTrackInfo;//上层
    private String uuid;
    private String classNameInfo;//名称对象
    private String methodNameInfo;//方法名称
    private Integer parameterCountInfo;//入参个数
    private List<Object> parameterTypesInfo;//入参类型
    private List<Object> argsInfo;//入参内容
    private Object returnTypeInfo;//出参类型
    private Object resultInfo;//出参内容
    private long startInfo;//开始时间
    private long endInfo;//结束时间
    private long costInfo;//结束时间
    private Object sourceObjectInfo;//原始代理对象
    private Object sourceMethodInfo;//原始方法
    private Integer childTrackSizeInfo;//子层size 和 childTracks 保持一致
    private List<TrackInfo> childTrackInfos;//子层

    private static Collection<String> classFilter = new HashSet<>();

    static {
        classFilter.add("com.zaxxer.hikari.HikariDataSource");
        classFilter.add("java.lang.reflect.Method");
        classFilter.add("org.springframework.beans.factory.support.DefaultListableBeanFactory");
        classFilter.add("org.springframework.cglib.core.Signature");
        classFilter.add("org.springframework.data.redis.connection.jedis.JedisConnectionFactory");
        classFilter.add("org.springframework.data.redis.core.DefaultHashOperations");
        classFilter.add("org.springframework.data.redis.core.DefaultListOperations");
        classFilter.add("org.springframework.data.redis.core.DefaultSetOperations");
        classFilter.add("org.springframework.data.redis.core.DefaultValueOperations");
        classFilter.add("org.springframework.data.redis.core.DefaultZSetOperations");
        classFilter.add("org.springframework.data.redis.core.RedisTemplate");
        classFilter.add("org.springframework.jdbc.core.JdbcTemplate");
        classFilter.add("org.springframework.jdbc.datasource.DataSourceTransactionManager");
        classFilter.add("org.springframework.transaction.support.TransactionTemplate");
        classFilter.add("org.springframework.web.context.support.StandardServletEnvironment");
        classFilter.add("springfox.documentation.spring.web.plugins.Docket");
    }

    public static TrackInfo build(Track track) {
        TrackInfo trackInfo = new TrackInfo();
        clone(track, trackInfo);
        return trackInfo;
    }

    private static void clone(Track track, TrackInfo trackInfo) {
        TrackInfo.copyProperties(track, trackInfo);
        track.getChild().forEach(child -> {
            TrackInfo trackChild = new TrackInfo();
            trackInfo.getChildTrackInfos().add(trackChild);
//            trackChild.setFatherTrackInfo(trackInfo);
            TrackInfo.clone(child, trackChild);
        });
    }

    /**
     * 只build根
     */
    public static TrackInfo buildOnlyRoot(Track track) {
        TrackInfo trackInfo = new TrackInfo();
        TrackInfo.copyProperties(track, trackInfo);
        return trackInfo;
    }


    public synchronized List<TrackInfo> getChildTrackInfos() {
        if (null == this.childTrackInfos) {
            this.childTrackInfos = new ArrayList<>();
        }
        return this.childTrackInfos;
    }

    /**
     * 下面两个使用递归处理
     * private TrackInfo fatherTrackInfo;       //上层
     * private List<TrackInfo> childTrackInfos; //子层
     */
    private static void copyProperties(Track track, TrackInfo trackInfo) {
        trackInfo.setUuid(track.getUuid());
        trackInfo.setClassNameInfo(track.getClassName());
        trackInfo.setMethodNameInfo(track.getMethodName());
        trackInfo.setParameterCountInfo(track.getParameterCount());
        trackInfo.setParameterTypesInfo(toList(Arrays.asList(track.getParameterTypes())));
        trackInfo.setArgsInfo(toList(Arrays.asList(track.getArgs())));
        trackInfo.setReturnTypeInfo(TrackInfo.change(track.getReturnType()));
        trackInfo.setResultInfo(TrackInfo.change(track.getResult()));
        trackInfo.setStartInfo(track.getStart());
        trackInfo.setEndInfo(track.getEnd());
        trackInfo.setCostInfo(track.getCost());
        trackInfo.setChildTrackSizeInfo(track.getChildTrackSize());
        //获取source对象可能会导致是循环
        trackInfo.setSourceObjectInfo(track.getSourceObject().toString());
        trackInfo.setSourceMethodInfo(TrackInfo.change(track.getSourceMethod()));
    }

    private static List<Object> toList(Collection<Object> objects) {
        List<Object> result = new ArrayList<>();
        objects.forEach(o -> {
            result.add(TrackInfo.change(o));
        });
        return result;
    }

    public static Object change(Object o) {
        if (null == o) {
            return o;
        }
        boolean b = canToJson(o);
        if (b == true) {
            return o;
        } else {
            return o.toString();
        }
    }

    public static boolean canToJson(Object object) {
        boolean canToJson = true;
        try {
            if (classFilter.contains(object.getClass().getName())) {
                canToJson = false;
            } else {
                JsonMapper jsonMapper = new JsonMapper();
                jsonMapper.writeValueAsString(object);
            }
        } catch (Exception e) {
            log.info("不能转换成json:{}", object.getClass().getName());
            classFilter.add(object.getClass().getName());
            canToJson = false;
        }
        return canToJson;
    }

    @Override
    public String toString() {
        return "TrackInfo{" +
//                "fatherTrackInfo=" + fatherTrackInfo +
                ", uuid='" + uuid + '\'' +
                ", classNameInfo='" + classNameInfo + '\'' +
                ", methodNameInfo='" + methodNameInfo + '\'' +
                ", parameterCountInfo=" + parameterCountInfo +
                ", parameterTypesInfo=" + parameterTypesInfo +
                ", argsInfo=" + argsInfo +
                ", returnTypeInfo=" + returnTypeInfo +
                ", resultInfo=" + resultInfo +
                ", startInfo=" + startInfo +
                ", endInfo=" + endInfo +
                ", costInfo=" + costInfo +
                ", sourceObjectInfo=" + sourceObjectInfo +
                ", sourceMethodInfo=" + sourceMethodInfo +
                ", childTrackSizeInfo=" + childTrackSizeInfo +
//                ", childTrackInfos=" + childTrackInfos +
                '}';
    }
}