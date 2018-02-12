package com.everygamer.orm.dao.mybatis;

import com.everygamer.bean.BaseItem;
import com.everygamer.orm.dao.ItemListStatisDao;
import com.everygamer.orm.dao.exception.DBUpdateException;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Repository("ItemListStatisDao")
public class ItemListStatisticsDaoImpl extends SqlSessionDaoSupport implements ItemListStatisDao {
    private ItemListStatisDao dao;

    @Resource(name = "sqlSessionFactory")
    public void setSuperSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }

    @Override
    public BaseItem isExist(String name, Integer itemType, Integer manu, String exData) {
        return dao.isExist(name, itemType, manu, exData);
    }

    @Override
    public List<BaseItem> getTopItem(int type, int size) {
        return dao.getTopItem(type, size);
    }

    @Override
    public int updateStatis(int id, Integer num, BigDecimal price) {
        int cRows = dao.updateStatis(id, num, price);
        if (cRows != 1) {
            throw new DBUpdateException("操作失败(操作数不为1),引发类: " + this.getClass().getName() + " 方法: updateStatis");
        }
        return cRows;
    }

    @Override
    public int addStatis(String name, Integer itemType, Integer manu, Integer num, BigDecimal price, String exData) {
        int cRows = dao.addStatis(name, itemType, manu, num, price, exData);
        if (cRows != 1) {
            throw new DBUpdateException("操作失败(操作数不为1),引发类: " + this.getClass().getName() + " 方法: addStatis");
        }
        return cRows;
    }

    @Override
    public int mergeExData(int itemType, String exData) {
        return dao.mergeExData(itemType, exData);
    }

    @Override
    public int splitItem(String name, Integer itemType, Integer manu, Integer num, BigDecimal price, String exData) {
        int cRows = dao.splitItem(name, itemType, manu, num, price, exData);
        if (cRows != 1) {
            throw new DBUpdateException("操作失败(操作数不为1),引发类: " + this.getClass().getName() + " 方法: splitItem");
        }
        return cRows;
    }

    @Override
    public void init() {
        dao = getSqlSession().getMapper(ItemListStatisDao.class);
    }
}
