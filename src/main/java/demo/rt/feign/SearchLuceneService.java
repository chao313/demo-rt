package demo.rt.feign;

import demo.rt.config.Bootstrap;
import demo.rt.config.feign.FeignServiceConfig;
import demo.rt.po.request.SearchSourceBuilder;
import demo.rt.po.request.aggs.VoidAggs;
import demo.rt.po.request.lucene.LuceneQueryStringQuery;
import demo.rt.po.request.lucene.LuceneSimpleQueryStringQuery;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * Lucene语法使用
 * <a href="https://www.elastic.co/guide/en/elasticsearch/reference/7.9/query-dsl-query-string-query.html"></a>
 */
@FeignClient(name = "SearchLuceneService", url = Bootstrap.IN_USE, configuration = FeignServiceConfig.class)
public interface SearchLuceneService {


    @RequestMapping(value = "/{index}/_search", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String _search_query_String(@PathVariable(value = "index") String index, @RequestBody SearchSourceBuilder<LuceneQueryStringQuery, VoidAggs> luceneQueryStringRequest);


    @RequestMapping(value = "/{index}/_search", method = RequestMethod.POST, headers = {"content-type=application/json"})
    String _search_simple_query_String(@PathVariable(value = "index") String index, @RequestBody SearchSourceBuilder<LuceneSimpleQueryStringQuery, VoidAggs> simpleQueryStringRequest);


}
