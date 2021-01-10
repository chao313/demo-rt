package demo.rt.feign.newV;

import demo.rt.config.Bootstrap;
import demo.rt.config.feign.FeignServiceConfig;
import org.springframework.cloud.openfeign.FeignClient;


/**
 * 检索相关(Lucene语法)
 */
@FeignClient(name = "Search-Lucene-Service", url = Bootstrap.IN_USE, configuration = FeignServiceConfig.class)
public interface Search_Lucene_Service {

}
















