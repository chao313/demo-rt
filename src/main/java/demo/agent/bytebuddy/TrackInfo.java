package demo.agent.bytebuddy;

import lombok.Data;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class TrackInfo {

    private String className;//名称对象
    private String methodName;//方法名称
    private Integer parameterCount;//入参个数
    private List<String> parameterTypes;//入参类型
    private Object[] args;//入参内容
    private List<String> returnType;//出参类型
    private Object result;//出参内容
    private long start;//开始时间
    private long end;//结束时间
    private long cost;//结束时间

    private TrackInfo fatherTrackInfo;//上层
    private List<TrackInfo> childTrackInfos;//子层
    private Integer childTrackSize;//子层size 和 childTracks 保持一致

    private String sourceObject;//原始代理对象
    private String sourceMethod;//原始方法


    public static TrackInfo build(Track track) {
        TrackInfo trackInfo = new TrackInfo();
        clone(track, trackInfo);
        return trackInfo;
    }

    private static void clone(Track track, TrackInfo trackInfo) {
        BeanUtils.copyProperties(track, trackInfo);
        trackInfo.setSourceObject(track.getSourceObject().toString());
        trackInfo.setSourceMethod(track.getSourceMethod().getName());
        track.getChild().forEach(child -> {
            TrackInfo trackChild = new TrackInfo();
            trackInfo.getChildTrackInfos().add(trackChild);
//            trackChild.setFatherTrackInfo(trackInfo);
            clone(child, trackChild);
        });
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Integer getParameterCount() {
        return parameterCount;
    }

    public void setParameterCount(Integer parameterCount) {
        this.parameterCount = parameterCount;
    }

    public List<String> getParameterTypes() {
        return parameterTypes;
    }

    public void setParameterTypes(List<String> parameterTypes) {
        this.parameterTypes = parameterTypes;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }

    public List<String> getReturnType() {
        return returnType;
    }

    public void setReturnType(List<String> returnType) {
        this.returnType = returnType;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public long getStart() {
        return start;
    }

    public void setStart(long start) {
        this.start = start;
    }

    public long getEnd() {
        return end;
    }

    public void setEnd(long end) {
        this.end = end;
    }

    public long getCost() {
        return cost;
    }

    public void setCost(long cost) {
        this.cost = cost;
    }

    public TrackInfo getFatherTrackInfo() {
        return fatherTrackInfo;
    }

    public void setFatherTrackInfo(TrackInfo fatherTrackInfo) {
        this.fatherTrackInfo = fatherTrackInfo;
    }

    public synchronized List<TrackInfo> getChildTrackInfos() {
        if (null == this.childTrackInfos) {
            this.childTrackInfos = new ArrayList<>();
        }
        return this.childTrackInfos;
    }

    public void setChildTrackInfos(List<TrackInfo> childTrackInfos) {
        this.childTrackInfos = childTrackInfos;
    }

    public Integer getChildTrackSize() {
        return childTrackSize;
    }

    public void setChildTrackSize(Integer childTrackSize) {
        this.childTrackSize = childTrackSize;
    }

    public String getSourceObject() {
        return sourceObject;
    }

    public void setSourceObject(String sourceObject) {
        this.sourceObject = sourceObject;
    }

    public String getSourceMethod() {
        return sourceMethod;
    }

    public void setSourceMethod(String sourceMethod) {
        this.sourceMethod = sourceMethod;
    }
}