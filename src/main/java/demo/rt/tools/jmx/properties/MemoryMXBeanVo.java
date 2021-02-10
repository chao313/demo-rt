/**
 * Copyright 2021 json.cn
 */
package demo.rt.tools.jmx.properties;

import lombok.Data;

import java.util.List;

/**
 * 基本完成,没有其他的隐藏属性
 */
@Data
public class MemoryMXBeanVo {

    private boolean verbose;//是否详细
    private HeapMemoryUsage heapMemoryUsage;//堆内存使用
    private NonHeapMemoryUsage nonHeapMemoryUsage;//非堆内存使用
    private int objectPendingFinalizationCount;//对象被挂起以便回收数
    private List<NotificationInfo> notificationInfo;

    @Data
    public static class HeapMemoryUsage {
        private long init;//初始化
        private long used;//已使用
        private long committed;//已提交(JVM保证能获取的最大值)
        private long max;//最大值(内存管理系统可以使用的最大内存量)
    }

    @Data
    public static class NonHeapMemoryUsage {
        private long init;//初始化
        private long used;//已使用
        private long committed;//已提交
        private long max;//最大值
    }

    @Data
    public static class NotificationInfo {
        private String name;
        private String description;
        private Descriptor descriptor;
    }

    @Data
    public static class Descriptor {
        private List<String> fields;
        private boolean valid;
        private List<String> fieldNames;
    }
}
/**
 * <pre>
 * {
 *     "verbose": false,
 *     "heapMemoryUsage": {
 *       "init": 134217728,
 *       "used": 308634408,
 *       "committed": 966787072,
 *       "max": 1884815360
 *     },
 *     "nonHeapMemoryUsage": {
 *       "init": 2555904,
 *       "used": 82898680,
 *       "committed": 86900736,
 *       "max": -1
 *     },
 *     "objectPendingFinalizationCount": 0,
 *     "notificationInfo": [
 *       {
 *         "name": "javax.management.Notification",
 *         "description": "Memory Notification",
 *         "descriptor": {
 *           "fields": [],
 *           "valid": true,
 *           "fieldNames": []
 *         },
 *         "notifTypes": [
 *           "java.management.memory.threshold.exceeded",
 *           "java.management.memory.collection.threshold.exceeded"
 *         ]
 *       }
 *     ]
 *   }
 * </pre>
 */