package demo.rt.request.dsl.term;

import demo.rt.feign.SearchService;
import demo.rt.po.request.QueryBuilders;
import demo.rt.po.request.SearchSourceBuilder;
import demo.rt.po.request.aggs.VoidAggs;
import demo.rt.po.request.dsl.term.TermsQuery;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;

@SpringBootTest
@Slf4j
public class TermsRequestTests {

    @Resource
    private SearchService searchService;

    @Test
    public void testTermsRequest() {
        SearchSourceBuilder<TermsQuery, VoidAggs> request = new SearchSourceBuilder<>();
        request.from(0).size(1).query(QueryBuilders.termsQuery("city", Arrays.asList("dante")));
        log.info("请求body:{}", request.getRequestBody());
        String response = searchService.DSL_search_terms("index_bulk", request);
        log.info("response:{}", response);
    }
}
