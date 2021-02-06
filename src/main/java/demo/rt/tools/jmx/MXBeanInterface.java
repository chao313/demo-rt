package demo.rt.tools.jmx;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import demo.rt.tools.jmx.impl.JMXMXBean;
import demo.rt.tools.jmx.impl.SelfMXBean;

import javax.management.MBeanServer;
import java.io.IOException;
import java.lang.management.*;
import java.util.List;

public interface MXBeanInterface {

    ClassLoadingMXBean getClassLoadingMXBean() throws IOException;

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

    List<MemoryManagerMXBean> getMemoryManagerMXBeans();

    /**
     * 获取服务端Server
     */
    MBeanServer getPlatformMBeanServer();

    static MXBeanInterface getSelfMXBean() {
        return SelfMXBean.geInstance();
    }

    static MXBeanInterface getJMXMXBean(String url) throws IOException {
        return JMXMXBean.getInstance(url);
    }

    static MXBeanInterface getJMXMXBean(Integer pid) throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        return JMXMXBean.getInstance(pid);
    }
}
