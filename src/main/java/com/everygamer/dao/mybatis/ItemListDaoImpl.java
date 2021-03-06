package com.everygamer.dao.mybatis;

import com.everygamer.bean.BaseItem;
import com.everygamer.dao.ItemListDao;
import com.everygamer.dao.exception.DBUpdateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository("ItemListDao")
public class ItemListDaoImpl implements ItemListDao {
    @Autowired
    private ItemListDao dao;

    @Override
    public String getExDataByType(int type) {
        return dao.getExDataByType(type);
    }

    @Override
    public List<BaseItem> getItemByName(String name) {
        return dao.getItemByName(name);
    }

    @Override
    public BaseItem getItemById(int id) {
        return dao.getItemById(id);
    }

    @Override
    public BaseItem getItemByCondition(String name, int type, int manu, String exData, ArrayList<Integer> notIN) {
        return dao.getItemByCondition(name, type, manu, exData, notIN);
    }

    @Override
    public int mergeExData(int itemType, Object key, Object value) {
        return dao.mergeExData(itemType, key, value);
    }

    @Override
    public int deleteExDataKey(int itemType, Object key) {
        return dao.deleteExDataKey(itemType, key);
    }

    @Override
    public int addItem(String name, Integer itemType, Integer manu, String model, Integer num, BigDecimal price, String exData, String desc) {
        int cRows = dao.addItem(name, itemType, manu, model, num, price, exData, desc);
        if (cRows != 1) {
            throw new DBUpdateException("操作失败(操作数不为1),引发类: " + this.getClass().getName() + " 方法: addItem");
        }
        return cRows;
    }

    @Override
    public int delItem(int id) {
        int cRows = dao.delItem(id);
        if (cRows != 1) {
            throw new DBUpdateException("操作失败(操作数不为1),引发类: " + this.getClass().getName() + " 方法: delItem");
        }
        return cRows;
    }

    @Override
    public int splitItem(int id, int count) {
        int cRows = dao.splitItem(id, count);
        if (cRows != 1) {
            throw new DBUpdateException("操作失败(操作数不为1),引发类: " + this.getClass().getName() + " 方法: splitItem");
        }
        return cRows;
    }
}
