package com.gaote.wuliu.ui.depository;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.gaote.wuliu.R;
import com.gaote.wuliu.net.MyPath;

@Route(path = MyPath.Depository.GoodsInKu)
public class GoodsInKuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_in_ku);
    }
}
