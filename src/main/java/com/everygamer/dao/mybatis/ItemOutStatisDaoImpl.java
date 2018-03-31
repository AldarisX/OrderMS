package com.everygamer.dao.mybatis;

import com.everygamer.bean.BaseItem;
import com.everygamer.dao.ItemOutStatisDao;
import com.everygamer.dao.exception.DBUpdateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository("ItemOutStatisDao")
public class ItemOutStatisDaoImpl implements ItemOutStatisDao {
    @Autowired
    private ItemOutStatisDao dao;

    @Override
    public int addStatis(int itemListStatisId, int count, BigDecimal price) {
        int cRows = dao.addStatis(itemListStatisId, count, price);
        if (cRows != 1) {
            throw new DBUpdateException("操作失败(操作数不为1),引发类: " + this.getClass().getName() + " 方法: addStatis");
        }
        return cRows;
    }

    @Override
    public int updateStatis(int id, int count, BigDecimal price) {
        int cRows = dao.updateStatis(id, count, price);
        if (cRows != 1) {
            throw new DBUpdateException("操作失败(操作数不为1),引发类: " + this.getClass().getName() + " 方法: updateStatis");
        }
        return cRows;
    }

    @Override
    public BaseItem isExist(String name, Integer itemType, Integer manu, String exData) {
        return dao.isExist(name, itemType, manu, exData);
    }

}
