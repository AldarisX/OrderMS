package com.everygamer.control;

import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/order.json")
public class OrderControl {

    @ResponseBody
    @RequestMapping(params = "action=addOrder")
    public String addOrder() {
        JSONObject result = new JSONObject();

        return result.toString();
    }
}
