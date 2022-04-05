package com.jt;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Base64;

@SpringBootTest
public class Base64Tests {
    @Test
    void testBase64() {
        String username="admin";
        //基于Base64算法对指定内容进行编码
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] encode = encoder.encode(username.getBytes());
        System.out.println(new String(encode));
        //基于Base64算法对指定内容进行解码
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decode = decoder.decode(encode);
        System.out.println(new String(decode));
    }
}
