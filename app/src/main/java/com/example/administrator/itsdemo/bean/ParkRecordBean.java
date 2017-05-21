package com.example.administrator.itsdemo.bean;

/**
 * Created by Administrator on 2017/5/16.
 */

public class ParkRecordBean {

    private String id;
    private String type;
    private String money;
    private String time;

    public ParkRecordBean(String id, String type, String money, String time) {
        this.id = id;
        this.type = type;
        this.money = money;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
