package com.everygamer.control;

import com.everygamer.bean.BaseItem;
import com.everygamer.dao.exception.DBUpdateException;
import com.everygamer.service.StoreService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@Api(description = "库存")
@RestController
@RequestMapping(value = "/api/store", produces = "application/json;charset=UTF-8")
public class ItemStoreControl extends BaseControl {
    @Autowired
    private StoreService storeService;

    @ApiOperation(value = "搜索库存", notes = "搜索库存", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "物品类型", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "manu", value = "厂商", required = false, dataType = "Integer"),
            @ApiImplicitParam(name = "kw", value = "关键字", required = false, dataType = "String"),
            @ApiImplicitParam(name = "exData", value = "物品附加数据", required = false, dataType = "JSONObject"),
            @ApiImplicitParam(name = "page", value = "当前页", required = false, dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false, dataType = "Integer")
    })
    @ResponseBody
    @RequestMapping(value = "/search", method = RequestMethod.POST)
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

    @ApiOperation(value = "入库记录", notes = "列出入库记录", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "物品类型", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "manu", value = "厂商", required = false, dataType = "Integer"),
            @ApiImplicitParam(name = "kw", value = "关键字", required = false, dataType = "String"),
            @ApiImplicitParam(name = "exData", value = "物品附加数据", required = false, dataType = "JSONObject"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = false, dataType = "Integer"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = false, dataType = "Integer"),
            @ApiImplicitParam(name = "page", value = "当前页", required = false, dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false, dataType = "Integer")
    })
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String listStore(Integer type, String manu, String kw, String exData, Integer startTime, Integer endTime, Integer page, Integer pageSize) {
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

    @ApiOperation(value = "按型号搜索型号", notes = "按型号搜索型号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "term", value = "关键字", required = true, dataType = "String", paramType = "path"),
    })
    @ResponseBody
    @RequestMapping(value = "/search/name", method = RequestMethod.GET)
    public String listName(String term) {
        JSONArray result = JSONArray.fromObject(storeService.listName(null, term, null));

        return result.toString();
    }

    @ApiOperation(value = "按名字搜索名字", notes = "按名字搜索名字")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "term", value = "关键字", required = true, dataType = "String", paramType = "path"),
    })
    @ResponseBody
    @RequestMapping(value = "/search/model", method = RequestMethod.GET)
    public String listModel(String term) {
        JSONArray result = JSONArray.fromObject(storeService.listModel(null, term, null));

        return result.toString();
    }

    @ApiOperation(value = "入库", notes = "入库", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "型号", required = true, dataType = "String"),
            @ApiImplicitParam(name = "itemType", value = "物品类型ID", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "manu", value = "厂商ID", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "model", value = "名称", required = true, dataType = "String"),
            @ApiImplicitParam(name = "num", value = "数量", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "price", value = "价格", required = true, dataType = "BigDecimal"),
            @ApiImplicitParam(name = "exData", value = "附加数据", required = true, dataType = "JSONObject"),
            @ApiImplicitParam(name = "desc", value = "备注", required = false, dataType = "String")
    })
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
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

    @ApiOperation(value = "删除入库记录", notes = "删除入库记录", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "入库记录ID", required = true, dataType = "int", paramType = "path")
    })
    @ResponseBody
    @RequestMapping(value = "/{id}/del", method = RequestMethod.POST)
    public String delItemFormStore(@PathVariable int id) {
        JSONObject result = new JSONObject();
        try {
            storeService.delItem(id);
            result.accumulate("result", true);
        } catch (DBUpdateException e) {
            setResultFalse(result, e);
        }
        return result.toString();
    }

    @ApiOperation(value = "查询库存最大的物品", notes = "查询库存最大的物品", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "物品类型ID", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "count", value = "要查询的数量", required = true, dataType = "Integer")
    })
    @ResponseBody
    @RequestMapping(value = "/top", method = RequestMethod.GET)
    public String getTop(Integer type, Integer count) {
        JSONObject result = new JSONObject();

        result.accumulate("result", true);
        result.accumulate("data", JSONArray.fromObject(storeService.getTopItem(type, count)));

        return result.toString();
    }
}
