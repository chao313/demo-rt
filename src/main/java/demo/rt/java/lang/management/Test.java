package demo.rt.java.lang.management;

import sun.tools.jps.Jps;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

public class Test {

    public static void main(String[] args) {
//        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
//        runtimeMXBean.getName();
        Jps.main(args);
    }
}
