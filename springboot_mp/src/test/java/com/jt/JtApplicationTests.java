package com.jt;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.Update;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jt.mapper.UserMapper;
import com.jt.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;

import java.util.List;

@SpringBootTest //Spring专门为测试准备的注解  启动了Spring容器
class JtApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Test
    void findAll() {
        List<User> userList = userMapper.findAll();
        System.out.println(userList);
    }

    /**
     *  MP新增操作测试
     *  思想: 全自动的ORM  映射是自动的, sql自动生成的
     */
    @Test
    public void insertUser(){
        User user = new User(null,"猴子",18, "男");
        userMapper.insert(user);
        System.out.println("新增入库成功!!!!");
    }

    /*
    *  关于MP编码的思想:
    *       根据其中不为null的属性进行业务操作!!!!
    */

    //1.根据Id查询用户  id=1的用户
    @Test
    public void testFindById(){
        User user = userMapper.selectById(1);
        System.out.println(user);
    }
    //1.查询name="黑熊精"的数据
    //QueryWrapper条件构造器 动态拼接where条件的
    //select * from demo_user where name=#{xxx}
    @Test
    public void testFindByName(){
        User user = new User();
        user.setName("黑熊精");
        QueryWrapper queryWrapper = new QueryWrapper(user);
        List<User> userList = userMapper.selectList(queryWrapper);
        System.out.println(userList);
    }

    //1.查询age > 18岁的用户   age=18!!!!
    // > gt, < lt, = eq, >= ge, <= le, <> ne
    @Test
    public void testFindByAge(){
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.gt("age",18);
        List<User> userList = userMapper.selectList(queryWrapper);
        System.out.println(userList);
    }

    //1.查询age > 18岁的并且 name 包含"乔"的用户
    //select * from demo_user where age > 18 and name like "%乔%"
    //select * from demo_user where age > 18 and name like "%乔"
    //条件构造器如果多个条件时,默认使用and
    @Test
    public void testFindByLike(){
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.gt("age",18)
                    .likeLeft("name","乔");
        List<User> userList = userMapper.selectList(queryWrapper);
        System.out.println(userList);
    }

    /**
     * 查询id=1,3,4,6,7的数据,并且根据年龄降序排列
     * Sql: select * from demo_user where id in (1,3,4,6,7)
     *      order by age desc
     */
    @Test
    public void testFindByOrder(){
        Integer[] ids = {1,3,4,6,7};
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        queryWrapper.in("id",ids)
                    .orderByDesc("age", "sex");
        List<User> userList = userMapper.selectList(queryWrapper);
        System.out.println(userList);
    }

    /**
     * 动态sql的查询
     * 根据name/sex查询数据. 但是name/sex可能为null
     * Sql: select * from demo_user where name=xxx and sex=xxx
     * 关于condition的说明:
     *       condition=true   负责拼接条件
     *       condition=false  不拼接条件
     */
    @Test
    public void testFindByIf(){
        String name = null;
        String sex = "女";
        QueryWrapper<User> queryWrapper = new QueryWrapper();
        //判断字符串是否为null
        boolean nameFlag = StringUtils.hasLength(name);
        boolean sexFlag = StringUtils.hasLength(sex);
        //动态Sql的写法
        queryWrapper.eq(nameFlag,"name",name)
                    .eq(sexFlag,"sex",sex);
        List<User> userList = userMapper.selectList(queryWrapper);
        System.out.println(userList);
    }

    /**
     *  需求: 动态查询主键字段(第一列数据) 适用范围 进行关联查询时使用.
     *  Sql: select id from demo_user
     *  设计: 关联查询 修改为 多张单表查询
     */
    @Test
    public void testFindByObjs(){

        List<Object> idList = userMapper.selectObjs(null);
        System.out.println(idList);
    }

    /**
     * MP更新操作
     * 说明: 将id=354 的名称改为"六耳猕猴"
     */
    @Test
    public void updateUserById(){
        User user = new User(354,"六耳猕猴",null,null);
        int rows = userMapper.updateById(user);
        System.out.println("响应了"+ rows +"行");
    }

    /**
     * MP更新操作
     * 说明: 将名称为"猴子"的数据改为"齐天大圣"
     * 用法:
     *      1. 参数1, 将修改的数据封装.
     *      2. 参数2, 将修改的条件封装
     * Sql: update demo_user set name="齐天大圣" where name="猴子"
     */
    @Test
    public void updateUserByName(){
        //1.利用pojo对象封装修改数据
        User user = new User(null,"齐天大圣",null,null);
        //1.利用UpdateWrapper封装where条件
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("name","猴子");
        userMapper.update(user,updateWrapper);
        System.out.println("修改操作成功!!!");
    }

}
