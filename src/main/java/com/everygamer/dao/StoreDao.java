package com.everygamer.dao;

import com.everygamer.bean.BaseItem;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface StoreDao {
    List<BaseItem> searchStore(@Param("type") Integer type, @Param("kw") String kw, @Param("manu") Integer manu, @Param("exData") String exData);

    List<BaseItem> listStore(@Param("type") Integer type, @Param("kw") String kw, @Param("manu") String manu, @Param("exData") String exData, @Param("startTime") Integer startTime, @Param("endTime") Integer endTime);

    List<String> listName(@Param("type") Integer type, @Param("name") String name, @Param("exData") String exData);

    List<String> listModel(@Param("type") Integer type, @Param("model") String name, @Param("exData") String exData);

    List<BaseItem> getTopStore(@Param("type") Integer type, @Param("count") int count);
}
