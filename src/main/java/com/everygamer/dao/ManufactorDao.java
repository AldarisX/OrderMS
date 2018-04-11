package com.everygamer.dao;

import com.everygamer.bean.Manufactor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ManufactorDao {
    List<Manufactor> getAllManufactor();

    List<Manufactor> getAllManufactorByItemType(int itemType);

    List<Manufactor> getAllManufactorByName(String name);

    Manufactor getManufactor(String name, int itemType);

    int addManufactor(String name, int itemType);

    int delManufactor(int id);
}
