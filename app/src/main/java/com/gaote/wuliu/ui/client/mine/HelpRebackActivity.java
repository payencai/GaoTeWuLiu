package com.gaote.wuliu.ui.client.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.gaote.wuliu.R;
import com.gaote.wuliu.tools.CheckDoubleClick;
import com.gaote.wuliu.widget.MyStepView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class HelpRebackActivity extends AppCompatActivity {
    @BindView(R.id.mystep)
    MyStepView myStepView;
    private List<String> mSteps = new ArrayList<>();//每一步的文字信息
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_reback);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mSteps.add("个人信息");
        mSteps.add("车辆信息");
        mSteps.add("照片信息");
        myStepView.setSteps(mSteps);
        myStepView.setCurrentStep(3);
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
