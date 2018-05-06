package com.everygamer.logger;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public enum DBLogLevel {
    Info(0, "信息"), Warring(1, "警告"), Error(2, "错误"), Debug(3, "调试");

    static Map<Integer, DBLogLevel> enumMap = new HashMap<>();

    static {
        for (DBLogLevel type : DBLogLevel.values()) {
            enumMap.put(type.getId(), type);
        }
    }

    @Getter
    @Setter
    int id;
    @Getter
    @Setter
    String name;

    DBLogLevel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static DBLogLevel getEnum(int id) {
        return enumMap.get(id);
    }
}
