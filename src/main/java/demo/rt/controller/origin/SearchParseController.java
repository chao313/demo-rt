package demo.rt.controller.origin;

import com.alibaba.fastjson.JSONObject;
import demo.rt.config.Bootstrap;
import demo.rt.config.web.CustomInterceptConfig;
import demo.rt.out.remove.compound.Body;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 解析查询使用
 */
@RequestMapping(value = "/origin/SearchParseController")
@RestController
@Slf4j
public class SearchParseController {

    @ApiOperation(value = "解析成标准DSL查询语句")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/parse")
    public JSONObject _search(
            @RequestBody Body body) {
        log.info("解析的json:{}", body.parse());
        return JSONObject.parseObject(body.parse());
    }
}
