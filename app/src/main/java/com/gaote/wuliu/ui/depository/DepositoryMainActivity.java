package com.gaote.wuliu.ui.depository;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.gaote.wuliu.R;
import com.gaote.wuliu.net.MyPath;
import com.gaote.wuliu.tools.CheckDoubleClick;
import com.gaote.wuliu.ui.login.BlogFindActivity;
import com.gaote.wuliu.ui.login.ChangePhoneActivity;
import com.gaote.wuliu.ui.login.MileFindActivity;
import com.gaote.wuliu.ui.login.PhoneLostActivity;
import com.gaote.wuliu.ui.login.QQFindActivity;
import com.gaote.wuliu.ui.login.WechatFindActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = MyPath.Depository.Main)
public class DepositoryMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_depository_main);
        ButterKnife.bind(this);
    }
    @OnClick({R.id.ll_exit, R.id.ll_shop,R.id.tv_saoma_out,R.id.tv_saoma_in,R.id.rl_out,R.id.rl_store,R.id.rl_driver,R.id.rl_documnent,R.id.rl_package})
    void OnClcik(View view) {
        if (CheckDoubleClick.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.tv_saoma_in:

                break;
            case R.id.tv_saoma_out:

                break;
            case R.id.ll_exit:

                break;
            case R.id.ll_shop:
                ARouter.getInstance().build(MyPath.Depository.DepositoryShop).navigation();
                break;
            case R.id.rl_out:

                break;
            case R.id.rl_store:

                break;
            case R.id.rl_driver:

                break;
            case R.id.rl_documnent:

                break;
            case R.id.rl_package:
                ARouter.getInstance().build(MyPath.Depository.PackStatus).navigation();
                break;

        }
    }
}
