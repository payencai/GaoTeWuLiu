package com.gaote.wuliu.ui.client.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.gaote.wuliu.R;
import com.gaote.wuliu.tools.CheckDoubleClick;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class HelpRebackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_reback);
        ButterKnife.bind(this);
    }
    @OnClick({R.id.rl_reback, R.id.iv_back,R.id.rl_help})
    void OnClcik(View view) {
        if (CheckDoubleClick.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.rl_help:
                startActivity(new Intent(HelpRebackActivity.this,RebackActivity.class));
                break;
            case R.id.rl_reback:
                startActivity(new Intent(HelpRebackActivity.this,FeedbackActivity.class));
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }
}
