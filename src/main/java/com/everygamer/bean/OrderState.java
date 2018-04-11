package com.everygamer.bean;

import java.util.HashMap;
import java.util.Map;

public enum OrderState {
    Create(0, "排单"), Outgoing(1, "出库"), Finish(2, "完成"), Closed(3, "关闭"), Unknow(-1, "未知");

    static Map<Integer, OrderState> enumMap = new HashMap<>();

    static {
        for (OrderState type : OrderState.values()) {
            enumMap.put(type.getId(), type);
        }
    }

    int id;
    String name;

    OrderState(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static OrderState getState(String name) {
        if (name == null) {
            return null;
        }
        for (OrderState state : OrderState.values()) {
            if (state.name.equals(name)) {
                return state;
            }
        }
        return Unknow;
    }

    public static OrderState getEnum(int id) {
        return enumMap.get(id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
