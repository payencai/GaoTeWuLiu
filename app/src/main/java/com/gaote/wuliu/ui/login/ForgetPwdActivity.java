package com.gaote.wuliu.ui.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.blankj.utilcode.util.ToastUtils;
import com.gaote.wuliu.MyApp;
import com.gaote.wuliu.R;
import com.gaote.wuliu.bean.Result;
import com.gaote.wuliu.net.Api;
import com.gaote.wuliu.net.NetUtils;
import com.gaote.wuliu.net.OnMessageReceived;
import com.gaote.wuliu.tools.CheckDoubleClick;
import com.gaote.wuliu.tools.GsonUtil;

import com.lzy.okgo.model.HttpParams;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ForgetPwdActivity extends AppCompatActivity {
    @BindView(R.id.tv_send)
    TextView tv_send;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_code)
    EditText et_code;
    @BindView(R.id.et_pwd)
    EditText et_pwd;
    int count=60;
    MyCountDownTimer myCountDownTimer;
    SVProgressHUD svProgressHUD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pwd);
        ButterKnife.bind(this);
        svProgressHUD=new SVProgressHUD(this);
        initView();
    }

    private void initView() {
        myCountDownTimer=new MyCountDownTimer(60000,1000);
    }

    @OnClick({R.id.iv_back,R.id.tv_submit, R.id.tv_lost,R.id.tv_send})
    void OnClcik(View view) {
        if (CheckDoubleClick.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.tv_submit:
                if(TextUtils.isEmpty(et_phone.getEditableText().toString())){
                    ToastUtils.showShort("请输入手机号码！");
                    return;
                }
                if(TextUtils.isEmpty(et_code.getEditableText().toString())){
                    ToastUtils.showShort("请输入验证码！");
                    return;
                }
                if(TextUtils.isEmpty(et_pwd.getEditableText().toString())){
                    ToastUtils.showShort("请输入密码！");
                    return;
                }
                setPassword();
                break;
            case R.id.tv_send:
                if(TextUtils.isEmpty(et_phone.getEditableText().toString())){
                    ToastUtils.showShort("请输入手机号码！");
                    return;
                }
                sendCode();
                break;
            case R.id.tv_lost:
                startActivity(new Intent(ForgetPwdActivity.this, PhoneLostActivity.class));
                break;
            case R.id.iv_back:
                finish();
                break;

        }
    }

    private void setPassword(){
        HttpParams httpParams=new HttpParams();
        httpParams.put("code",et_code.getEditableText().toString());
        httpParams.put("telephone",et_phone.getEditableText().toString());
        httpParams.put("password",et_pwd.getEditableText().toString());
        NetUtils.getInstance().post(Api.BASE_URL + Api.Manage.forgetPassword,httpParams, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                Result<String> result= GsonUtil.fromJsonObject(response,String.class);
                if(result.getResultCode()==0){
                    ToastUtils.showShort("修改成功");
                    finish();
                }else{
                    ToastUtils.showShort(result.getMessage());
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }
    class MyCountDownTimer extends CountDownTimer {


        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tv_send.setEnabled(false);
            tv_send.setTextColor(getResources().getColor(R.color.color_999));
            count--;
            //倒计时的过程中回调该函数
            tv_send.setText(count + "s");

        }

        @Override
        public void onFinish() {
            tv_send.setEnabled(true);
            tv_send.setText("重新发送");
            count=60;
            tv_send.setTextColor(Color.parseColor("#189BF8"));
        }
    }
    private void sendCode(){
        svProgressHUD.show();
        HttpParams httpParams=new HttpParams();
        httpParams.put("telephone",et_phone.getEditableText().toString());
        NetUtils.getInstance().post(Api.BASE_URL + Api.Manage.sendCode, MyApp.token, httpParams, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                svProgressHUD.dismiss();
                Result<String> result= GsonUtil.fromJsonObject(response,String.class);
                if(result.getResultCode()==0){
                    myCountDownTimer.start();
                    ToastUtils.showShort("验证码已发送，请注意查收！");
                }else{
                    ToastUtils.showShort(result.getMessage());
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }
}
