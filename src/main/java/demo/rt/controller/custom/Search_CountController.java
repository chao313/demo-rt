package demo.rt.controller.custom;

import com.alibaba.fastjson.JSONObject;
import demo.rt.config.Bootstrap;
import demo.rt.config.web.CustomInterceptConfig;
import demo.rt.feign.CountService;
import demo.rt.framework.Response;
import demo.rt.thread.ThreadLocalFeign;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;


/**
 * 检索相关(EQL语法)
 */
@RequestMapping(value = "/Search_EQLController")
@RestController
public class Search_CountController {

    @ApiOperation(value = "只查询匹配到的数量", notes = "```" +
            "\n" +
            "{\n" +
            " \"query\": {\n" +
            "  \"bool\": {\n" +
            "   \"must\":[\n" +
            "    {\"exists\": {\"field\": \"age\"}},\n" +
            "    {\"exists\": {\"field\": \"address\"}},\n" +
            "    {\"term\": {\"age\": {\n" +
            "       \"boost\": 0,\n" +
            "       \"value\": 32\n" +
            "      }}\n" +
            "    }\n" +
            "   ]\n" +
            "  }\n" +
            " }\n" +
            "}" +
            "```")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/{index}/_count")
    public Response _search(@ApiParam(defaultValue = "tb_object_0088") @PathVariable(value = "index") String index,
                            @RequestBody String body) {
        CountService countService = ThreadLocalFeign.getFeignService(CountService.class);
        String result = countService._count(index, body);
        return Response.Ok(JSONObject.parseObject(result));
    }

}
















