package com.everygamer.dao.mybatis;

import com.everygamer.bean.ItemType;
import com.everygamer.dao.ItemTypeDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ItemTypeDao")
public class ItemTypeDaoImpl implements ItemTypeDao {
    @Autowired
    private ItemTypeDao dao;

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
    public int updateItemTypeExData(int id, String exData) {
        return dao.updateItemTypeExData(id, exData);
    }

    @Override
    public int updateItemType(int id, String name, int inIndex, String exData) {
        return dao.updateItemType(id, name, inIndex, exData);
    }

    @Override
    public int delItemType(int id) {
        return dao.delItemType(id);
    }

}
