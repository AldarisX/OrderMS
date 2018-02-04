package com.everygamer.orm.dao;

import com.everygamer.bean.BaseItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StoreDao extends BaseDao {
    List<BaseItem> searchStore(@Param("type") Integer type, @Param("kw") String kw, @Param("manu") Integer manu, @Param("exData") String exData);

    List<BaseItem> listStore(@Param("type") Integer type, @Param("kw") String kw, @Param("manu") String manu, @Param("exData") String exData, @Param("startTime") Integer startTime, @Param("endTime") Integer endTime);
}
