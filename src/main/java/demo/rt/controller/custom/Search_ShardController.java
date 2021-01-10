package demo.rt.controller.custom;

import com.alibaba.fastjson.JSONObject;
import demo.rt.config.Bootstrap;
import demo.rt.config.web.CustomInterceptConfig;
import demo.rt.feign.SearchService;
import demo.rt.framework.Response;
import demo.rt.thread.ThreadLocalFeign;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 检索相关(Shard相关)
 */
@RequestMapping(value = "/Search_ShardController")
@RestController
public class Search_ShardController {

    @ApiOperation(value = "验证搜索的代价")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @GetMapping(value = "/{index}/_search_shards")
    public Response _validate(
            @ApiParam(defaultValue = "tb_object_0088")
            @PathVariable(value = "index") String index) {
        SearchService searchService = ThreadLocalFeign.getFeignService(SearchService.class);
        String result = searchService._search_shards(index);
        return Response.Ok(JSONObject.parse(result));
    }
}
















