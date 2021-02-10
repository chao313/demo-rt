package demo.rt.tools.jmx.properties;

import lombok.Data;

@Data
public class CompilationMXBeanVoxxx {

    private String name;//编译器名称
    private boolean compilationTimeMonitoringSupported;//编译时间监控是否支持
    private long totalCompilationTime;//总编译时间

    private ObjectName objectName;
}

/**
 * <pre>
 * {
 *     "verbose": false,
 *     "objectName": {
 *       "canonicalName": "java.lang:type=Memory",
 *       "canonicalKeyPropertyListString": "type=Memory",
 *       "domain": "java.lang",
 *       "keyPropertyList": {
 *         "type": "Memory"
 *       },
 *       "keyPropertyListString": "type=Memory",
 *       "domainPattern": false,
 *       "pattern": false,
 *       "propertyListPattern": false,
 *       "propertyPattern": false,
 *       "propertyValuePattern": false
 *     },
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