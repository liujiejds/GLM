package com.example.admin.glm.bmobData;

import cn.bmob.v3.BmobUser;

/**
 * Created by admin on 17-8-7.
 */

public class User extends BmobUser{
    private String schoolID;
    public String getSchoolID() {
        return schoolID;
    }

    public void setSchoolID(String schoolID) {
        this.schoolID = schoolID;
    }
}
