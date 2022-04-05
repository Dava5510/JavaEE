package com.jt.controller;

import com.jt.pojo.Rights;
import com.jt.service.RightsService;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/rights")
public class RightsController {

    @Autowired
    private RightsService rightsService;

    /**
     * 查询一级二级数据
     * URL: /rights/getRightsList
     * 参数: 无
     * 返回值: SysResult()
     */
    @GetMapping("/getRightsList")
    public SysResult getRightsList(){
        //返回的是一级菜单数据(children 包含二级数据)
        List<Rights> rights = rightsService.getRightsList();
        return SysResult.success(rights);
    }
}
