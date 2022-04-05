package com.jt.provider.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.jt.provider.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/provider")
public class ProviderSentinelController {
    //拓展练习：要求对此方法访问按时间进行限制。
    //解决方案：基于spring mvc中的拦截器进行实现
    @GetMapping("/sentinel01")
    public String doSentinel01(){
        return "sentinel 01 test  ...";
    }
    @GetMapping("/sentinel02")
    public String doSentinel02(){
        return "sentinel 02 test  ...";
    }
    /**通过如下方法演示链路限流*/
    @Autowired
    private ResourceService resourceService;
    @GetMapping("/sentinel03")
    public String doSentinel03(){
        //请求链路中的资源访问
        return resourceService.doGetResource();
        //return "sentinel 03 test  ...";
    }

    /**
     * 通过此方法演示服务降级
     * AtomicInteger 对象提供了一种线程安全(底层通过乐观锁保证)的自增和自减操作
     */
    private AtomicInteger atomicInteger=new AtomicInteger(1);
    @GetMapping("/sentinel04")
    public  String doSentinel04() throws InterruptedException {
        int num = atomicInteger.getAndIncrement();
        if(num%2==0) {
            Thread.sleep(300);//线程休眠(模拟耗时操作):慢调用
            //num/=0;//异常比例
        }
        return "sentinel 04 test  ...";
    }

    @GetMapping("/sentinel/findById")
    @SentinelResource("resource")
    public String doFindById(@RequestParam Integer id){
        return "resource id is "+id;
    }



}
