package demo.rt.controller.custom;

import com.alibaba.fastjson.JSONObject;
import demo.rt.config.Bootstrap;
import demo.rt.config.web.CustomInterceptConfig;
import demo.rt.feign.SearchService;
import demo.rt.framework.Response;
import demo.rt.po.request.SearchSourceBuilder;
import demo.rt.po.request.aggs.VoidAggs;
import demo.rt.po.request.dsl.compound.BoolQuery;
import demo.rt.thread.ThreadLocalFeign;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;


/**
 * 检索相关(DSL 组合语法)
 */
@RequestMapping(value = "/Search_DSL_CompoundController")
@RestController
public class Search_DSL_CompoundController {

    @ApiOperation(value = "bool查询")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/DSL/bool/{index}/_search")
    public Response _search(@PathVariable(value = "index") String index, @RequestBody SearchSourceBuilder<BoolQuery, VoidAggs> boolRequest) {
        SearchService searchService = ThreadLocalFeign.getFeignService(SearchService.class);
        String result = searchService.DSL_search_bool(index, boolRequest);
        return Response.Ok(JSONObject.parse(result));
    }

}
















