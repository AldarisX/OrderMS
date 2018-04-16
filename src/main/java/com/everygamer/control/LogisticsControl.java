package com.everygamer.control;

import com.everygamer.bean.Logistics;
import com.everygamer.service.LogisticsService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/api/logistics.json", produces = "application/json;charset=UTF-8")
public class LogisticsControl {
    @Autowired
    private LogisticsService logisticsService;

    @ResponseBody
    @RequestMapping(params = "action=getAllLogistics")
    public String getAllLogistics() {
        JSONObject result = new JSONObject();

        JSONArray datas = JSONArray.fromObject(logisticsService.getAllLogistics());
        result.accumulate("result", true);
        result.accumulate("data", datas);

        return result.toString();
    }

    @ResponseBody
    @RequestMapping(params = "action=getLogisticsById")
    public String getLogisticsById(int id) {
        JSONObject result = new JSONObject();

        Logistics log = logisticsService.getLogisticsById(id);
        if (log != null) {
            result.accumulate("result", true);
            result.accumulate("data", JSONObject.fromObject(log));
        } else {
            result.accumulate("result", false);
            result.accumulate("msg", "找不到这个物流!id:" + id);
        }

        return result.toString();
    }

    @ResponseBody
    @RequestMapping(params = "action=addLogistics")
    public String addLogistics(String name) {
        JSONObject result = new JSONObject();
        if (logisticsService.addLogistics(name) == 1) {
            result.accumulate("result", true);
        } else {
            result.accumulate("result", false);
            result.accumulate("msg", "添加物流失败,已存在:" + name);
        }

        return result.toString();
    }

    @ResponseBody
    @RequestMapping(params = "action=updateLogistics")
    public String updateLogistics(Logistics logistics) {
        JSONObject result = new JSONObject();

        if (logisticsService.updateLogistics(logistics) == 1) {
            result.accumulate("result", true);
        } else {
            result.accumulate("result", false);
            result.accumulate("msg", "更新物流失败,不存在:" + logistics.getName());
        }

        return result.toString();
    }

    @ResponseBody
    @RequestMapping(params = "action=delLogistics")
    public String delLogistics(int id) {
        JSONObject result = new JSONObject();

        if (logisticsService.delLogistics(id) == 1) {
            result.accumulate("result", true);
        } else {
            result.accumulate("result", false);
            result.accumulate("msg", "删除物流失败");
        }

        return result.toString();
    }
}
