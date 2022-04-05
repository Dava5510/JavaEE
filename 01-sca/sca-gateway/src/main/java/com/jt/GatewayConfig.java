package com.jt;

import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.BlockRequestHandler;
import com.alibaba.csp.sentinel.adapter.gateway.sc.callback.GatewayCallbackManager;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * 配置网关层面出现限流时，客户端的响应结果。
 */
//@Configuration
public class GatewayConfig {//了解，假如想深入了解Mono机制还需要去学习spring webflux技术
    public GatewayConfig(){
        GatewayCallbackManager.setBlockHandler(new BlockRequestHandler() {
            @Override
            public Mono<ServerResponse> handleRequest(
                    ServerWebExchange serverWebExchange,Throwable throwable) {
                //1.构建响应数据
                //String jsonStr="{\"code:\":429,\"msg\":\"many request\"}";
                Map<String,Object> map=new HashMap<>();
                map.put("code", 429);
                map.put("msg", "many request");
                String jsonStr= JSON.toJSONString(map);
                //2.将响应数据存储一个异步序列中(Mono对象~0个或1个异步序列)
                return ServerResponse.ok().body(Mono.just(jsonStr),String.class);
            }
        });
    }
}
