package com.yangchenle.electricityconsumptionsystem;

import com.yangchenle.electricityconsumptionsystem.common.ManagerInterceptor;
import com.yangchenle.electricityconsumptionsystem.common.ReaderInterceptor;
import com.yangchenle.electricityconsumptionsystem.common.UserInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Resource
    private ReaderInterceptor readerInterceptor;
    @Resource
    private ManagerInterceptor managerInterceptor;
    @Resource
    private UserInterceptor userInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(managerInterceptor).addPathPatterns("/manager/**")
                .excludePathPatterns("/manager/login");
        registry.addInterceptor(readerInterceptor).addPathPatterns("/reader/**")
                .excludePathPatterns("/reader/login")
                .excludePathPatterns("/reader/verifyCode");
        registry.addInterceptor(userInterceptor).addPathPatterns("/user/**")
                .excludePathPatterns("/user/login")
                .excludePathPatterns("/user/verifyCode")
                .excludePathPatterns("/user/register");

    }

    @Override
    public void addCorsMappings(CorsRegistry corsRegistry) {
        corsRegistry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("POST", "GET", "PUT", "DELETE", "OPTIONS")
                .maxAge(3600)
                .allowCredentials(true);
    }
}
