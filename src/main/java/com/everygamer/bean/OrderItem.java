package com.everygamer.bean;

import lombok.Getter;
import lombok.Setter;

public class OrderItem {
    @Getter
    @Setter
    private int id;
    @Getter
    @Setter
    private String orderNum;
    @Getter
    @Setter
    private String userName;
    @Getter
    @Setter
    private String userWW;
    @Getter
    @Setter
    private String tel;
    @Getter
    @Setter
    private String phone;
    @Getter
    @Setter
    private String address;
    @Getter
    @Setter
    private String itemStatisList;
    @Getter
    @Setter
    private int logisticsType;
    @Getter
    @Setter
    private String logistics;
    @Getter
    @Setter
    private String logisticsNum;
    @Getter
    @Setter
    private double transCost;
    @Getter
    @Setter
    private String desc;
    @Getter
    @Setter
    private OrderState orderState;
    @Getter
    @Setter
    private int isAlive;
    @Getter
    @Setter
    private String insDate;
    @Getter
    @Setter
    private String upDate;
    @Getter
    @Setter
    private String delDate;
}
