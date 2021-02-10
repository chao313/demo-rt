package demo.rt.tools.jmx;

import com.alibaba.fastjson.JSON;
import com.sun.management.HotSpotDiagnosticMXBean;
import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import demo.rt.tools.jmx.impl.JMXMXBean;
import demo.rt.tools.jmx.impl.SelfMXBean;
import demo.rt.tools.jmx.properties.*;
import demo.rt.tools.jmx.properties.MemoryPoolMXBeanVo;
import demo.rt.tools.jmx.properties.GarbageCollectorMXBeanVo;

import javax.management.MBeanServer;
import java.io.IOException;
import java.lang.management.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface MXBeanInterface {

    /**
     * 类加载 MXBean
     */
    ClassLoadingMXBean getClassLoadingMXBean() throws IOException;

    /**
     * 编译 MXBean
     */
    CompilationMXBean getCompilationMXBean() throws IOException;

    /**
     * 运行时 MXBean
     */
    RuntimeMXBean getRuntimeMXBean() throws IOException;

    /**
     * 线程 MXBean
     */
    ThreadMXBean getThreadMXBean() throws IOException;

    /**
     * 操作系统 MXBean
     */
    OperatingSystemMXBean getOperatingSystemMXBean() throws IOException;

    /**
     * 垃圾回收器 MXBean
     */
    List<GarbageCollectorMXBean> getGarbageCollectorMXBeans() throws IOException;

    /**
     * 内存 MXBean
     */
    MemoryMXBean getMemoryMXBean() throws IOException;

    /**
     * 内存池 MXBean
     */
    List<MemoryPoolMXBean> getMemoryPoolMXBeans();

    /**
     * 内存管理器
     */
    List<MemoryManagerMXBean> getMemoryManagerMXBeans();

    /**
     * 获取服务端Server
     */
    MBeanServer getPlatformMBeanServer();

    /**
     * 获取 HotSpot 的诊断器(堆栈)
     * 这里的 HotSpotDiagnosticMXBean 不是规范的(是)
     * https://docs.oracle.com/en/java/javase/11/docs/api/jdk.management/com/sun/management/HotSpotDiagnosticMXBean.html
     */
    HotSpotDiagnosticMXBean getDiagnosticMXBean() throws IOException;


    //
    static MXBeanInterface getSelfMXBean() {
        return SelfMXBean.geInstance();
    }

    static MXBeanInterface getJMXMXBean(String url) throws IOException {
        return JMXMXBean.getInstance(url);
    }

    static MXBeanInterface getJMXMXBean(Integer pid) throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        return JMXMXBean.getInstance(pid);
    }

    /**
     * 转换
     */
    static RuntimeMXBeanVo build(RuntimeMXBean source) {
        String s = JSON.toJSON(source).toString();
        RuntimeMXBeanVo vo = JSON.parseObject(s, RuntimeMXBeanVo.class);
        return vo;
    }

    /**
     * 转换
     */
    static OperatingSystemMXBeanVo build(OperatingSystemMXBean source) {
        String s = JSON.toJSON(source).toString();
        OperatingSystemMXBeanVo vo = JSON.parseObject(s, OperatingSystemMXBeanVo.class);
        return vo;
    }

    /**
     * 转换
     */
    static ClassLoadingMXBeanVo build(ClassLoadingMXBean source) {
        String s = JSON.toJSON(source).toString();
        ClassLoadingMXBeanVo vo = JSON.parseObject(s, ClassLoadingMXBeanVo.class);
        return vo;
    }

    /**
     * 转换
     */
    static CompilationMXBeanVo build(CompilationMXBean source) {
        String s = JSON.toJSON(source).toString();
        CompilationMXBeanVo vo = JSON.parseObject(s, CompilationMXBeanVo.class);
        return vo;
    }

    /**
     * 转换
     */
    static ThreadMXBeanVo build(ThreadMXBean source) {
        ThreadMXBeanVo vo = ThreadMXBeanVo.build(source);
        return vo;
    }

    /**
     * 转换
     */
    static MemoryMXBeanVo build(MemoryMXBean source) {
        String s = JSON.toJSON(source).toString();
        MemoryMXBeanVo vo = JSON.parseObject(s, MemoryMXBeanVo.class);
        return vo;
    }

    /**
     * 转换
     */
    static MemoryPoolMXBeanVo build(MemoryPoolMXBean source) {
        return MemoryPoolMXBeanVo.build(source);
    }

    /**
     * 转换
     */
    static Collection<MemoryPoolMXBeanVo> build(Collection<MemoryPoolMXBean> sources, MemoryPoolMXBeanVo type) {
        Collection<MemoryPoolMXBeanVo> result = new ArrayList<>();
        sources.forEach(source -> {
            MemoryPoolMXBeanVo memoryPoolMXBeanVo = MemoryPoolMXBeanVo.build(source);
            result.add(memoryPoolMXBeanVo);
        });
        return result;
    }

    /**
     * 转换
     */
    static Collection<MemoryManagerMXBeanVo> build(Collection<MemoryManagerMXBean> sources, MemoryManagerMXBeanVo type) {
        Collection<MemoryManagerMXBeanVo> result = new ArrayList<>();
        sources.forEach(source -> {
            MemoryManagerMXBeanVo vo = MemoryManagerMXBeanVo.build(source);
            result.add(vo);
        });
        return result;
    }

    /**
     * 转换
     */
    static Collection<GarbageCollectorMXBeanVo> build(Collection<GarbageCollectorMXBean> sources, GarbageCollectorMXBeanVo type) throws NoSuchFieldException, IllegalAccessException {
        Collection<GarbageCollectorMXBeanVo> result = new ArrayList<>();
        for (GarbageCollectorMXBean source : sources) {
            GarbageCollectorMXBeanVo vo = GarbageCollectorMXBeanVo.build(source);
            result.add(vo);
        }
        return result;
    }
}
