package com.everygamer.dao;

import com.everygamer.bean.BaseItem;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;

@Mapper
public interface ItemOutStatisDao {
    int addStatis(int itemListStatisId, int count, BigDecimal price);

    int updateStatis(int id, int count, BigDecimal price);

    BaseItem isExist(String name, Integer itemType, Integer manu, String exData);
}
