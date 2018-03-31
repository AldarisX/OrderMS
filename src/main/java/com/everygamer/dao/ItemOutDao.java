package com.everygamer.dao;

import com.everygamer.bean.OutItem;

import java.math.BigDecimal;

public interface ItemOutDao {
    int itemOut(int orderId, String itemList, int count, BigDecimal price, String desc);

    OutItem getItemByOrder(int orderId);
}
