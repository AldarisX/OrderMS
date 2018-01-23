package com.everygamer.orm.dao.mybatis;

import com.everygamer.bean.Manufactor;
import com.everygamer.orm.dao.ManufactorDao;
import com.everygamer.orm.dao.exception.DBUpdateException;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

@Repository("ManuDao")
public class ManufactorImpl extends SqlSessionDaoSupport implements ManufactorDao {
    private ManufactorDao dao;

    @Resource(name = "sqlSessionFactory")
    public void setSuperSessionFactory(SqlSessionFactory sqlSessionFactory) {
        super.setSqlSessionFactory(sqlSessionFactory);
    }

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

    @Override
    public void init() {
        dao = getSqlSession().getMapper(ManufactorDao.class);
    }
}
