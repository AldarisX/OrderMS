package com.everygamer.control;

import com.everygamer.bean.BaseItem;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@Api(description = "出库")
@RestController
@RequestMapping(value = "/api/sale", produces = "application/json;charset=UTF-8")
public class ItemOutControl {
    @Autowired
    private StoreService storeService;

    @ApiOperation(value = "出库", notes = "出库", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "items", value = "物品的JSON", required = true, dataType = "JSONObject"),
            @ApiImplicitParam(name = "desc", value = "备注", required = true, dataType = "String")
    })
    @ResponseBody
    @RequestMapping(value = "/out", method = RequestMethod.POST)
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

    @ApiOperation(value = "出库记录", notes = "列出出库记录", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "物品类型", required = false, dataType = "Integer"),
            @ApiImplicitParam(name = "manu", value = "厂商", required = false, dataType = "String"),
            @ApiImplicitParam(name = "kw", value = "关键字", required = false, dataType = "String"),
            @ApiImplicitParam(name = "exData", value = "附加数据", required = false, dataType = "JSONObject"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = false, dataType = "Integer"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = false, dataType = "Integer"),
            @ApiImplicitParam(name = "page", value = "当前页", required = true, dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = true, dataType = "Integer")
    })
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String listItemOut(Integer type, String manu, String kw, String exData, Integer startTime, Integer endTime, Integer page, Integer pageSize) {
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

        PageInfo<BaseItem> pages = storeService.listItemOut(type, kw, manu, exData, startTime, endTime, page, pageSize);
        result.accumulate("result", true);
        result.accumulate("data", JSONArray.fromObject(pages.getList()));
        result.accumulate("pages", pages.getPages());
        result.accumulate("count", pages.getTotal());

        return result.toString();
    }
}
