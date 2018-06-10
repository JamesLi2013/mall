package com.james.mall.config;

import com.james.mall.base.MallHandlerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class PreInterceptorConfig extends WebMvcConfigurerAdapter {
    @Bean
    public MallHandlerInterceptor mallHandlerInterceptor(){
        return new MallHandlerInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        super.addInterceptors(registry);
        registry.addInterceptor(mallHandlerInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/user/*","/auth/*","*.js","*.html","*.css","*.png","*.jpg","*.jpeg");
    }


}
