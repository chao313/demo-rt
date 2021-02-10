package demo.rt.tools.jmx.impl;

import com.sun.management.HotSpotDiagnosticMXBean;
import demo.rt.tools.jmx.MXBeanInterface;
import sun.management.ManagementFactoryHelper;

import javax.management.MBeanServer;
import java.lang.management.*;
import java.util.List;

public class SelfMXBean implements MXBeanInterface {

    private static SelfMXBean selfMXBean = new SelfMXBean();

    public static MXBeanInterface geInstance() {
        return selfMXBean;
    }

    private SelfMXBean() {
    }

    @Override
    public ClassLoadingMXBean getClassLoadingMXBean() {
        return ManagementFactory.getClassLoadingMXBean();
    }

    @Override
    public CompilationMXBean getCompilationMXBean() {
        return ManagementFactory.getCompilationMXBean();
    }

    @Override
    public RuntimeMXBean getRuntimeMXBean() {
        return ManagementFactory.getRuntimeMXBean();
    }

    @Override
    public ThreadMXBean getThreadMXBean() {
        return ManagementFactory.getThreadMXBean();
    }

    @Override
    public OperatingSystemMXBean getOperatingSystemMXBean() {
        return ManagementFactory.getOperatingSystemMXBean();
    }

    public List<GarbageCollectorMXBean> getGarbageCollectorMXBeans() {
        return ManagementFactory.getGarbageCollectorMXBeans();
    }

    @Override
    public MemoryMXBean getMemoryMXBean() {
        return ManagementFactory.getMemoryMXBean();
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
    public HotSpotDiagnosticMXBean getDiagnosticMXBean() {
        return ManagementFactoryHelper.getDiagnosticMXBean();
    }
}
