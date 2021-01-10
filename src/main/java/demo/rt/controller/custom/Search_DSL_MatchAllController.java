package demo.rt.controller.custom;

import com.alibaba.fastjson.JSONObject;
import demo.rt.config.Bootstrap;
import demo.rt.config.web.CustomInterceptConfig;
import demo.rt.feign.SearchMatchAllService;
import demo.rt.framework.Response;
import demo.rt.po.request.SearchSourceBuilder;
import demo.rt.po.request.aggs.VoidAggs;
import demo.rt.po.request.dsl.matchall.MatchAllQuery;
import demo.rt.thread.ThreadLocalFeign;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;


/**
 * 检索相关(match_all 语法)
 */
@RequestMapping(value = "/Search_DSL_MatchAllController")
@RestController
public class Search_DSL_MatchAllController {

    @ApiOperation(value = "match all检索")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @PostMapping(value = "/{index}/_search")
    public Response _search(@PathVariable(value = "index") String index,
                            @RequestBody SearchSourceBuilder<MatchAllQuery, VoidAggs> matchAllRequest) {
        SearchMatchAllService searchMatchAllService = ThreadLocalFeign.getFeignService(SearchMatchAllService.class);
        String result = searchMatchAllService.match_all_search(index, matchAllRequest);
        return Response.Ok(JSONObject.parse(result));
    }

}
















