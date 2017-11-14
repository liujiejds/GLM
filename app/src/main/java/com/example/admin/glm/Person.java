package com.example.admin.glm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.admin.glm.bmobData.User;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class Person extends AppCompatActivity {
  private EditText pwd,new_pwd,new_pwd1;
  private String sp,snp,snp1;
  private Button bt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        viewInit();
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BmobUtils();
            }
        });
    }

    private void viewInit() {
        pwd=(EditText)findViewById(R.id.pwd);
        new_pwd=(EditText)findViewById(R.id.new_pwd);
        new_pwd1=(EditText)findViewById(R.id.new_pwd1);
        bt=(Button)findViewById(R.id.bt1);
    }
    private void BmobUtils(){
        sp=pwd.getText().toString().trim();
        snp=new_pwd.getText().toString().trim();
        snp1=new_pwd1.getText().toString().trim();
        if (!sp.isEmpty()&&!snp.isEmpty()&&snp.equals(snp1)) {
            BmobUser.updateCurrentUserPassword(sp, snp, new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e.toString().isEmpty()) {
                        Toast.makeText(Person.this, "修改成功", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(Person.this,e.toString(),Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }else {
            Toast.makeText(Person.this,"输入有错！",Toast.LENGTH_SHORT).show();
        }
    }
}
