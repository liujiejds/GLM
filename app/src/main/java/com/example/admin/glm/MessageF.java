package com.example.admin.glm;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.admin.glm.bmobData.Data;
import com.example.admin.glm.bmobData.User;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * Created by admin on 17-5-10.
 */

public class MessageF extends Fragment {
    private Button submit;
    private EditText pay,address,describe;
    private TextView name;
    private listenr listenr;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    public interface listenr{
       void data(String name, String address, String pay,String describe);
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.message,container,false);
        submit= (Button) view.findViewById(R.id.submit);
        pay=(EditText)view.findViewById(R.id.pay);
        name=(TextView)view.findViewById(R.id.name);
        address=(EditText)view.findViewById(R.id.address);
        describe=(EditText)view.findViewById(R.id.describe);
        User user= BmobUser.getCurrentUser(User.class);
        name.setText(user.getUsername().toString());
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });
        return view;

    }
    private void clear(){
        User user= BmobUser.getCurrentUser(User.class);
        final String nameString=name.getText().toString().trim();
        String payString=  pay.getText().toString().trim();
        final String addressString=address.getText().toString().trim();
        final String desString=describe.getText().toString();
        Data data=new Data();
        data.setAddress(addressString);
        data.setMessage(desString);
        data.setMoney(payString);
        data.setDone(2);
        data.setUser(user);
        data.setOK(false);
        data.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e!=null){
                    Toast.makeText(getContext(),"发布失败"+e.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        });
       if(listenr!=null){
            listenr.data(nameString,addressString,payString,desString);
        }
        Toast.makeText(getContext(),"发布成功",Toast.LENGTH_SHORT).show();
        pay.setText("");
        address.setText("");
        describe.setText("");

    }
}
