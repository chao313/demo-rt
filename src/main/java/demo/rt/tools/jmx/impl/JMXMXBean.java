package demo.rt.tools.jmx.impl;

import com.sun.management.HotSpotDiagnosticMXBean;
import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import demo.rt.tools.jmx.MXBeanInterface;
import demo.rt.tools.jmx.VirtualMachineUtil;
import sun.management.HotSpotDiagnostic;

import javax.management.MBeanServer;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.lang.management.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 使用单例模式:
 * 不可创建没有维护的实例
 */
public class JMXMXBean implements MXBeanInterface {

    private static Map<String, JMXMXBean> mapUrl = new ConcurrentHashMap<>();//缓存url到JMXMXBean
    private static Map<Integer, JMXMXBean> mapPid = new ConcurrentHashMap<>();//缓存pid到JMXMXBean


    public static JMXMXBean getInstance(String url) throws IOException {
        if (mapUrl.containsKey(url)) {
            return mapUrl.get(url);
        } else {
            synchronized (JMXMXBean.class) {
                if (!mapUrl.containsKey(url)) {
                    JMXMXBean jmxmxBean = new JMXMXBean(url);
                    mapUrl.put(url, jmxmxBean);
                }
            }
        }
        return mapUrl.get(url);
    }

    public static JMXMXBean getInstance(Integer pid) throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        if (mapPid.containsKey(pid)) {
            return mapPid.get(pid);
        } else {
            synchronized (JMXMXBean.class) {
                if (!mapPid.containsKey(pid)) {
                    String url = VirtualMachineUtil.openJMXAndGetUrl(pid);
                    JMXMXBean jmxmxBean = new JMXMXBean(url);
                    mapPid.put(pid, jmxmxBean);
                }
            }
        }
        return mapPid.get(pid);
    }

    private JMXConnector connector = null;

    private JMXMXBean(String url) throws IOException {
        JMXServiceURL jmxServiceURL = new JMXServiceURL(url);
        JMXConnector connector = JMXConnectorFactory.connect(jmxServiceURL);
        this.connector = connector;
    }


    @Override
    public ClassLoadingMXBean getClassLoadingMXBean() throws IOException {
        String mxBeanName = "java.lang:type=ClassLoading";
        ClassLoadingMXBean mxBean = ManagementFactory.newPlatformMXBeanProxy(connector
                .getMBeanServerConnection(), mxBeanName, ClassLoadingMXBean.class);
        return mxBean;
    }

    @Override
    public CompilationMXBean getCompilationMXBean() throws IOException {
        String mxBeanName = "java.lang:type=Compilation";
        CompilationMXBean mxBean = ManagementFactory.newPlatformMXBeanProxy(connector
                .getMBeanServerConnection(), mxBeanName, CompilationMXBean.class);
        return mxBean;
    }

    @Override
    public RuntimeMXBean getRuntimeMXBean() throws IOException {
        String mxBeanName = "java.lang:type=Runtime";
        RuntimeMXBean mxBean = ManagementFactory.newPlatformMXBeanProxy(connector
                .getMBeanServerConnection(), mxBeanName, RuntimeMXBean.class);
        return mxBean;
    }

    @Override
    public ThreadMXBean getThreadMXBean() throws IOException {
        String mxBeanName = "java.lang:type=Threading";
        ThreadMXBean mxBean = ManagementFactory.newPlatformMXBeanProxy(connector
                .getMBeanServerConnection(), mxBeanName, ThreadMXBean.class);
        return mxBean;
    }

    @Override
    public OperatingSystemMXBean getOperatingSystemMXBean() throws IOException {
        String mxBeanName = "java.lang:type=OperatingSystem";
        OperatingSystemMXBean mxBean = ManagementFactory.newPlatformMXBeanProxy(connector
                .getMBeanServerConnection(), mxBeanName, OperatingSystemMXBean.class);
        return mxBean;
    }

    public List<GarbageCollectorMXBean> getGarbageCollectorMXBeans() throws IOException {
        String mxBeanName = "java.lang:type=GarbageCollector";
        GarbageCollectorMXBean mxBean = ManagementFactory.newPlatformMXBeanProxy(connector
                .getMBeanServerConnection(), mxBeanName, GarbageCollectorMXBean.class);
        return Arrays.asList(mxBean);
    }

    @Override
    public MemoryMXBean getMemoryMXBean() throws IOException {
        String mxBeanName = "java.lang:type=Memory";
        MemoryMXBean mxBean = ManagementFactory.newPlatformMXBeanProxy(connector
                .getMBeanServerConnection(), mxBeanName, MemoryMXBean.class);
        return mxBean;
    }


    @Override
    public List<MemoryPoolMXBean> getMemoryPoolMXBeans() {
        return ManagementFactory.getMemoryPoolMXBeans();
    }

    @Override
    public List<MemoryManagerMXBean> getMemoryManagerMXBeans() {
        return ManagementFactory.getMemoryManagerMXBeans();
    }

    @Override
    public MBeanServer getPlatformMBeanServer() {
        return ManagementFactory.getPlatformMBeanServer();
    }

    @Override
    public HotSpotDiagnosticMXBean getDiagnosticMXBean() throws IOException {
        String mxBeanName = "com.sun.management:type=HotSpotDiagnostic";
        HotSpotDiagnosticMXBean mxBean = ManagementFactory.newPlatformMXBeanProxy(connector
                .getMBeanServerConnection(), mxBeanName, HotSpotDiagnosticMXBean.class);
        return mxBean;
    }


}
