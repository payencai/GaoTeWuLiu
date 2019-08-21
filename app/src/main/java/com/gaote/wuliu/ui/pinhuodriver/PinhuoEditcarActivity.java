package com.gaote.wuliu.ui.pinhuodriver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gaote.wuliu.R;
import com.gaote.wuliu.net.MyPath;

@Route(path = MyPath.PinhuoDriver.EditCar)
public class PinhuoEditcarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinhuo_editcar);
    }
}
