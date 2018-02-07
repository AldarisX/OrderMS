package com.everygamer.bean;

public class User {
    private int id;
    private String uname;
    private String passwd;
    private int level;
    private int lastLogin;
    private int ins_date;
    private int up_date;

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

    public int getIns_date() {
        return ins_date;
    }

    public void setIns_date(int ins_date) {
        this.ins_date = ins_date;
    }

    public int getUp_date() {
        return up_date;
    }

    public void setUp_date(int up_date) {
        this.up_date = up_date;
    }
}
