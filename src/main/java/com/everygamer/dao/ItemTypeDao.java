package com.everygamer.dao;

import com.everygamer.bean.ItemType;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ItemTypeDao {
    ItemType getItemTypeById(int id);

    ItemType getItemTypeByName(String name);

    String getExData(int id);

    boolean isItemExist(String name);

    List<ItemType> getAllItemType();

    int addItemType(String name, int order, String exData);

    int updateItemTypeExData(int id, String exData);

    int updateItemType(int id, String name, int inIndex, int order, String exData);

    int delItemType(int id);
}
