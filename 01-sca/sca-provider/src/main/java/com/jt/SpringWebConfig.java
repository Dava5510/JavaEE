package com.jt;

import com.jt.provider.interceptor.TimeInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 定义Spring Web MVC 配置，需要实现 spring中的WebMvcConfigurer接口
 */
@Configuration
public class SpringWebConfig implements WebMvcConfigurer {
    /**
     * 注册拦截器(添加到spring容器)，并指定拦截规则
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TimeInterceptor())
                .addPathPatterns("/provider/sentinel01");
    }
}
