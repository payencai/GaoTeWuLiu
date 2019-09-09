package com.gaote.wuliu.ui.login;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.gaote.wuliu.MyApp;
import com.gaote.wuliu.R;
import com.gaote.wuliu.bean.Result;
import com.gaote.wuliu.net.Api;
import com.gaote.wuliu.net.NetUtils;
import com.gaote.wuliu.net.OnMessageReceived;
import com.gaote.wuliu.tools.CheckDoubleClick;
import com.gaote.wuliu.tools.GsonUtil;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ChangeConfirmActivity extends AppCompatActivity {
    @BindView(R.id.tv_send)
    TextView tv_send;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_code)
    EditText et_code;
    int count=60;
    String phone;
    MyCountDownTimer myCountDownTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_confirm);
        ButterKnife.bind(this);
        myCountDownTimer=new MyCountDownTimer(60000,1000);
        phone=getIntent().getStringExtra("phone");
    }
    @OnClick({R.id.iv_back,R.id.tv_submit,R.id.tv_send})
    void OnClcik(View view) {
        if (CheckDoubleClick.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.tv_send:
                if(TextUtils.isEmpty(et_phone.getEditableText().toString())){
                    return;
                }
                sendCode();
                break;
            case R.id.tv_submit:
                if(TextUtils.isEmpty(et_phone.getEditableText().toString())){
                    ToastUtils.showShort("请输入手机号码！");
                    return;
                }
                if(TextUtils.isEmpty(et_code.getEditableText().toString())){
                    ToastUtils.showShort("请输入验证码！");
                    return;
                }
                findAccount();
                break;


            case R.id.iv_back:
                finish();
                break;

        }
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

        HttpParams httpParams=new HttpParams();
        httpParams.put("telephone",et_phone.getEditableText().toString());
        NetUtils.getInstance().post(Api.BASE_URL + Api.Manage.getCode, MyApp.token, httpParams, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {

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
    private void findAccount(){
        HttpParams httpParams=new HttpParams();
        httpParams.put("code",et_code.getEditableText().toString());
        httpParams.put("newTelephone",et_phone.getEditableText().toString());
        httpParams.put("oldTelephone",phone);
        NetUtils.getInstance().post(Api.BASE_URL + Api.Manage.updateUserByNewTel,httpParams, new OnMessageReceived() {
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
}
