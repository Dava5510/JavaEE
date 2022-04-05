package com.jt.provider.controller;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * 构建一个请求解析对象，可以对请求数据进行解析，例如请求行，请求头，请求体
 */
@Component
public class DefaultRequestOriginParser implements RequestOriginParser {
    //http://localhost:8081/provider/sentinel01?origin=app1
    @Override
    public String parseOrigin(HttpServletRequest request) {
        //解析请求参数，作为黑白名单判断依据
        //String origin=request.getParameter("origin");
        //return origin;
        //解析ip地址，作为黑白名单判断依据
        String ip=request.getRemoteAddr();
        System.out.println("id="+ip);
        return ip;
    }
}
