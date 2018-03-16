package com.everygamer.dao;

import java.math.BigDecimal;

public interface ItemOutDao extends BaseDao {
    int itemOut(int itemListStatisId, String itemList, int count, BigDecimal price, String desc);
}
