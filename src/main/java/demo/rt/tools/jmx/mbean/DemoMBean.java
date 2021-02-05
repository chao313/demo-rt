package demo.rt.tools.jmx.mbean;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

/**
 * 需要开启:
 * spring.jmx.enabled=true
 * <p>
 * https://blog.csdn.net/inrgihc/article/details/104775481
 */
@Component
@ManagedResource(objectName = "demo.rt.tools.jmx.mbean.jmx:type=DemoMBean", description = "DemoMBean")
public class DemoMBean {

    private long id;
    private String name;
    private int age;

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    /**
     * 这里编写暴露方法
     */
    @ManagedOperation(description = "通过JMX可以执行的方法")
    public String display() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}