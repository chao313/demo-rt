package demo.agent.bytebuddy;

import lombok.Data;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Data
public class TrackBak {
    private TrackBak fatherTrack;//上层
    private String uuid;
    private String className;//名称对象
    private String methodName;//方法名称
    private Integer parameterCount;//入参个数
    private Class<?>[] parameterTypes;//入参类型
    private Object[] args;//入参内容
    private Class<?> returnType;//出参类型
    private Object result;//出参内容
    private long start;//开始时间
    private long end;//结束时间
    private long cost;//结束时间

    private Object sourceObject;//原始代理对象
    private Method sourceMethod;//原始方法
    private Integer childTrackSize;//子层size 和 childTracks 保持一致
    private List<TrackBak> childTracks;//子层

    @Override
    public String toString() {
        return "Track{" +
                "className='" + className + '\'' +
                ", methodName='" + methodName + '\'' +
                ", parameterCount=" + parameterCount +
                ", parameterTypes=" + Arrays.toString(parameterTypes) +
                ", args=" + Arrays.toString(args) +
                ", returnType=" + returnType +
                ", result=" + result +
                ", start=" + start +
                ", end=" + end +
                ", childTracks=" + (childTracks == null ? "0" : childTracks.size()) +
                ", sourceObject=" + sourceObject +
                ", sourceMethod=" + sourceMethod +
                '}';
    }

    public static TrackBak build(Object thisObject,//当前对象
                                 Method method,//拦截方法
                                 Object[] args//参数
    ) {
        TrackBak track = new TrackBak();
        track.uuid = UUID.randomUUID().toString().replace("-", "");
        track.sourceMethod = method;
        track.sourceObject = thisObject;
        //
        track.className = thisObject.getClass().getName();
        track.methodName = method.getName();
        track.parameterCount = method.getParameterCount();
        track.parameterTypes = method.getParameterTypes();
        track.args = args;
        track.returnType = method.getReturnType();
        track.start = System.currentTimeMillis();
        return track;
    }


    public void exit(Object result,//执行结果
                     long end
    ) {
        this.result = result;
        this.end = end;
        this.cost = this.end - this.start;
    }

    public synchronized void addChild(TrackBak child) {
        if (null == this.childTracks) {
            this.childTracks = new ArrayList<>();
        }
        this.childTracks.add(child);
        if (childTrackSize == null) {
            childTrackSize = 0;
        }
        childTrackSize++;
    }

    public synchronized List<TrackBak> getChild() {
        if (null == this.childTracks) {
            this.childTracks = new ArrayList<>();
        }
        return this.childTracks;
    }

    public OutputStream getTreeStr(OutputStream outputStream) throws IOException {
        return getTreeStr(this, outputStream);
    }

    public static OutputStream getTreeStr(TrackBak track, OutputStream outputStream) throws IOException {
        StringBuilder str = new StringBuilder();
        print(outputStream, track, 0);
        return outputStream;
    }

    private static void print(OutputStream outputStream, TrackBak track, int level) throws IOException {
        for (int i = 0; i < level; i++) {
            outputStream.write("\t".getBytes());
        }
        outputStream.write((track.className + "#" + track.methodName + ":耗时:" + (track.getEnd() - track.getStart()) + "\n").getBytes());
        for (TrackBak childTrack : track.getChild()) {
            int levelNext = level + 1;
            print(outputStream, childTrack, levelNext);
        }
    }
}