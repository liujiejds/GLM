package com.example.admin.glm;

import android.support.v4.widget.PopupWindowCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.glm.bmobData.Data;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;

public class AboutMessageActivity extends AppCompatActivity {
    private TextView address,pay,describe,name,receiver,pay1;
    private Button Return;
    private PopupWindow mPopUpWindow;
    private String m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_message);
        receiver=(TextView)findViewById(R.id.Receiver);
        address=(TextView)findViewById(R.id.address);
        pay=(TextView)findViewById(R.id.pay);
        describe=(TextView)findViewById(R.id.describe);
        name=(TextView)findViewById(R.id.name);
        Return=(Button)findViewById(R.id.Return);
        String id=getIntent().getExtras().getString("id");
        Return.setVisibility(View.INVISIBLE);
        Return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopWindow();
            }
        });
        BmobQuery<Data> query=new BmobQuery<>();
        query.include("user");
        query.include("worker");
        query.getObject(id, new QueryListener<Data>() {
            @Override
            public void done(Data data, BmobException e) {
                address.setText(data.getAddress());
                pay.setText(data.getMoney());
                m=data.getMoney();
                describe.setText(data.getMessage());
                name.setText(data.getUser().getUsername());
                try {
                    receiver.setText(data.getWorker().getUsername() + "电话" + data.getWorker().getMobilePhoneNumber());
                }catch (Exception e1){
                    receiver.setText("目前无人接单！！！");
                }
                if (data.isOK()){
                    Return.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private void showPopWindow() {
        View contentView= LayoutInflater.from(AboutMessageActivity.this).inflate(R.layout.pop_window,null);
        mPopUpWindow=new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT,true);
        mPopUpWindow.setContentView(contentView);
        Button submit=(Button)contentView.findViewById(R.id.submit);
        TextView pay1=(TextView)contentView.findViewById(R.id.pay1);
        ImageView imageView=(ImageView)contentView.findViewById(R.id.imageView2);
        imageView.setImageResource(R.drawable.wechat);
        View view=LayoutInflater.from(AboutMessageActivity.this).inflate(R.layout.activity_about_message,null);
        mPopUpWindow.showAtLocation(view, Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL,0,0);
        pay1.setText("¥:"+m+"元");
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(AboutMessageActivity.this,"支付成功！",Toast.LENGTH_SHORT).show();
                mPopUpWindow.dismiss();
                finish();
            }
        });
    }
}
