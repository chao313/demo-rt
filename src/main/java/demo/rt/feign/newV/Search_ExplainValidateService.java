package demo.rt.feign.newV;

import demo.rt.config.Bootstrap;
import demo.rt.config.feign.FeignServiceConfig;
import org.springframework.cloud.openfeign.FeignClient;


/**
 * 检索相关(Explain相关+验证相关)
 */
@FeignClient(name = "Search-ExplainValidateService", url = Bootstrap.IN_USE, configuration = FeignServiceConfig.class)
public interface Search_ExplainValidateService {



}
















