/**
 * Copyright 2021 json.cn
 */
package demo.rt.tools.jmx.properties;

import lombok.Data;

/**
 *
 */
@Data
public class OperatingSystemMXBeanVo {

    private String name;//操作系统名称
    private String arch;//操作系统架构
    private String version;//操作系统版本
    private String systemLoadAverage;//操作系统负载
    private String availableProcessors;//操作系统可以得到的处理器

    private ObjectName objectName;

}


/**
 * <pre>
 *   {
 *     "name": "Mac OS X",
 *     "objectName": {
 *       "canonicalName": "java.lang:type=OperatingSystem",
 *       "pattern": false,
 *       "domainPattern": false,
 *       "propertyPattern": false,
 *       "propertyListPattern": false,
 *       "propertyValuePattern": false,
 *       "domain": "java.lang",
 *       "keyPropertyList": {
 *         "type": "OperatingSystem"
 *       },
 *       "keyPropertyListString": "type=OperatingSystem",
 *       "canonicalKeyPropertyListString": "type=OperatingSystem"
 *     },
 *     "arch": "x86_64",
 *     "version": "10.12.6",
 *     "systemLoadAverage": 2.470703125,
 *     "availableProcessors": 4
 *   }
 *
 * </pre>
 **/