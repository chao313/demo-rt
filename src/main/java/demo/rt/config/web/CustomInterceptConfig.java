//package demo.rt.config.web;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.databind.SerializationFeature;
//import demo.rt.thread.ThreadLocalFeign;
//import demo.rt.util.HttpRequestUtils;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.http.converter.HttpMessageConverter;
//import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.List;
//
///**
// * 继承了mvc的适配器 复写其中的addInterceptors方法
// * 请求拦截器（HTTP层面的）
// */
//@Configuration
//@Slf4j
//public class CustomInterceptConfig extends WebMvcConfigurationSupport {
//
//    public static final String ES_HOST_HEADER_KEY = "ES_HOST";
//    public static final String ES_PAGE_HEADER_KEY = "ES_PAGE";
//    public static final String ES_PAGE_SIZE_HEADER_KEY = "ES_PAGE_SIZE";
//    public static final String ES_FILTER_HEADER_KEY = "ES_FILTER";
//    public static final String ES_PAGE_HEADER_KEY_DEFAULT = "true";
//    public static final String ES_PAGE_SIZE_HEADER_KEY_DEFAULT = "10";
//    public static final String ES_FILTER_HEADER_KEY_EXAMPLE = "{key:value}";
//
//    private static Logger LOGGER = LoggerFactory.getLogger(CustomInterceptConfig.class);
//
////    @Override
////    public void addInterceptors(InterceptorRegistry registry) {
////        /**
////         * 自定义拦截器
////         */
////        HandlerInterceptor handlerInterceptor = new HandlerInterceptor() {
////            @Override
////            public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
////                LOGGER.info("【拦截请求】: 请求路径 {}", request.getRequestURI());
////                String ES_HOST = request.getHeader(ES_HOST_HEADER_KEY);
////                if (StringUtils.isNotBlank(ES_HOST) && ES_HOST.matches("http.*")) {
////                    ThreadLocalFeign.setES_HOST(ES_HOST);//放入线程变量(指定格式http)
////                }
////
////                String ES_PAGE = request.getHeader(ES_PAGE_HEADER_KEY);
////                if (StringUtils.isNotBlank(ES_PAGE)) {
////                    ThreadLocalFeign.setES_PAGE(Boolean.valueOf(ES_PAGE));//是否分页放入线程变量
////                }
////
////                String ES_PAGE_SIZE = request.getHeader(ES_PAGE_SIZE_HEADER_KEY);
////                if (StringUtils.isNotBlank(ES_PAGE_SIZE)) {
////                    ThreadLocalFeign.setES_PAGE_SIZE(Integer.valueOf(ES_PAGE_SIZE));//分页的size
////                }
////
////                String ES_FILTER = request.getHeader(ES_FILTER_HEADER_KEY);
////                if (StringUtils.isNotBlank(ES_PAGE_SIZE)) {
////                    ThreadLocalFeign.setES_FILTER(ES_FILTER);//过滤
////                }
////                return true;
////            }
////
////            @Override
////            public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
////                String ip = HttpRequestUtils.getRealRequestIp(request);
////                log.info("请求的ip:{}", ip);
////            }
////
////            @Override
////            public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
////                ThreadLocalFeign.setES_HOST(null);//移除
////                ThreadLocalFeign.setES_PAGE(null);//移除
////                ThreadLocalFeign.setES_PAGE_SIZE(null);//移除
////                ThreadLocalFeign.setES_FILTER(null);//移除
////            }
////        };
////        /**
////         * 把创建的拦截器注册
////         *
////         * /**是拦截所有请求
////         */
////        registry.addInterceptor(handlerInterceptor).addPathPatterns("/**");
////
////    }
//
////    @Override
////    protected void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
////        super.configureMessageConverters(converters);
////        converters.add(mappingJackson2HttpMessageConverter());
////    }
////
////    // 解决序列化空对象问题
////    private MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
////        ObjectMapper mapper = new ObjectMapper();
////        // 关键代码
////        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
////        MappingJackson2HttpMessageConverter converter =
////                new MappingJackson2HttpMessageConverter(mapper);
////        return converter;
////    }
//}
