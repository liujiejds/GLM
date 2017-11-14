package com.example.admin.glm;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.glm.bmobData.User;
import com.siberiadante.titlelayoutlib.TitleLayoutLib;

import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobPushManager;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;


public class glm extends AppCompatActivity {
    private EditText RID,RPhone,RPassWord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_glm);
        initBmob();
        userInit();
        TitleLayoutLib.init(getApplicationContext());
    }
    private void initBmob(){
        Bmob.initialize(this,"7475e823df6fbc2ab9afde99a75124e9");
    }
//  初始化 获得编辑文本的内容
    private void userInit() {
        RID=(EditText)findViewById(R.id.RIDEditText);
        RPhone=(EditText)findViewById(R.id.RPhoneEditText);
        RPassWord=(EditText)findViewById(R.id.RPwdEditText);
    }
    //登录 注册的点击事件 实现跳转页面
public void doClick(View v){
    switch (v.getId()){
        case R.id.RegisterButton:{
            Intent intent =new Intent(this,Register.class);
            startActivity(intent);
            break;}
        case R.id.RLoadButton:{
            String id=RID.getText().toString();
            String userName=RPhone.getText().toString();
            String pwd=RPassWord.getText().toString();
            User user=new User();
            user.setUsername(userName);
            user.setSchoolID(id);
            user.setPassword(pwd);
            user.login(new SaveListener<User>() {
                @Override
                public void done(User user, BmobException e) {
                    if (e==null){
                               finish();
                               Intent intent=new Intent(glm.this,Loading.class);
                               startActivity(intent);
                    }else {
                        Toast.makeText(glm.this,"输入有错误或者你没有注册账户"+e.toString(),Toast.LENGTH_LONG).show();
                    }
                }
            });
            break;
        }
    }
}

}
