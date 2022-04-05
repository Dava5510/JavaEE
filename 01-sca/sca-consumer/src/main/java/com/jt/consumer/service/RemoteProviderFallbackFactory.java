package com.jt.consumer.service;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * 服务回调工厂，当远程服务不可用或者远程服务调用超时，可以通过
 * 配置的方式，调用本地服务返回一个结果
 */
@Component
public class RemoteProviderFallbackFactory
        implements FallbackFactory<RemoteProviderService> {
    @Override
    public RemoteProviderService create(Throwable throwable) {
        return new RemoteProviderService() {
            @Override
            public String echoMessage(String msg) {
                //...给运维人员发短信...,
                return "服务维护中，稍等片刻再访问 ";
            }
        };
    }
}
