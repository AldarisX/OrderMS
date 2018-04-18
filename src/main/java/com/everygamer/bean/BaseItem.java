package com.everygamer.bean;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Data
public class BaseItem {
    @Getter
    @Setter
    private int id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private String itemType;
    @Getter
    @Setter
    private String manufactor;
    @Getter
    @Setter
    private String model;
    @Getter
    @Setter
    private int count;
    @Getter
    @Setter
    private int remain;
    @Getter
    @Setter
    private BigDecimal price;
    @Getter
    @Setter
    private String desc;
    @Getter
    @Setter
    private String exData;
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
