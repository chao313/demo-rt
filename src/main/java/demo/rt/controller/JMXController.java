package demo.rt.controller;

import com.sun.tools.hat.internal.model.Snapshot;
import com.sun.tools.hat.internal.parser.HprofReader;
import com.sun.tools.hat.internal.parser.PositionDataInputStream;
import com.sun.tools.hat.internal.parser.ReadBuffer;
import demo.rt.config.framework.Response;
import demo.rt.tools.jmx.VirtualMachineUtil;
import demo.rt.tools.jmx.properties.hprof.SnapshotVo;
import demo.rt.util.ReflectUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.IOException;
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

    @ApiOperation(value = "解析 hprof")
    @GetMapping(value = "/parse_HeapDump")
    public Response parse_HeapDump(@ApiParam(value = "转储的文件地址") @RequestParam(value = "outputFile") String outputFile)
            throws IOException, NoSuchFieldException, IllegalAccessException {
        Snapshot snapshot = HprofReader.readFile(outputFile, true, 100);
        snapshot.resolve(true);
        SnapshotVo snapshotVo = SnapshotVo.builder(snapshot);
        return Response.Ok(true);
    }


}
