package com.jt.resource.pojo;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 构建用户行为的日志pojo对象，基于这个对象实现用户行为日志的封装，
 * 过程如下：
 * 1)在UI端(页面上)访问我的资源
 * 2)通过AOP方式获取是谁在什么时间访问这个资源，执行的什么操作，对应的具体方法等信息
 * 3)将获取的这些信息记录到当前Log类型的对象中
 * 4)通过Feign将Log对象传递给sso-system工程
 * 5)sso-system工程会将这个对象写入到数据库
 *
 */
@Data
public class Log implements Serializable {
    private static final long serialVersionUID = 6181824662663239549L;
    private Long id;
    /**
     * 用户执行这个操作时的ip地址
     */
    private String ip;
    /**
     * 这个用户表示登录用户
     */
    private String username;
    /**
     * 什么时间执行的这个操作
     */
    private Date createdTime;
    /**
     * 具体操作名
     */
    private String operation;
    /**
     * 具体操作对应的方法(这里要求写类全名+方法名)
     */
    private String method;
    /**
     * 执行方法时，传递的实际参数
     */
    private String params;
    /**
     * 执行这个操作是成功了还是失败了
     */
    private Integer status;
    /**
     * 当状态是失败状态时，我们要记录失败的原因
     */
    private String error;
    /**
     * 耗时(执行这个操作消耗的时间)
     */
    private Long time;
}
