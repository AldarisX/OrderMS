package com.everygamer.dao;

import com.everygamer.bean.OrderItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface OrderListDao {
    List<OrderItem> listOrder(@Param("userName") String userName, @Param("phone") String phone, @Param("state") String state, @Param("startTime") Integer startTime, @Param("endTime") Integer endTime);

    OrderItem getOrderById(int id);

    int addOrder(OrderItem orderItem);
}
