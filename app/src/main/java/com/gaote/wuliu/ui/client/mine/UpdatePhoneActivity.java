package com.gaote.wuliu.ui.client.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.gaote.wuliu.R;
import com.gaote.wuliu.tools.CheckDoubleClick;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class UpdatePhoneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_phone);
        ButterKnife.bind(this);
    }
    @OnClick({R.id.tv_submit, R.id.iv_back})
    void OnClcik(View view) {
        if (CheckDoubleClick.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.tv_submit:
                startActivity(new Intent(UpdatePhoneActivity.this,BindPhoneActivity.class));
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
