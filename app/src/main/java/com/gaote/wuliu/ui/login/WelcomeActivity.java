package com.gaote.wuliu.ui.login;

import android.Manifest;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.gaote.wuliu.R;
import com.gaote.wuliu.ui.net.NetMainActivity;
import com.tbruyelle.rxpermissions2.RxPermissions;

public class WelcomeActivity extends AppCompatActivity {
    RxPermissions rxPermissions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        requestPermission();//请求权限
    }
    private void requestPermission(){
        rxPermissions=new RxPermissions(this);
        rxPermissions
                .request( Manifest.permission.READ_PHONE_STATE,Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.CALL_PHONE, Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION)
                .subscribe(granted -> {
                    if (granted) { // Always true pre-M
                        startActivty();
                    } else {
                        finish();
                    }
                });
    }
    private void startActivty(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                finish();
            }
        },1000);
    }
}
