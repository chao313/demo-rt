/**
 * Copyright 2021 json.cn
 */
package demo.rt.tools.jmx.properties;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 *
 */
@Data
public class RuntimeMXBeanVo {
    private String name;//进程@系统用户名称 73677@chaodeMacBook-Pro.local
    private String classPath;//class路径
    private long startTime;//开始时间
    private String specName;//规范名称 Java Virtual Machine Specification
    private String specVendor;//规范系统卖主 Oracle Corporation
    private String specVersion;//规范版本 1.8
    private String managementSpecVersion;//管理版本
    private List<String> inputArguments; //输入参数
    private SystemProperties systemProperties;//宿主机属性
    private boolean bootClassPathSupported;//是否支持bootClassPath
    private String vmName;//vm名称
    private String vmVendor;//vm卖主
    private String vmVersion;//vm版本
    private String libraryPath;//lib的地址
    private String bootClassPath;//bootClassPath地址
    private long uptime;//启动时间
    private ObjectName objectName;

    @Data
    public static class SystemProperties {

        @JSONField(name = "application.home")
        @JsonProperty(value = "application.home")
        private String application_home;//应用的Home /Library/Java/JavaVirtualMachines/jdk1.8.0_144.jdk/Contents/Home
        @JSONField(name = "awt.toolkit")
        @JsonProperty(value = "awt.toolkit")
        private String awt_toolkit;//awt工具套件
        @JSONField(name = "file.encoding.pkg")
        @JsonProperty(value = "file.encoding.pkg")
        private String file_encoding_pkg;//文件编码包
        @JSONField(name = "file.encoding")
        @JsonProperty(value = "file.encoding")
        private String file_encoding;//文件编码
        @JSONField(name = "file.separator")
        @JsonProperty(value = "file.separator")
        private String file_separator;//文件分隔符
        @JSONField(name = "java.awt.graphicsenv")
        @JsonProperty(value = "java.awt.graphicsenv")
        private String java_awt_graphicsenv;//java awt 图形环境
        @JSONField(name = "java.awt.printerjob")
        @JsonProperty(value = "java.awt.printerjob")
        private String java_awt_printerjob;//java awt 打印机工作
        @JSONField(name = "java.class.path")
        @JsonProperty(value = "java.class.path")
        private String java_class_path;//java Class路径
        @JSONField(name = "java.class.version")
        @JsonProperty(value = "java.class.version")
        private String java_class_version;//java Class版本
        @JSONField(name = "java.endorsed.dirs")
        @JsonProperty(value = "java.endorsed.dirs")
        private String java_endorsed_dirs;
        @JSONField(name = "java.ext.dirs")
        @JsonProperty(value = "java.ext.dirs")
        private String java_ext_dirs;//java 扩展路径
        @JSONField(name = "java.home")
        @JsonProperty(value = "java.home")
        private String java_home;//java home
        @JSONField(name = "java.io.tmpdir")
        @JsonProperty(value = "java.io.tmpdir")
        private String java_io_tmpdir;//java io 临时文件夹
        @JSONField(name = "java.library.path")
        @JsonProperty(value = "java.library.path")
        private String java_library_path;//java lib 路径
        @JSONField(name = "java.rmi.server.randomIDs")
        @JsonProperty(value = "java.rmi.server.randomIDs")
        private String java_rmi_server_randomIDs;//java 远程随机id
        @JSONField(name = "java.runtime.name")
        @JsonProperty(value = "java.runtime.name")
        private String java_runtime_name;//java 运行时名称
        @JSONField(name = "java.runtime.version")
        @JsonProperty(value = "java.runtime.version")
        private String java_runtime_version;//java 运行时版本
        @JSONField(name = "java.specification.name")
        @JsonProperty(value = "java.specification.name")
        private String java_specification_name;//java 规范名称
        @JSONField(name = "java.specification.vendor")
        @JsonProperty(value = "java.specification.vendor")
        private String java_specification_vendor;//java 规范卖主
        @JSONField(name = "java.specification.version")
        @JsonProperty(value = "java.specification.version")
        private String java_specification_version;//java 规范版本
        @JSONField(name = "java.vendor.url.bug")
        @JsonProperty(value = "java.vendor.url.bug")
        private String java_vendor_url_bug;//java 卖主 bug url
        @JSONField(name = "java.vendor.url")
        @JsonProperty(value = "java.vendor.url")
        private String java_vendor_url;//java 卖主 url
        @JSONField(name = "java.vendor")
        @JsonProperty(value = "java.vendor")
        private String java_vendor;//java 卖主
        @JSONField(name = "java.version")
        @JsonProperty(value = "java.version")
        private String java_version;//java 版本
        @JSONField(name = "java.vm.info")
        @JsonProperty(value = "java.vm.info")
        private String java_vm_info;//java vm 信息
        @JSONField(name = "java.vm.name")
        @JsonProperty(value = "java.vm.name")
        private String java_vm_name;//java vm 名称
        @JSONField(name = "java.vm.specification.name")
        @JsonProperty(value = "java.vm.specification.name")
        private String java_vm_specification_name;//java vm 范称名规
        @JSONField(name = "java.vm.specification.vendor")
        @JsonProperty(value = "java.vm.specification.vendor")
        private String java_vm_specification_vendor;//java vm 范称卖主
        @JSONField(name = "java.vm.specification.version")
        @JsonProperty(value = "java.vm.specification.version")
        private String java_vm_specification_version;//java vm 范称版本
        @JSONField(name = "java.vm.vendor")
        @JsonProperty(value = "java.vm.vendor")
        private String java_vm_vendor;//java vm 卖主
        @JSONField(name = "java.vm.version")
        @JsonProperty(value = "java.vm.version")
        private String java_vm_version;//java vm 版本
        @JSONField(name = "jconsole.showOutputViewer")
        @JsonProperty(value = "jconsole.showOutputViewer")
        private String jconsole_showOutputViewer;
        @JSONField(name = "line.separator")
        @JsonProperty(value = "line.separator")
        private String line_separator;//行分隔符
        @JSONField(name = "os.arch")
        @JsonProperty(value = "os.arch")
        private String os_arch;//os 架构
        @JSONField(name = "os.name")
        @JsonProperty(value = "os.name")
        private String os_name;//os 名称
        @JSONField(name = "os.version")
        @JsonProperty(value = "os.version")
        private String os_version;//os 版本
        @JSONField(name = "path.separator")
        @JsonProperty(value = "path.separator")
        private String path_separator;//路径分隔符
        @JSONField(name = "sun.arch.data.model")
        @JsonProperty(value = "sun.arch.data.model")
        private String sun_arch_data_model;//sun 架构 数据模型
        @JSONField(name = "sun.awt.enableExtraMouseButtons")
        @JsonProperty(value = "sun.awt.enableExtraMouseButtons")
        private String sun_awt_enableExtraMouseButtons;
        @JSONField(name = "sun.boot.class.path")
        @JsonProperty(value = "sun.boot.class.path")
        private String sun_boot_class_path;
        @JSONField(name = "sun.boot.library.path")
        @JsonProperty(value = "sun.boot.library.path")
        private String sun_boot_library_path;
        @JSONField(name = "sun.cpu.endian")
        @JsonProperty(value = "sun.cpu.endian")
        private String sun_cpu_endian;
        @JSONField(name = "sun.cpu.isalist")
        @JsonProperty(value = "sun.cpu.isalist")
        private String sun_cpu_isalist;
        @JSONField(name = "sun.font.fontmanager")
        @JsonProperty(value = "sun.font.fontmanager")
        private String sun_font_fontmanager;
        @JSONField(name = "sun.io.unicode.encoding")
        @JsonProperty(value = "sun.io.unicode.encoding")
        private String sun_io_unicode_encoding;
        @JSONField(name = "sun.java.command")
        @JsonProperty(value = "sun.java.command")
        private String sun_java_command;//sun java 命令
        @JSONField(name = "sun.java.launcher")
        @JsonProperty(value = "sun.java.launcher")
        private String sun_java_launcher;//sun java发送器
        @JSONField(name = "sun.jnu.encoding")
        @JsonProperty(value = "sun.jnu.encoding")
        private String sun_jnu_encoding;//sun jnu 编码
        @JSONField(name = "sun.management.compiler")
        @JsonProperty(value = "sun.management.compiler")
        private String sun_management_compiler;//sun 管理编译器
        @JSONField(name = "sun.os.patch.level")
        @JsonProperty(value = "sun.os.patch.level")
        private String sun_os_patch_level;
        @JSONField(name = "user.country")
        @JsonProperty(value = "user.country")
        private String user_country;//用户国家
        @JSONField(name = "user.dir")
        @JsonProperty(value = "user.dir")
        private String user_dir;//用户文件夹
        @JSONField(name = "user.home")
        @JsonProperty(value = "user.home")
        private String user_home;//用户home
        @JSONField(name = "user.language")
        @JsonProperty(value = "user.language")
        private String user_language;//用户语言
        @JSONField(name = "user.name")
        @JsonProperty(value = "user.name")
        private String user_name;//用户名称
        @JSONField(name = "user.timezone")
        @JsonProperty(value = "user.timezone")
        private String user_timezone;
        @JSONField(name = "gopherProxySet")
        @JsonProperty(value = "gopherProxySet")
        private String gopherProxySet;
    }

}


/**
 * <pre>
 *   {
 *     "name":"73677@chaodeMacBook-Pro.local",
 *     "classPath":"/Library/Java/JavaVirtualMachines/jdk1.8.0_144.jdk/Contents/Home/lib/jconsole.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_144.jdk/Contents/Home/lib/tools.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_144.jdk/Contents/Home/classes",
 *     "objectName":{
 *         "canonicalName":"java.lang:type=Runtime",
 *         "pattern":false,
 *         "domainPattern":false,
 *         "propertyPattern":false,
 *         "propertyListPattern":false,
 *         "propertyValuePattern":false,
 *         "domain":"java.lang",
 *         "keyPropertyList":{
 *             "type":"Runtime"
 *         },
 *         "keyPropertyListString":"type=Runtime",
 *         "canonicalKeyPropertyListString":"type=Runtime"
 *     },
 *     "startTime":1612448724656,
 *     "specName":"Java Virtual Machine Specification",
 *     "specVendor":"Oracle Corporation",
 *     "specVersion":"1.8",
 *     "managementSpecVersion":"1.2",
 *     "inputArguments":[
 *         "-Dapplication.home=/Library/Java/JavaVirtualMachines/jdk1.8.0_144.jdk/Contents/Home",
 *         "-Xms8m",
 *         "-Djconsole.showOutputViewer"
 *     ],
 *     "systemProperties":{
 *         "gopherProxySet":"false",
 *         "awt.toolkit":"sun.lwawt.macosx.LWCToolkit",
 *         "file.encoding.pkg":"sun.io",
 *         "java.specification.version":"1.8",
 *         "sun.cpu.isalist":"",
 *         "sun.jnu.encoding":"UTF-8",
 *         "sun.awt.enableExtraMouseButtons":"true",
 *         "java.class.path":"/Library/Java/JavaVirtualMachines/jdk1.8.0_144.jdk/Contents/Home/lib/jconsole.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_144.jdk/Contents/Home/lib/tools.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_144.jdk/Contents/Home/classes",
 *         "java.vm.vendor":"Oracle Corporation",
 *         "sun.arch.data.model":"64",
 *         "sun.font.fontmanager":"sun.font.CFontManager",
 *         "java.vendor.url":"http://java.oracle.com/",
 *         "user.timezone":"Asia/Shanghai",
 *         "jconsole.showOutputViewer":"",
 *         "os.name":"Mac OS X",
 *         "java.vm.specification.version":"1.8",
 *         "user.country":"CN",
 *         "sun.java.launcher":"SUN_STANDARD",
 *         "sun.boot.library.path":"/Library/Java/JavaVirtualMachines/jdk1.8.0_144.jdk/Contents/Home/jre/lib",
 *         "sun.java.command":"sun.tools.jconsole.JConsole",
 *         "sun.cpu.endian":"little",
 *         "user.home":"/Users/chao",
 *         "user.language":"zh",
 *         "java.specification.vendor":"Oracle Corporation",
 *         "java.home":"/Library/Java/JavaVirtualMachines/jdk1.8.0_144.jdk/Contents/Home/jre",
 *         "file.separator":"/",
 *         "line.separator":"
 * ",
 *         "java.vm.specification.vendor":"Oracle Corporation",
 *         "java.specification.name":"Java Platform API Specification",
 *         "java.awt.graphicsenv":"sun.awt.CGraphicsEnvironment",
 *         "sun.boot.class.path":"/Library/Java/JavaVirtualMachines/jdk1.8.0_144.jdk/Contents/Home/jre/lib/resources.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_144.jdk/Contents/Home/jre/lib/rt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_144.jdk/Contents/Home/jre/lib/sunrsasign.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_144.jdk/Contents/Home/jre/lib/jsse.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_144.jdk/Contents/Home/jre/lib/jce.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_144.jdk/Contents/Home/jre/lib/charsets.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_144.jdk/Contents/Home/jre/lib/jfr.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_144.jdk/Contents/Home/jre/classes",
 *         "sun.management.compiler":"HotSpot 64-Bit Tiered Compilers",
 *         "java.runtime.version":"1.8.0_144-b01",
 *         "user.name":"chao",
 *         "path.separator":":",
 *         "os.version":"10.12.6",
 *         "java.endorsed.dirs":"/Library/Java/JavaVirtualMachines/jdk1.8.0_144.jdk/Contents/Home/jre/lib/endorsed",
 *         "java.runtime.name":"Java(TM) SE Runtime Environment",
 *         "file.encoding":"UTF-8",
 *         "java.vm.name":"Java HotSpot(TM) 64-Bit Server VM",
 *         "java.vendor.url.bug":"http://bugreport.sun.com/bugreport/",
 *         "java.io.tmpdir":"/var/folders/f8/1vp9t7g167n5whqs12nvxrt80000gn/T/",
 *         "java.version":"1.8.0_144",
 *         "user.dir":"/Users/chao",
 *         "os.arch":"x86_64",
 *         "java.vm.specification.name":"Java Virtual Machine Specification",
 *         "java.awt.printerjob":"sun.lwawt.macosx.CPrinterJob",
 *         "sun.os.patch.level":"unknown",
 *         "application.home":"/Library/Java/JavaVirtualMachines/jdk1.8.0_144.jdk/Contents/Home",
 *         "java.library.path":"/Users/chao/Library/Java/Extensions:/Library/Java/Extensions:/Network/Library/Java/Extensions:/System/Library/Java/Extensions:/usr/lib/java:.",
 *         "java.vm.info":"mixed mode",
 *         "java.vendor":"Oracle Corporation",
 *         "java.vm.version":"25.144-b01",
 *         "java.rmi.server.randomIDs":"true",
 *         "java.ext.dirs":"/Users/chao/Library/Java/Extensions:/Library/Java/JavaVirtualMachines/jdk1.8.0_144.jdk/Contents/Home/jre/lib/ext:/Library/Java/Extensions:/Network/Library/Java/Extensions:/System/Library/Java/Extensions:/usr/lib/java",
 *         "sun.io.unicode.encoding":"UnicodeBig",
 *         "java.class.version":"52.0"
 *     },
 *     "bootClassPathSupported":true,
 *     "vmName":"Java HotSpot(TM) 64-Bit Server VM",
 *     "vmVendor":"Oracle Corporation",
 *     "vmVersion":"25.144-b01",
 *     "libraryPath":"/Users/chao/Library/Java/Extensions:/Library/Java/Extensions:/Network/Library/Java/Extensions:/System/Library/Java/Extensions:/usr/lib/java:.",
 *     "bootClassPath":"/Library/Java/JavaVirtualMachines/jdk1.8.0_144.jdk/Contents/Home/jre/lib/resources.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_144.jdk/Contents/Home/jre/lib/rt.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_144.jdk/Contents/Home/jre/lib/sunrsasign.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_144.jdk/Contents/Home/jre/lib/jsse.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_144.jdk/Contents/Home/jre/lib/jce.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_144.jdk/Contents/Home/jre/lib/charsets.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_144.jdk/Contents/Home/jre/lib/jfr.jar:/Library/Java/JavaVirtualMachines/jdk1.8.0_144.jdk/Contents/Home/jre/classes",
 *     "uptime":7787554
 * }
 * </pre>
 **/