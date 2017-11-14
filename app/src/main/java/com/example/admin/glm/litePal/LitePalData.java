package com.example.admin.glm.litePal;

import org.litepal.crud.DataSupport;

/**
 * Created by admin on 17-8-10.
 */

public class LitePalData extends DataSupport {
    private String objectId;
    private String userName;
    private String describe;
    private String pay;
    private String address;

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public String getPay() {
        return pay;
    }

    public void setPay(String pay) {
        this.pay = pay;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
