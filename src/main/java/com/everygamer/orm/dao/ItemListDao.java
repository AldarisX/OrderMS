package com.everygamer.orm.dao;

import com.everygamer.bean.BaseItem;

import java.math.BigDecimal;
import java.util.List;

public interface ItemListDao extends BaseDao {
    String getExDataByType(int type);

    List<BaseItem> getItemByName(String name);

    BaseItem getItemById(int id);

    int mergeExData(int itemType, String exData);

    int addItem(String name, Integer itemType, Integer manu, String model, Integer num, BigDecimal price, String exData, String desc);

    int delItem(int id);
}
