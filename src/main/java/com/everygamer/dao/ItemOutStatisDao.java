package com.everygamer.dao;

import com.everygamer.bean.BaseItem;

import java.math.BigDecimal;

public interface ItemOutStatisDao extends BaseDao {
    int addStatis(int itemListStatisId, int count, BigDecimal price);

    int updateStatis(int id, int count, BigDecimal price);

    BaseItem isExist(String name, Integer itemType, Integer manu, String exData);
}
