package com.gaote.wuliu.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.gaote.wuliu.R;
import com.gaote.wuliu.tools.CheckDoubleClick;
import com.gaote.wuliu.ui.client.mine.AccountSafeActivity;
import com.gaote.wuliu.ui.client.mine.MsgSettingActivity;
import com.gaote.wuliu.ui.client.mine.ResetPwdActivity;
import com.gaote.wuliu.ui.client.mine.SettingsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ForgetPwdActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);
        ButterKnife.bind(this);
    }
    @OnClick({R.id.iv_back, R.id.tv_lost})
    void OnClcik(View view) {
        if (CheckDoubleClick.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.tv_lost:
                startActivity(new Intent(ForgetPwdActivity.this, PhoneLostActivity.class));
                break;
            case R.id.iv_back:
                finish();
                break;

        }
    }
}
