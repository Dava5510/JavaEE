package com.jt.resource.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer //启动资源服务器默认配置(例如所有资源都要认证)
@EnableGlobalMethodSecurity(prePostEnabled = true)//启动方法层面的权限控制
public class ResourceConfig extends ResourceServerConfigurerAdapter {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        //super.configure(http);
        //1.关闭跨域攻击
        http.csrf().disable();
        //2.配置认证规则
        http.authorizeRequests()
                //配置需要认证的请求
                .antMatchers("/resource/**").authenticated()
                //除了需要认证的，其它都放行
                .anyRequest().permitAll();
    }
}

