package com.everygamer.dao;

import com.everygamer.bean.ItemType;

import java.util.List;

public interface ItemTypeDao extends BaseDao {
    ItemType getItemTypeById(int id);

    ItemType getItemTypeByName(String name);

    String getExData(int id);

    boolean isItemExist(String name);

    List<ItemType> getAllItemType();

    int addItemType(String name, String exData);

    int updateItemTypeExData(int id, String exData);

    int updateItemType(int id, String name, int inIndex, String exData);

    int delItemType(int id);
}
