package com.everygamer.dao;

import com.everygamer.bean.OutItem;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ItemOutDao {
    int itemOut(int orderId, String itemList, String desc);

    OutItem getItemByOrder(int orderId);
}
