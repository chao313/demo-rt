package demo.rt.feign;

import demo.rt.config.Bootstrap;
import demo.rt.config.feign.FeignServiceConfig;
import demo.rt.po.request.SearchSourceBuilder;
import demo.rt.po.request.aggs.VoidAggs;
import demo.rt.po.request.dsl.matchall.MatchAllQuery;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * 匹配全面文档使用
 */
@FeignClient(name = "SearchMatchAllService", url = Bootstrap.IN_USE, configuration = FeignServiceConfig.class)
public interface SearchMatchAllService {

    /**
     * 全文搜索(匹配全部的文档)
     */
    @RequestMapping(value = "/{index}/_search", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String match_all_search(@PathVariable(value = "index") String index, @RequestBody SearchSourceBuilder<MatchAllQuery, VoidAggs> matchAllRequest);


}
