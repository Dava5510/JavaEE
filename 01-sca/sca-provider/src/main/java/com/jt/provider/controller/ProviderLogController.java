package com.jt.provider.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Slf4j 注解描述类会在类中自动创建一个org.slf4j.Logger对象。
 * @RefreshScope 注解描述类时，会在配置中心数据发生变化后，再次访问此注解描述的对象，
 * 会重新构建对象，初始化属性。
 */
@RefreshScope
@Slf4j
@RestController
public class ProviderLogController {
//  private static final Logger log=
//            LoggerFactory.getLogger(ProviderLogController.class);
    @GetMapping("/provider/log/doLog01")
    public String doLog01(){
        log.trace("==trace==");
        log.debug("==debug==");
        log.info("==info==");
        log.warn("==warn==");
        log.error("==error==");
        return "nacos config test";
    }

    public ProviderLogController(){
        System.out.println("===ProviderLogController()===");
    }

    @Value("${logging.level.com.jt:info}")
    private String logLevel;//构建对象时属性进行初始化

    //http://localhost:8081/provider/log/doLog02
    @GetMapping("/provider/log/doLog02")
    public String doLog02(){
        return "log level is "+logLevel;
    }
}
