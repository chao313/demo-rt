package demo.rt.tools;

import com.sun.tools.attach.AgentInitializationException;
import com.sun.tools.attach.AgentLoadException;
import com.sun.tools.attach.AttachNotSupportedException;
import com.sun.tools.attach.VirtualMachine;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.File;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.Properties;

@Slf4j
public class VirtualMachineService {

    /**
     * https://www.cnblogs.com/zhengah/p/4962352.html
     * <p>
     * 使用attach方式连接到JVM上面去启动JMX
     */
    @Test
    public void VirtualMachineAttach() throws IOException, AttachNotSupportedException, AgentLoadException, AgentInitializationException {
        VirtualMachine virtualMachine = VirtualMachine.attach("11256");
        /**
         * 获取进程对应的系统配置
         */
        Properties systemProperties = virtualMachine.getSystemProperties();
        Properties agentProperties = virtualMachine.getAgentProperties();
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
        String address = (String) properties.get("com.sun.management.jmxremote.localConnectorAddress");
        /**
         * Detach
         */
        virtualMachine.detach();
        JMXServiceURL url = new JMXServiceURL(address);
        JMXConnector connector = JMXConnectorFactory.connect(url);
        RuntimeMXBean rmxb = ManagementFactory.newPlatformMXBeanProxy(connector
                .getMBeanServerConnection(), "java.lang:type=Runtime", RuntimeMXBean.class);
        log.info("");
    }

}
