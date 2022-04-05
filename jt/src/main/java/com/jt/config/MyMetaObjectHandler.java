package com.jt.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    //框架用法说明: MP根据实现类,自动的完成数据的注入. MP框架自动调用
    //metaObject: 指定默认的规则
    @Override
    public void insertFill(MetaObject metaObject) {
        Date date = new Date();
        this.setFieldValByName("created", date, metaObject);
        this.setFieldValByName("updated", date, metaObject);
    }

    @Override
    public void updateFill(MetaObject metaObject) {

        this.setFieldValByName("updated", new Date(), metaObject);
    }
}
