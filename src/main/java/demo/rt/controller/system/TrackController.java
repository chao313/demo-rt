package demo.rt.controller.system;

import com.fasterxml.jackson.core.JsonProcessingException;
import demo.agent.bytebuddy.MonitorTrack;
import demo.agent.bytebuddy.TrackInfo;
import demo.rt.framework.Response;
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
        MonitorTrack.tracks.forEach(track -> {
            trackInfos.add(TrackInfo.build(track));
        });
        return Response.Ok(trackInfos);
    }

    @ApiOperation(value = " 获取链路追踪的根节点test")
    @GetMapping(value = "/trackTest")
    public Response trackTest(@ApiParam(value = "param") String param) throws UnknownHostException {
        DateUtil.getNow();
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
}
