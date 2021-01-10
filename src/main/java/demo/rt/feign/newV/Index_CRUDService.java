package demo.rt.feign.newV;

import demo.rt.config.Bootstrap;
import demo.rt.config.feign.FeignServiceConfig;
import org.springframework.cloud.openfeign.FeignClient;


/**
 * Index CRUD 相关
 */
@FeignClient(name = "Index-CRUDService", url = Bootstrap.IN_USE, configuration = FeignServiceConfig.class)
public interface Index_CRUDService {



}
















