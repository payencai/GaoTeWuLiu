package com.gaote.wuliu.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import com.gaote.wuliu.ui.login.mvp.model.LoginModel;
import com.gaote.wuliu.ui.login.mvp.model.ServiceUser;
import com.gaote.wuliu.ui.login.mvp.module.LoginModule;
import com.gaote.wuliu.ui.login.mvp.presenter.LoginPresenter;
import com.gaote.wuliu.ui.login.mvp.view.LoginView;


import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

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
    @BindView(R.id.tv_again)
    TextView tv_again;
    @BindView(R.id.ll_phone)
    LinearLayout ll_phone;
    @BindView(R.id.ll_pwd)
    LinearLayout ll_pwd;
    @BindView(R.id.ivdel)
    ImageView ivdel;
    int type=2;//2.用户端1服务端
    String phone="";
    int LoginType=1;//1 手机 2 密码 3 qq 4 wechat 5 blog
    @Inject LoginPresenter loginPresenter;
    LoginModel.LoginRequest loginRequest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
    }

    @OnClick({R.id.tv_type,R.id.ivask,R.id.cancelBtn,R.id.tv_proxy,R.id.ivdel,R.id.tv_login})
    void OnClick(View view ){
        switch (view.getId()){
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
        setDebugData();//设置调试数据
        initTab(); //初始化tab
        tv_send.setEnabled(false);
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
                    tv_send.setTextColor(getResources().getColor(R.color.btn_color_blue));
                    ivdel.setVisibility(View.VISIBLE);
                    if(phone.length()==11){
                        tv_send.setEnabled(true);
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
                }else{
                    type=1;
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
    public void saveClientUser(ClientUser clientUser) {
        SPUtils.getInstance().put("role","2");
        ToastUtils.showShort("用户端登录成功");
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
}
