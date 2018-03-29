package com.everygamer.dao;

import com.everygamer.bean.OutItem;

import java.math.BigDecimal;

public interface ItemOutDao extends BaseDao {
    int itemOut(int orderId, int itemListStatisId, String itemList, int count, BigDecimal price, String desc);

    OutItem getItemByOrder(int orderId);
}
