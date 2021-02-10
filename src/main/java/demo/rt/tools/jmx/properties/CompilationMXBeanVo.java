package demo.rt.tools.jmx.properties;

import lombok.Data;

@Data
public class CompilationMXBeanVo {

    private String name;//编译器名称
    private boolean compilationTimeMonitoringSupported;//编译时间监控是否支持
    private long totalCompilationTime;//总编译时间

    private ObjectName objectName;
}

/**
 * <pre>
 *     {
 *     "name": "HotSpot 64-Bit Tiered Compilers",
 *     "objectName": {
 *       "canonicalName": "java.lang:type=Compilation",
 *       "canonicalKeyPropertyListString": "type=Compilation",
 *       "domain": "java.lang",
 *       "keyPropertyList": {
 *         "type": "Compilation"
 *       },
 *       "keyPropertyListString": "type=Compilation",
 *       "domainPattern": false,
 *       "pattern": false,
 *       "propertyListPattern": false,
 *       "propertyPattern": false,
 *       "propertyValuePattern": false
 *     },
 *     "compilationTimeMonitoringSupported": true,
 *     "totalCompilationTime": 15310
 *   }
 * </pre>
 */