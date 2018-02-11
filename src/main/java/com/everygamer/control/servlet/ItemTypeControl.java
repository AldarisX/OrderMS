package com.everygamer.control.servlet;

import com.everygamer.bean.ItemType;
import com.everygamer.orm.dao.exception.DBUpdateException;
import com.everygamer.service.ItemTypeService;
import com.sun.istack.internal.Nullable;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/itemType.json")
public class ItemTypeControl extends BaseControl {
    @Autowired
    private ItemTypeService itemTypeService;

    @ResponseBody
    @RequestMapping(params = "action=getAll", produces = "application/json;charset=UTF-8")
    public String getAllItemType() {
        JSONObject result = new JSONObject();

        List<ItemType> itemTypeList = itemTypeService.getAllItemType();
        result.accumulate("result", true);
        result.accumulate("data", JSONArray.fromObject(itemTypeList));
        return result.toString();
    }

    @ResponseBody
    @RequestMapping(params = "action=getItemTypeById", produces = "application/json;charset=UTF-8")
    public String getItemTypeById(Integer id) {
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

    @ResponseBody
    @RequestMapping(params = "action=addItemType", produces = "application/json;charset=UTF-8")
    public String addItemType(String name, @Nullable String exData) {
        JSONObject result = new JSONObject();

        if ("\"\"".equals(exData)) {
            exData = null;
        }

        ItemType itemType = itemTypeService.getItemTypeByName(name);
        if (itemType == null) {
            int cRows = itemTypeService.addItemType(name, exData);
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

    @ResponseBody
    @RequestMapping(params = "action=getExData", produces = "application/json;charset=UTF-8")
    public String getExData(int type) {
        JSONObject result = new JSONObject();

        String exData = itemTypeService.getExData(type);

        result.accumulate("result", true);
        result.accumulate("exData", exData);

        return result.toString();
    }

    @ResponseBody
    @RequestMapping(params = "action=updateItemType", produces = "application/json;charset=UTF-8")
    public String updateItemType(Integer id, String name, boolean inIndex, String exData) {
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
                int cRows = itemTypeService.updateItemType(id, name, showInIndex, exDataJson);
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

    @ResponseBody
    @RequestMapping(params = "action=delItemType", produces = "application/json;charset=UTF-8")
    public String delItemType(int id) {
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
