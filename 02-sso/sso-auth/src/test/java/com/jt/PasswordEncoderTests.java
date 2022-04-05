package com.jt;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
public class PasswordEncoderTests {
   @Test
    void testPasswordEncoder(){
       String password="123456";
       BCryptPasswordEncoder encoder=new BCryptPasswordEncoder();
       String encode = encoder.encode(password);
       System.out.println(encode);
   }
}
