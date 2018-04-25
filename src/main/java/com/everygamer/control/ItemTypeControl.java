package com.everygamer.control;

import com.everygamer.bean.ItemType;
import com.everygamer.dao.exception.DBUpdateException;
import com.everygamer.service.ItemTypeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(description = "物品类型")
@RestController
@RequestMapping(value = "/api/itype", produces = "application/json;charset=UTF-8")
public class ItemTypeControl extends BaseControl {
    @Autowired
    private ItemTypeService itemTypeService;

    @ApiOperation(value = "查询全部物品类型", notes = "查询全部物品类型", consumes = "application/x-www-form-urlencoded")
    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getAllItemType() {
        JSONObject result = new JSONObject();

        List<ItemType> itemTypeList = itemTypeService.getAllItemType();
        result.accumulate("result", true);
        result.accumulate("data", JSONArray.fromObject(itemTypeList));
        return result.toString();
    }

    @ApiOperation(value = "查询物品类型", notes = "按ID查询物品类型", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "物品类型ID", required = true, dataType = "Integer", paramType = "path")
    })
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getItemTypeById(@PathVariable Integer id) {
        JSONObject result = new JSONObject();
        if (id > 0) {
            ItemType itemType = itemTypeService.getItemTypeById(id);
            result.accumulate("result", true);
            result.accumulate("data", JSONObject.fromObject(itemType));
        } else {
            result.accumulate("result", false);
            result.accumulate("msg", "JS错误,ID不正确\nID=" + id);
        }
        return result.toString();
    }

    @ApiOperation(value = "添加物品类型", notes = "添加物品类型", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "物品类型名称", required = true, dataType = "String"),
            @ApiImplicitParam(name = "order", value = "物品类型优先级", required = true, dataType = "int"),
            @ApiImplicitParam(name = "exData", value = "物品类型的附加数据", required = false, dataType = "JSONArray")
    })
    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String addItemType(String name, int order, String exData) {
        JSONObject result = new JSONObject();

        if ("\"\"".equals(exData)) {
            exData = null;
        }

        ItemType itemType = itemTypeService.getItemTypeByName(name);
        if (itemType == null) {
            int cRows = itemTypeService.addItemType(name, order, exData);
            if (cRows == 1) {
                result.accumulate("result", true);
                result.accumulate("msg", "添加成功");
            } else {
                result.accumulate("result", false);
                result.accumulate("msg", "添加物品类型时出现异常\n请立即检查数据是否完好");
            }
        } else {
            result.accumulate("result", false);
            result.accumulate("msg", "已经存在当前物品类型\n" + name);
        }
        return result.toString();
    }

    @ApiOperation(value = "取得物品类型的附加数据", notes = "取得物品类型的附加数据", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "type", value = "物品类型ID", required = true, dataType = "int", paramType = "path")
    })
    @ResponseBody
    @RequestMapping(value = "/{type}/exData", method = RequestMethod.GET)
    public String getExData(@PathVariable int type) {
        JSONObject result = new JSONObject();

        String exData = itemTypeService.getExData(type);

        result.accumulate("result", true);
        result.accumulate("exData", exData);

        return result.toString();
    }

    @ApiOperation(value = "更新物品类型", notes = "更新物品类型", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "物品类型ID", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "name", value = "物品类型名称", required = true, dataType = "String"),
            @ApiImplicitParam(name = "inIndex", value = "是否在主页显示", required = true, dataType = "boolean"),
            @ApiImplicitParam(name = "order", value = "物品类型优先级", required = true, dataType = "int"),
            @ApiImplicitParam(name = "exData", value = "物品类型的附加数据", required = false, dataType = "JSONArray")
    })
    @ResponseBody
    @RequestMapping(value = "/{id}/update", method = RequestMethod.POST)
    public String updateItemType(@PathVariable Integer id, String name, boolean inIndex, int order, String exData) {
        JSONObject result = new JSONObject();

        //先判断exData的格式，格式不对就返回错误
        try {
            JSONArray exDataJson;
            if ("\"\"".equals(exData)) {
                exDataJson = null;
            } else {
                exDataJson = JSONArray.fromObject(exData);
            }

            int showInIndex = 1;
            if (!inIndex) {
                showInIndex = 0;
            }

            try {
                int cRows = itemTypeService.updateItemType(id, name, showInIndex, order, exDataJson);
                result.accumulate("result", true);
                result.accumulate("msg", "共修改了" + cRows + "条进货记录");
            } catch (DBUpdateException e) {
                setResultFalse(result, e);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            result.accumulate("result", false);
            result.accumulate("msg", "JSON格式错误\n" + exData);
        }
        return result.toString();
    }

    @ApiOperation(value = "删除物品类型附加数据的key", notes = "删除物品类型附加数据的key", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "物品类型ID", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "key", value = "key名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "exData", value = "附加数据", required = true, dataType = "String")
    })
    @ResponseBody
    @RequestMapping(value = "/{id}/exData/del", method = RequestMethod.POST)
    public String delExDataKey(@PathVariable Integer id, String key, String exData) {
        JSONObject result = new JSONObject();

        result.accumulate("result", true);
        itemTypeService.delExDataKey(id, key, JSONArray.fromObject(exData));

        return result.toString();
    }

    @ApiOperation(value = "删除物品类型", notes = "删除物品类型", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "物品类型ID", required = true, dataType = "Integer", paramType = "path"),
    })
    @ResponseBody
    @RequestMapping(value = "/{id}/del", method = RequestMethod.GET)
    public String delItemType(@PathVariable int id) {
        JSONObject result = new JSONObject();

        try {
            int cRows = itemTypeService.delItemType(id);
            result.accumulate("result", true);
        } catch (DBUpdateException e) {
            setResultFalse(result, e);
        }
        return result.toString();
    }
}
