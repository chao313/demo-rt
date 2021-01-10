package demo.rt.feign.newV;

import demo.rt.config.Bootstrap;
import demo.rt.config.feign.FeignServiceConfig;
import org.springframework.cloud.openfeign.FeignClient;


/**
 * 指标聚合相关
 */
@FeignClient(name = "Aggregation-MetricsService", url = Bootstrap.IN_USE, configuration = FeignServiceConfig.class)
public interface Aggregation_MetricsService {


}
















