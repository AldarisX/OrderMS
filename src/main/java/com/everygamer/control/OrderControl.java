package com.everygamer.control;

import com.everygamer.bean.OrderItem;
import com.everygamer.service.OrderService;
import com.github.pagehelper.PageInfo;
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
    @RequestMapping(params = "action=listOrder")
    public String listOrder(String userName, String phone, String state, Integer startTime, Integer endTime, Integer page, Integer pageSize) {
        page = page == null ? 1 : page;
        pageSize = pageSize == null ? 50 : pageSize;
        JSONObject result = new JSONObject();

        PageInfo<OrderItem> pages = orderService.listOrders(userName, phone, OrderItem.State.getState(state), startTime, endTime, page, pageSize);
        result.accumulate("result", true);
        result.accumulate("data", JSONArray.fromObject(pages.getList()));
        result.accumulate("pages", pages.getPages());
        result.accumulate("count", pages.getTotal());

        return result.toString();
    }

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
