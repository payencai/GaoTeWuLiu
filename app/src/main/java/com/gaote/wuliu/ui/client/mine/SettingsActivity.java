package com.gaote.wuliu.ui.client.mine;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.gaote.wuliu.R;
import com.gaote.wuliu.tools.CheckDoubleClick;
import com.gaote.wuliu.ui.login.LoginActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
    }
    @OnClick({R.id.rl_account, R.id.resetPassword, R.id.rl_about,R.id.rl_friend,R.id.rl_msg})
    void OnClcik(View view) {
        if (CheckDoubleClick.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.rl_msg:
                startActivity(new Intent(SettingsActivity.this,MsgSettingActivity.class));
                break;
            case R.id.rl_friend:
                break;
            case R.id.rl_account:
                startActivity(new Intent(SettingsActivity.this, AccountSafeActivity.class));
                break;
            case R.id.resetPassword:
                startActivity(new Intent(SettingsActivity.this, ResetPwdActivity.class));
                break;
            case R.id.rl_about:

                break;
        }
    }
}
