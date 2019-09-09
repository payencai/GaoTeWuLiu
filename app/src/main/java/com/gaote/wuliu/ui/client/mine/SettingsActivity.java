package com.gaote.wuliu.ui.client.mine;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.gaote.wuliu.MyApp;
import com.gaote.wuliu.R;
import com.gaote.wuliu.tools.CheckDoubleClick;
import com.gaote.wuliu.ui.client.mine.bean.ClientPinhuoOrder;
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
    @OnClick({R.id.iv_back,R.id.rl_exit,R.id.rl_account, R.id.resetPassword, R.id.rl_about,R.id.rl_friend,R.id.rl_msg})
    void OnClcik(View view) {
        if (CheckDoubleClick.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_exit:
                showExitDialog();
                break;
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
    private void showExitDialog() {
        final Dialog dialog = new Dialog(this, R.style.CustomDialog);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_order, null);
        TextView tvTitle = view.findViewById(R.id.tv_title);
        TextView tv_cancel = view.findViewById(R.id.tv_cancel);
        TextView tv_confirm = view.findViewById(R.id.tv_confirm);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                MyApp.isLogin=false;
                MyApp.token="";
                MyApp.getInstance().setClientUser(null);
                MyApp.getInstance().setServiceUser(null);
                finish();
            }
        });

         tvTitle.setText("确认退出吗？");

        dialog.setContentView(view);
        dialog.show();
        Window window = dialog.getWindow();
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = (int) (display.getWidth() * 0.7);
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);
    }
}
