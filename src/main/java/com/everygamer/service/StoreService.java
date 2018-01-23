package com.everygamer.service;

import com.everygamer.bean.BaseItem;
import com.everygamer.orm.dao.ItemListDao;
import com.everygamer.orm.dao.ItemListStatisDao;
import com.everygamer.orm.dao.StoreDao;
import com.everygamer.orm.dao.exception.DBUpdateException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@Service("StoreService")
public class StoreService {
    @Autowired
    @Qualifier("ItemListDao")
    private ItemListDao itemListDao;
    @Autowired
    @Qualifier("ItemListStatisDao")
    private ItemListStatisDao itemListStatisDao;
    @Autowired
    @Qualifier("StoreDao")
    private StoreDao storeDao;

    /**
     * 向仓库里添加物品
     *
     * @param name     型号(例如RX480-8G-OC)
     * @param itemType 物品类型
     * @param manu     厂商
     * @param model    *名称(Gamer什么什么的)
     * @param num      (数量)
     * @param price    (单价)
     * @param exData   (附加数据)
     * @return
     */
    @Transactional()
    public synchronized int addItem(String name, Integer itemType, Integer manu, String model, Integer num, BigDecimal price, String exData, String desc) throws DBUpdateException {
        int cRows = itemListDao.addItem(name, itemType, manu, model, num, price, exData, desc);

        BaseItem item = itemListStatisDao.isExist(name, itemType, manu, model, exData);
        if (item != null) {
            itemListStatisDao.updateStatis(item.getId(), num, price);
        } else {
            itemListStatisDao.addStatis(name, itemType, manu, model, num, price, exData);
        }

        return cRows;
    }

    @Transactional
    public synchronized int[] delItem(int id) throws DBUpdateException {
        BaseItem item = itemListDao.getItemById(id);
        int cRows = itemListDao.delItem(id);
        int xRows = itemListStatisDao.splitItem(item.getName(), Integer.parseInt(item.getItemType()), Integer.parseInt(item.getManufactor()), item.getModel(), item.getCount(), item.getPrice(), item.getExData());
        return new int[]{cRows, xRows};
    }

    public PageInfo<BaseItem> searchStore(int type, String kw, Integer manu, String exData, int page, int pageSize) {
        HashMap<String, Object> exDataList = null;
        if (exData != null) {
            exDataList = getJsonSQL(exData);
        }

        PageHelper.startPage(page, pageSize);
        List<BaseItem> items = storeDao.searchStore(type, kw, manu, exDataList);
        return new PageInfo<>(items);
    }

    public PageInfo<BaseItem> listStore(Integer type, String kw, String manu, String exData, Integer startTime, Integer endTime, int page, int pageSize) {
        HashMap<String, Object> exDataList = null;
        if (exData != null) {
            exDataList = getJsonSQL(exData);
        }

        PageHelper.startPage(page, pageSize);
        List<BaseItem> items = storeDao.listStore(type, kw, manu, exDataList, startTime, endTime);
        return new PageInfo<>(items);
    }

    /**
     * 将JSONObject字符串转化成HashMap
     *
     * @param jsonString JSONObject字符串
     * @return HashMap
     */
    private HashMap<String, Object> getJsonSQL(String jsonString) {
        HashMap<String, Object> jsonData = new HashMap<>();
        JSONObject exDataJson = JSONObject.fromObject(jsonString);
        Iterator<String> keys = exDataJson.keys();
        while (keys.hasNext()) {
            String key = keys.next();
            jsonData.put(key, exDataJson.get(key));
        }
        return jsonData;
    }
}
