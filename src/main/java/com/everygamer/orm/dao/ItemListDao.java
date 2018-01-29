package com.everygamer.orm.dao;

import com.everygamer.bean.BaseItem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public interface ItemListDao extends BaseDao {
    String getExDataByType(int type);

    List<BaseItem> getItemByName(String name);

    BaseItem getItemById(int id);

    BaseItem getItemByCondition(String name, int type, int manu, String exData, ArrayList<Integer> notIN);

    int mergeExData(int itemType, String exData);

    int addItem(String name, Integer itemType, Integer manu, String model, Integer num, BigDecimal price, String exData, String desc);

    int delItem(int id);

    int splitItem(int id, int count);
}
