package com.everygamer.control;

import net.sf.json.JSONObject;

public class BaseControl {
    public void setResultFalse(JSONObject result, Exception e) {
        result.accumulate("result", false);
        result.accumulate("msg", "操作失败\n" + e.getMessage());
    }
}
