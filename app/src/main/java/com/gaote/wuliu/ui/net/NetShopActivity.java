package com.gaote.wuliu.ui.net;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.gaote.wuliu.R;
import com.gaote.wuliu.net.MyPath;
import com.gaote.wuliu.tools.CheckDoubleClick;

import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = MyPath.Net.Shop)
public class NetShopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_shop);
        ButterKnife.bind(this);
    }
    @OnClick({R.id.tv_wallet,R.id.iv_back,R.id.rl_pic,R.id.rl_address,R.id.rl_name})
    void OnClick(View view){
        if(CheckDoubleClick.isFastDoubleClick()){
            return;
        }
        switch (view.getId()){
            case R.id.tv_wallet:
                ARouter.getInstance().build(MyPath.Net.Wallet).navigation();
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_pic:
                break;
            case R.id.rl_address:
                break;
            case R.id.rl_name:
                break;

        }
    }
}
