package com.jt.system.service.impl;

import com.jt.system.dao.UserMapper;
import com.jt.system.pojo.User;
import com.jt.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User selectUserByUsername(String username) {
        return userMapper.selectUserByUsername(username);
    }

    @Override
    public List<String> selectUserPermissions(Long userId) {
       //方案1：3次单表查询
       //List<Long> roleIds=userMapper.selectRoleIdByUserId(userId);
       //List<Long> menuIds=userMapper.selectMenuIdByRoleId(roleIds);
       //List<String> permissions=userMapper.selectUserPermissionsByMenuIds(menuIds);
       //return permissions;
       //方案2：多表关联查询
        return userMapper.selectUserPermissions(userId);

    }
}
