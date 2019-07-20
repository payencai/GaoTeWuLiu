package com.gaote.wuliu.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.gaote.wuliu.R;
import com.gaote.wuliu.tools.CheckDoubleClick;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class PhoneLostActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_lost);
        ButterKnife.bind(this);
    }
    @OnClick({R.id.iv_back, R.id.tv_item1,R.id.tv_item2,R.id.tv_item3,R.id.tv_item4,R.id.tv_item5})
    void OnClcik(View view) {
        if (CheckDoubleClick.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.tv_item1:
                startActivity(new Intent(PhoneLostActivity.this, MileFindActivity.class));
                break;
            case R.id.tv_item2:
                startActivity(new Intent(PhoneLostActivity.this, WechatFindActivity.class));
                break;
            case R.id.tv_item3:
                startActivity(new Intent(PhoneLostActivity.this, BlogFindActivity.class));
                break;
            case R.id.tv_item4:
                startActivity(new Intent(PhoneLostActivity.this, QQFindActivity.class));
                break;
            case R.id.tv_item5:
                startActivity(new Intent(PhoneLostActivity.this, ChangePhoneActivity.class));
                break;
            case R.id.iv_back:
                finish();
                break;

        }
    }
}
