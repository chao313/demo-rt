package demo.rt.feign.newV;

import demo.rt.config.Bootstrap;
import demo.rt.config.feign.FeignServiceConfig;
import org.springframework.cloud.openfeign.FeignClient;


/**
 * 集群模板相关
 */
@FeignClient(name = "Cluster-TemplateService", url = Bootstrap.IN_USE, configuration = FeignServiceConfig.class)
public interface Cluster_TemplateService {


}
















