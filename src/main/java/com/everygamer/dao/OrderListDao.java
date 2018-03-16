package com.everygamer.dao;

import com.everygamer.bean.OrderItem;

public interface OrderListDao extends BaseDao {
    int addOrder(OrderItem orderItem);

    OrderItem getOrderById(int id);
}
