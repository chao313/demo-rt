package demo.rt.feign.newV;

import demo.rt.config.Bootstrap;
import demo.rt.config.feign.FeignServiceConfig;
import org.springframework.cloud.openfeign.FeignClient;


@FeignClient(name = "Aggregation-BucketService", url = Bootstrap.IN_USE, configuration = FeignServiceConfig.class)
public interface Aggregation_BucketService {

}


















