package com.everygamer.service;

import com.everygamer.bean.BaseItem;
import com.everygamer.bean.ItemType;
import com.everygamer.bean.Manufactor;
import com.everygamer.orm.dao.*;
import com.everygamer.orm.dao.exception.DBUpdateException;
import com.everygamer.service.expection.DataCheckExpection;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

@Service("StoreService")
public class StoreService {
    @Autowired
    @Qualifier("ItemTypeDao")
    private ItemTypeDao itemTypeDao;
    @Autowired
    @Qualifier("ManuDao")
    private ManufactorDao manuDao;
    @Autowired
    @Qualifier("ItemListDao")
    private ItemListDao itemListDao;
    @Autowired
    @Qualifier("ItemListStatisDao")
    private ItemListStatisDao itemListStatisDao;
    @Autowired
    @Qualifier("ItemOutDao")
    private ItemOutDao itemOutDao;
    @Autowired
    @Qualifier("ItemOutStatisDao")
    private ItemOutStatisDao itemOutStatisDao;
    @Autowired
    @Qualifier("StoreDao")
    private StoreDao storeDao;

    /**
     * 向仓库里添加物品
     *
     * @param name     型号(例如RX480-8G-OC)
     * @param itemType 物品类型
     * @param manu     厂商
     * @param model    *名称(可能没什么用)
     * @param num      (数量)
     * @param price    (单价)
     * @param exData   (附加数据)
     * @return
     */
    @Transactional()
    public synchronized int addItem(String name, Integer itemType, Integer manu, String model, Integer num, BigDecimal price, String exData, String desc) throws DBUpdateException {
        int cRows = itemListDao.addItem(name, itemType, manu, model, num, price, exData, desc);

        BaseItem item = itemListStatisDao.isExist(name, itemType, manu, exData);
        if (item != null) {
            itemListStatisDao.updateStatis(item.getId(), num, price);
        } else {
            itemListStatisDao.addStatis(name, itemType, manu, num, price, exData);
        }
        return cRows;
    }

    /**
     * 删除入库记录
     *
     * @param id 入库id
     * @return
     * @throws DBUpdateException
     */
    @Transactional
    public synchronized int[] delItem(int id) throws DBUpdateException {
        //取得入库的数据
        BaseItem item = itemListDao.getItemById(id);
        //从库存记录中删除
        int cRows = itemListDao.delItem(id);
        //从库存统计中减去
        int xRows = itemListStatisDao.splitItem(item.getName(), Integer.parseInt(item.getItemType()), Integer.parseInt(item.getManufactor()), item.getCount(), item.getPrice(), item.getExData());
        //可以不要返回值
        return new int[]{cRows, xRows};
    }

    @Transactional
    public synchronized void itemOut(JSONArray items, String desc) throws DBUpdateException, DataCheckExpection {
        //遍历选择的物品
        for (int i = 0; i < items.size(); i++) {
            JSONObject item = items.getJSONObject(i);
            //当前物品类型
            ItemType itemType = itemTypeDao.getItemTypeByName(item.getString("itemType"));
            //当前物品厂商
            Manufactor manu = manuDao.getManufactor(item.getString("manufactor"), itemType.getId());
            //对应的库存统计统计
            BaseItem itemStatis = itemListStatisDao.isExist(item.getString("name"), itemType.getId(), manu.getId(), item.getString("exData"));
            //正常情况 库存肯定会有记录 如果找不到库存记录那么肯定为异常
            if (itemStatis == null) {
                throw new DataCheckExpection("数据异常，库存中没有对应记录\n" + this.getClass().getName() + "\nitemOut");
            } else {
                //是否计数完成
                boolean countDone = false;
                //移除的数量
                int checkCount = 0;
                //选择的物品
                ArrayList<BaseItem> selectItems = new ArrayList<>();
                //被选择的入库记录的ID
                ArrayList<Integer> selectIds = new ArrayList<>();
                //从入库记录中选取
                while (!countDone) {
                    BaseItem tarItem = itemListDao.getItemByCondition(item.getString("name"), itemType.getId(), manu.getId(), item.getString("exData"), selectIds);
                    //添加到被选择的入库记录的ID中
                    selectIds.add(tarItem.getId());
                    //添加到被选择的物品中
                    selectItems.add(tarItem);
                    //移除物品的数量
                    checkCount += tarItem.getCount();

                    //如果已经移除够了
                    if (checkCount >= item.getInt("count")) {
                        //让循环结束
                        countDone = true;
                    }
                }

                //记到数据库中的 被选择的物品
                JSONArray tarItems = new JSONArray();
                //遍历被选择的物品(选择了多个 不含最后一个的情况)
                for (int j = 0; j < selectItems.size() - 1; j++) {
                    BaseItem tarItem = selectItems.get(j);
                    //将物品从入库记录中扣除
                    itemListDao.splitItem(tarItem.getId(), tarItem.getCount());
                    //将物品从库存统计中扣除
                    itemListStatisDao.splitItem(tarItem.getName(), itemType.getId(), manu.getId(), tarItem.getCount(), tarItem.getPrice(), item.getString("exData"));
                    //出库的物品记录
                    JSONObject tarItemJson = new JSONObject();
                    tarItemJson.accumulate("id", tarItem.getId());
                    tarItemJson.accumulate("count", tarItem.getCount());
                    tarItems.add(tarItemJson);
                }
                //被选择的物品(第一个 或者最后一个的情况)
                if (selectItems.size() >= 1) {
                    BaseItem tarItem = selectItems.get(selectItems.size() - 1);
                    //计算差值
                    int countDiff = tarItem.getCount() - (checkCount - item.getInt("count"));
                    if (countDiff > 0) {
                        itemListDao.splitItem(tarItem.getId(), countDiff);
                        itemListStatisDao.splitItem(tarItem.getName(), itemType.getId(), manu.getId(), countDiff, tarItem.getPrice(), item.getString("exData"));
                    } else if (countDiff == 0) {
                        itemListDao.splitItem(tarItem.getId(), tarItem.getCount());
                        itemListStatisDao.splitItem(tarItem.getName(), itemType.getId(), manu.getId(), tarItem.getCount(), tarItem.getPrice(), item.getString("exData"));
                    } else {
                        //以免万一
                        throw new DataCheckExpection("库存不足!!!!\n" + this.getClass().getName() + "\nitemOut");
                    }
                    JSONObject tarItemJson = new JSONObject();
                    tarItemJson.accumulate("id", tarItem.getId());
                    tarItemJson.accumulate("count", countDiff);
                    tarItems.add(tarItemJson);
                }
                //添加到出库记录
                itemOutDao.itemOut(itemStatis.getId(), tarItems.toString(), item.getInt("count"), BigDecimal.valueOf(item.getDouble("price")), desc);
                BaseItem itemOutStatis = itemOutStatisDao.isExist(item.getString("name"), itemType.getId(), manu.getId(), item.getString("exData"));
                //更新出库统计
                if (itemOutStatis != null) {
                    itemOutStatisDao.updateStatis(itemOutStatis.getId(), item.getInt("count"), BigDecimal.valueOf(item.getDouble("price")));
                } else {
                    BaseItem itemListStatis = itemListStatisDao.isExist(item.getString("name"), itemType.getId(), manu.getId(), item.getString("exData"));
                    itemOutStatisDao.addStatis(itemListStatis.getId(), item.getInt("count"), BigDecimal.valueOf(item.getDouble("price")));
                }
            }
        }
    }

    /**
     * 库存检索
     *
     * @param type     物品类型
     * @param kw       关键字
     * @param manu     厂商
     * @param exData   附加数据
     * @param page     当前页数
     * @param pageSize 每页显示数量
     * @return
     */
    public PageInfo<BaseItem> searchStore(Integer type, String kw, Integer manu, String exData, int page,
                                          int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<BaseItem> items = storeDao.searchStore(type, kw, manu, exData);
        return new PageInfo<>(items);
    }

    public PageInfo<BaseItem> listStore(Integer type, String kw, String manu, String exData, Integer
            startTime, Integer endTime, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        List<BaseItem> items = storeDao.listStore(type, kw, manu, exData, startTime, endTime);
        return new PageInfo<>(items);
    }

    public List<BaseItem> getTopItem(int type, int count) {
        return storeDao.getTopStore(type, count);
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
