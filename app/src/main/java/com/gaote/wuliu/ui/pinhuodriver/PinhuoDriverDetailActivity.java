package com.gaote.wuliu.ui.pinhuodriver;

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

@Route(path = MyPath.PinhuoDriver.PinhuoDriver)
public class PinhuoDriverDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinhuo_driver_detail);
        ButterKnife.bind(this);
    }
    @OnClick({R.id.iv_back,R.id.tv_mana})
    void OnViewClick(View view){
        if(CheckDoubleClick.isFastDoubleClick()){
            return;
        }
        switch (view.getId()){
            case R.id.tv_mana:
                ARouter.getInstance().build(MyPath.PinhuoDriver.EditCar).navigation();
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
