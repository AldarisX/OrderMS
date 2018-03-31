package com.everygamer.dao;

import com.everygamer.bean.BaseItem;

import java.math.BigDecimal;

public interface ItemListStatisDao {
    BaseItem isExist(String name, Integer itemType, Integer manu, String exData);

    int updateStatis(int id, Integer num, BigDecimal price);

    int addStatis(String name, Integer itemType, Integer manu, Integer num, BigDecimal price, String exData);

    int mergeExData(int itemType, Object key, Object value);

    int deleteExDataKey(int itemType, Object key);

    int splitItem(String name, Integer itemType, Integer manu, Integer num, BigDecimal price, String exData);
}
