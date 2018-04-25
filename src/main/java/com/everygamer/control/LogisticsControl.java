package com.everygamer.control;

import com.everygamer.bean.Logistics;
import com.everygamer.service.LogisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(description = "物流")
@RestController
@RequestMapping(path = "/api/logistics", produces = "application/json;charset=UTF-8")
public class LogisticsControl {
    @Autowired
    private LogisticsService logisticsService;

    @ApiOperation(value = "获取全部物流", notes = "获取全部物流")
    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getAllLogistics() {
        JSONObject result = new JSONObject();

        JSONArray datas = JSONArray.fromObject(logisticsService.getAllLogistics());
        result.accumulate("result", true);
        result.accumulate("data", datas);

        return result.toString();
    }

    @ApiOperation(value = "按ID获取物流信息", notes = "按ID获取物流信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "物流ID", required = true, dataType = "int", paramType = "path"),
    })
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getLogisticsById(@PathVariable int id) {
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

    @ApiOperation(value = "添加物流", notes = "添加物流")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "物流名称", required = true, dataType = "String", paramType = "path"),
    })
    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String addLogistics(String name, Integer order) {
        JSONObject result = new JSONObject();
        if (logisticsService.addLogistics(name, order) == 1) {
            result.accumulate("result", true);
        } else {
            result.accumulate("result", false);
            result.accumulate("msg", "添加物流失败,已存在:" + name);
        }

        return result.toString();
    }

    @ApiOperation(value = "更新物流", notes = "更新物流")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "物流名称", required = true, dataType = "String"),
            @ApiImplicitParam(name = "order", value = "物流优先级", required = true, dataType = "int"),
    })
    @ResponseBody
    @RequestMapping(value = "/update/", method = RequestMethod.POST)
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

    @ApiOperation(value = "删除物流", notes = "删除物流")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "物流ID", required = true, dataType = "int", paramType = "path"),
    })
    @ResponseBody
    @RequestMapping(value = "/{id}/del", method = RequestMethod.GET)
    public String delLogistics(@PathVariable int id) {
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
