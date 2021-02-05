package demo.rt.tools;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import io.swagger.models.auth.In;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import sun.jvmstat.monitor.MonitoredHost;
import sun.jvmstat.monitor.MonitoredVm;
import sun.jvmstat.monitor.MonitoredVmUtil;
import sun.jvmstat.monitor.VmIdentifier;

import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.*;

@Slf4j
public class VirtualMachineService {


    /**
     * 获取当前服务器的java的ip和main类
     */
    public static Map<String, Set<Integer>> getMainClassToPidsMap() throws Exception {
        Map<String, Set<Integer>> result = new HashMap<>();
        // 获取监控主机
        MonitoredHost local = MonitoredHost.getMonitoredHost("localhost");
        // 取得所有在活动的虚拟机集合
        Set<Integer> processList = new HashSet<>(local.activeVms());
        // 遍历集合，输出PID和进程名
        for (Integer process : processList) {
            MonitoredVm vm = local.getMonitoredVm(new VmIdentifier("//" + process));
            // 获取类名
            String processName = MonitoredVmUtil.mainClass(vm, true);
            if (result.containsKey(processName)) {
                result.get(processName).add(process);
            } else {
                Set<Integer> pids = new HashSet<>();
                pids.add(process);
                result.put(processName, pids);
            }
        }
        return result;
    }

    /**
     * https://www.cnblogs.com/zhengah/p/4962352.html
     * 使用attach方式连接到JVM上面去启动JMX
     * 开启指定进程的JMX,并返回地址
     */
    public static String openJMXAndGetUrl(Integer process) throws IOException, AgentLoadException, AgentInitializationException, AttachNotSupportedException {
        VirtualMachine virtualMachine = VirtualMachine.attach(process.toString());
        /**
         * 让JVM加载jmx Agent(JRE自带的)
         */
        String javaHome = virtualMachine.getSystemProperties().getProperty("java.home");
        String jmxAgent = javaHome + File.separator + "lib" + File.separator + "management-agent.jar";
        virtualMachine.loadAgent(jmxAgent, "com.sun.management.jmxremote");
        /**
         * 获得连接地址(JMX使用的,固定的key为com.sun.management.jmxremote.localConnectorAddress)
         * value大概为
         * service:jmx:rmi://127.0.0.1/stub/rO0ABXN9AAAAAQAlamF2YXgubWFuYWdlbWVudC5yZW1vdGUucm1pLlJNSVNlcnZlcnhyABdqYXZhLmxhbmcucmVmbGVjdC5Qcm94eeEn2iDMEEPLAgABTAABaHQAJUxqYXZhL2xhbmcvcmVmbGVjdC9JbnZvY2F0aW9uSGFuZGxlcjt4cHNyAC1qYXZhLnJtaS5zZXJ2ZXIuUmVtb3RlT2JqZWN0SW52b2NhdGlvbkhhbmRsZXIAAAAAAAAAAgIAAHhyABxqYXZhLnJtaS5zZXJ2ZXIuUmVtb3RlT2JqZWN002G0kQxhMx4DAAB4cHc5AAtVbmljYXN0UmVmMgAADjEwLjIwMC4xMjcuMTA2AADl3ox1va53/IvfOEX2EQAAAXds4v5jgAEAeA==
         */
        Properties properties = virtualMachine.getAgentProperties();
        String address = properties.get("com.sun.management.jmxremote.localConnectorAddress").toString();
        virtualMachine.detach();
        return address;
    }


    public static void monitor(Integer process) throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        String url = openJMXAndGetUrl(process);
        System.out.println("url:{}"+url);
        JMXServiceURL jmxServiceURL = new JMXServiceURL(url);
        JMXConnector connector = JMXConnectorFactory.connect(jmxServiceURL);
        RuntimeMXBean rmxb = ManagementFactory.newPlatformMXBeanProxy(connector
                .getMBeanServerConnection(), "java.lang:type=Runtime", RuntimeMXBean.class);
        log.info("");
    }


    @Test
    public void getIpToMainClassMapTest() throws Exception {
        Map<String, Set<Integer>> mainClassToPidsMap = getMainClassToPidsMap();
        log.info("ipToMainClassMap:{}", mainClassToPidsMap);

        Set<Integer> pids = mainClassToPidsMap.get("sun.tools.jconsole.JConsole");

        for (Integer pid : pids) {
            monitor(pid);
        }
    }

}
