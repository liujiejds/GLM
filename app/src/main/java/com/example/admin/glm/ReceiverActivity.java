package com.example.admin.glm;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.admin.glm.bmobData.Data;
import com.example.admin.glm.bmobData.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class ReceiverActivity extends AppCompatActivity {
    private ListView listView;
    private SimpleAdapter adapter;
    private List<Map<String,Object>> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_pager1);
        listView=(ListView)findViewById(R.id.my_listView);
        dataList=new ArrayList<Map<String,Object>>();
        adapter=new SimpleAdapter(this, dataList,R.layout.my_item,new String[]{"address","pay","name","userImage","checkBox","time"},
                new int[]{R.id.address,R.id.pay,R.id.name,R.id.userImage,R.id.checkbox,R.id.time});
        User user= BmobUser.getCurrentUser(User.class);
        BmobQuery<Data> dataBmobQuery=new BmobQuery<>();
        dataBmobQuery.addWhereEqualTo("worker",user);
        dataBmobQuery.include("user");
        dataBmobQuery.findObjects(new FindListener<Data>() {
            @Override
            public void done(List<Data> list, BmobException e) {
                if (list != null) {
                    for (Data data : list) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("address", "地址:"+data.getAddress().toString());
                        map.put("pay", "酬劳:"+data.getMoney()+"元");
                        map.put("name", "发单人:"+data.getUser().getUsername());
                        map.put("userImage", R.drawable.xiaotubiao);
                        map.put("id",data.getObjectId());
                        map.put("checkBox",data.isOK());
                        map.put("time",subStr(data.getCreatedAt()));
                        dataList.add(map);
                        adapter.notifyDataSetChanged();
                    }
                }
            }
        });
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String dataID= (String) dataList.get(position).get("id");
                Intent intent=new Intent(ReceiverActivity.this,AboutMyReceiverActivity.class);
                intent.putExtra("id",dataID);
                startActivity(intent);
            }
        });
    }
    private String subStr(String s){
        String s1=s.substring(2,13)+"时";
        return s1;
    }
}
