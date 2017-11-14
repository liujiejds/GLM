package com.example.admin.glm;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.glm.bmobData.Data;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.BmobUpdateListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

public class AboutMyReceiverActivity extends AppCompatActivity {

    private TextView address,pay,describe,name;
    private Button Return;
    private String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_my_receiver);
        address=(TextView)findViewById(R.id.address);
        pay=(TextView)findViewById(R.id.pay);
        describe=(TextView)findViewById(R.id.describe);
        name=(TextView)findViewById(R.id.name);
        Return=(Button)findViewById(R.id.Return);
        id=getIntent().getExtras().getString("id");
        Return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Data d=new Data();
                d.setOK(true);
                d.update(id, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        Toast.makeText(AboutMyReceiverActivity.this,"送达完毕！",Toast.LENGTH_LONG).show();
                    }
                });
                finish();
            }
        });
        BmobQuery<Data> query=new BmobQuery<>();
        query.include("user");
        query.getObject(id, new QueryListener<Data>() {
            @Override
            public void done(Data data, BmobException e) {
                address.setText(data.getAddress());
                pay.setText(data.getMoney());
                describe.setText("发布人电话:"+data.getUser().getMobilePhoneNumber()+"订单信息:"+data.getMessage());
                name.setText(data.getUser().getUsername());
            }
        });
    }
}
