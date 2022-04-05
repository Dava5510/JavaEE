package com.jt.common.basic;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

class Container{
    private static Map<String,Object> map=new HashMap<>();
    private static Container container=new Container();
    public Container(){
        map.put("A", 100);
        map.put("A", 200);
    }
    public static Container getInstance(){
        return container;
    }
}
public class ClassLoader01 {
    public static void main(String[] args) {
        Container.getInstance();
    }
}
