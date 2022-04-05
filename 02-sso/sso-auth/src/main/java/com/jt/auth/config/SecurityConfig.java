package com.jt.auth.config;

import org.apache.http.impl.conn.Wire;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    /**
     * 构建加密算法对象，基于此算法对用户端输入的密码进行加密
     * Client-->(username,password)
     * Server-->(对未加密的密码进行加密然后与数据库已加密的密码进行比对)
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 此对象拿出来交给spring去管理，目的是与后续oauth2协议进行整合。
     * @return
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 配置认证规则(系统默认规则是所有资源必须先认证才能访问)
     * 1)哪些请求必须认证，哪些请求无需认证
     * 2)配置认证成功和失败处理
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //super.configure(http);
        //1.关闭跨域攻击
        //假如这一项没禁用，使用postman,idea的httpclient工具，发送post请求会403异常
        http.csrf().disable();
        //2.设置请求认证规则(系统默认规则是所有资源必须先认证才能访问)
        //http.authorizeRequests().anyRequest().authenticated();//所有请求都要认证
        http.authorizeRequests().anyRequest().permitAll();//放行所有请求
        // http.authorizeRequests()
        //        .mvcMatchers("/default.html").authenticated()//认证
        //        .anyRequest().permitAll();//其它都放行
        //3.设置认证结果处理器(默认认证成功以后会跳转到一个index.html页面)
        //formLogin方法执行后会创建一个/login路径
        http.formLogin().successHandler(successHandler()).failureHandler(failureHandler());
    }
    /**
     * 构建认证成功处理器对象
     * @return
     */
    @Bean
    public AuthenticationSuccessHandler successHandler(){
//        return new AuthenticationSuccessHandler() {
//            @Override
//            public void onAuthenticationSuccess(
//                    HttpServletRequest request,
//                    HttpServletResponse response,
//                    Authentication authentication) throws IOException, ServletException {
//                Map<String,Object> data=new HashMap<>();
//                data.put("state", "200");
//                data.put("message","login success");
//                writeJsonToClient(response,data);
//            }
//        };
        return (request, response,authentication)->{
                Map<String,Object> data=new HashMap<>();
                data.put("state", "200");
                data.put("message","login success");
                writeJsonToClient(response,data);
        };
    }
    /**
     * 构建认证失败处理器对象
     * @return
     */
    @Bean
    public AuthenticationFailureHandler failureHandler(){
//        return new AuthenticationFailureHandler() {
//            @Override
//            public void onAuthenticationFailure(
//                    HttpServletRequest request,
//                    HttpServletResponse response,
//                    AuthenticationException e) throws IOException, ServletException {
//                Map<String,Object> data=new HashMap<>();
//                data.put("state", "500");
//                data.put("message","login failure");
//                writeJsonToClient(response,data);
//            }
//        };
        return (request,response,e)->{
                Map<String,Object> data=new HashMap<>();
                data.put("state", "500");
                data.put("message","login failure");
                writeJsonToClient(response,data);
        };//lambda表达式的写法

    }
    private void writeJsonToClient(HttpServletResponse response,
                                   Map<String,Object> map) throws IOException {
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json;charset=utf-8");
        PrintWriter writer = response.getWriter();
        String jsonStr=
                new ObjectMapper().writeValueAsString(map);
        writer.write(jsonStr);
        writer.flush();
    }
}
