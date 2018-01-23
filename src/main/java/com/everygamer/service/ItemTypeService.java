package com.everygamer.service;

import com.everygamer.bean.ItemType;
import com.everygamer.orm.dao.ItemListDao;
import com.everygamer.orm.dao.ItemListStatisDao;
import com.everygamer.orm.dao.ItemTypeDao;
import com.everygamer.orm.dao.exception.DBUpdateException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service("ItemTypeService")
public class ItemTypeService {
    @Autowired
    @Qualifier("ItemTypeDao")
    private ItemTypeDao itemTypeDao;
    @Autowired
    @Qualifier("ItemListStatisDao")
    private ItemListStatisDao itemListStatisDao;
    @Autowired
    @Qualifier("ItemListDao")
    private ItemListDao itemListDao;

    public List<ItemType> getAllItemType() {
        return itemTypeDao.getAllItemType();
    }

    public ItemType getItemTypeById(int id) {
        return itemTypeDao.getItemTypeById(id);
    }

    public ItemType getItemTypeByName(String name) {
        return itemTypeDao.getItemTypeByName(name);
    }

    public int addItemType(String name, String exData) {
        return itemTypeDao.addItemType(name, exData);
    }

    public String getExData(int type) {
        return itemTypeDao.getExData(type);
    }

    @Transactional
    public int updateItemType(Integer id, String name, JSONArray exData) throws DBUpdateException {
        JSONArray exDataJson = JSONArray.fromObject(exData);

        String tempItemType = itemListDao.getExDataByType(id);
        int cRows = itemTypeDao.updateItemType(id, name, exData.toString());
        int cRowsItemList;
        if (tempItemType != null) {
            JSONObject exDataPatch = calcPatchExData(tempItemType, exDataJson);
            cRowsItemList = itemListDao.mergeExData(id, exDataPatch.toString());
            itemListStatisDao.mergeExData(id, exDataPatch.toString());
        } else {
            cRowsItemList = 0;
        }
        return cRowsItemList;
    }

    @Transactional
    public int delItemType(int id) throws DBUpdateException {
        return itemTypeDao.delItemType(id);
    }

    private JSONObject calcPatchExData(String existData, JSONArray exDataList) {
        JSONObject existExData = JSONObject.fromObject(existData);
        JSONObject exData = new JSONObject();
        if (existData != null) {
            for (int i = 0; i < exDataList.size(); i++) {
                JSONObject exDataItem = exDataList.getJSONObject(i);

                String name = exDataItem.getString("name");
                if (existExData.get(name) == null) {
                    switch (exDataItem.getString("type")) {
                        case "text":
                            exData.accumulate(name, "");
                            break;
                        case "number":
                            exData.accumulate(name, 0);
                        case "checkbox":
                            exData.accumulate(name, false);
                            break;
                    }
                }
            }
        }
        return exData;
    }
}
