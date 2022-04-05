package com.jt.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
@TableName("demo_user")
        //对象与表一一对应 如果对象名和表名一致则表名可以省略
public class User implements Serializable {
    @TableId(type = IdType.AUTO)  //主键自增
    private Integer id;
    //@TableField(value = "name") //如果名称与属性一致则注解可以省略
    private String name;
    private Integer age;
    private String sex;
}
