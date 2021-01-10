package demo.rt.feign.newV;

import demo.rt.config.Bootstrap;
import demo.rt.config.feign.FeignServiceConfig;
import org.springframework.cloud.openfeign.FeignClient;


/**
 * 索引 文档相关
 */
@FeignClient(name = "Index-DocumentService", url = Bootstrap.IN_USE, configuration = FeignServiceConfig.class)
public interface Index_DocumentService {


}
















