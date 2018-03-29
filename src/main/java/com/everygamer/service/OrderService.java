package com.everygamer.service;

import com.everygamer.bean.OrderItem;
import com.everygamer.dao.OrderListDao;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {
    @Autowired
    @Qualifier("OrderListDao")
    private OrderListDao orderListDao;
    @Autowired
    private StoreService storeService;

    @Transactional
    public int addOrder(OrderItem order, JSONArray itemList) {
        int orderId = orderListDao.addOrder(order);

        String desc = "".equals(order.getDesc()) ? "来自订单" : order.getDesc();
        storeService.itemOut(orderId, itemList, desc);
        return 1;
    }
}
