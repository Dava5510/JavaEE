package com.jt.common.reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

class Point{
    private int x;
    private int y;
    public Point(){}
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}

public class ClassObjectTests {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        //1.获取Point类的字节码对象(反射技术的起点)
        Class<Point> pointClass1 = Point.class;
        Class<?> pointClass2 = Class.forName("com.jt.common.reflect.Point");
        Class<?> pointClass3=new Point().getClass();
        System.out.println(pointClass1==pointClass2);//true
        System.out.println(pointClass2==pointClass3);//true
        //2.基于类的字节码对象创建类的实例对象
        Point point1 = pointClass1.newInstance();//底层调用无参构造函数
        Point point2 = pointClass1.newInstance();//底层调用无参构造函数
        System.out.println(point1==point2);//false
        //3.获取类中指定构造方法，然后基于构造方法构建类的实例对象
        Constructor<Point> declaredConstructor =
                pointClass1.getDeclaredConstructor(int.class, int.class);
        Point point3 = declaredConstructor.newInstance(10, 20);
        Point point4 = declaredConstructor.newInstance(30, 50);
        System.out.println(point3);
        System.out.println(point4);
    }
}
