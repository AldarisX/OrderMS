package com.everygamer.dao;

import com.everygamer.bean.OutItem;
import org.apache.ibatis.annotations.Mapper;

import java.math.BigDecimal;

@Mapper
public interface ItemOutDao {
    int itemOut(int orderId, String itemList, int count, BigDecimal price, String desc);

    OutItem getItemByOrder(int orderId);
}
