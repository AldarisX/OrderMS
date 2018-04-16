package com.everygamer.dao.mybatis;

import com.everygamer.bean.Logistics;
import com.everygamer.dao.LogisticsDao;
import com.everygamer.dao.exception.DBUpdateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("LogisticsDao")
public class LogisticsDaoImpl implements LogisticsDao {
    @Autowired
    private LogisticsDao dao;

    @Override
    public Logistics getLogisticsByName(String name) {
        return dao.getLogisticsByName(name);
    }

    @Override
    public Logistics getLogisticsById(int id) {
        return dao.getLogisticsById(id);
    }

    @Override
    public List<Logistics> getAllLogistics() {
        return dao.getAllLogistics();
    }

    @Override
    public int addLogistics(String name) {
        int cRows = dao.addLogistics(name);
        if (cRows != 1) {
            throw new DBUpdateException("操作失败(操作数不为1),引发类: " + this.getClass().getName() + " 方法: addLogistics");
        }
        return cRows;
    }

    @Override
    public int updateLogistics(Logistics logistics) {
        int cRows = dao.updateLogistics(logistics);
        if (cRows != 1) {
            throw new DBUpdateException("操作失败(操作数不为1),引发类: " + this.getClass().getName() + " 方法: updateLogistics");
        }
        return cRows;
    }

    @Override
    public int delLogistics(int id) {
        int cRows = dao.delLogistics(id);
        if (cRows != 1) {
            throw new DBUpdateException("操作失败(操作数不为1),引发类: " + this.getClass().getName() + " 方法: delLogistics");
        }
        return cRows;
    }

}
