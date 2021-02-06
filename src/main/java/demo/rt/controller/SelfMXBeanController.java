package demo.rt.controller;

import com.sun.jmx.interceptor.DefaultMBeanServerInterceptor;
import com.sun.jmx.mbeanserver.NamedObject;
import com.sun.jmx.mbeanserver.Repository;
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
@RequestMapping(value = "/SelfMXBeanController")
public class SelfMXBeanController {

    MXBeanInterface mxBeanInterface = MXBeanInterface.getSelfMXBean();

    @ApiOperation(value = "ClassLoadingMXBean")
    @GetMapping(value = "/ClassLoadingMXBean")
    public Response ClassLoadingMXBean() throws IOException {
        ClassLoadingMXBean classLoadingMXBean = mxBeanInterface.getClassLoadingMXBean();
        return Response.Ok(classLoadingMXBean);
    }

    @ApiOperation(value = "CompilationMXBean")
    @GetMapping(value = "/CompilationMXBean")
    public Response CompilationMXBean() throws IOException {
        CompilationMXBean compilationMXBean = mxBeanInterface.getCompilationMXBean();
        return Response.Ok(compilationMXBean);
    }

    @ApiOperation(value = "MemoryMXBean")
    @GetMapping(value = "/MemoryMXBean")
    public Response MemoryMXBean() throws IOException {
        MemoryMXBean memoryMXBean = mxBeanInterface.getMemoryMXBean();
        return Response.Ok(memoryMXBean);
    }

    @ApiOperation(value = "RuntimeMXBean")
    @GetMapping(value = "/RuntimeMXBean")
    public Response RuntimeMXBean() throws IOException {
        RuntimeMXBean runtimeMXBean = mxBeanInterface.getRuntimeMXBean();
        return Response.Ok(runtimeMXBean);
    }

    @ApiOperation(value = "ThreadMXBean")
    @GetMapping(value = "/ThreadMXBean")
    public Response ThreadMXBean() throws IOException {
        ThreadMXBean threadMXBean = mxBeanInterface.getThreadMXBean();
        return Response.Ok(threadMXBean);
    }

    @ApiOperation(value = "OperatingSystemMXBean")
    @GetMapping(value = "/OperatingSystemMXBean")
    public Response OperatingSystemMXBean() throws IOException {
        OperatingSystemMXBean operatingSystemMXBean = mxBeanInterface.getOperatingSystemMXBean();
        return Response.Ok(operatingSystemMXBean);
    }


    @ApiOperation(value = "GarbageCollectorMXBean")
    @GetMapping(value = "/GarbageCollectorMXBean")
    public Response GarbageCollectorMXBean() throws IOException {
        List<GarbageCollectorMXBean> garbageCollectorMXBeans = mxBeanInterface.getGarbageCollectorMXBeans();
        return Response.Ok(garbageCollectorMXBeans);
    }

    @ApiOperation(value = "MemoryPoolMXBean")
    @GetMapping(value = "/MemoryPoolMXBean")
    public Response MemoryPoolMXBean() {
        List<MemoryPoolMXBean> memoryPoolMXBeans = mxBeanInterface.getMemoryPoolMXBeans();
        return Response.Ok(memoryPoolMXBeans);
    }

    @ApiOperation(value = "MemoryManagerMXBean")
    @GetMapping(value = "/MemoryManagerMXBean")
    public Response MemoryManagerMXBean() {
        List<MemoryManagerMXBean> memoryManagerMXBeans = mxBeanInterface.getMemoryManagerMXBeans();
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
