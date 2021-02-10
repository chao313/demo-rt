package demo.rt.tools.jmx.properties;

import demo.rt.util.ReflectUtil;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import javax.management.openmbean.CompositeType;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.MemoryPoolMXBean;

/**
 * 这边比较复杂
 * 还有一小部分数据未完全处理
 * https://docs.oracle.com/en/java/javase/11/docs/api/java.management/java/lang/management/GarbageCollectorMXBean.html
 */
@Data
public class GarbageCollectorMXBeanVo {

    private long collectionCount;//收集的次数
    private long collectionTime;//收集的时间(以毫秒为单位)
    private String[] poolNames = null;
    private GcInfoBuilder gcInfoBuilder = new GcInfoBuilder();
    private String name;
    private boolean isValid;
    private MemoryPoolMXBean[] pools;

    public static GarbageCollectorMXBeanVo build(GarbageCollectorMXBean garbageCollectorMXBean) throws NoSuchFieldException, IllegalAccessException {
        GarbageCollectorMXBeanVo vo = new GarbageCollectorMXBeanVo();
        vo.collectionCount = garbageCollectorMXBean.getCollectionCount();
        vo.collectionTime = garbageCollectorMXBean.getCollectionTime();
        BeanUtils.copyProperties(garbageCollectorMXBean, vo);//复制属性
        sun.management.GcInfoBuilder gcInfoBuilder = ReflectUtil.getFieldValue(garbageCollectorMXBean, "gcInfoBuilder");
        //从父类中获取属性
        vo.name = ReflectUtil.getSuperFieldValue(garbageCollectorMXBean, "name");
        vo.isValid = ReflectUtil.getSuperFieldValue(garbageCollectorMXBean, "isValid");
        vo.pools = ReflectUtil.getSuperFieldValue(garbageCollectorMXBean, "pools");
        //复制 GC 属性
        if (null != gcInfoBuilder) {
            vo.gcInfoBuilder = GcInfoBuilder.builder(gcInfoBuilder);
        }
        return vo;
    }


    @Data
    public static class GcInfoBuilder {
        private GarbageCollectorMXBean gc;
        private String[] poolNames;
        private String[] allItemNames;
        private CompositeType gcInfoCompositeType;
        private int gcExtItemCount;
        private String[] gcExtItemNames;
        private String[] gcExtItemDescs;
        private char[] gcExtItemTypes;

        public static GcInfoBuilder builder(sun.management.GcInfoBuilder gcInfoBuilder) throws NoSuchFieldException, IllegalAccessException {
            GcInfoBuilder vo = new GcInfoBuilder();
            vo.gc = ReflectUtil.getFieldValue(gcInfoBuilder, "gc");
            vo.poolNames = ReflectUtil.getFieldValue(gcInfoBuilder, "poolNames");
            vo.allItemNames = ReflectUtil.getFieldValue(gcInfoBuilder, "allItemNames");
            vo.gcInfoCompositeType = ReflectUtil.getFieldValue(gcInfoBuilder, "gcInfoCompositeType");
            vo.gcExtItemCount = ReflectUtil.getFieldValue(gcInfoBuilder, "gcExtItemCount");
            vo.gcExtItemNames = ReflectUtil.getFieldValue(gcInfoBuilder, "gcExtItemNames");
            vo.gcExtItemDescs = ReflectUtil.getFieldValue(gcInfoBuilder, "gcExtItemDescs");
            vo.gcExtItemTypes = ReflectUtil.getFieldValue(gcInfoBuilder, "gcExtItemTypes");
            return vo;
        }


    }


}