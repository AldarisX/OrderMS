package com.everygamer.orm.dao;

import com.everygamer.bean.BaseItem;

import java.math.BigDecimal;

public interface ItemListStatisDao extends BaseDao {
    BaseItem isExist(String name, Integer itemType, Integer manu, String model, String exData);

    int updateStatis(int id, Integer num, BigDecimal price);

    int addStatis(String name, Integer itemType, Integer manu, String model, Integer num, BigDecimal price, String exData);

    int mergeExData(int itemType, String exData);

    int splitItem(String name, Integer itemType, Integer manu, String model, Integer num, BigDecimal price, String exData);
}
