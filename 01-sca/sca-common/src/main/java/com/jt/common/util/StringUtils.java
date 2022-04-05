package com.jt.common.util;

/**
 * 课堂练习：
 * 1.创建01-sca工程的子工程，工程模块名sca-common。
 * 2.在sca-common模块工程中创建一个工具类com.jt.common.util.StringUtils,并
 * 在类中添加一个判断字符串是否为空的静态方法。
 * 3.在sca-provider工程中添加一个springboot启动类，类名为com.jt.ProviderApplication
 * 4.将sca-common工程以依赖的方式添加到sca-provider工程中？(怎么写，自己玩)
 * 5.在sca-provider工程中写一个单元测试类，类全名为com.jt.util.StringTests,并添加
 * 单元测试方法，在这个单元测试方法中使用sca-common工程中写的StringUtils类，
 * 测试一个字符串是否为空.
 */

public class StringUtils {
    /**
     * 通过此方法判定传入的字符串是否为空
     * @param str
     * @return
     */
    public static boolean isEmpty(String str){
        return str==null||"".equals(str);
    }
}
