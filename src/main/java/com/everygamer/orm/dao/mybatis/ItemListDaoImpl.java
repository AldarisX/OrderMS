package com.everygamer.orm.dao.mybatis;

import com.everygamer.bean.BaseItem;
import com.everygamer.orm.dao.ItemListDao;
import com.everygamer.orm.dao.exception.DBUpdateException;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

@Repository("ItemListDao")
public class ItemListDaoImpl extends SqlSessionDaoSupport implements ItemListDao {
    private ItemListDao dao;

    @Resource(name = "sqlSessionFactory")
    public void setSuperSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }

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
    public int mergeExData(int itemType, String exData) {
        return dao.mergeExData(itemType, exData);
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
    public void init() {
        dao = getSqlSession().getMapper(ItemListDao.class);
    }
}
