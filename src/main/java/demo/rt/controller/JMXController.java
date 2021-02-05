package demo.rt.controller;

import com.sun.jmx.interceptor.DefaultMBeanServerInterceptor;
import com.sun.jmx.mbeanserver.NamedObject;
import com.sun.jmx.mbeanserver.Repository;
import demo.rt.config.framework.Response;
import demo.rt.tools.jmx.VirtualMachineUtil;
import demo.rt.util.ReflectUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.*;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.lang.management.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 链路追踪
 */
@Slf4j
@RestController
@RequestMapping(value = "/JMXController")
public class JMXController {


    @ApiOperation(value = "获取当前主机的全部java进程")
    @GetMapping(value = "/getMainClassToPidsMap")
    public Response getMainClassToPidsMap() throws Exception {
        Map<String, Set<Integer>> mainClassToPidsMap = VirtualMachineUtil.getMainClassToPidsMap();
        return Response.Ok(mainClassToPidsMap);
    }

    @ApiOperation(value = "开启并获取指定java进程的JMX")
    @GetMapping(value = "/openJMXAndGetUrl")
    public Response openJMXAndGetUrl(Integer process) throws Exception {
        String url = VirtualMachineUtil.openJMXAndGetUrl(process);
        return Response.Ok(url);
    }


    @ApiOperation(value = "getJMX")
    @GetMapping(value = "/getJMX")
    public Response getJMX(String url) throws Exception {
        JMXServiceURL jmxServiceURL = new JMXServiceURL(url);
        JMXConnector connector = JMXConnectorFactory.connect(jmxServiceURL);
        RuntimeMXBean runtimeMXBean = ManagementFactory.newPlatformMXBeanProxy(connector
                .getMBeanServerConnection(), "java.lang:type=Runtime", RuntimeMXBean.class);

        return Response.Ok(runtimeMXBean);
    }


    @ApiOperation(value = "ClassLoadingMXBean")
    @GetMapping(value = "/ClassLoadingMXBean")
    public Response ClassLoadingMXBean() {
        ClassLoadingMXBean classLoadingMXBean = ManagementFactory.getClassLoadingMXBean();
        return Response.Ok(classLoadingMXBean);
    }

    @ApiOperation(value = "CompilationMXBean")
    @GetMapping(value = "/CompilationMXBean")
    public Response CompilationMXBean() {
        CompilationMXBean compilationMXBean = ManagementFactory.getCompilationMXBean();
        return Response.Ok(compilationMXBean);
    }

    @ApiOperation(value = "MemoryMXBean")
    @GetMapping(value = "/MemoryMXBean")
    public Response MemoryMXBean() {
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        return Response.Ok(memoryMXBean);
    }

    @ApiOperation(value = "RuntimeMXBean")
    @GetMapping(value = "/RuntimeMXBean")
    public Response RuntimeMXBean() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        return Response.Ok(runtimeMXBean);
    }

    @ApiOperation(value = "ThreadMXBean")
    @GetMapping(value = "/ThreadMXBean")
    public Response ThreadMXBean() {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        return Response.Ok(threadMXBean);
    }

    @ApiOperation(value = "OperatingSystemMXBean")
    @GetMapping(value = "/OperatingSystemMXBean")
    public Response OperatingSystemMXBean() {
        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
        return Response.Ok(operatingSystemMXBean);
    }


    @ApiOperation(value = "GarbageCollectorMXBean")
    @GetMapping(value = "/GarbageCollectorMXBean")
    public Response GarbageCollectorMXBean() {
        List<GarbageCollectorMXBean> garbageCollectorMXBeans = ManagementFactory.getGarbageCollectorMXBeans();
        return Response.Ok(garbageCollectorMXBeans);
    }

    @ApiOperation(value = "MemoryPoolMXBean")
    @GetMapping(value = "/MemoryPoolMXBean")
    public Response MemoryPoolMXBean() {
        List<MemoryPoolMXBean> memoryPoolMXBeans = ManagementFactory.getMemoryPoolMXBeans();
        return Response.Ok(memoryPoolMXBeans);
    }

    @ApiOperation(value = "MemoryManagerMXBean")
    @GetMapping(value = "/MemoryManagerMXBean")
    public Response MemoryManagerMXBean() {
        List<MemoryManagerMXBean> memoryManagerMXBeans = ManagementFactory.getMemoryManagerMXBeans();
        return Response.Ok(memoryManagerMXBeans);
    }

    /**
     * ((DefaultMBeanServerInterceptor) ((JmxMBeanServer) )mBeanServer.mbsInterceptor).repository
     */
    @ApiOperation(value = "MBeanServer:包含所有注册MBean的服务端")
    @GetMapping(value = "/MBeanServer")
    public Response MBeanServer() throws NoSuchFieldException, IllegalAccessException {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        String[] domains = mBeanServer.getDomains();//获取全部的域
        mBeanServer.getMBeanCount();
        //domainToMBean
        Map<String, Map<String, NamedObject>> domainTb = domainTb(mBeanServer);

        Map<String, Map<String, String>> result = new HashMap<>();
        //搜集信息(因为无法序列化)
        domainTb.forEach((key, map) -> {
            Map<String, String> tmp = new HashMap<>();
            map.forEach((k, v) -> {
                tmp.put(k, v.getName().getCanonicalKeyPropertyListString());
            });
            result.put(key, tmp);
        });
        return Response.Ok(result);
    }

    /**
     * 获取全部的域 -> MBean
     *
     * @param mBeanServer
     * @return
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private Map<String, Map<String, NamedObject>> domainTb(MBeanServer mBeanServer) throws NoSuchFieldException, IllegalAccessException {
        DefaultMBeanServerInterceptor mBeanServerInterceptor = ReflectUtil.getFieldValue(mBeanServer, "mbsInterceptor");
        Repository repository = ReflectUtil.getFieldValue(mBeanServerInterceptor, "repository");
        Map<String, Map<String, NamedObject>> domainTb = ReflectUtil.getFieldValue(repository, "domainTb");
        return domainTb;
    }

}
