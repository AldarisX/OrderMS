package com.everygamer.dao;

import com.everygamer.bean.BaseItem;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Mapper
public interface ItemListDao {
    String getExDataByType(int type);

    List<BaseItem> getItemByName(String name);

    BaseItem getItemById(int id);

    BaseItem getItemByCondition(String name, int type, int manu, String exData, ArrayList<Integer> notIN);

    int mergeExData(int itemType, Object key, Object value);

    int deleteExDataKey(int itemType, Object key);

    int addItem(String name, Integer itemType, Integer manu, String model, Integer num, BigDecimal price, String exData, String desc);

    int delItem(int id);

    int splitItem(int id, int count);
}
