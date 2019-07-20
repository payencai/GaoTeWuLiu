package com.gaote.wuliu.ui.client.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.gaote.wuliu.R;
import com.gaote.wuliu.tools.CheckDoubleClick;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class AccountSafeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_safe);
        ButterKnife.bind(this);
    }
    @OnClick({R.id.rl_reset, R.id.rl_account, R.id.rl_phone,R.id.tv_bind})
    void OnClcik(View view) {
        if (CheckDoubleClick.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.rl_reset:
                startActivity(new Intent(AccountSafeActivity.this,UpdatePwdActivity.class));
                break;
            case R.id.rl_account:

                break;
            case R.id.rl_phone:

                break;
            case R.id.tv_bind:

                break;
        }
    }
}
