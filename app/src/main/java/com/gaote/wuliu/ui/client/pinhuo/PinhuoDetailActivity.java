package com.gaote.wuliu.ui.client.pinhuo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.gaote.wuliu.MyApp;
import com.gaote.wuliu.R;
import com.gaote.wuliu.base.BaseActivity;
import com.gaote.wuliu.net.MyPath;

import butterknife.ButterKnife;

@Route(path = MyPath.Pinhuo.DETAIL)
public class PinhuoDetailActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinhuo_detail);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
    }
}
