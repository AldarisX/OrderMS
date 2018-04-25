package com.everygamer.control;

import com.everygamer.bean.Manufactor;
import com.everygamer.dao.exception.DBUpdateException;
import com.everygamer.service.ManuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(description = "厂商")
@RestController
@RequestMapping(value = "/api/manu", produces = "application/json;charset=UTF-8")
public class ManuControl extends BaseControl {
    @Autowired
    private ManuService manuService;

    @ApiOperation(value = "获取全部厂商", notes = "获取全部厂商")
    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String getAllManu() {
        JSONObject result = new JSONObject();

        List<Manufactor> memTypeList = manuService.getAllManu();
        JSONArray jsonList = JSONArray.fromObject(memTypeList);
        result.accumulate("result", true);
        result.accumulate("data", jsonList);

        return result.toString();
    }

    @ApiOperation(value = "按物品类型ID获取厂商", notes = "按物品类型ID获取厂商")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "itemType", value = "物品类型ID", required = true, dataType = "int", paramType = "path"),
    })
    @ResponseBody
    @RequestMapping(value = "/item_type/{itemType}", method = RequestMethod.GET)
    public String getAllManuByItemType(@PathVariable int itemType) {
        JSONObject result = new JSONObject();

        List<Manufactor> manuTypeList = manuService.getAllManuByItemType(itemType);
        JSONArray jsonList = JSONArray.fromObject(manuTypeList);
        result.accumulate("result", true);
        result.accumulate("data", jsonList);

        return result.toString();
    }

    @ApiOperation(value = "按厂商名称获取厂商信息", notes = "按厂商名称获取厂商信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "厂商名称", required = true, dataType = "String", paramType = "path"),
    })
    @ResponseBody
    @RequestMapping(value = "/{name}/info", method = RequestMethod.GET)
    public String getManuInfo(@PathVariable String name) {
        JSONObject result = new JSONObject();

        List<Manufactor> manu = manuService.getAllManuByName(name);
        result.accumulate("result", true);
        result.accumulate("data", JSONArray.fromObject(manu));

        return result.toString();
    }

    @ApiOperation(value = "按厂商ID获取厂商信息", notes = "按厂商ID获取厂商信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "厂商ID", required = true, dataType = "int", paramType = "path"),
    })
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getManuById(@PathVariable int id) {
        JSONObject result = new JSONObject();

        Manufactor manu = manuService.getManuById(id);
        result.accumulate("result", true);
        result.accumulate("data", JSONObject.fromObject(manu));

        return result.toString();
    }

    @ApiOperation(value = "添加厂商", notes = "添加厂商")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "厂商名", required = true, dataType = "String"),
            @ApiImplicitParam(name = "itemTypeId", value = "物品类型ID", required = true, dataType = "Integer"),
    })
    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String addMaun(String name, Integer itemTypeId) {
        JSONObject result = new JSONObject();
        Manufactor manu = manuService.getManu(name, itemTypeId);
        if (manu == null) {
            try {
                manuService.addManu(name, itemTypeId);
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

    @ApiOperation(value = "修改厂商优先级", notes = "修改厂商优先级")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name", value = "厂商名", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "order", value = "优先级", required = true, dataType = "Integer", paramType = "path"),
    })
    @ResponseBody
    @RequestMapping(value = "/{name}/{order}", method = RequestMethod.POST)
    public String setManuOrder(@PathVariable String name, @PathVariable int order) {
        JSONObject result = new JSONObject();
        manuService.setManuOrder(name, order);
        result.accumulate("result", true);
        return result.toString();
    }

    @ApiOperation(value = "删除厂商", notes = "删除厂商")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "厂商ID", required = true, dataType = "int", paramType = "path"),
    })
    @ResponseBody
    @RequestMapping(value = "/{id}/del", method = RequestMethod.GET)
    public String delItemType(@PathVariable int id) {
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
