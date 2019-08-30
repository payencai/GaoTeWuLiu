package com.gaote.wuliu.ui.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gaote.wuliu.MyApp;
import com.gaote.wuliu.R;
import com.gaote.wuliu.base.even.WuliuEvent;
import com.gaote.wuliu.bean.Result;
import com.gaote.wuliu.net.Api;
import com.gaote.wuliu.net.MyPath;
import com.gaote.wuliu.net.NetUtils;
import com.gaote.wuliu.net.OnMessageReceived;
import com.gaote.wuliu.tools.CheckDoubleClick;
import com.gaote.wuliu.tools.GsonUtil;
import com.gaote.wuliu.ui.login.mvp.model.ClientUser;
import com.gaote.wuliu.ui.login.mvp.model.LoginEvent;
import com.gaote.wuliu.ui.login.mvp.model.LoginModel;
import com.gaote.wuliu.ui.login.mvp.model.ServiceUser;
import com.gaote.wuliu.widget.VerCodeInputView;
import com.google.gson.Gson;
import com.lzy.okgo.model.HttpParams;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = MyPath.Login.BindAccount)
public class BindAccountActivity extends AppCompatActivity {
    @Autowired(name = "data")
    LoginModel.LoginRequest loginRequest;

    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.tv_send)
    TextView tv_send;
    @BindView(R.id.tv_again)
    TextView tv_again;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.ll_fir)
    LinearLayout ll_fir;
    @BindView(R.id.ll_sec)
    LinearLayout ll_sec;
    @BindView(R.id.ivdel)
    ImageView ivdel;
    @BindView(R.id.vi_code)
    VerCodeInputView verCodeInputView;
    String phone;
    int count=60;
    String code;
    MyCountDownTimer myCountDownTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_account);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
    }

    private void initView() {
        tv_send.setEnabled(false);
        tv_again.setEnabled(false);
        verCodeInputView.setOnCompleteListener(new VerCodeInputView.OnCompleteListener() {
            @Override
            public void onComplete(String content) {
                code=content;
                if(loginRequest.getLoginType()==3){
                    bindQQ();
                }else if(loginRequest.getLoginType()==4){
                    bindWeixin();
                }else{

                }

            }
        });
        myCountDownTimer=new MyCountDownTimer(60000,1000);
        et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                phone=s.toString();
                tv_phone.setText(phone);
                if(TextUtils.isEmpty(phone)){
                    ivdel.setVisibility(View.GONE);
                }else{
                    ivdel.setVisibility(View.VISIBLE);
                }
                if(phone.length()==11){
                    tv_send.setEnabled(true);
                    tv_send.setTextColor(Color.parseColor("#189BF8"));
                }else{
                    tv_send.setEnabled(false);
                    tv_send.setTextColor(Color.parseColor("#999999"));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
               phone=s.toString();
            }
        });
    }
    class MyCountDownTimer extends CountDownTimer{

        /**
         * @param millisInFuture    The number of millis in the future from the call
         *                          to {@link #start()} until the countdown is done and {@link #onFinish()}
         *                          is called.
         * @param countDownInterval The interval along the way to receive
         *                          {@link #onTick(long)} callbacks.
         */
        public MyCountDownTimer(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long millisUntilFinished) {
            tv_again.setEnabled(false);
            tv_again.setTextColor(getResources().getColor(R.color.color_999));
            count--;
            //倒计时的过程中回调该函数
            tv_again.setText(count + "s");

        }

        @Override
        public void onFinish() {
            tv_again.setEnabled(true);
            tv_again.setText("重新发送");
            count=60;
            tv_again.setTextColor(Color.parseColor("#189BF8"));
        }
    }
    @OnClick({R.id.tv_again,R.id.iv_back,R.id.ivdel,R.id.tv_send})
    void OnClick(View view ){
        if(CheckDoubleClick.isFastDoubleClick()){
            return;
        }
        switch (view.getId()){
            case R.id.tv_send:
                sendCode();
                break;
            case R.id.ivdel:
                et_phone.setText("");
                phone="";
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_again:
                sendCode();
                break;

        }
    }
    private void sendCode(){
        HttpParams httpParams=new HttpParams();
        httpParams.put("telephone",phone);
        NetUtils.getInstance().post(Api.BASE_URL + Api.Manage.getCode, MyApp.token, httpParams, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                Result<String> result=GsonUtil.fromJsonObject(response,String.class);
                if(result.getResultCode()==0){
                    ll_fir.setVisibility(View.GONE);
                    ll_sec.setVisibility(View.VISIBLE);
                    ToastUtils.showShort("验证码发送成功，请注意查收");
                    myCountDownTimer.start();
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }
    private void bindQQ(){
        HttpParams httpParams=new HttpParams();
        httpParams.put("telephone",phone);
        httpParams.put("qqId",loginRequest.getOpenId());
        httpParams.put("code",code);
        httpParams.put("nickName",loginRequest.getName());
        httpParams.put("heading",loginRequest.getHeadUrl());
        //LogUtils.e(phone+id+code);
        NetUtils.getInstance().post(Api.BASE_URL + Api.Manage.addUserByQqId, MyApp.token, httpParams, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                LogUtils.e(response);
                if(!response.contains("data")){
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        ToastUtils.showShort(jsonObject.getString("message"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        int code = jsonObject.getInt("resultCode");
                        if (code == 0) {
                            JSONObject data = jsonObject.getJSONObject("data");
                            if (loginRequest.getType() == 2) { //客户端登录
                                ClientUser clientUser = new Gson().fromJson(data.toString(), ClientUser.class);
                                MyApp.setClientUser(clientUser);
                                MyApp.isLogin = true;
                                MyApp.token=clientUser.getToken();
                                EventBus.getDefault().post("mine");
                                EventBus.getDefault().post(new LoginEvent(200));

                            } else {     //服务器端登录，分为3种情况：网点，物流司机，拼货司机
                                ServiceUser serviceUser = new Gson().fromJson(data.toString(), ServiceUser.class);
                                MyApp.isLogin = true;
                                MyApp.token=serviceUser.getToken();
                                MyApp.setServiceUser(serviceUser);
                                EventBus.getDefault().post(new LoginEvent(200));
                                //发出通知让MainActivity关闭
                                if(serviceUser.getType()==4){  //物流司机
                                    ARouter.getInstance().build(MyPath.WuliuDriver.Main).navigation();
                                }else if(serviceUser.getType()==5){  //网点
                                    ARouter.getInstance().build(MyPath.Net.Main).navigation();
                                }else{   //6拼货司机
                                    ARouter.getInstance().build(MyPath.PinhuoDriver.Main).navigation();
                                }
                            }
                            finish();
                        } else {
                            String msg = jsonObject.getString("message");
                            ToastUtils.showShort(msg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onError(String error) {

            }
        });
    }
    private void bindWeixin(){
        HttpParams httpParams=new HttpParams();
        httpParams.put("telephone",phone);
        httpParams.put("wxId",loginRequest.getOpenId());
        httpParams.put("code",code);
        httpParams.put("nickName",loginRequest.getName());
        httpParams.put("heading",loginRequest.getHeadUrl());
        //LogUtils.e(phone+id+code);
        NetUtils.getInstance().post(Api.BASE_URL + Api.Manage.addUserByWxId, MyApp.token, httpParams, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                LogUtils.e(response);
                if(!response.contains("data")){
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        ToastUtils.showShort(jsonObject.getString("message"));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        int code = jsonObject.getInt("resultCode");
                        if (code == 0) {
                            JSONObject data = jsonObject.getJSONObject("data");
                            if (loginRequest.getType() == 2) { //客户端登录
                                ClientUser clientUser = new Gson().fromJson(data.toString(), ClientUser.class);
                                MyApp.setClientUser(clientUser);
                                MyApp.isLogin = true;
                                MyApp.token=clientUser.getToken();
                                EventBus.getDefault().post("mine");
                                EventBus.getDefault().post(new LoginEvent(200));

                            } else {     //服务器端登录，分为3种情况：网点，物流司机，拼货司机
                                ServiceUser serviceUser = new Gson().fromJson(data.toString(), ServiceUser.class);
                                MyApp.isLogin = true;
                                MyApp.token=serviceUser.getToken();
                                MyApp.setServiceUser(serviceUser);
                                EventBus.getDefault().post(new LoginEvent(200));
                                //发出通知让MainActivity关闭
                                if(serviceUser.getType()==4){  //物流司机
                                    ARouter.getInstance().build(MyPath.WuliuDriver.Main).navigation();
                                }else if(serviceUser.getType()==5){  //网点
                                    ARouter.getInstance().build(MyPath.Net.Main).navigation();
                                }else{   //6拼货司机
                                    ARouter.getInstance().build(MyPath.PinhuoDriver.Main).navigation();
                                }
                            }
                            finish();
                        } else {
                            String msg = jsonObject.getString("message");
                            ToastUtils.showShort(msg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onError(String error) {

            }
        });
    }
}
