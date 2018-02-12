package com.everygamer.orm.dao;

import com.everygamer.bean.BaseItem;

import java.math.BigDecimal;
import java.util.List;

public interface ItemListStatisDao extends BaseDao {
    BaseItem isExist(String name, Integer itemType, Integer manu, String exData);

    List<BaseItem> getTopItem(int type, int size);

    int updateStatis(int id, Integer num, BigDecimal price);

    int addStatis(String name, Integer itemType, Integer manu, Integer num, BigDecimal price, String exData);

    int mergeExData(int itemType, String exData);

    int splitItem(String name, Integer itemType, Integer manu, Integer num, BigDecimal price, String exData);
}
