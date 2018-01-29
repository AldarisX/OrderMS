package com.everygamer.orm.dao;

import java.math.BigDecimal;

public interface ItemOutDao extends BaseDao {
    int itemOut(String itemList, int count, BigDecimal price, String desc);
}
