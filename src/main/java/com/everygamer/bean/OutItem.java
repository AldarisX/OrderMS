package com.everygamer.bean;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

public class OutItem {
    @Getter
    @Setter
    private int id;
    @Getter
    @Setter
    private int orderId;
    @Getter
    @Setter
    private String itemList;
    @Getter
    @Setter
    private int count;
    @Getter
    @Setter
    private BigDecimal price;
    @Getter
    @Setter
    private String desc;
    @Getter
    @Setter
    private int isAlive;
    @Getter
    @Setter
    private String insDate;
    @Getter
    @Setter
    private String delDate;
    @Getter
    @Setter
    private String upDate;
}
