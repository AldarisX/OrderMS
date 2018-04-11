package com.everygamer.control;

import com.everygamer.bean.OrderItem;
import com.everygamer.bean.OrderState;
import com.everygamer.service.OrderService;
import com.github.pagehelper.PageInfo;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/api/order.json", produces = "application/json;charset=UTF-8")
public class OrderControl {
    @Autowired
    private OrderService orderService;

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

    @ResponseBody
    @RequestMapping(params = "action=listOrder")
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

    @ResponseBody
    @RequestMapping(params = "action=addOrder")
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
}
