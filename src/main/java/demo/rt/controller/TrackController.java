package demo.rt.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import demo.agent.bytebuddy.MonitorTrack;
import demo.agent.bytebuddy.Track;
import demo.agent.bytebuddy.TrackInfo;
import demo.rt.config.framework.Response;
import demo.rt.service.ToolService;
import demo.rt.util.DateUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * 链路追踪
 */
@Slf4j
@RestController
@RequestMapping(value = "/TrackController")
public class TrackController {

    @Autowired
    private ToolService toolService;

    /**
     * 获取链路追踪的根节点
     */
    @ApiOperation(value = " 获取链路追踪的根节点")
    @GetMapping(value = "/getRootTrack")
    public Response getRootTrack() throws JsonProcessingException {
        List<TrackInfo> trackInfos = new ArrayList<>();
        MonitorTrack.rootTracks.forEach(track -> {
            if ((track.getClassName() + "#" + track.getMethodName()).equals("demo.rt.controller.system.TrackController#getRootTrack")) {
                //必须要移除自己:否则会是循环
                return;
            }
            trackInfos.add(TrackInfo.build(track));
        });
        return Response.Ok(trackInfos);
    }

    @ApiOperation(value = " 获取链路追踪的根节点test")
    @GetMapping(value = "/trackTest")
    public Response trackTest(@ApiParam(value = "param") String param) throws UnknownHostException {
        DateUtil.getNow();
        Thread.currentThread().getStackTrace();
        return Response.Ok(toolService.getSystemEnvInfo());
    }

    /**
     * 获取链路追踪的根节点
     */
    @ApiOperation(value = " 获取链路追踪的根节点")
    @GetMapping(value = "/getRootTrackByKey")
    public Response getRootTrackByKey(@ApiParam(value = "param") @RequestParam(name = "param") String key) throws JsonProcessingException {

        List<TrackInfo> trackInfos = new ArrayList<>();
        MonitorTrack.mapTracks.get(key).forEach(track -> {
            trackInfos.add(TrackInfo.build(track));
        });
        return Response.Ok(trackInfos);
    }

    /**
     * 获取链路追踪节点 根据UUID
     */
    @ApiOperation(value = "获取链路追踪节点,根据UUID")
    @GetMapping(value = "/getTrackByUUID")
    public Response getTrackByUUID(@ApiParam(value = "UUID") @RequestParam(name = "UUID") String UUID) throws JsonProcessingException {
        Track track = MonitorTrack.mapTrack.get(UUID);
        return Response.Ok(TrackInfo.build(track));
    }

    /**
     * 获取链路追踪节点 根据UUID
     */
    @ApiOperation(value = "执行链路追踪节点,根据UUID")
    @GetMapping(value = "/execTrackByUUID")
    public Response execTrackByUUID(@ApiParam(value = "UUID") @RequestParam(name = "UUID") String UUID) throws JsonProcessingException, InvocationTargetException, IllegalAccessException {
        Track track = MonitorTrack.mapTrack.get(UUID);
        Method method = track.getSourceMethod();
        Object object = track.getSourceObject();
        method.setAccessible(true);
        Object invokeResult = method.invoke(object, track.getArgs());
        return Response.Ok(invokeResult);
    }
}
