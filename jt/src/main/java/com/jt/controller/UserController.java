package com.jt.controller;

import com.jt.pojo.User;
import com.jt.service.UserService;
import com.jt.vo.PageResult;
import com.jt.vo.SysResult;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/findAll")
    public List<User> findAll(){

        return userService.findAll();
    }

    /**
     *  URL地址: /user/login
     *  请求类型: post
     *  参数:    JSON串 username/password
     *  返回值:  SysResult对象
     */
    @PostMapping("/login")
    public SysResult login(@RequestBody User user){

        //返回值一个字符串 token
        String token = userService.login(user);
        if(token == null){
            return SysResult.fail(); //201
        }
        return SysResult.success(token); //200
    }

    /**
     * 业务说明:
     *  1. /user/list
     *  2.请求类型: GET
     *  3.参数接收: 后台使用PageResult对象接收
     *  3.返回值: SysResult<PageResult>
     */
     @GetMapping("/list")
     public SysResult getUserList(PageResult pageResult){//参数3
         //业务查询总数.分页条数.
         pageResult = userService.getUserList(pageResult);
        return SysResult.success(pageResult);//参数5个
     }

    /**
     * 业务: 实现用户状态的修改
     * 参数: /user/status/{id}/{status}
     * 返回值: SysResult对象
     * 类型:   put 类型
     */
    @PutMapping("/status/{id}/{status}")
    public SysResult updateStatus(User user){

        userService.updateStatus(user);
        return SysResult.success();
    }

    /**
     * 业务: 实现用户新增操作
     * url:  /user/addUser   post类型
     * 参数: 使用User对象接收
     * 返回值: SysResult对象
     */
    @PostMapping("/addUser")
    public SysResult addUser(@RequestBody User user){

        userService.addUser(user);
        return SysResult.success();
    }

    /**
     * 根据ID查询数据库
     * URL:/user/{id}
     * 参数: id
     * 返回值: SysResult(user对象)
     */
    @GetMapping("/{id}")
    public SysResult findUserById(@PathVariable Integer id){

        User user = userService.findUserById(id);
        return SysResult.success(user);
    }

    /**
     * 业务说明: 实现数据的修改操作
     * URL:  /user/updateUser
     * 参数:  user对象
     * 返回值: SysResult对象
     * 请求类型: PUT
     */
    @PutMapping("/updateUser")
    public SysResult updateUser(@RequestBody User user){

        userService.updateUser(user);
        return SysResult.success();
    }

    /**
     * 关于请求的小结
     *    1.常规请求方式 get/delete   ?key=value&key2=value2
     *    2.post/put    data: JS对象    后端接收@RequestBody
     *    3.restFul风格  /url/arg1/arg2/arg3   使用对象接收
     * 完成用户删除操作
     *  1.URL地址 /user/{id}
     *  2.参数:  id
     *  3.返回值: SysResult
     */
    @DeleteMapping("/{id}")
    public SysResult deleteUserById(@PathVariable Integer id){

        userService.deleteUserById(id);
        return SysResult.success();
    }




}
