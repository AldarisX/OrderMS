package com.everygamer.orm.dao.mybatis;

import com.everygamer.bean.ItemType;
import com.everygamer.orm.dao.ItemTypeDao;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository("ItemTypeDao")
public class ItemTypeDaoImpl extends SqlSessionDaoSupport implements ItemTypeDao {
    private ItemTypeDao dao;

    @Resource(name = "sqlSessionFactory")
    public void setSuperSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }

    @Override
    public ItemType getItemTypeById(int id) {
        return dao.getItemTypeById(id);
    }

    @Override
    public ItemType getItemTypeByName(String name) {
        return dao.getItemTypeByName(name);
    }

    @Override
    public String getExData(int id) {
        return dao.getExData(id);
    }

    @Override
    public boolean isItemExist(String name) {
        return dao.isItemExist(name);
    }

    @Override
    public List<ItemType> getAllItemType() {
        return dao.getAllItemType();
    }

    @Override
    public int addItemType(String name, String exData) {
        return dao.addItemType(name, exData);
    }

    @Override
    public int updateItemType(int id, String name, int inIndex, String exData) {
        return dao.updateItemType(id, name, inIndex, exData);
    }

    @Override
    public int delItemType(int id) {
        return dao.delItemType(id);
    }

    @Override
    public void init() {
        dao = getSqlSession().getMapper(ItemTypeDao.class);
    }
}
