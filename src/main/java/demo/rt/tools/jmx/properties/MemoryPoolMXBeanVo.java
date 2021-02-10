package demo.rt.tools.jmx.properties;

import lombok.Data;

import java.lang.management.MemoryPoolMXBean;
import java.lang.management.MemoryType;
import java.lang.management.MemoryUsage;

/**
 * 基本完成，没有隐藏属性
 * https://docs.oracle.com/en/java/javase/11/docs/api/java.management/java/lang/management/MemoryPoolMXBean.html
 */
@Data
public class MemoryPoolMXBeanVo {

    private MemoryUsage collectionUsage;//最近一次GC之后的内存使用情况
    private long collectionUsageThreshold;//集合使用阈值
    private long collectionUsageThresholdCount;//集合使用超过阈值的次数
    private String[] memoryManagerNames;//此内存池管理器的名称(GC的名称)
    private String name;//内存池名称
    private MemoryUsage peakUsage;//内存池峰值
    private MemoryType type;//内存池类型(一般就是堆和非堆)
    private MemoryUsage usage;//内存池使用情况
    private long usageThreshold;//内存池使用率阈值
    private long usageThresholdCount;//内存池使用率阈值超过次数
    private boolean collectionUsageThresholdExceeded;//最近一次GC之后,内存池使用是否超过收集的使用量
    private boolean collectionUsageThresholdSupported;//是否支持使用量阈值
    private boolean usageThresholdExceeded;//使用量是否达到/超过阈值
    private boolean usageThresholdSupported;//是否支持使用率阈值
    private boolean valid;//内存池是否有效


    public static MemoryPoolMXBeanVo build(MemoryPoolMXBean memoryPoolMXBean) {
        MemoryPoolMXBeanVo memoryPoolMXBeanVo = new MemoryPoolMXBeanVo();
        memoryPoolMXBeanVo.collectionUsage = memoryPoolMXBean.getCollectionUsage();
        memoryPoolMXBeanVo.memoryManagerNames = memoryPoolMXBean.getMemoryManagerNames();
        memoryPoolMXBeanVo.name = memoryPoolMXBean.getName();
        memoryPoolMXBeanVo.peakUsage = memoryPoolMXBean.getPeakUsage();
        memoryPoolMXBeanVo.type = memoryPoolMXBean.getType();
        memoryPoolMXBeanVo.usage = memoryPoolMXBean.getUsage();

        memoryPoolMXBeanVo.valid = memoryPoolMXBean.isValid();
        //内存使用量
        memoryPoolMXBeanVo.collectionUsageThresholdSupported = memoryPoolMXBean.isCollectionUsageThresholdSupported();
        if (true == memoryPoolMXBeanVo.collectionUsageThresholdSupported) {
            memoryPoolMXBeanVo.collectionUsageThreshold = memoryPoolMXBean.getCollectionUsageThreshold();
            memoryPoolMXBeanVo.collectionUsageThresholdCount = memoryPoolMXBean.getCollectionUsageThresholdCount();
            memoryPoolMXBeanVo.collectionUsageThresholdExceeded = memoryPoolMXBean.isCollectionUsageThresholdExceeded();
        }
        //内存使用率
        memoryPoolMXBeanVo.usageThresholdSupported = memoryPoolMXBean.isUsageThresholdSupported();
        if (true == memoryPoolMXBeanVo.usageThresholdSupported) {
            memoryPoolMXBeanVo.usageThreshold = memoryPoolMXBean.getUsageThreshold();
            memoryPoolMXBeanVo.usageThresholdCount = memoryPoolMXBean.getUsageThresholdCount();
            memoryPoolMXBeanVo.usageThresholdExceeded = memoryPoolMXBean.isUsageThresholdExceeded();
        }
        return memoryPoolMXBeanVo;
    }
}
