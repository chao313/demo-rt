package demo.rt.tools.jmx.properties;

import lombok.Data;

@Data
public class ClassLoadingMXBeanVo {

    private int loadedClassCount;//已加装当前类
    private int unloadedClassCount;//已卸载类总数
    private int totalLoadedClassCount;//已加载类总数
    private boolean verbose;//是否详细展示

    private ObjectName objectName;
}


/**
 * <pre>
 *     {
 *     "objectName": {
 *       "canonicalName": "java.lang:type=ClassLoading",
 *       "canonicalKeyPropertyListString": "type=ClassLoading",
 *       "domain": "java.lang",
 *       "keyPropertyList": {
 *         "type": "ClassLoading"
 *       },
 *       "keyPropertyListString": "type=ClassLoading",
 *       "domainPattern": false,
 *       "pattern": false,
 *       "propertyListPattern": false,
 *       "propertyPattern": false,
 *       "propertyValuePattern": false
 *     },
 *     "loadedClassCount": 10022,
 *     "unloadedClassCount": 1,
 *     "totalLoadedClassCount": 10023,
 *     "verbose": false
 *   }
 * </pre>
 */