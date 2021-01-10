package demo.rt.request.dsl.term;

import demo.rt.feign.SearchService;
import demo.rt.po.request.QueryBuilders;
import demo.rt.po.request.SearchSourceBuilder;
import demo.rt.po.request.aggs.VoidAggs;
import demo.rt.po.request.dsl.term.WildcardQuery;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
@Slf4j
public class WildcardRequestTests {

    @Resource
    private SearchService searchService;

    @Test
    public void testWildcardRequest() {
        SearchSourceBuilder<WildcardQuery, VoidAggs> request = new SearchSourceBuilder<>();
        request.from(0).size(1).query(QueryBuilders.wildcardQuery("city", "dant*"));
        log.info("请求body:{}", request.getRequestBody());
        String response = searchService.DSL_search_wildcard("index_bulk", request);
        log.info("response:{}", response);
    }
}
