package com.example.admin.glm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

import com.example.admin.glm.bmobData.Data;

/**
 * Created by admin on 17-10-30.
 */

public class AboutMoney extends AppCompatActivity {
    private EditText money;
    private Button pay;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay);
        money=(EditText)findViewById(R.id.money);
        pay=(Button)findViewById(R.id.pay);
        Data data=new Data();
        data.setMyMoney(Integer.parseInt(money.getText().toString()));

    }
}
