package demo.rt.feign.newV;

import demo.rt.config.Bootstrap;
import demo.rt.config.feign.FeignServiceConfig;
import org.springframework.cloud.openfeign.FeignClient;


/**
 * 索引 mapping相关
 */
@FeignClient(name = "Index-MappingService", url = Bootstrap.IN_USE, configuration = FeignServiceConfig.class)
public interface Index_MappingService {



}
















