package com.example.admin.glm;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.model.LatLng;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 17-10-30.
 */

public class Location extends AppCompatActivity {
    private MapView mapView;
    private BaiduMap baiduMap;
    private Boolean isFirstLocate=true;
    public LocationClient mLocationClient;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.my_location);
        mLocationClient=new LocationClient(getApplicationContext());
        mLocationClient.registerLocationListener(new MyLocationListener());
        mapView=(MapView)findViewById(R.id.map_view);
        baiduMap=mapView.getMap();
        baiduMap.setMyLocationEnabled(true);
        List<String> permissionList=new ArrayList<>();
        if (ContextCompat.checkSelfPermission(Location.this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(Location.this, Manifest.permission.READ_PHONE_STATE)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (ContextCompat.checkSelfPermission(Location.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (!permissionList.isEmpty()){
            String[] permissions=permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(Location.this,permissions,1);
        }else {
            requestMap();
        }
    }
  private void navigeteTo(BDLocation location){
      if (isFirstLocate){
          LatLng ll=new LatLng(location.getLatitude(),location.getLongitude());
          MapStatusUpdate update= MapStatusUpdateFactory.newLatLng(ll);
          baiduMap.animateMapStatus(update);
          isFirstLocate=false;
      }
      MyLocationData.Builder locationBuilder=new MyLocationData.Builder();
      locationBuilder.latitude(location.getLatitude());
      locationBuilder.longitude(location.getLongitude());
      Log.i("Location",location.getLatitude()+"www 我");
      MyLocationData locationData=locationBuilder.build();
      baiduMap.setMyLocationData(locationData);
  }
  public class MyLocationListener implements BDLocationListener{
      @Override
      public void onReceiveLocation(BDLocation bdLocation) {
          if (bdLocation.getLocType()==BDLocation.TypeGpsLocation||bdLocation.getLocType()==BDLocation.TypeNetWorkLocation){
              navigeteTo(bdLocation);
          }
          navigeteTo(bdLocation);
      }
  }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case 1:
                if (grantResults.length>0){
                    for (int result:grantResults){
                        if (result!=PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this,"必须同意所有权限才能使用本程序",Toast.LENGTH_SHORT).show();
                            finish();
                            return;
                        }
                    }
                    requestMap();
                }else {
                    Toast.makeText(this,"发生未知错误",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mLocationClient.stop();
        mapView.onDestroy();
        baiduMap.setMyLocationEnabled(false);
    }

    private void requestMap() {
        LocationClientOption option=new LocationClientOption();
        option.setScanSpan(5000);
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
