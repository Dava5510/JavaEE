package com.jt.common.basic;

import java.security.DomainCombiner;

public class StackOverTests {
    static void doMethod(){
        doMethod();
    }
    public static void main(String[] args) {
        doMethod();
    }
}
