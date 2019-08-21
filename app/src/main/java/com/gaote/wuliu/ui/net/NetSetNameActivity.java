package com.gaote.wuliu.ui.net;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gaote.wuliu.R;
import com.gaote.wuliu.net.MyPath;

@Route(path = MyPath.Net.SetName)
public class NetSetNameActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_set_name);
    }
}
