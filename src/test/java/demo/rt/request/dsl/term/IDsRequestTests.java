package demo.rt.request.dsl.term;

import demo.rt.feign.SearchService;
import demo.rt.po.request.QueryBuilders;
import demo.rt.po.request.SearchSourceBuilder;
import demo.rt.po.request.aggs.VoidAggs;
import demo.rt.po.request.dsl.term.IDsQuery;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;

@SpringBootTest
@Slf4j
public class IDsRequestTests {

    @Resource
    private SearchService searchService;

    /**
     * 测试id相对
     */
    @Test
    public void testIDsRequest() {
        SearchSourceBuilder<IDsQuery, VoidAggs> request = new SearchSourceBuilder<>();
        request.from(0).size(1).query(QueryBuilders.idsQuery(Arrays.asList("1", "2")));
        log.info("请求body:{}", request.getRequestBody());
        String response = searchService.DSL_search_ids("index_bulk", request);
        log.info("response:{}", response);
    }
}
