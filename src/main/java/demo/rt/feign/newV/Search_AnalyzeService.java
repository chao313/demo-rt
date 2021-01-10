package demo.rt.feign.newV;

import demo.rt.config.Bootstrap;
import demo.rt.config.feign.FeignServiceConfig;
import demo.rt.po.request.analyze.AnalyzeRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


/**
 * 检索相关(分词器相关)
 */
@FeignClient(name = "Search-AnalyzeService", url = Bootstrap.IN_USE, configuration = FeignServiceConfig.class)
public interface Search_AnalyzeService {


}
















