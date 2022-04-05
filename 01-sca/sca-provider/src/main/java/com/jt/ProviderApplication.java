package com.jt;
import com.jt.common.cache.DefaultCache;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
@SpringBootApplication
public class ProviderApplication{
    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class,args);
    }
}
