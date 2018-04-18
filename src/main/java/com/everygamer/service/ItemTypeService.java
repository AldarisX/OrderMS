package com.everygamer.service;

import com.everygamer.bean.ItemType;
import com.everygamer.dao.ItemListDao;
import com.everygamer.dao.ItemListStatisDao;
import com.everygamer.dao.ItemTypeDao;
import com.everygamer.dao.exception.DBUpdateException;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
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

    public int addItemType(String name, int order, String exData) {
        return itemTypeDao.addItemType(name, order, exData);
    }

    public String getExData(int type) {
        return itemTypeDao.getExData(type);
    }

    @Transactional
    public int updateItemType(Integer id, String name, int inIndex, int order, JSONArray exData) throws DBUpdateException {
        JSONArray exDataJson;
        String exDataStr = null;
        if (exData == null) {
            exDataJson = null;
        } else {
            exDataJson = JSONArray.fromObject(exData);
            exDataStr = exData.toString();
        }

        String tempItemType = itemListDao.getExDataByType(id);
        int cRows = itemTypeDao.updateItemType(id, name, inIndex, order, exDataStr);
        int cRowsItemList = 0;
        if (tempItemType != null && !"{}".equals(tempItemType)) {
            HashMap<String, Object> exDataPatch = calcPatchExData(tempItemType, exDataJson);
            for (Object key : exDataPatch.keySet()) {
                cRowsItemList += itemListDao.mergeExData(id, key, exDataPatch.get(key));
                itemListStatisDao.mergeExData(id, key, exDataPatch.get(key));
            }
        } else {
            cRowsItemList = 0;
        }
        return 1;
    }

    @Transactional
    public int delExDataKey(int id, String key, JSONArray exData) {
        itemTypeDao.updateItemTypeExData(id, exData.toString());
        itemListDao.deleteExDataKey(id, key);
        itemListStatisDao.deleteExDataKey(id, key);
        return 1;
    }

    @Transactional
    public int delItemType(int id) throws DBUpdateException {
        return itemTypeDao.delItemType(id);
    }

    private HashMap calcPatchExData(String existData, JSONArray exDataList) {
        JSONObject existExData = JSONObject.fromObject(existData);
        HashMap exData = new HashMap();
        if (existData != null) {
            for (int i = 0; i < exDataList.size(); i++) {
                JSONObject exDataItem = exDataList.getJSONObject(i);

                String name = exDataItem.getString("name");
                if (existExData.get(name) == null) {
                    Object value = null;
                    switch (exDataItem.getString("type")) {
                        case "text":
                            value = "";
                            break;
                        case "number":
                            value = 0;
                            break;
                        case "checkbox":
                            value = false;
                            break;
                    }
                    exData.put("{\"" + name + "\"}", value);
                }
            }
        }
        return exData;
    }
}
