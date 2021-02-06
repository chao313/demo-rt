/**
 * Copyright 2021 json.cn
 */
package demo.rt.tools.jmx.properties;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Auto-generated: 2021-02-06 12:59:18
 *
 * @author json.cn (i@json.cn)
 * @website http://www.json.cn/java2pojo/
 */
@Data
public class OperatingSystemMXBeanVo {

    @JSONField(name = "name")
    private String name;//操作系统名称
    @JSONField(name = "arch")
    private String arch;//操作系统架构
    @JSONField(name = "version")
    private String version;//操作系统版本
    @JSONField(name = "systemLoadAverage")
    private String systemLoadAverage;//操作系统负载
    @JSONField(name = "availableProcessors")
    private String availableProcessors;//操作系统可以得到的处理器
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