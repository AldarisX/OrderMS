package com.everygamer.service;

import com.everygamer.bean.Manufactor;
import com.everygamer.dao.ManufactorDao;
import com.everygamer.dao.exception.DBUpdateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("ManuService")
public class ManuService {
    @Autowired
    @Qualifier("ManuDao")
    private ManufactorDao manuDao;

    public List<Manufactor> getAllManu() {
        return manuDao.getAllManufactor();
    }

    public List<Manufactor> getAllManuByItemType(int itemType) {
        return manuDao.getAllManufactorByItemType(itemType);
    }

    public List<Manufactor> getAllManuByName(String name) {
        return manuDao.getAllManufactorByName(name);
    }

    public Manufactor getManu(String name, Integer itemTypeId) {
        return manuDao.getManufactor(name, itemTypeId);
    }

    @Transactional
    public int addManu(String name, Integer itemTypeId) throws DBUpdateException {
        return manuDao.addManufactor(name, itemTypeId);
    }

    @Transactional
    public int delManu(int id) throws DBUpdateException {
        return manuDao.delManufactor(id);
    }
}
