package com.gaote.wuliu.ui.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;

import com.gaote.wuliu.R;

import com.gaote.wuliu.net.MyPath;
import com.gaote.wuliu.ui.login.mvp.component.DaggerLoginComponent;
import com.gaote.wuliu.ui.login.mvp.model.ClientUser;
import com.gaote.wuliu.ui.login.mvp.model.LoginEvent;
import com.gaote.wuliu.ui.login.mvp.model.LoginModel;
import com.gaote.wuliu.ui.login.mvp.model.ServiceUser;
import com.gaote.wuliu.ui.login.mvp.module.LoginModule;
import com.gaote.wuliu.ui.login.mvp.presenter.LoginPresenter;
import com.gaote.wuliu.ui.login.mvp.view.LoginView;
import com.gaote.wuliu.widget.VerCodeInputView;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;


import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
@Route(path = MyPath.Mine.Login)
public class LoginActivity extends AppCompatActivity implements LoginView {
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_pwd)
    EditText et_pwd;
    @BindView(R.id.et_account)
    EditText et_account;
    @BindView(R.id.tv_send)
    TextView tv_send;
    @BindView(R.id.tab_type)
    CommonTabLayout tab_type;
    @BindView(R.id.ivask)
    ImageView ivask;
    @BindView(R.id.tv_type)
    TextView tv_type;
    @BindView(R.id.tv_phone)
    TextView tv_phone;
    @BindView(R.id.tv_again)
    TextView tv_again;
    @BindView(R.id.ll_sec)
    LinearLayout ll_sec;
    @BindView(R.id.vi_code)
    VerCodeInputView vi_code;
    @BindView(R.id.ll_fir)
    LinearLayout ll_fir;
    @BindView(R.id.ll_phone)
    LinearLayout ll_phone;
    @BindView(R.id.ll_pwd)
    LinearLayout ll_pwd;
    @BindView(R.id.ivdel)
    ImageView ivdel;
    @BindView(R.id.ll_third)
    LinearLayout ll_third;
    int type=2;//2.用户端1服务端
    String phone="";
    int count=60;
    String code;
    int LoginType=1;//1 手机 2 密码 3 qq 4 wechat 5 blog
    String TAG="LOGIN";
    @Inject LoginPresenter loginPresenter;
    LoginModel.LoginRequest loginRequest;
    MyCountDownTimer myCountDownTimer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();
    }
     @Subscribe(threadMode = ThreadMode.MAIN)
     public void OnExit(LoginEvent loginEvent){
        if(loginEvent.getCode()==200){
            finish();
        }
     }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void qqLogin() {
        authorization(SHARE_MEDIA.QQ,1);
    }

    public void weiXinLogin() {
        authorization(SHARE_MEDIA.WEIXIN,2);
    }

    public void sinaLogin() {
        authorization(SHARE_MEDIA.SINA,3);
    }

    //授权
    private void authorization(SHARE_MEDIA share_media,int flag) {
        UMShareAPI.get(this).getPlatformInfo(this, share_media, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                Log.d(TAG, "onStart " + "授权开始");
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {


                //sdk是6.4.4的,但是获取值的时候用的是6.2以前的(access_token)才能获取到值,未知原因
                String uid = map.get("uid");
                String openid = map.get("openid");//微博没有
                String name = map.get("name");
                String gender = map.get("gender");
                String header = map.get("iconurl");
                Log.d(TAG, "onComplete:" + openid);
                Toast.makeText(getApplicationContext(), "name=" + name + ",gender=" + gender, Toast.LENGTH_SHORT).show();
                if(flag==3){  //qq
                    loginRequest.setOpenId(uid);
                }else{   //wb
                    loginRequest.setOpenId(openid);
                }
                loginRequest.setName(name);
                loginRequest.setHeadUrl(header);
                loginRequest.setType(type);
                loginRequest.setLoginType(LoginType);
                loginRequest.setPassword(et_pwd.getEditableText().toString());
                loginRequest.setUsername(et_account.getEditableText().toString());
                loginPresenter.start(loginRequest);
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                Log.d(TAG, "onError " + "授权失败");
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                Log.d(TAG, "onCancel " + "授权取消");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
    @OnClick({R.id.tv_type,R.id.ivask,R.id.cancelBtn,R.id.tv_proxy,R.id.ivdel,R.id.tv_login,R.id.iv_wechat,R.id.iv_qq,R.id.iv_wb,R.id.tv_send,R.id.tv_again})
    void OnClick(View view ){
        switch (view.getId()){
            case R.id.tv_again:
                loginPresenter.getCode(phone);
                break;
            case R.id.tv_send:
                loginPresenter.getCode(phone);
                break;
            case R.id.iv_wechat:
                LoginType=4;
                weiXinLogin();
                break;
            case R.id.iv_qq:
                LoginType=3;
                qqLogin();
                break;
            case R.id.iv_wb:
                LoginType=5;
                sinaLogin();
                break;
            case R.id.tv_login:
                loginRequest.setType(type);
                loginRequest.setLoginType(LoginType);
                loginRequest.setPassword(et_pwd.getEditableText().toString());
                loginRequest.setUsername(et_account.getEditableText().toString());
                loginPresenter.start(loginRequest);
                break;
            case R.id.ivdel:
                et_phone.setText("");
                break;
            case R.id.tv_proxy:
                break;
            case R.id.cancelBtn:
                finish();
                break;
            case R.id.tv_type:
                changeLoginType();
                break;
            case R.id.ivask:
                startActivity(new Intent(LoginActivity.this,ForgetPwdActivity.class));
                break;
        }
    }
    private void changeLoginType(){
        if(LoginType==1){
            LoginType=2;
            tv_type.setText("手机登录");
            ll_pwd.setVisibility(View.VISIBLE);
            ll_phone.setVisibility(View.GONE);
        }else{
            LoginType=1;
            tv_type.setText("密码登录");
            ll_phone.setVisibility(View.VISIBLE);
            ll_pwd.setVisibility(View.GONE);
        }
    }
    private void setDebugData(){
        et_account.setText("13202908144");
        et_pwd.setText("123456");
    }
    private void initView() {
        loginRequest=new LoginModel.LoginRequest();
        myCountDownTimer=new MyCountDownTimer(60000,1000);
        setDebugData();//设置调试数据
        initTab(); //初始化tab
        tv_send.setEnabled(false);
        vi_code.setOnCompleteListener(new VerCodeInputView.OnCompleteListener() {
            @Override
            public void onComplete(String content) {
                code=content;
                loginRequest.setUsername(phone);
                loginRequest.setType(type);
                loginRequest.setCode(code);
                loginRequest.setLoginType(LoginType);
                loginPresenter.start(loginRequest);
            }
        });
        et_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                phone=editable.toString();
                if(!TextUtils.isEmpty(phone)){
                    ivdel.setVisibility(View.VISIBLE);
                    if(phone.length()==11){
                        tv_send.setEnabled(true);
                        tv_send.setTextColor(getResources().getColor(R.color.btn_color_blue));
                    }
                }else{
                    tv_send.setTextColor(getResources().getColor(R.color.color_999));
                    ivdel.setVisibility(View.GONE);
                    tv_send.setEnabled(false);
                }
            }
        });
        DaggerLoginComponent.builder().loginModule(new LoginModule(this)).build().inject(this);
    }
    private void initTab(){
        ArrayList<CustomTabEntity> customTabEntities=new ArrayList<>();
        customTabEntities.add(new TabTitle("用户端"));
        customTabEntities.add(new TabTitle("服务端"));
        tab_type.setTabData(customTabEntities);
        tab_type.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                if(position==0){
                    type=2;
                    ll_third.setVisibility(View.VISIBLE);
                }else{
                    type=1;
                    ll_third.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }
    @Override
    public void showLoading() {

    }

    @Override
    public void dissmissLoading() {

    }

    @Override
    public void onSendCode() {
        tv_phone.setText(phone);
        ll_fir.setVisibility(View.GONE);
        ll_sec.setVisibility(View.VISIBLE);
        myCountDownTimer.start();

    }

    @Override
    public void saveClientUser(ClientUser clientUser) {
        SPUtils.getInstance().put("role","2");
        //ToastUtils.showShort("用户端登录成功");
        EventBus.getDefault().post("mine");
        finish();

    }

    @Override
    public void saveServiceUser(ServiceUser serviceUser) {
        SPUtils.getInstance().put("role","1");
        if(serviceUser.getType()==4){  //物流司机
            ARouter.getInstance().build(MyPath.WuliuDriver.Main).navigation();
        }else if(serviceUser.getType()==5){  //网点
            ARouter.getInstance().build(MyPath.Net.Main).navigation();
        }else{   //6拼货司机
            ARouter.getInstance().build(MyPath.PinhuoDriver.Main).navigation();
        }
        finish();
    }

    class TabTitle implements CustomTabEntity {
        private String title;
        public TabTitle(String title) {
            this.title=title;
        }

        @Override
        public String getTabTitle() {
            return title;
        }

        @Override
        public int getTabSelectedIcon() {
            return 0;
        }

        @Override
        public int getTabUnselectedIcon() {
            return 0;
        }
    }
    class MyCountDownTimer extends CountDownTimer {


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
}
