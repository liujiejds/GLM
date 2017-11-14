package com.example.admin.glm;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.glm.bmobData.SchoolId;
import com.example.admin.glm.bmobData.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by admin on 17-5-10.
 */

public class Register extends Activity {
    private EditText schoolID,pwd,phone,name,spwd;
    private Button submit;
    private String userid,userPhone,userPwd,userName,spwd1;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);
        schoolID=(EditText)findViewById(R.id.RIDEditText);
        pwd=(EditText)findViewById(R.id.RPwdEditText);
        phone=(EditText)findViewById(R.id.RPhoneEditText);
        name=(EditText)findViewById(R.id.user_name);
        submit=(Button)findViewById(R.id.RsButton);
        spwd=findViewById(R.id.spwd);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog=new AlertDialog.Builder(Register.this);
                dialog.setTitle("提示");
                dialog.setMessage("点击确定后将注册账号");
                dialog.setCancelable(false);
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        userid=schoolID.getText().toString().trim();
                        userPhone=phone.getText().toString();
                        userPwd=pwd.getText().toString();
                        userName=name.getText().toString();
                        spwd1=spwd.getText().toString();
                        BmobQuery<SchoolId> query=new BmobQuery<>();
                        query.findObjects(new FindListener<SchoolId>() {
                            @Override
                            public void done(List<SchoolId> list, BmobException e) {
                                    List<String> id=s(list);
                                    List<String> pwd=s1(list);
                                    if (id.contains(userid)&&pwd.contains(spwd1)){
                                        User user=new User();
                                        user.setUsername(userName);
                                        user.setSchoolID(userid);
                                        user.setPassword(userPwd);
                                        user.setMobilePhoneNumber(userPhone);
                                        user.signUp(new SaveListener<User>() {
                                            @Override
                                            public void done(User user, BmobException e) {
                                                if (e==null){
                                                    Toast.makeText(Register.this,"注册成功",Toast.LENGTH_LONG).show();
                                                    Intent intent=new Intent(Register.this,glm.class);
                                                    startActivity(intent);
                                                    finish();
                                                }else {
                                                    Toast.makeText(Register.this,e.toString()+"",Toast.LENGTH_LONG).show();
                                                }
                                            }
                                        });
                                    }
                                    else {
                                        Toast.makeText(Register.this,"不存在该校园卡号或者该校园卡已被注册",Toast.LENGTH_SHORT).show();
                                    }

                            }
                        });

                    }
                });
                dialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });
    }
    private List s(List<SchoolId> list){
        List<String> list1 =new ArrayList<>();
        for (SchoolId s:list) {
            list1.add(s.getSchoolID());
        }
        return list1;
    }
    private List s1(List<SchoolId> list){
        List<String> list1=new ArrayList<>();
        for (SchoolId s: list){
            list1.add(s.getPwd());
        }
        return list1;
    }
}
