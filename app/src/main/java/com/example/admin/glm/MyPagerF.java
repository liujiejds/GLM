package com.example.admin.glm;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.admin.glm.bmobData.Data;
import com.example.admin.glm.bmobData.User;
import com.example.admin.glm.litePal.LitePalData;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by admin on 17-5-10.
 */

public class MyPagerF extends Fragment {
    private ListView listView;
    private SimpleAdapter adapter;
    private List<Map<String,Object>> dataList;
    private Receiver receiver;
    private Button button;
    private boolean pagerReceiverTag=false;
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.my_pager,container,false);
        listView=(ListView)view.findViewById(R.id.my_listView);
        dataList=new ArrayList<Map<String,Object>>();
        adapter=new SimpleAdapter(getContext(), dataList,R.layout.my_item,new String[]{"address","pay","name","userImage","checkBox","time"},
                new int[]{R.id.address,R.id.pay,R.id.name,R.id.userImage,R.id.checkbox,R.id.time});
        User user= BmobUser.getCurrentUser(User.class);
        BmobQuery<Data> query=new BmobQuery<>();
        query.addWhereEqualTo("user",user);
        query.findObjects(new FindListener<Data>() {
            @Override
            public void done(List<Data> list, BmobException e) {
                if (list!=null){
                    for (Data data:list){
                        Map<String,Object> map=new HashMap<String, Object>();
                        map.put("address","地址:"+data.getAddress().toString());
                        map.put("pay","酬劳:"+data.getMoney()+"元");
                        map.put("name",data.getUser().getUsername());
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
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder dialog=new AlertDialog.Builder(getActivity());
                dialog.setTitle("删除该项");
                dialog.setMessage("删除后信息将不可撤回");
                dialog.setCancelable(true);
                dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                          String id= (String) dataList.get(position).get("id");
                        Data data=new Data();
                        data.setObjectId(id);
                        data.delete(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                Toast.makeText(getContext(),"删除成功",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
                dialog.show();
                return true;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String dataID= (String) dataList.get(position).get("id");
                Intent intent=new Intent(getActivity(),AboutMessageActivity.class);
                intent.putExtra("id",dataID);
                startActivity(intent);
            }
        });
        button=(Button)view.findViewById(R.id.textView);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent intent=new Intent(getActivity(),ReceiverActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
   class Receiver extends BroadcastReceiver{
       @Override
       public void onReceive(Context context, Intent intent) {
           final String id =intent.getExtras().getString("id");
           BmobQuery<Data> query=new BmobQuery<>();
           query.addWhereEqualTo("objectId",id);
           query.include("user");
           query.findObjects(new FindListener<Data>() {
               @Override
               public void done(List<Data> list, BmobException e) {
                      if (list!=null){
                          for (Data data:list){
                              Map<String,Object> map=new HashMap<String, Object>();
                              map.put("address",data.getAddress().toString());
                              map.put("pay",data.getMoney());
                              map.put("name",data.getUser().getUsername());
                              map.put("userImage",R.mipmap.ic_launcher);
                              dataList.add(map);
                              adapter.notifyDataSetChanged();
                          }
                      }
               }
           });
       }
   }
    @Override
    public void onAttach(Activity activity) {
        if (!pagerReceiverTag) {
            IntentFilter filter = new IntentFilter(ItemAccept.action);
            receiver = new Receiver();
            pagerReceiverTag=true;
            activity.registerReceiver(receiver, filter);
        }
        super.onAttach(activity);
    }

    @Override
    public void onDestroy() {
        if (pagerReceiverTag) {
            getActivity().unregisterReceiver(receiver);
            pagerReceiverTag=false;
        }
        super.onDestroy();
    }
    private void saveData(LitePalData data){
        data.save();
    }
    private String subStr(String s){
        String s1=s.substring(2,13)+"时";
        return s1;
    }
}
