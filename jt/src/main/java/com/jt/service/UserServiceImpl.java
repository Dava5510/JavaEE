package com.jt.service;

import com.jt.mapper.UserMapper;
import com.jt.pojo.Rights;
import com.jt.pojo.User;
import com.jt.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }

    /**
     * 业务需求:
     *  1.将密码进行加密处理
     *  2.根据username/password 查询数据库获取数据.
     *  3. 有数据 用户名密码正确
     *     无数据 用户名和密码错误
     * @param user
     * @return
     */
    @Override
    public String login(User user) {
        //1.将密码加密处理
        String password = user.getPassword();
        //2.利用md5加密算法 进行加密
        String md5Pass = DigestUtils.md5DigestAsHex(password.getBytes());
        user.setPassword(md5Pass);
        //3.查询数据库数据
        User userDB = userMapper.findUserByUP(user);
        if(userDB == null){
            //说明: 用户名和密码错误
            return null;
        }
            //说明: 用户名和密码正确,返回秘钥
        String uuid = UUID.randomUUID().toString()
                          .replace("-","");
        return uuid;
    }

    /**
     * 要求查询  1页10条
     * 特点: 数组的结果  口诀: 含头不含尾
     * 语  法:  select * from user limit 起始位置,查询的条数
     * 第一页:  select * from user limit 0,10       0-9
     * 第二页:  select * from user limit 10,10      10-19
     * 第三页:  select * from user limit 20,10      20-29
     * 第N页:   select * from user limit (n-1)*10,10
     * @param pageResult
     * @return
     */
    @Override
    public PageResult getUserList(PageResult pageResult) {
        //1.记录总数 total
        long total = userMapper.getTotal();
        //2.分页后的数据
        //2.1获取每页条数
        int size = pageResult.getPageSize();
        //2.2获取起始位置
        int start = (pageResult.getPageNum() - 1) * size;
        //2.3 获取用户查询的数据
        String query = pageResult.getQuery();
        List<User> rows = userMapper.findUserListByPage(start,size,query);
        return pageResult.setTotal(total).setRows(rows);
    }

    //更新操作时修改 status/updated 更新时间
    @Override
    @Transactional
    public void updateStatus(User user) {
        user.setUpdated(new Date());
        userMapper.updateStatus(user);
    }

    /**
     * 1.密码进行加密
     * 2.添加状态码信息
     * 3.添加创建时间/修改时间
     * 4.完成入库操作 xml方式
     * @param user
     */
    @Override
    @Transactional
    public void addUser(User user) {
        //1.密码加密处理
        Date date = new Date();
        String md5Pass = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        user.setPassword(md5Pass)
                .setStatus(true)
                .setCreated(date)
                .setUpdated(date); //最好保证时间唯一性.
        userMapper.addUser(user);
    }

    @Override
    public User findUserById(Integer id) {

        return userMapper.findUserById(id);
    }

    //id/phone/email
    @Override
    @Transactional
    public void updateUser(User user) {
        user.setUpdated(new Date());
        userMapper.updateUser(user);
    }

    //面试题: 常见运行时异常   常见检查异常(编译异常)
    //@Transactional(rollbackFor = IOException.class)      //事务的注解
    @Transactional
    @Override
    public void deleteUserById(Integer id){

        userMapper.deleteUserById(id);
        //int a = 1/0;
    }
}
