package com.everygamer.control;

import com.everygamer.bean.OrderItem;
import com.everygamer.service.OrderService;
import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/api/order.json", produces = "application/json;charset=UTF-8")
public class OrderControl {
    @Autowired
    private OrderService orderService;

    @ResponseBody
    @RequestMapping(params = "action=addOrder")
    public String addOrder(OrderItem order, String items) {
        JSONObject result = new JSONObject();
        try {
            JSONArray itemsArray = JSONArray.fromObject(items);
            if (itemsArray.size() > 0) {
                orderService.addOrder(order, itemsArray);
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
