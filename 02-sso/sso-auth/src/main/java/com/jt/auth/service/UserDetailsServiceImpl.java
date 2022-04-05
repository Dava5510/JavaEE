package com.jt.auth.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 使用自定义用户名和密码执行登录操作的逻辑实现(系统默认用户为user，密码是控制台密码~随机字符串)
 * 简易认证流程(Spring Security)：这个执行链底层框架已经设计好，我们只需要执行。
 * 1)客户端提交用户名和密码给服务端
 * 2)服务端调用Spring Security框架中的过滤器(Filters)对用户名和密码进行预处理
 * 3)过滤器(Filters)将用户名和密码传递给认证管理器(AuthenticationManager)完成用户身份认证
 * 4)认证管理器会调用UserDetailsService对象获取远端服务或数据库中的用户信息，然后
 * 与客户端提交的用户信息进行比对(这个比对过程就是认证)。
 * 5)认证通过则基于用户权限对用户进行资源访问授权。
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private RemoteUserService remoteUserService;
    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {
        //1.基于用户名获取用户对象信息
        com.jt.auth.pojo.User user =
                remoteUserService.selectUserByUsername(username);
        if(user==null)
            throw new UsernameNotFoundException("user is not exits");
        //2.基于用户id获取用户权限信息
        List<String> permissions =
                remoteUserService.selectUserPermissions(user.getId());
        //3.封装查询结果并返回(交给认证管理器去完成认证操作)
        List<GrantedAuthority> authorities =
          AuthorityUtils.createAuthorityList(permissions.toArray(new String[]{}));
        User userDetails=new User(user.getUsername(),
                user.getPassword(),//已加密
                authorities);
        return userDetails;
    }
}
