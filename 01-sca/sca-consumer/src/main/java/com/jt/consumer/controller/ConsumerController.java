package com.jt.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Random;

@RestController
public class ConsumerController {

    @Autowired
    private RestTemplate restTemplate;
    //打开浏览器
    //访问:http://localhost:8090/consumer/doRestEcho1
    @GetMapping("/consumer/doRestEcho1")
    public String doRestEcho1(){
        String url="http://localhost:8081/provider/echo/8090";
        return restTemplate.getForObject(url,//远端服务的url
                String.class);//远端服务url对应的返回值类型(ResponseType)
    }

    //==========================================================
    /**
     * 借助此对象，可以基于服务名从nacos获取多个服务实例，
     * 并且基于一定的负载均衡算法进行远端服务调用。
     */
    @Autowired
    private LoadBalancerClient loadBalancerClient;
    //实现类RibbonLoadBalancerClient

    @Value("${spring.application.name:8090}")
    private String appName;


    //访问http://localhost:8090/consumer/doRestEcho2
    @GetMapping("/consumer/doRestEcho2")
    public String doRestEcho2(){
        //1.基于服务名获取服务实例
        ServiceInstance serviceInstance = //alt+回车 可以生成变量
                loadBalancerClient.choose("sca-provider");//serviceId为nacos中的服务名
        //2.基于服务实例构建要访问的服务的url
        String ip=serviceInstance.getHost();
        int port=serviceInstance.getPort();
        //String url="http://"+ip+":"+port+"/provider/echo/8090";
        //基于String类的format方法进行字符串拼接，这里的%s表示占位符，传值时注意顺序

        //String url=String.format("http://%s:%s/provider/echo/%s",ip,port,appName);
        //return restTemplate.getForObject(url, String.class);

        String url=String.format("http://%s:%s/provider/echo/{msg}",ip,port);
        return restTemplate.getForObject(url,//远端服务的url
                String.class,appName);//远端服务url对应的返回值类型(ResponseType)

    }
    @Autowired
    //@Qualifier("loadBalancedRestTemplate")
    private RestTemplate loadBalancedRestTemplate;

    @GetMapping("/consumer/doRestEcho3")
    public String doRestEcho3(){
        String serviceName="sca-provider";
        String url=String.format("http://%s/provider/echo/{msg}",serviceName);
        return loadBalancedRestTemplate.getForObject(url,//远端服务的url
                String.class,appName);//远端服务url对应的返回值类型(ResponseType)
        //这里的appName是传给了url中的{msg},这是rest风格的一种写法
    }

}
