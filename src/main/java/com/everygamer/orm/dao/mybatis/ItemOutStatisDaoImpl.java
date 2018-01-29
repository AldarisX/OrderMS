package com.everygamer.orm.dao.mybatis;

import com.everygamer.bean.BaseItem;
import com.everygamer.orm.dao.ItemOutStatisDao;
import com.everygamer.orm.dao.exception.DBUpdateException;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.math.BigDecimal;

@Repository("ItemOutStatisDao")
public class ItemOutStatisDaoImpl extends SqlSessionDaoSupport implements ItemOutStatisDao {
    private ItemOutStatisDao dao;

    @Resource(name = "sqlSessionFactory")
    public void setSuperSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }

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

    @Override
    public void init() {
        dao = getSqlSession().getMapper(ItemOutStatisDao.class);
    }
}
