package com.jt.provider.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * 此对象要对外提供一些访问服务
 */

@RestController
public class ProviderController {

    @Value("${server.port:8080}")
    private String server;
    @GetMapping("/provider/echo/{msg}")
    public String doRestEcho1(@PathVariable("msg") String msg)
            throws InterruptedException {
        //模拟耗时操作
        //Thread.sleep(5000);
        return server+" say: hello "+msg;
    }
}
