package com.jt.provider.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@RefreshScope
@RestController
public class ProviderCacheController {
    @Value("${useLocalCache:false}")
    private boolean useLocalCache;//开关

    @RequestMapping("/provider/cache01")
    public String doUseLocalCache01(){
        return "useLocalCache'value is   "+useLocalCache;
    }
    //构建一个线程安全的List对象，基于此对象存储从数据库获取的一些数据
    private List<String> cache=new CopyOnWriteArrayList<>();
    //homework
    @RequestMapping("/provider/cache02")
    public List<String> doUseLocalCache02(){
        if(!useLocalCache){
            System.out.println("Get Data from Database");
            return Arrays.asList("电器","服装","医疗健康");
        }
        if(cache.isEmpty()) {
            synchronized (cache) {
                if (cache.isEmpty()) {
                    System.out.println("从数据库取数据");
                    //假设这些数据来自数据库
                    List<String> cateList = Arrays.asList("电器", "服装", "医疗健康");
                    cache.addAll(cateList);
                }
            }
        }
        return cache;
    }
}
