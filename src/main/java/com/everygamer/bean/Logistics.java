package com.everygamer.bean;

public class Logistics {
    private int id;
    private String name;
    private int isAlive;
    private String insDate;
    private String delDate;
    private String upDate;

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

    public int getIsAlive() {
        return isAlive;
    }

    public void setIsAlive(int isAlive) {
        this.isAlive = isAlive;
    }

    public String getInsDate() {
        return insDate;
    }

    public void setInsDate(String insDate) {
        this.insDate = insDate;
    }

    public String getDelDate() {
        return delDate;
    }

    public void setDelDate(String delDate) {
        this.delDate = delDate;
    }

    public String getUpDate() {
        return upDate;
    }

    public void setUpDate(String upDate) {
        this.upDate = upDate;
    }
}
