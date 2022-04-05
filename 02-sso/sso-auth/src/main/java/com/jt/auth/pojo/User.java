package com.jt.auth.pojo;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 构建User对象，基于此对象封装从远程服务获取到的用户信息。
 * 本次服务调用设计：
 * 认证服务(sso-auth)-->OpenFeign-->系统服务(sso-system)
 */
@Data
public class User implements Serializable {
    private static final long serialVersionUID = -7779976555863579590L;
    private Long id;
    private String username;
    private String password;
    private String status;
}
