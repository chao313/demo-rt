package demo.rt.feign.newV;

import demo.rt.config.Bootstrap;
import demo.rt.config.feign.FeignServiceConfig;
import org.springframework.cloud.openfeign.FeignClient;


/**
 * 索引开关相关
 */
@FeignClient(name = "Index-OpenCloseService", url = Bootstrap.IN_USE, configuration = FeignServiceConfig.class)
public interface Index_OpenCloseService {

}
















