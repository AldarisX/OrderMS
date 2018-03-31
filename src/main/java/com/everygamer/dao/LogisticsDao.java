package com.everygamer.dao;

import com.everygamer.bean.Logistics;

import java.util.List;

public interface LogisticsDao extends BaseDao {
    Logistics getLogisticsByName(String name);

    List<Logistics> getAllLogistics();

    int addLogistics(String name);

    int updateLogistics(Logistics logistics);

    int delLogistics(int id);
}
