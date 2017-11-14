package com.example.admin.glm;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.glm.bmobData.Data;
import com.example.admin.glm.bmobData.User;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by admin on 17-5-17.
 */

public class ItemAccept extends Activity {
    private TextView address,pay,describe,name,tips;
    private Button accept;
    private int position;
    public static final String action="listView.action";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_selected);
        address=(TextView)findViewById(R.id.address);
        pay=(TextView)findViewById(R.id.pay);
        describe=(TextView)findViewById(R.id.describe);
        name=(TextView)findViewById(R.id.name);
        accept=(Button)findViewById(R.id.accept);
        tips=(TextView)findViewById(R.id.tips);
        Bundle bundle = this.getIntent().getExtras();
        final String id=bundle.getString("id");
        position=bundle.getInt("position");
        findObject(id);
        describe.setVisibility(View.INVISIBLE);
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user= BmobUser.getCurrentUser(User.class);
                Data data=new Data();
                data.setWorker(user);
                data.setDone(1);
                data.update(id, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e!=null){
                            Log.i("Item",e.toString());
                        }
                    }
                });
                Intent intent=new Intent(action);
                intent.setClass(ItemAccept.this,AboutMyReceiverActivity.class);
                intent.putExtra("position",position);
                intent.putExtra("id",id);
                sendBroadcast(intent);
                startActivity(intent);
                Toast.makeText(ItemAccept.this,"接单成功！",Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
    private void findObject(String id){
        BmobQuery<Data> query=new BmobQuery();
        query.getObject(id, new QueryListener<Data>() {
            @Override
            public void done(Data data, BmobException e) {
                address.setText(data.getAddress());
                pay.setText(data.getMoney());
                describe.setText(data.getMessage());
                name.setText(data.getUser().getUsername());
            }
        });
    }
}
