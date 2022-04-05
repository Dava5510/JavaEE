package com.jt.mapper;

import com.jt.pojo.User;
import org.apache.ibatis.annotations.*;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
public interface UserMapper {

    @Select("select * from user")
    List<User> findAll();
    @Select("select * from user where username=#{username} and password=#{password}")
    User findUserByUP(User user);
    @Select("select count(1) from user")
    long getTotal();
    List<User> findUserListByPage(@Param("start") int start,
                                  @Param("size") int size,
                                  @Param("query") String query);
    /* @Select("select * from user limit #{start},#{size}")
    List<User> findUserListByPage(@Param("start") int start,@Param("size") int size);*/

    @Update("update user set status = #{status},updated = #{updated} where id=#{id}")
    void updateStatus(User user);

    void addUser(User user);

    //原理: mybatis在进行单值传递时(int等基本类型/string) 取值时名称任意
    //     底层通过下标[0]获取的数据和名称无关.
    @Select("select * from user where id=#{id}")
    User findUserById(Integer id);

    @Update("update user set phone=#{phone},email=#{email} where id=#{id}")
    void updateUser(User user);

    @Delete("delete from user where id=#{id}")
    void deleteUserById(Integer id);
}
