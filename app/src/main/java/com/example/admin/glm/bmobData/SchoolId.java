package com.example.admin.glm.bmobData;

import cn.bmob.v3.BmobObject;

/**
 * Created by admin on 17-11-5.
 */

public class SchoolId extends BmobObject {
    private String SchoolID;
    private String pwd;

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getSchoolID() {
        return SchoolID;
    }

    public void setSchoolID(String schoolID) {
        SchoolID = schoolID;
    }
}
