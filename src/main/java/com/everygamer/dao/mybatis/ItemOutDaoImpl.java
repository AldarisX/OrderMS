package com.everygamer.dao.mybatis;

import com.everygamer.bean.OutItem;
import com.everygamer.dao.ItemOutDao;
import com.everygamer.dao.exception.DBUpdateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("ItemOutDao")
public class ItemOutDaoImpl implements ItemOutDao {
    @Autowired
    private ItemOutDao dao;

    @Override
    public int itemOut(int orderId, String itemList, String desc) {
        int cRows = dao.itemOut(orderId, itemList, desc);
        if (cRows <= 0) {
            throw new DBUpdateException("操作失败(返回ID<0),引发类: " + this.getClass().getName() + " 方法: itemOut");
        }
        return cRows;
    }

    @Override
    public OutItem getItemByOrder(int orderId) {
        return dao.getItemByOrder(orderId);
    }

}
