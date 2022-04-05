package com.jt.util;
import com.jt.common.cache.DefaultCache;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DefaultCacheTests {
    @Autowired //DI
    private DefaultCache defaultCache;
    @Test
    void testDefaultCache(){
        System.out.println(defaultCache.toString());
    }
}
