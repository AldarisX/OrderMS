package com.everygamer.dao;

import com.everygamer.bean.Manufactor;

import java.util.List;

public interface ManufactorDao {
    List<Manufactor> getAllManufactor();

    List<Manufactor> getAllManufactorByItemType(int itemType);

    List<Manufactor> getAllManufactorByName(String name);

    Manufactor getManufactor(String name, int itemType);

    int addManufactor(String name, int itemType);

    int delManufactor(int id);
}
