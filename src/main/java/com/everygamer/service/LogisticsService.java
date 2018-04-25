package com.everygamer.service;

import com.everygamer.bean.Logistics;
import com.everygamer.dao.LogisticsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LogisticsService {
    @Autowired
    @Qualifier("LogisticsDao")
    private LogisticsDao logisticsDao;

    public List<Logistics> getAllLogistics() {
        return logisticsDao.getAllLogistics();
    }

    public Logistics getLogisticsById(int id) {
        return logisticsDao.getLogisticsById(id);
    }

    @Transactional
    public int addLogistics(String name, Integer order) {
        Logistics eLog = logisticsDao.getLogisticsByName(name);
        if (eLog == null) {
            return logisticsDao.addLogistics(name, order);
        } else {
            return 0;
        }
    }

    @Transactional
    public int updateLogistics(Logistics logistics) {
        Logistics eLog = logisticsDao.getLogisticsByName(logistics.getName());
        if (eLog != null) {
            logistics.setId(eLog.getId());
            return logisticsDao.updateLogistics(logistics);
        } else {
            return 0;
        }
    }

    @Transactional
    public int delLogistics(int id) {
        return logisticsDao.delLogistics(id);
    }
}
