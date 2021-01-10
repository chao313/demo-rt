package demo.rt.feign.newV;

import demo.rt.config.Bootstrap;
import demo.rt.config.feign.FeignServiceConfig;
import org.springframework.cloud.openfeign.FeignClient;


/**
 * 检索相关(DSL 术语级查询 语法)
 */
@FeignClient(name = "Search-DSL-TermLevelService", url = Bootstrap.IN_USE, configuration = FeignServiceConfig.class)
public interface Search_DSL_TermLevelService {


}
















