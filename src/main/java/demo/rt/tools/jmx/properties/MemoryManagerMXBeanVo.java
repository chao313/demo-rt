package demo.rt.tools.jmx.properties;

import lombok.Data;

import java.lang.management.MemoryManagerMXBean;

/**
 * ParallelScavenge（PS）
 * PS代表并行
 */
@Data
public class MemoryManagerMXBeanVo {
    private String name;//內存管理器的名称
    private boolean valid;//当前内存管理器是否有效
    private String[] memoryPoolNames;//获取内存管理器管理的内存区域

    public static MemoryManagerMXBeanVo build(MemoryManagerMXBean memoryManagerMXBean) {
        MemoryManagerMXBeanVo memoryManagerMXBeanVo = new MemoryManagerMXBeanVo();
        memoryManagerMXBeanVo.name = memoryManagerMXBean.getName();
        memoryManagerMXBeanVo.valid = memoryManagerMXBean.isValid();
        memoryManagerMXBeanVo.memoryPoolNames = memoryManagerMXBean.getMemoryPoolNames();
        return memoryManagerMXBeanVo;
    }
}
