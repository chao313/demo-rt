package demo.rt.request.dsl.matchall;

import demo.rt.feign.SearchMatchAllService;
import demo.rt.po.request.QueryBuilders;
import demo.rt.po.request.SearchSourceBuilder;
import demo.rt.po.request.aggs.VoidAggs;
import demo.rt.po.request.dsl.matchall.MatchAllQuery;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
@Slf4j
public class MatchAllRequestTests {

    @Resource
    private SearchMatchAllService searchMatchAllService;

    @Test
    public void testMatchAllRequest() {

        SearchSourceBuilder<MatchAllQuery, VoidAggs> request = new SearchSourceBuilder<>();
        request.from(0).size(2).query(QueryBuilders.matchAllQuery());
        log.info("请求body:{}", request.getRequestBody());
        String response = searchMatchAllService.match_all_search("index_bulk", request);
        log.info("response:{}", response);

    }
}
