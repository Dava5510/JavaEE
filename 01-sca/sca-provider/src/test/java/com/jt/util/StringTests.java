package com.jt.util;
import com.jt.common.util.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StringTests {
    @Test //org.junit.jupiter.api.Test
    void testStringEmpty(){
         String str="";
         boolean empty = StringUtils.isEmpty(str);
         System.out.println(empty);
    }
}
