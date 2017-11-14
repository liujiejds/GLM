package com.example.admin.glm;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.admin.glm.bmobData.Data;
import com.example.admin.glm.bmobData.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * Created by admin on 17-5-10.
 */

public class MainF extends Fragment   {
    private ListView listView;
    private SimpleAdapter simpleAdapter;
    private List<Map<String,Object>> datalist;
    private int myPosition;
    private MyReceiver receiver;
    private boolean mainReceiverTag=false;
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.main,container,false);
        listView=(ListView)view.findViewById(R.id.listView);
        datalist=new ArrayList<Map<String, Object>>();
//      User user= BmobUser.getCurrentUser(User.class);
        BmobQuery<Data> query=new BmobQuery<>();
        query.include("user");
        query.findObjects(new FindListener<Data>() {
            @Override
            public void done(List<Data> list, BmobException e) {
                  if (list!=null) {
                      for (Data data : list) {
                          if (data.getDone()==2){
                          String id=data.getObjectId();
                          Map<String, Object> map = new HashMap<>();
                          map.put("id",id);
                          map.put("describe", data.getMessage());
                          map.put("address", "地址:"+data.getAddress());
                          map.put("pay", "酬劳:"+data.getMoney()+"元");
                          map.put("name", "发单人:"+data.getUser().getUsername());
                          map.put("userImage", R.drawable.xiaotubiao);
                          map.put("time",subStr(data.getCreatedAt()));
                          datalist.add(map);
                          simpleAdapter.notifyDataSetChanged();
                          }
                      }
                  }else {
                      Toast.makeText(getContext(),"错误"+e.toString(),Toast.LENGTH_SHORT).show();
                  }
            }
        });
        simpleAdapter=new SimpleAdapter(getContext(),datalist,R.layout.list_item,new String[]{"address","pay","name","userImage","time"},
                new int[]{R.id.address,R.id.pay,R.id.name,R.id.userImage,R.id.time});
        listView.setAdapter(simpleAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(),ItemAccept.class);
                Bundle bundle=new Bundle();
                String pay= datalist.get(position).get("pay").toString();
                String name= datalist.get(position).get("name").toString();
                String address=datalist.get(position).get("address").toString();
                String describe=datalist.get(position).get("describe").toString();
                String mid=datalist.get(position).get("id").toString();
                bundle.putString("id",mid);
                bundle.putString("describe",describe);
                bundle.putString("address",address);
                bundle.putString("pay",pay);
                bundle.putString("name",name);
                myPosition=position;
                bundle.putInt("position",position);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        Log.i("MainF","onCreatedView");
        return view;
    }
    class MyReceiver extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            myPosition=intent.getExtras().getInt("position");
            datalist.remove(myPosition);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        if (!mainReceiverTag) {
        IntentFilter filter = new IntentFilter(ItemAccept.action);
        receiver = new MyReceiver();
        activity.registerReceiver(receiver, filter);
        mainReceiverTag=true;
    }
        super.onAttach(activity);
        Log.i("MainF","OnAttach");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("MainF","onDestroyView()");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("MainF","onCreate()");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i("MainF","onActivityCreated()");
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.i("MainF","onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("MainF","onResume()");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.i("MainF","onPause()");
    }

    @Override
    public void onStop() {
        super.onStop();


        Log.i("MainF","onStop()");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.i("MainF","onDestroy()");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (mainReceiverTag) {
            try {
                if (receiver!=null) {
                    getActivity().unregisterReceiver(receiver);
                }
            }catch (Exception e){
                Log.i("MainF",e.toString());
            }
            finally {

            }
        }
        Log.i("MainF","onDetach()");
    }
    private String subStr(String s){
       String s1=s.substring(2,13)+"时";
       return s1;
    }
}
