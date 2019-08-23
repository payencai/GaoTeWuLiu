package com.gaote.wuliu.ui.depository;

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

@Route(path = MyPath.Depository.DepositoryShop)
public class ShopInoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_ino);
        ButterKnife.bind(this);
    }
    @OnClick({R.id.iv_back, R.id.rl_pic,R.id.rl_name,R.id.rl_address,R.id.rl_data,R.id.rl_out,R.id.rl_in})
    void OnClcik(View view) {
        if (CheckDoubleClick.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_pic:
                break;
            case R.id.rl_name:
                break;
            case R.id.rl_address:

                break;
            case R.id.rl_out:
                ARouter.getInstance().build(MyPath.Depository.GoodsOutKu).navigation();
                break;
            case R.id.rl_data:
                ARouter.getInstance().build(MyPath.Depository.StatisticsData).navigation();
                break;
            case R.id.rl_in:
                ARouter.getInstance().build(MyPath.Depository.GoodsInKu).navigation();
                break;


        }
    }
}
