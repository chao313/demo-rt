package demo.rt.controller;

import com.sun.jmx.interceptor.DefaultMBeanServerInterceptor;
import com.sun.jmx.mbeanserver.NamedObject;
import com.sun.jmx.mbeanserver.Repository;
import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import demo.rt.config.framework.Response;
import demo.rt.tools.jmx.MXBeanInterface;
import demo.rt.util.ReflectUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.management.MBeanServer;
import java.io.IOException;
import java.lang.management.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 链路追踪
 */
@Slf4j
@RestController
@RequestMapping(value = "/LocalMXBeanController")
public class JMXMXBeanController {


    @ApiOperation(value = "ClassLoadingMXBean")
    @GetMapping(value = "/ClassLoadingMXBean")
    public Response ClassLoadingMXBean(Integer pid) throws AgentInitializationException, AgentLoadException, AttachNotSupportedException, IOException {
        ClassLoadingMXBean classLoadingMXBean = MXBeanInterface.getJMXMXBean(pid).getClassLoadingMXBean();
        return Response.Ok(classLoadingMXBean);
    }

    @ApiOperation(value = "CompilationMXBean")
    @GetMapping(value = "/CompilationMXBean")
    public Response CompilationMXBean(Integer pid) throws AgentInitializationException, AgentLoadException, AttachNotSupportedException, IOException {
        CompilationMXBean compilationMXBean = MXBeanInterface.getJMXMXBean(pid).getCompilationMXBean();
        return Response.Ok(compilationMXBean);
    }

    @ApiOperation(value = "MemoryMXBean")
    @GetMapping(value = "/MemoryMXBean")
    public Response MemoryMXBean(Integer pid) throws AgentInitializationException, AgentLoadException, AttachNotSupportedException, IOException {
        MemoryMXBean memoryMXBean = MXBeanInterface.getJMXMXBean(pid).getMemoryMXBean();
        return Response.Ok(memoryMXBean);
    }

    @ApiOperation(value = "RuntimeMXBean")
    @GetMapping(value = "/RuntimeMXBean")
    public Response RuntimeMXBean(Integer pid) throws AgentInitializationException, AgentLoadException, AttachNotSupportedException, IOException {
        RuntimeMXBean runtimeMXBean = MXBeanInterface.getJMXMXBean(pid).getRuntimeMXBean();
        return Response.Ok(runtimeMXBean);
    }

    @ApiOperation(value = "ThreadMXBean")
    @GetMapping(value = "/ThreadMXBean")
    public Response ThreadMXBean(Integer pid) throws AgentInitializationException, AgentLoadException, AttachNotSupportedException, IOException {
        ThreadMXBean threadMXBean = MXBeanInterface.getJMXMXBean(pid).getThreadMXBean();
        return Response.Ok(threadMXBean);
    }

    @ApiOperation(value = "OperatingSystemMXBean")
    @GetMapping(value = "/OperatingSystemMXBean")
    public Response OperatingSystemMXBean(Integer pid) throws AgentInitializationException, AgentLoadException, AttachNotSupportedException, IOException {
        OperatingSystemMXBean operatingSystemMXBean = MXBeanInterface.getJMXMXBean(pid).getOperatingSystemMXBean();
        return Response.Ok(operatingSystemMXBean);
    }


    @ApiOperation(value = "GarbageCollectorMXBean")
    @GetMapping(value = "/GarbageCollectorMXBean")
    public Response GarbageCollectorMXBean(Integer pid) throws AgentInitializationException, AgentLoadException, AttachNotSupportedException, IOException {
        List<GarbageCollectorMXBean> garbageCollectorMXBeans = MXBeanInterface.getJMXMXBean(pid).getGarbageCollectorMXBeans();
        return Response.Ok(garbageCollectorMXBeans);
    }

    @ApiOperation(value = "MemoryPoolMXBean")
    @GetMapping(value = "/MemoryPoolMXBean")
    public Response MemoryPoolMXBean(Integer pid) throws AgentInitializationException, AgentLoadException, AttachNotSupportedException, IOException {
        List<MemoryPoolMXBean> memoryPoolMXBeans = MXBeanInterface.getJMXMXBean(pid).getMemoryPoolMXBeans();
        return Response.Ok(memoryPoolMXBeans);
    }

    @ApiOperation(value = "MemoryManagerMXBean")
    @GetMapping(value = "/MemoryManagerMXBean")
    public Response MemoryManagerMXBean(Integer pid) throws AgentInitializationException, AgentLoadException, AttachNotSupportedException, IOException {
        List<MemoryManagerMXBean> memoryManagerMXBeans = MXBeanInterface.getJMXMXBean(pid).getMemoryManagerMXBeans();
        return Response.Ok(memoryManagerMXBeans);
    }

    /**
     * ((DefaultMBeanServerInterceptor) ((JmxMBeanServer) )mBeanServer.mbsInterceptor).repository
     */
    @ApiOperation(value = "MBeanServer:包含所有注册MBean的服务端")
    @GetMapping(value = "/MBeanServer")
    public Response MBeanServer(Integer pid) throws AgentInitializationException, AgentLoadException, AttachNotSupportedException, IOException, NoSuchFieldException, IllegalAccessException {
        MBeanServer mBeanServer = MXBeanInterface.getJMXMXBean(pid).getPlatformMBeanServer();
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
