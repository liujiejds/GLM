package com.example.admin.glm.bmobData;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobPointer;

/**
 * Created by admin on 17-8-7.
 */

public class Data extends BmobObject{
    private String message;
    private String address;
    private User user;
    private String money;
    private int done;
    private int myMoney;
    public int getMyMoney() {
        return myMoney;
    }

    public void setMyMoney(int myMoney) {
        this.myMoney = myMoney;
    }

    public int getDone() {
        return done;
    }

    public void setDone(int done) {
        this.done = done;
    }

    private User worker;
    private boolean isOK;

    public boolean isOK() {
        return isOK;
    }

    public void setOK(boolean OK) {
        isOK = OK;
    }

    public User getWorker() {
        return worker;
    }

    public void setWorker(User worker) {
        this.worker = worker;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
