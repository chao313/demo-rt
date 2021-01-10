package demo.rt.controller.custom;

import com.alibaba.fastjson.JSONObject;
import demo.rt.config.Bootstrap;
import demo.rt.config.web.CustomInterceptConfig;
import demo.rt.feign.ScriptService;
import demo.rt.feign.SearchService;
import demo.rt.framework.Response;
import demo.rt.thread.ThreadLocalFeign;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;


/**
 * 检索相关(Script相关)
 */
@RequestMapping(value = "/Search_ScriptController")
@RestController
public class Search_ScriptController {

    @ApiOperation(value = "创建一个Script")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/_scripts/{id}")
    public Response add(
            @PathVariable(value = "id") String id,
            @RequestBody String body) {
        ScriptService scriptService = ThreadLocalFeign.getFeignService(ScriptService.class);
        String s = scriptService.add(id, body);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "查看一个Script")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @GetMapping(value = "/_scripts/{id}")
    public Response get(@PathVariable(value = "id") String id) {
        ScriptService scriptService = ThreadLocalFeign.getFeignService(ScriptService.class);
        String s = scriptService.get(id);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "删除一个Script")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @DeleteMapping(value = "/_scripts/{id}")
    public Response delete(@PathVariable(value = "id") String id) {
        ScriptService scriptService = ThreadLocalFeign.getFeignService(ScriptService.class);
        String s = scriptService.del(id);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiOperation(value = "查询一个index的type", notes = "```" +
            "\n" +
            "{\n" +
            " \"script_fields\": {\n" +
            "  \"my_doubled_field\": {\n" +
            "    \"script\": {\n" +
            "      \"id\": \"MyScriptsId\",\n" +
            "      \"params\": {\n" +
            "        \"multiplier\": 20\n" +
            "      }\n" +
            "    }\n" +
            "   }\n" +
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
    @PostMapping(value = "/{index}/_search")
    public Response _search(
//            @PathVariable(value = "index") String index,
            @RequestParam(defaultValue = "comstore_tb_object_0088") String indexTmp,
            @RequestBody String body) {
        SearchService searchService = ThreadLocalFeign.getFeignService(SearchService.class);
        String s = searchService._search(indexTmp, body);
        return Response.Ok(JSONObject.parse(s));
    }

}
















