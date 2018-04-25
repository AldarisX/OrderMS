package com.everygamer.control;

import com.everygamer.bean.OrderItem;
import com.everygamer.bean.OrderState;
import com.everygamer.service.OrderService;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

@Api(description = "订单接口")
@RestController
@RequestMapping(value = "/api/order", produces = "application/json;charset=UTF-8")
public class OrderControl {
    @Autowired
    private OrderService orderService;

    /**
     * JSON处理枚举
     *
     * @return JSON设置
     */
    @Bean
    JsonConfig getOrderStateConfig() {
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(OrderState.class, new JsonValueProcessor() {
            @Override
            public Object processArrayValue(Object o, JsonConfig jsonConfig) {
                return null;
            }

            @Override
            public Object processObjectValue(String s, Object o, JsonConfig jsonConfig) {
                if (o instanceof OrderState) {
                    return ((OrderState) o).getName();
                }
                return null;
            }
        });
        return jsonConfig;
    }

    @ApiOperation(value = "根据ID获取订单详情", notes = "根据ID获取订单详情", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户id", required = true, dataType = "int", paramType = "path")
    })
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String getOrderById(@PathVariable Integer id) {
        JSONObject result = new JSONObject();

        OrderItem order = orderService.getOrderById(id);
        if (order != null) {
            result.accumulate("result", true);
            result.accumulate("data", JSONObject.fromObject(order, getOrderStateConfig()));
        } else {
            result.accumulate("result", false);
            result.accumulate("msg", "找不到此订单");
        }


        return result.toString();
    }

    @ApiOperation(value = "列出订单列表", notes = "列出订单列表", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名", required = false, dataType = "Integer"),
            @ApiImplicitParam(name = "phone", value = "电话", required = false, dataType = "Integer"),
            @ApiImplicitParam(name = "state", value = "订单状态", required = false, dataType = "Integer"),
            @ApiImplicitParam(name = "startTime", value = "开始时间", required = false, dataType = "Integer"),
            @ApiImplicitParam(name = "endTime", value = "结束时间", required = false, dataType = "Integer"),
            @ApiImplicitParam(name = "page", value = "当前页", required = false, dataType = "Integer"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = false, dataType = "Integer")
    })
    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public String listOrder(String userName, String phone, Integer state, Integer startTime, Integer endTime, Integer page, Integer pageSize) {
        page = page == null ? 1 : page;
        pageSize = pageSize == null ? 50 : pageSize;
        JSONObject result = new JSONObject();

        PageInfo<OrderItem> pages = orderService.listOrders(userName, phone, state, startTime, endTime, page, pageSize);
        result.accumulate("result", true);
        result.accumulate("data", JSONArray.fromObject(pages.getList(), getOrderStateConfig()));
        result.accumulate("pages", pages.getPages());
        result.accumulate("count", pages.getTotal());

        return result.toString();
    }

    @ApiOperation(value = "添加订单", notes = "添加订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "term", value = "关键字", required = true, dataType = "String")
    })
    @ResponseBody
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public String addOrder(OrderItem order) {
        JSONObject result = new JSONObject();
        try {
            JSONArray itemsArray = JSONArray.fromObject(order.getItemStatisList());
            if (itemsArray.size() > 0) {
                order.setOrderState(OrderState.Create);
                orderService.addOrder(order);
                result.accumulate("result", true);
            } else {
                result.accumulate("result", false);
                result.accumulate("msg", "请选择物品");
            }
        } catch (JSONException e) {
            result.accumulate("result", false);
            result.accumulate("msg", "JS异常");
        }
        return result.toString();
    }

    @ApiOperation(value = "更新订单状态", notes = "更新订单状态", consumes = "application/x-www-form-urlencoded")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "订单ID", required = true, dataType = "Integer", paramType = "path"),
            @ApiImplicitParam(name = "state", value = "订单状态", required = true, dataType = "Integer", paramType = "path"),
    })
    @ResponseBody
    @RequestMapping(value = "/{id}/state/{state}", method = RequestMethod.POST)
    public String updateState(@PathVariable Integer id, @PathVariable Integer state) {
        JSONObject result = new JSONObject();

        OrderState orderState = OrderState.getEnum(state);
        if (orderState != null) {
            orderService.updateState(id, orderState);
            result.accumulate("result", true);
        } else {
            result.accumulate("result", false);
            result.accumulate("msg", "不存在这个状态");
        }

        return result.toString();
    }
}
