package com.jt.config;

import com.jt.vo.SysResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
//定义全局异常处理机制 内部封装了AOP
@RestControllerAdvice   //标识Controller层 返回值JSON串
public class SystemException {

    /**
     * 业务说明: 如果后端服务器报错,问应该返回什么数据?
     * 返回值:   SysResult对象(status=201)
     * 异常类型:  运行时异常
     */
    @ExceptionHandler(value = RuntimeException.class)
    public SysResult fail(Exception e){
        //输出异常
        e.printStackTrace();
        return SysResult.fail();
    }
}
