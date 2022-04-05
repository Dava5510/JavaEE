package com.jt.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jt.mapper.ItemDescMapper;
import com.jt.mapper.ItemMapper;
import com.jt.pojo.Item;
import com.jt.pojo.ItemDesc;
import com.jt.vo.ItemVO;
import com.jt.vo.PageResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class ItemServiceImpl implements ItemService{

    @Autowired
    private ItemMapper itemMapper;
    @Autowired
    private ItemDescMapper itemDescMapper;

    /**
     *  要求: 3+2(总记录数,分页结果)
     *  关于selectPage(参数说明)
     *   参数1: page MP提供的分页对象
     *   参数2: 条件构造器
     * @param pageResult
     * @return
     */
    @Override
    public PageResult getItemList(PageResult pageResult) {
        //1.构建分页对象  参数1: 第几页   参数2: 多少条
        Page<Item> page = new Page<>(pageResult.getPageNum(),pageResult.getPageSize());
        //2.准备条件构造器 构建模糊查询
        QueryWrapper queryWrapper = new QueryWrapper();
        String query = pageResult.getQuery();
        boolean flag = StringUtils.hasLength(query);
        queryWrapper.like(flag,"title",query);

        //3.根据MP查询 实现分页数据的自动封装
        page = itemMapper.selectPage(page,queryWrapper);

        //4.获取数据,返回分页对象
        long total = page.getTotal();
        //获取分页结果
        List<Item> rows = page.getRecords();
        return pageResult.setTotal(total).setRows(rows);
    }

    @Override
    @Transactional //控制事务
    public void updateItemStatus(Item item) {

        itemMapper.updateById(item);
    }

    @Override
    @Transactional
    public void deleteItemById(Integer id) {

        itemMapper.deleteById(id);
        itemDescMapper.deleteById(id);
    }

    /**
     * 问题: id是主键自增. 入库之后才有主键所以
     *      应该让主键动态回显
     * 1.Mybatis 动态实现回显
     *      <insert id="xxxx" useGeneratedKeys="true" keyColumn="id" keyProperty="id">
     *         insertinto xxxx
     *     </insert>
     * 2.MP是mybatis的增强版本.所以可以实现自动的主键回显!!!
     * @param itemVO
     */
    @Override
    @Transactional
    public void saveItem(ItemVO itemVO) {
        Item item = itemVO.getItem();
        //设定状态
        item.setStatus(true);
        itemMapper.insert(item); //数据信息自动回显
        //获取商品详情
        ItemDesc itemDesc = itemVO.getItemDesc();
        itemDesc.setId(item.getId());
        itemDescMapper.insert(itemDesc);
    }
}
