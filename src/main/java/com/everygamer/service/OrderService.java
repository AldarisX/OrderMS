package com.everygamer.service;

import com.everygamer.bean.ItemType;
import com.everygamer.bean.OrderItem;
import com.everygamer.bean.OrderState;
import com.everygamer.dao.ItemTypeDao;
import com.everygamer.dao.OrderListDao;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    @Qualifier("OrderListDao")
    private OrderListDao orderListDao;
    @Autowired
    @Qualifier("ItemTypeDao")
    private ItemTypeDao itemTypeDao;
    @Autowired
    private StoreService storeService;

    public OrderItem getOrderById(Integer id) {
        return orderListDao.getOrderById(id);
    }

    public PageInfo<OrderItem> listOrders(String userName, String phone, Integer state, Integer startTime, Integer endTime, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<OrderItem> datas = orderListDao.listOrder(userName, phone, state, startTime, endTime);
        return new PageInfo<>(datas);
    }

    @Transactional
    public int addOrder(OrderItem order) {
        JSONArray itemList = JSONArray.fromObject(order.getItemStatisList());
        for (int i = 0; i < itemList.size(); i++) {
            JSONObject item = itemList.getJSONObject(i);
            item.remove("id");
            item.remove("desc");
            item.remove("remain");
            item.remove("upDate");
            item.remove("delDate");
            item.remove("insDate");
            item.remove("isAlive");
            ItemType itemType = itemTypeDao.getItemTypeByName(item.getString("itemType"));
            item.accumulate("exDataStr", itemType.getExData());
        }
        order.setItemStatisList(itemList.toString());

        int cRows = orderListDao.addOrder(order);

        String desc = "来自订单" + order.getId();
        storeService.itemOut(order.getId(), JSONArray.fromObject(order.getItemStatisList()), desc);
        return cRows;
    }

    public int updateState(Integer id, OrderState state) {
        int cRows = orderListDao.updateState(id, state.getId());
        return cRows;
    }
}
