package com.everygamer.bean;

public class OrderItem {
    private int id;
    private String orderNum;
    private String userName;
    private String userWW;
    private String tel;
    private String phone;
    private String address;
    private String itemList;
    private int logisticsType;
    private String logistics;
    private int logisticsNum;
    private double transCost;
    private String desc;
    private State orderState = State.Create;
    private int isAlive;
    private String insDate;
    private String upDate;
    private String delDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String orderNum) {
        this.orderNum = orderNum;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserWW() {
        return userWW;
    }

    public void setUserWW(String userWW) {
        this.userWW = userWW;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getItemList() {
        return itemList;
    }

    public void setItemList(String itemList) {
        this.itemList = itemList;
    }

    public int getLogisticsType() {
        return logisticsType;
    }

    public void setLogisticsType(int logisticsType) {
        this.logisticsType = logisticsType;
    }

    public String getLogistics() {
        return logistics;
    }

    public void setLogistics(String logistics) {
        this.logistics = logistics;
    }

    public int getLogisticsNum() {
        return logisticsNum;
    }

    public void setLogisticsNum(int logisticsNum) {
        this.logisticsNum = logisticsNum;
    }

    public double getTransCost() {
        return transCost;
    }

    public void setTransCost(double transCost) {
        this.transCost = transCost;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public State getOrderState() {
        return orderState;
    }

    public void setOrderState(State orderState) {
        this.orderState = orderState;
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

    public String getUpDate() {
        return upDate;
    }

    public void setUpDate(String upDate) {
        this.upDate = upDate;
    }

    public String getDelDate() {
        return delDate;
    }

    public void setDelDate(String delDate) {
        this.delDate = delDate;
    }

    public enum State {
        Create("排单"), Outgoing("出库"), Finish("完成"), Closed("关闭"), Unknow("未知");

        String name;

        State(String name) {
            this.name = name;
        }

        public static State getState(String name) {
            if (name == null) {
                return null;
            }
            for (State state : State.values()) {
                if (state.name.equals(name)) {
                    return state;
                }
            }
            return Unknow;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
