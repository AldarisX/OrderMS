package com.everygamer.control;

import com.everygamer.bean.BaseItem;
import com.everygamer.dao.exception.DBUpdateException;
import com.everygamer.service.StoreService;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;

@Controller
@RequestMapping(value = "/api/itemStore.json", produces = "application/json;charset=UTF-8")
public class ItemStoreControl extends BaseControl {
    @Autowired
    private StoreService storeService;

    @ResponseBody
    @RequestMapping(params = "action=search")
    public String storeSearch(Integer type, Integer manu, String kw, String exData, Integer page, Integer pageSize) {
        page = page == null ? 1 : page;
        pageSize = pageSize == null ? 50 : pageSize;

        JSONObject result = new JSONObject();

        try {
            JSONObject exDataJson = JSONObject.fromObject(exData);
        } catch (JSONException e) {
            exData = null;
        }

        PageInfo<BaseItem> pages = storeService.searchStore(type, kw, manu, exData, page, pageSize);
        result.accumulate("result", true);
        result.accumulate("data", JSONArray.fromObject(pages.getList()));
        result.accumulate("pages", pages.getPages());
        result.accumulate("count", pages.getTotal());

        return result.toString();
    }

    @ResponseBody
    @RequestMapping(params = "action=listStore")
    public String listStore(Integer type, String kw, String manu, String exData, Integer startTime, Integer endTime, Integer page, Integer pageSize) {
        page = page == null ? 1 : page;
        pageSize = pageSize == null ? 50 : pageSize;

        if ("".equals(manu)) {
            manu = null;
        }

        JSONObject result = new JSONObject();

        try {
            JSONObject exDataJson = JSONObject.fromObject(exData);
        } catch (JSONException e) {
            exData = null;
        }

        PageInfo<BaseItem> pages = storeService.listStore(type, kw, manu, exData, startTime, endTime, page, pageSize);
        result.accumulate("result", true);
        result.accumulate("data", JSONArray.fromObject(pages.getList()));
        result.accumulate("pages", pages.getPages());
        result.accumulate("count", pages.getTotal());

        return result.toString();
    }

    @ResponseBody
    @RequestMapping(params = "action=listName")
    public String listName(String term) {
        JSONArray result = JSONArray.fromObject(storeService.listName(null, term, null));

        return result.toString();
    }

    @ResponseBody
    @RequestMapping(params = "action=listModel")
    public String listModel(String term) {
        JSONArray result = JSONArray.fromObject(storeService.listModel(null, term, null));

        return result.toString();
    }

    @ResponseBody
    @RequestMapping(params = "action=addItem")
    public String addItemToStore(String name, Integer itemType, Integer manu, String model, Integer num, BigDecimal price, String exData, String desc) {
        JSONObject result = new JSONObject();

        try {
            int cRows = storeService.addItem(name, itemType, manu, model, num, price, exData, desc);
            result.accumulate("result", true);
        } catch (DBUpdateException e) {
            setResultFalse(result, e);
        }

        return result.toString();
    }

    @ResponseBody
    @RequestMapping(params = "action=delItem")
    public String delItemFormStore(int id) {
        JSONObject result = new JSONObject();
        try {
            storeService.delItem(id);
            result.accumulate("result", true);
        } catch (DBUpdateException e) {
            setResultFalse(result, e);
        }
        return result.toString();
    }

    @ResponseBody
    @RequestMapping(params = "action=itemOut")
    public String itemOut(String items, String desc) {
        JSONObject result = new JSONObject();

        try {
            JSONArray itemsArray = JSONArray.fromObject(items);
            storeService.itemOut(0, itemsArray, desc);
            result.accumulate("result", true);
        } catch (JSONException e) {
            result.accumulate("result", false);
            result.accumulate("msg", "JS异常");
        }

        return result.toString();
    }

    @ResponseBody
    @RequestMapping(params = "action=getTop")
    public String getTop(@RequestParam() Integer type, @RequestParam() Integer count) {
        JSONObject result = new JSONObject();

        result.accumulate("result", true);
        result.accumulate("data", JSONArray.fromObject(storeService.getTopItem(type, count)));

        return result.toString();
    }
}
