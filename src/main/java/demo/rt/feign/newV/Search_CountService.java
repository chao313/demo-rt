package demo.rt.feign.newV;

import demo.rt.config.Bootstrap;
import demo.rt.config.feign.FeignServiceConfig;
import org.springframework.cloud.openfeign.FeignClient;


/**
 * 检索相关(EQL语法)
 */
@FeignClient(name = "Search-CountService", url = Bootstrap.IN_USE, configuration = FeignServiceConfig.class)
public interface Search_CountService {



}
















