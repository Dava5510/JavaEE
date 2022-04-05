package com.jt.controller;

import com.jt.pojo.ItemCat;
import com.jt.service.ItemCatService;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/itemCat")
public class ItemCatController {

    @Autowired
    private ItemCatService itemCatService;

    /**
     * 需求: 查询商品分类信息
     * 参数: /{level}   1一级  2 一二级  3 一二三级
     * url: /itemCat/findItemCatList/{level}  restFul
     * 返回值: SysResult(3级列表信息)
     */
    @GetMapping("findItemCatList/{level}")
    public SysResult findItemCatList(@PathVariable Integer level){

        List<ItemCat> itemCatList = itemCatService.findItemCatList(level);
        return SysResult.success(itemCatList);
    }

    /**
     * 业务需求:  实现商品分类新增
     * URL:  /itemCat/saveItemCat
     * 类型: post
     * 参数: {"name":"AAAAAA","parentId":0,"level":1} json串
     * 返回值: SysResult对象
     */
    @PostMapping("/saveItemCat")
    public SysResult saveItem(@RequestBody ItemCat itemCat){

        itemCatService.saveItem(itemCat);
        return SysResult.success();
    }


    /**
     * 完成商品分类的删除操作
     * 1. 编辑URL: /itemCat/deleteItemCat
     * 2. 参数: id/level
     * 3. 返回值: SysResult()
     */
    @DeleteMapping("/deleteItemCat")
    public SysResult deleteItemCat(Integer id,Integer level){

        itemCatService.deleteItemCat(id,level);
        return SysResult.success();
    }

    /**
     * 实现商品分类状态修改
     * URL: /itemCat/status/{id}/{status}
     * 请求类型: PUT/POST
     * 请求参数: id/status
     * 返回值: SysResult对象
     */
    @PutMapping("/status/{id}/{status}")
    public  SysResult updateStatus(ItemCat itemCat){

        itemCatService.updateStatus(itemCat);
        return SysResult.success();
    }

    /**
     * 修改商品分类名称
     * URL: /itemCat/updateItemCat
     * 参数: 整个form表单  JSON串
     * 返回值: SysResult对象
     */
    @PutMapping("/updateItemCat")
    public SysResult updateItemCat(@RequestBody ItemCat itemCat){

        itemCatService.updateItemCat(itemCat);
        return SysResult.success();
    }


}
