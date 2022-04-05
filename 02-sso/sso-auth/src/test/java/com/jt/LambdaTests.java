package com.jt;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

@SpringBootTest
public class LambdaTests {
    //Lambda表达式是JDK8中的一种新的语法结构
    @Test
    void testLambda01(){
       new Thread(new Runnable() {
           @Override
           public void run() {
               System.out.println("thread.run()");
           }
       }).start();//->Thread.run()->Runnable.run()
      // new Thread(()->{System.out.println("lambda.thread.run()");}).start();
         new Thread(()->System.out.println("thread.run()")).start();//->Thread.run()->Runnable.run()
    }
    @Test
    void testLambda02(){
        List<String> list = Arrays.asList("A", "B", "C");
        list.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        });
        //lambda表达式的写法
        list.forEach(e->System.out.println(e));
    }
}
