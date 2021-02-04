package demo.rt.tools;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.stereotype.Service;

import javax.management.MBeanServerConnection;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.net.MalformedURLException;

@Slf4j
@Service
public class ManagementFactoryService {

    @Test
    public void xx() throws IOException {
        JMXServiceURL serviceURL = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://127.0.0.1:9999/jmxrmi");
        JMXConnector conn = JMXConnectorFactory.connect(serviceURL);
        MBeanServerConnection mbsc = conn.getMBeanServerConnection();
        log.info("");
    }
}
