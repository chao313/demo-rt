package demo.rt.controller;

import demo.rt.config.framework.Response;
import demo.rt.tools.jmx.VirtualMachineUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Set;

/**
 * 链路追踪
 */
@Slf4j
@RestController
@RequestMapping(value = "/JMXController")
public class JMXController {


    @ApiOperation(value = "获取当前主机的全部java进程")
    @GetMapping(value = "/getMainClassToPidsMap")
    public Response getMainClassToPidsMap() throws Exception {
        Map<String, Set<Integer>> mainClassToPidsMap = VirtualMachineUtil.getMainClassToPidsMap();
        return Response.Ok(mainClassToPidsMap);
    }

    @ApiOperation(value = "开启并获取指定java进程的JMX")
    @GetMapping(value = "/openJMXAndGetUrl")
    public Response openJMXAndGetUrl(Integer process) throws Exception {
        String url = VirtualMachineUtil.openJMXAndGetUrl(process);
        return Response.Ok(url);
    }


}
