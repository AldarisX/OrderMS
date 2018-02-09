package com.everygamer.bean;

public class User {
    private int id;
    private String uname;
    private String passwd;
    private int level;
    private int lastLogin;
    private int insDate;
    private int upDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(int lastLogin) {
        this.lastLogin = lastLogin;
    }

    public int getInsDate() {
        return insDate;
    }

    public void setInsDate(int insDate) {
        this.insDate = insDate;
    }

    public int getUpDate() {
        return upDate;
    }

    public void setUpDate(int upDate) {
        this.upDate = upDate;
    }
}
