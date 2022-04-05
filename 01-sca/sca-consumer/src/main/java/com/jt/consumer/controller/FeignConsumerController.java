package com.jt.consumer.controller;

import com.jt.consumer.service.RemoteProviderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consumer")
public class FeignConsumerController {
    @Autowired
    private RemoteProviderService remoteProviderService;
    //http://localhost:8090/consumer/echo/8090
    @GetMapping("/echo/{msg}")
    public String doFeignEcho(@PathVariable("msg") String msg){
        return remoteProviderService.echoMessage(msg);
    }
}
