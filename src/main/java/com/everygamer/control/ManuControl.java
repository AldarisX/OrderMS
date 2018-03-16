package com.everygamer.control;

import com.everygamer.bean.Manufactor;
import com.everygamer.dao.exception.DBUpdateException;
import com.everygamer.service.ManuService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/api/manu.json")
public class ManuControl extends BaseControl {
    @Autowired
    private ManuService manuService;

    @ResponseBody
    @RequestMapping(params = "action=getAll", produces = "application/json;charset=UTF-8")
    public String getAllManu() {
        JSONObject result = new JSONObject();

        List<Manufactor> memTypeList = manuService.getAllManu();
        JSONArray jsonList = JSONArray.fromObject(memTypeList);
        result.accumulate("result", true);
        result.accumulate("data", jsonList);

        return result.toString();
    }

    @ResponseBody
    @RequestMapping(params = "action=getAllByItemType", produces = "application/json;charset=UTF-8")
    public String getAllManuByItemType(int itemType) {
        JSONObject result = new JSONObject();

        List<Manufactor> manuTypeList = manuService.getAllManuByItemType(itemType);
        JSONArray jsonList = JSONArray.fromObject(manuTypeList);
        result.accumulate("result", true);
        result.accumulate("data", jsonList);

        return result.toString();
    }

    @ResponseBody
    @RequestMapping(params = "action=getManuInfo", produces = "application/json;charset=UTF-8")
    public String getManuInfo(String name) {
        JSONObject result = new JSONObject();

        List<Manufactor> manu = manuService.getAllManuByName(name);
        result.accumulate("result", true);
        result.accumulate("data", JSONArray.fromObject(manu));

        return result.toString();
    }

    @ResponseBody
    @RequestMapping(params = "action=addManu", produces = "application/json;charset=UTF-8")
    public String addMaun(String name, Integer itemTypeId) {
        JSONObject result = new JSONObject();
        Manufactor manu = manuService.getManu(name, itemTypeId);
        if (manu == null) {
            try {
                int cRows = manuService.addManu(name, itemTypeId);
                result.accumulate("result", true);
            } catch (DBUpdateException e) {
                setResultFalse(result, e);
            }
        } else {
            result.accumulate("result", false);
            result.accumulate("msg", "已经存在当前厂商\n做" + manu.getItemType() + "的" + name);
        }
        return result.toString();
    }


    @ResponseBody
    @RequestMapping(params = "action=delManu", produces = "application/json;charset=UTF-8")
    public String delItemType(int id) {
        JSONObject result = new JSONObject();

        try {
            int cRows = manuService.delManu(id);
            result.accumulate("result", true);
        } catch (DBUpdateException e) {
            setResultFalse(result, e);
        }
        return result.toString();
    }
}
