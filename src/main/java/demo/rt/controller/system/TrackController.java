package demo.rt.controller.system;

import com.fasterxml.jackson.core.JsonProcessingException;
import demo.agent.bytebuddy.MonitorTrack;
import demo.agent.bytebuddy.TrackInfo;
import demo.rt.framework.Response;
import demo.rt.service.ToolService;
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
//    @ApiOperation(value = " 获取链路追踪的根节点")
//    @GetMapping(value = "/getRootTrack")
//    public Response getRootTrack() throws JsonProcessingException {
//        List<MonitorTrack.Track> list = new ArrayList<>();
//        MonitorTrack.tracks.forEach(track -> {
//            MonitorTrack.Track tmp = new MonitorTrack.Track();
////            BeanUtils.copyProperties(track, tmp);
//            tmp.setClassName(track.getClassName());
//            tmp.setParameterCount(track.getParameterCount());
//            tmp.setArgs(track.getArgs());
//            tmp.setMethodName(track.getMethodName());
//            tmp.setStart(track.getStart());
//            tmp.setEnd(track.getEnd());
//            tmp.setCost(track.getCost());
//            tmp.setParameterTypes(null);
//            tmp.setParameterTypesStr(track.getParameterTypesStr());
//            tmp.setReturnType(track.getReturnType());
//            tmp.setReturnType(null);
//            tmp.setChildTrackSize(track.getChildTrackSize());
//            list.add(tmp);
//        });
////        return Response.Ok(com.alibaba.fastjson.JSON.toJSON(list));
////        JsonMapper jsonMapper = new JsonMapper();
////        jsonMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
////        jsonMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
////        String body = jsonMapper.writeValueAsString(list);
//        return Response.Ok(list);
//    }
    @ApiOperation(value = " 获取链路追踪的根节点")
    @GetMapping(value = "/trackTest")
    public Response trackTest(@ApiParam(value = "param") String param) throws UnknownHostException {
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
