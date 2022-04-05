package com.jt.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jt.mapper.ItemCatMapper;
import com.jt.pojo.ItemCat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class ItemCatServiceImpl implements ItemCatService{

    @Autowired
    private ItemCatMapper itemCatMapper;

    /**
     * 利用Map集合封装所有的数据库记录
     * 封装数据:
     *      1.遍历所有的数据信息.
     *      2.获取每一个parentId的值.
     * 例子:
     *      1.{id=1,parentId=0,name="张三"}
     *      2.{id=2,parentId=0,name="李四"}
     *      3.{id=3,parentId=1,name="王五"}
     *      Map= {
     *          key : value
     *          0   : List[张三对象,李四对象.....],
     *          1   : List[王五对象......]
     *      }
     * @return
     */
    public Map<Integer,List<ItemCat>> getMap(){
        Map<Integer,List<ItemCat>> map = new HashMap<>();
        //1.查询所有的数据库信息
        List<ItemCat> itemCatList = itemCatMapper.selectList(null);
        //2.将数据封装到map集合中
        for (ItemCat itemCat : itemCatList){
            Integer key = itemCat.getParentId(); //获取parentId当做key
            //3.判断map集合中是否有值.
            if(map.containsKey(key)){
                //有值: 获取List集合,将自己追加到其中
                map.get(key).add(itemCat);
            }else{
                //没值: 添加数据.将自己作为第一个元素填充
                List<ItemCat> list = new ArrayList<>();
                list.add(itemCat);
                map.put(key,list);
            }
        }
        return map;
    }

    @Override
    public List<ItemCat> findItemCatList(Integer level) {
        long startTime = System.currentTimeMillis();
        Map<Integer,List<ItemCat>> map = getMap();
        //根据level获取子级信息
        if(level == 1){ //只获取一级列表信息
            return map.get(0);
        }
        if(level == 2){ //获取一级和二级数据
            return getTwoList(map);
        }
        List<ItemCat> oneList = getThreeList(map);
        long endTime = System.currentTimeMillis();
        System.out.println("优化前的耗时: 500ms,优化后耗时:"+(endTime - startTime)+"ms");
        return oneList;
    }

    @Override
    @Transactional  //事务控制
    public void saveItem(ItemCat itemCat) {
      /*  Date date = new Date();
        itemCat.setStatus(true).setCreated(date).setUpdated(date);//启动
        itemCatMapper.insert(itemCat);*/
        itemCat.setStatus(true);
        itemCatMapper.insert(itemCat);
    }


    //获取三级列表信息  先获取1级数据,再获取2级数据.再获取3级数据
    private List<ItemCat> getThreeList(Map<Integer, List<ItemCat>> map) {
        //1.调用2级菜单方法.
        List<ItemCat> oneList = getTwoList(map);
        //2.实现思路 遍历一级集合,获取二级数据. 封装三级菜单
        for(ItemCat oneItemCat : oneList){
            //2.1 获取二级数据
            List<ItemCat> twoList = oneItemCat.getChildren();
            if(twoList == null || twoList.size()==0){
                //判断二级集合是否为null.如果为null,表示没有二级菜单.
                //则跳过本次循环,进入下一次循环
                continue;
            }
            for (ItemCat twoItemCat : twoList){
                int twoId = twoItemCat.getId();
                List<ItemCat> threeList = map.get(twoId);
                twoItemCat.setChildren(threeList);
            }
        }
        return oneList;
    }

    //通过map集合 获取一级二级菜单信息.
    private List<ItemCat> getTwoList(Map<Integer, List<ItemCat>> map) {
        List<ItemCat> oneList = map.get(0);
        //获取二级信息,应该先遍历一级集合
        for (ItemCat oneItemCat : oneList){
            int oneId = oneItemCat.getId();
            //根据一级Id,获取二级集合
            List<ItemCat> twoList = map.get(oneId);
            oneItemCat.setChildren(twoList);
        }
        return oneList;
    }


    //删除商品分类数据
    @Override
    @Transactional  //事务控制
    public void deleteItemCat(Integer id, Integer level) {
        //判断是否为3级菜单
        if(level == 3){
            itemCatMapper.deleteById(id);
        }

        if(level == 2){
            //如果是二级,应该先获取三级数据之后删除,再删除自己
            //delete from item_cat where parent_id=#{id} or id = #{id}
            QueryWrapper<ItemCat> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("parent_id",id)
                        .or()
                        .eq("id",id);
            itemCatMapper.delete(queryWrapper);
        }

        /**
         * 如何删除一级菜单?
         *  1.获取二级ID
         *  终极sql: delete from item_cat where
         *          parent_id in (twoIds)
         *          or  id in (twoIds)
         *          or  id = #{id}
         */
        if(level == 1){
            QueryWrapper<ItemCat> queryWrapper = new QueryWrapper();
            queryWrapper.eq("parent_id",id);
            List twoIds = itemCatMapper.selectObjs(queryWrapper);
            //清空数据
            queryWrapper.clear();
            //规则: 如果2级菜单有值,才会删除 2级和三级
            queryWrapper.in(twoIds.size()>0,"parent_id",twoIds)
                        .or()
                        .in(twoIds.size()>0,"id",twoIds)
                        .or()
                        .eq("id",id);
            itemCatMapper.delete(queryWrapper);
        }
    }

    @Override
    @Transactional
    public void updateStatus(ItemCat itemCat) {//id/status
        //itemCat.setUpdated(new Date());
        itemCatMapper.updateById(itemCat);
    }

    //由于页面只修改的name名称.所以sql也只修改name/updated
    @Override
    @Transactional
    public void updateItemCat(ItemCat itemCat) {
        //用户只修改name,updated by id
        ItemCat temp = new ItemCat();
        temp.setId(itemCat.getId())
                .setName(itemCat.getName())
                .setUpdated(new Date());
        itemCatMapper.updateById(temp);
    }


    /**
     * 步骤1.查询一级菜单列表
     * @param level
     * @return
     */
   /* @Override
    public List<ItemCat> findItemCatList(Integer level) {
        long startTime = System.currentTimeMillis();
        //1.查询一级菜单
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("parent_id",0);
        List<ItemCat> oneList = itemCatMapper.selectList(queryWrapper);
        //2.查询二级菜单 二级数据是一级数据的子级 封装到一级数据中.
        for(ItemCat oneItemCat : oneList){
            int oneId = oneItemCat.getId(); //一级对象ID
            //清空原始条件  必须有
            queryWrapper.clear();
            queryWrapper.eq("parent_id",oneId);
            List<ItemCat> twoList = itemCatMapper.selectList(queryWrapper);
            for(ItemCat twoItemCat : twoList){
                //获取二级分类ID
                int twoId = twoItemCat.getId();
                //查询三级列表信息
                queryWrapper.clear();
                queryWrapper.eq("parent_id",twoId);
                List<ItemCat> threeList = itemCatMapper.selectList(queryWrapper);
                //将三级列表 封装到二级对象中
                twoItemCat.setChildren(threeList);
            }
            //将二级数据封装到一级对象中
            oneItemCat.setChildren(twoList);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("耗时:"+ (endTime - startTime)+"ms");
        return oneList;
    }*/

}
