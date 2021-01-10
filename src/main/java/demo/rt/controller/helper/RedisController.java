package demo.rt.controller.helper;

import demo.rt.framework.Response;
import demo.rt.service.RedisService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * redis专门使用的 用于分页使用
 */
@Slf4j
@RequestMapping(value = "/RedisController")
@RestController
public class RedisController {


    @Autowired
    private RedisService redisService;

    @ApiOperation(value = "根据ScrollId获取数据(分页数据)")
    @GetMapping(value = "/getRecordByScrollId")
    public Object getRecordByScrollId(
            @RequestParam(name = "scrollId", defaultValue = "")
                    String scrollId,
            @RequestParam(value = "pageNum", defaultValue = "1", required = false)
                    Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false)
                    Integer pageSize
    ) {

        return Response.Ok(redisService.getRecordByScrollId(scrollId, pageNum, pageSize));
    }
}
