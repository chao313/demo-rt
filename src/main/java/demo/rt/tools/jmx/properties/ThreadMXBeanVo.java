package demo.rt.tools.jmx.properties;

import demo.rt.util.StackTraceUtils;
import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Data
public class ThreadMXBeanVo {
    private int daemonThreadCount;//守护线程数
    private int peakThreadCount;//峰值线程数
    private int threadCount;//活动线程数
    private long totalStartedThreadCount;//启动的线程总数
    private long[] allThreadIds;//所有线程id

    private boolean currentThreadCpuTimeSupported;
    private boolean objectMonitorUsageSupported;
    private boolean synchronizerUsageSupported;
    private boolean threadAllocatedMemoryEnabled;
    private boolean threadAllocatedMemorySupported;
    private boolean threadContentionMonitoringEnabled;
    private boolean threadContentionMonitoringSupported;
    private boolean threadCpuTimeEnabled;
    private long currentThreadCpuTime;
    private long currentThreadUserTime;
    private boolean threadCpuTimeSupported;
    private ObjectName objectName = new ObjectName();
    //add
    private ThreadInfo[] threadInfos; //所有的线程
    private Map<String, ThreadInfo> threadInfosMap; //所有的线程的Map
    private Map<String, String> threadInfosMapStr; //所有的线程的Map,str
    private Map<String, Long> threadInfosCpuTime; //所有的线程的Map,cpu时间
    private ThreadInfo[] threadInfosDump; //所有的线程的快照


    public static ThreadMXBeanVo build(ThreadMXBean threadMXBean) {
        ThreadMXBeanVo vo = new ThreadMXBeanVo();
        vo.daemonThreadCount = threadMXBean.getDaemonThreadCount();//守护线程数
        vo.peakThreadCount = threadMXBean.getPeakThreadCount();//峰值线程数
        vo.threadCount = threadMXBean.getThreadCount();//活动线程数
        vo.totalStartedThreadCount = threadMXBean.getTotalStartedThreadCount();//启动的线程总数
        vo.allThreadIds = threadMXBean.getAllThreadIds();//所有线程id
        //当前线程的cpu时间
        vo.currentThreadCpuTime = threadMXBean.getCurrentThreadCpuTime();
        vo.currentThreadUserTime = threadMXBean.getCurrentThreadUserTime();
        //
        vo.currentThreadCpuTimeSupported = threadMXBean.isCurrentThreadCpuTimeSupported();
        vo.objectMonitorUsageSupported = threadMXBean.isObjectMonitorUsageSupported();
        vo.synchronizerUsageSupported = threadMXBean.isSynchronizerUsageSupported();
        vo.threadAllocatedMemoryEnabled = threadMXBean.isThreadContentionMonitoringEnabled();
        vo.threadAllocatedMemorySupported = threadMXBean.isThreadContentionMonitoringSupported();
        vo.threadContentionMonitoringEnabled = threadMXBean.isThreadContentionMonitoringEnabled();
        vo.threadContentionMonitoringSupported = threadMXBean.isThreadContentionMonitoringSupported();
        vo.threadCpuTimeEnabled = threadMXBean.isThreadCpuTimeEnabled();
        vo.threadCpuTimeSupported = threadMXBean.isThreadCpuTimeSupported();
        BeanUtils.copyProperties(threadMXBean.getObjectName(), vo.objectName);
        //获取全部线程信息
        vo.threadInfos = threadMXBean.getThreadInfo(threadMXBean.getAllThreadIds(), Integer.MAX_VALUE);
        vo.threadInfosMap = transform(vo.threadInfos);
        vo.threadInfosMapStr = transform(vo.threadInfosMap);
        vo.threadInfosCpuTime = getThreadCpuTime(vo.threadInfos, threadMXBean);
        //获取全部线程的快照（监控锁:消耗比较大）
        vo.threadInfosDump = threadMXBean.dumpAllThreads(true, true);
        return vo;
    }

    /**
     * 转换成map
     */
    private static Map<String, ThreadInfo> transform(ThreadInfo[] threadInfos) {
        Map<String, ThreadInfo> threadInfosMap = new HashMap<>();
        Arrays.stream(threadInfos).forEach(threadInfo -> {
            threadInfosMap.put(threadInfo.getThreadName(), threadInfo);
        });
        return threadInfosMap;
    }

    /**
     * 转换成map 字符串的格式
     */
    private static Map<String, String> transform(Map<String, ThreadInfo> threadInfosMap) {
        Map<String, String> threadInfosMapStr = new HashMap<>();
        threadInfosMap.forEach((key, threadInfo) -> {
            String stackMsg = StackTraceUtils.getStackMsg(threadInfo.getStackTrace());
            threadInfosMapStr.put(key, stackMsg);
        });
        return threadInfosMapStr;
    }

    /**
     * 转换成map 字符串的格式
     */
    private static Map<String, Long> getThreadCpuTime(ThreadInfo[] threadInfos, ThreadMXBean threadMXBean) {
        Map<String, Long> threadInfosMapCpuTime = new HashMap<>();
        Arrays.stream(threadInfos).forEach(threadInfo -> {
            long threadCpuTime = threadMXBean.getThreadCpuTime(threadInfo.getThreadId());
            threadInfosMapCpuTime.put(threadInfo.getThreadName(), threadCpuTime);
        });
        return threadInfosMapCpuTime;
    }
}
/**
 * <pre>
 *     {
 *     "objectName": {
 *       "canonicalName": "java.lang:type=Threading",
 *       "canonicalKeyPropertyListString": "type=Threading",
 *       "domain": "java.lang",
 *       "keyPropertyList": {
 *         "type": "Threading"
 *       },
 *       "keyPropertyListString": "type=Threading",
 *       "domainPattern": false,
 *       "pattern": false,
 *       "propertyListPattern": false,
 *       "propertyPattern": false,
 *       "propertyValuePattern": false
 *     },
 *     "daemonThreadCount": 18,
 *     "peakThreadCount": 22,
 *     "currentThreadCpuTimeSupported": true,
 *     "objectMonitorUsageSupported": true,
 *     "synchronizerUsageSupported": true,
 *     "threadAllocatedMemoryEnabled": true,
 *     "threadAllocatedMemorySupported": true,
 *     "threadContentionMonitoringEnabled": false,
 *     "threadContentionMonitoringSupported": true,
 *     "threadCpuTimeEnabled": true,
 *     "allThreadIds": [
 *       36,
 *       2
 *     ],
 *     "currentThreadCpuTime": 15600100,
 *     "currentThreadUserTime": 15600100,
 *     "threadCount": 22,
 *     "totalStartedThreadCount": 27,
 *     "threadCpuTimeSupported": true
 *    }
 * </pre>
 */