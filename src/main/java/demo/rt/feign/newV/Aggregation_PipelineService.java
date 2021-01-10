package demo.rt.feign.newV;

import demo.rt.config.Bootstrap;
import demo.rt.config.feign.FeignServiceConfig;
import org.springframework.cloud.openfeign.FeignClient;


/**
 * 通道聚合相关
 */
@FeignClient(name = "Aggregation-PipelineService", url = Bootstrap.IN_USE, configuration = FeignServiceConfig.class)
public interface Aggregation_PipelineService {


}



