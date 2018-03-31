package com.everygamer.dao.mybatis;

import com.everygamer.bean.Manufactor;
import com.everygamer.dao.ManufactorDao;
import com.everygamer.dao.exception.DBUpdateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("ManuDao")
public class ManufactorImpl implements ManufactorDao {
    @Autowired
    private ManufactorDao dao;

    @Override
    public List<Manufactor> getAllManufactor() {
        return dao.getAllManufactor();
    }

    @Override
    public List<Manufactor> getAllManufactorByItemType(int itemType) {
        return dao.getAllManufactorByItemType(itemType);
    }

    @Override
    public List<Manufactor> getAllManufactorByName(String name) {
        return dao.getAllManufactorByName(name);
    }

    @Override
    public Manufactor getManufactor(String name, int itemType) {
        return dao.getManufactor(name, itemType);
    }

    @Override
    public int addManufactor(String name, int itemType) {
        int cRows = dao.addManufactor(name, itemType);
        if (cRows != 1) {
            throw new DBUpdateException("操作失败(操作数不为1),引发类: " + this.getClass().getName() + " 方法: addManufactor");
        }
        return cRows;
    }

    @Override
    public int delManufactor(int id) {
        int cRows = dao.delManufactor(id);
        if (cRows != 1) {
            throw new DBUpdateException("操作失败(操作数不为1),引发类: " + this.getClass().getName() + " 方法: delManufactor");
        }
        return cRows;
    }
}
