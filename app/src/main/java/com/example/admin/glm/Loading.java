package com.example.admin.glm;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.admin.glm.bmobData.User;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnMenuTabClickListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;

/**
 * Created by admin on 17-5-10.
 */

public class Loading extends FragmentActivity {
    private List<String>title;
    private List<Fragment>fragmentList;
    private PagerTitleStrip pagerTitleStrip;
    private Map<String,Object> map;
    private List<Map<String,Object>> datalist;
    public  MyFragmenPagerAdap myFragmenPagerAdap;
    private BottomBar bottomBar;
    private ViewPager viewPager;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private TextView head_name;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.view_pager);
        viewInit();
        createBottomBar(savedInstanceState);
    }
    private void viewInit(){
        viewPager=(ViewPager)findViewById(R.id.myViewpager);
        pagerTitleStrip=(PagerTitleStrip)findViewById(R.id.pagerTitleStrip);
        drawerLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        navigationView=(NavigationView)findViewById(R.id.nav_view);
        View headLayout=navigationView.inflateHeaderView(R.layout.nav_header);
        head_name= (TextView) headLayout.findViewById(R.id.user_name);
        UpdateUserName();
        pagerTitleStrip.setTextColor(Color.RED);
        title=new ArrayList<String>();
        title.add("主界面");
        title.add("发布");
        title.add("个人");
        MessageF messageF=new MessageF();
        MainF mainF=new MainF();
        MyPagerF myPagerF=new MyPagerF();
        fragmentList=new ArrayList<Fragment>();
        fragmentList.add(mainF);
        fragmentList.add(messageF);
        fragmentList.add(myPagerF);
        myFragmenPagerAdap=new MyFragmenPagerAdap(getSupportFragmentManager(),fragmentList,title);
        viewPager.setAdapter(myFragmenPagerAdap);
        datalist=new ArrayList<Map<String, Object>>();
        navigationView.setCheckedItem(R.id.nav_map);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                drawerLayout.closeDrawers();
                switch (item.getItemId()){
                    case R.id.nav_exit:{
                        finish();
                        Intent intent=new Intent(Loading.this,glm.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.nav_map: {
                        Intent intent = new Intent(Loading.this, Location.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.nav_pay: {
                        Intent intent = new Intent(Loading.this, AboutRule.class);
                        startActivity(intent);
                        break;
                    }
                    case R.id.nav_person:{
                        Intent intent=new Intent(Loading.this,Person.class);
                        startActivity(intent);
                        break;}
                    case R.id.nav_search:{
                        Intent intent =new Intent(Loading.this,Search.class);
                        startActivity(intent);
                        break;
                    }
                }
                return true;
            }
        });
    }

    private void createBottomBar(Bundle savedInstanceState){
        bottomBar=BottomBar.attach(this,savedInstanceState);
        bottomBar.setItemsFromMenu(R.menu.bt, new OnMenuTabClickListener() {
            @Override
            public void onMenuTabSelected(@IdRes int menuItemId) {
                switch (menuItemId){
                    case R.id.bottomBarItemOne:
                        viewPager.setCurrentItem(0);
                        break;
                    case R.id.bottomBarItemThree:
                        viewPager.setCurrentItem(2);
                        break;
                    case R.id.bottomBarItemTwo:
                        viewPager.setCurrentItem(1);
                        break;
                    default:
                }

            }

            @Override
            public void onMenuTabReSelected(@IdRes int menuItemId) {

            }
        });
        bottomBar.setActiveTabColor(Color.GREEN);
        bottomBar.mapColorForTab(0, ContextCompat.getColor(this,R.color.colorAccent));
        bottomBar.mapColorForTab(1, 0xFF00FF00);
        bottomBar.mapColorForTab(2, 0xFF00FF00);

    }
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        bottomBar.onSaveInstanceState(outState);
    }
    private void UpdateUserName(){
        User user= BmobUser.getCurrentUser(User.class);
        head_name.setText(user.getUsername().toString());
        Log.i("Loading",user.getUsername().toString());
    }
}
