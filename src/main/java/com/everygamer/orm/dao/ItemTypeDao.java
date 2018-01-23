package com.everygamer.orm.dao;

import com.everygamer.bean.ItemType;

import java.util.List;

public interface ItemTypeDao extends BaseDao {
    ItemType getItemTypeById(int id);

    ItemType getItemTypeByName(String name);

    String getExData(int id);

    boolean isItemExist(String name);

    List<ItemType> getAllItemType();

    int addItemType(String name, String exData);

    int updateItemType(int id, String name, String exData);

    int delItemType(int id);
}
