package demo.rt.controller.custom;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import demo.rt.config.Bootstrap;
import demo.rt.config.web.CustomInterceptConfig;
import demo.rt.feign.CatService;
import demo.rt.feign.IndexService;
import demo.rt.feign.enums.FormatEnum;
import demo.rt.framework.Response;
import demo.rt.thread.ThreadLocalFeign;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;


/**
 * 索引 recovery相关
 */
@RequestMapping(value = "/Index_RecoveryController")
@RestController
public class Index_RecoveryController {

    @ApiOperation(value = "返回有关正在进行和已完成的碎片恢复的信息")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @RequestMapping(value = "/{index}/_recovery", method = RequestMethod.GET)
    public Response _recovery(@ApiParam(value = "索引名称(要操作所有索引，请使用_all)") @PathVariable(value = "index") String index) {
        IndexService indexService = ThreadLocalFeign.getFeignService(IndexService.class);
        String s = indexService._recovery(index);
        return Response.Ok(JSONObject.parse(s));
    }

    @ApiImplicitParams(value = {
            @ApiImplicitParam(
                    name = CustomInterceptConfig.ES_HOST_HEADER_KEY,
                    value = Bootstrap.EXAMPLE,
                    dataType = "string",
                    paramType = "header",
                    defaultValue = Bootstrap.DEFAULT_VALUE)
    })
    @ApiOperation(value = "返回有关正在进行的和已完成的碎片恢复的信息")
    @GetMapping(value = "/_cat/recovery/{index}")
    public Object _cat_recovery_index(@ApiParam(value = "是否格式化") @RequestParam(name = "v", defaultValue = "true") boolean v,
                                      @PathVariable(value = "index") String index,
                                      @ApiParam(value = "格式") @RequestParam(name = "format", required = false) FormatEnum formatEnum) throws JsonProcessingException {
        CatService catService = ThreadLocalFeign.getFeignService(CatService.class);
        String s = catService._cat_recovery_index(v, index, formatEnum);
        if (null != formatEnum && formatEnum.equals(FormatEnum.JSON)) {
            return Response.Ok(new JsonMapper().readTree(s));
        } else {
            return s;
        }
    }

}
















