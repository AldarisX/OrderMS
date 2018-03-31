package com.everygamer.dao.mybatis;

import com.everygamer.bean.OrderItem;
import com.everygamer.dao.OrderListDao;
import com.everygamer.dao.exception.DBUpdateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("OrderListDao")
public class OrderListDaoImpl implements OrderListDao {
    @Autowired
    private OrderListDao dao;

    @Override
    public int addOrder(OrderItem orderItem) {
        int cRows = dao.addOrder(orderItem);
        if (cRows <= 0) {
            throw new DBUpdateException("操作失败(返回ID<0),引发类: " + this.getClass().getName() + " 方法: addOrder");
        }
        return 1;
    }

    @Override
    public OrderItem getOrderById(int id) {
        return dao.getOrderById(id);
    }

}
