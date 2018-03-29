package com.everygamer.dao.mybatis;

import com.everygamer.bean.OutItem;
import com.everygamer.dao.ItemOutDao;
import com.everygamer.dao.exception.DBUpdateException;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Repository("ItemOutDao")
public class ItemOutDaoImpl extends SqlSessionDaoSupport implements ItemOutDao {
    private ItemOutDao dao;

    @Resource(name = "sqlSessionFactory")
    public void setSuperSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }

    @Override
    public int itemOut(int orderId, int itemListStatisId, String itemList, int count, BigDecimal price, String desc) {
        int cRows = dao.itemOut(orderId, itemListStatisId, itemList, count, price, desc);
        if (cRows <= 0) {
            throw new DBUpdateException("操作失败(返回ID<0),引发类: " + this.getClass().getName() + " 方法: itemOut");
        }
        return cRows;
    }

    @Override
    public OutItem getItemByOrder(int orderId) {
        return dao.getItemByOrder(orderId);
    }

    @Override
    public void init() {
        dao = getSqlSession().getMapper(ItemOutDao.class);
    }
}
