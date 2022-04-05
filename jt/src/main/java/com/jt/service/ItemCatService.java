package com.jt.service;

import com.jt.pojo.ItemCat;

import java.util.List;

public interface ItemCatService {
    List<ItemCat> findItemCatList(Integer level);

    void saveItem(ItemCat itemCat);

    void deleteItemCat(Integer id, Integer level);

    void updateStatus(ItemCat itemCat);

    void updateItemCat(ItemCat itemCat);
}
