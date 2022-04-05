package com.jt.resource.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

@Configuration
public class TokenConfig {
    //jwt令牌签名key(系统底层对令牌的header,payload部分进行签名时使用的key，
    //这个key一定要保存好，不告知客户端)
    private String signingKey= "auth";//后续还可以将其写到配置中心
    @Bean
    public JwtAccessTokenConverter tokenConverter(){
        JwtAccessTokenConverter tokenConverter=new JwtAccessTokenConverter();
        tokenConverter.setSigningKey(signingKey);//auth为签名key
        return tokenConverter;
    }
    @Bean
    public TokenStore tokenStore(){
        JwtTokenStore tokenStore=new JwtTokenStore(tokenConverter());
        return tokenStore;
    }
}
