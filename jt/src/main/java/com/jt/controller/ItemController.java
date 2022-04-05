package com.jt.controller;

import com.jt.pojo.Item;
import com.jt.service.ItemService;
import com.jt.vo.ItemVO;
import com.jt.vo.PageResult;
import com.jt.vo.SysResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/item")
public class ItemController {

    @Autowired
    private ItemService itemService;

    /**
     * 业务: 实现商品的分页查询
     * URL: /item/getItemList?query=&pageNum=1&pageSize=10
     * 参数: query=&pageNum=1&pageSize=10
     * 返回值: SysResult(PageResult)
     */
    @GetMapping("/getItemList")
    public SysResult getItemList(PageResult pageResult){//3
        //3+2(总记录数,分页结果)
        pageResult  = itemService.getItemList(pageResult);
        return SysResult.success(pageResult);//5
    }

    /**
     * 修改商品的状态信息
     * URL: /item/updateItemStatus
     * 参数: JSON串 {id:xx,status:xx}
     * 返回值: SysResult对象
     */
    @PutMapping("/updateItemStatus")
    public SysResult updateItemStatus(@RequestBody Item item){

        itemService.updateItemStatus(item);
        return SysResult.success();
    }

    /**
     * 业务需求: 根据Id 删除数据
     * URL: /item/deleteItemById
     * 参数: id
     * 返回值: SysResult对象
     */
    @DeleteMapping("/deleteItemById")
    public SysResult deleteItemById(Integer id){

        itemService.deleteItemById(id);
        return SysResult.success();
    }

    /**
     * 完成商品新增操作
     * 1.URL地址  http://localhost:8091/item/saveItem
     * 2.参数     post   itemVO JSON串
     * 3.返回值   SysResult对象
     */
    @PostMapping("/saveItem")
    public SysResult saveItem(@RequestBody ItemVO itemVO){

        itemService.saveItem(itemVO);
        return SysResult.success();
    }
}
