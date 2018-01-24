package com.everygamer.orm.dao;

import com.everygamer.bean.BaseItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface StoreDao extends BaseDao {
    List<BaseItem> searchStore(@Param("type") Integer type, @Param("kw") String kw, @Param("manu") Integer manu, @Param("exData") Map<String, Object> exData);

    List<BaseItem> listStore(@Param("type") Integer type, @Param("kw") String kw, @Param("manu") String manu, @Param("exData") Map<String, Object> exData, @Param("startTime") Integer startTime, @Param("endTime") Integer endTime);
}
