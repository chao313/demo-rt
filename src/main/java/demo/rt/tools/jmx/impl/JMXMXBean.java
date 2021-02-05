package demo.rt.tools.jmx.impl;

import demo.rt.tools.jmx.MXBeanInterface;

import javax.management.MBeanServer;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.lang.management.*;
import java.lang.reflect.Array;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;

public class JMXMXBean implements MXBeanInterface {

    private JMXConnector connector = null;

    public JMXMXBean(String url) throws IOException {
        JMXServiceURL jmxServiceURL = new JMXServiceURL(url);
        JMXConnector connector = JMXConnectorFactory.connect(jmxServiceURL);
        this.connector = connector;
    }

    public JMXMXBean(JMXServiceURL jmxServiceURL) throws IOException {
        JMXConnector connector = JMXConnectorFactory.connect(jmxServiceURL);
        this.connector = connector;
    }

    public JMXMXBean(JMXConnector connector) throws IOException {
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


}
