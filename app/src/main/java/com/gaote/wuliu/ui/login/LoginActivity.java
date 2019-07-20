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

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.gaote.wuliu.R;


import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_pwd)
    EditText et_pwd;
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
    int type=1;
    String phone="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initView();
    }
    @OnClick({R.id.tv_type,R.id.ivask,R.id.cancelBtn,R.id.tv_proxy,R.id.ivdel})
    void OnClick(View view ){
        switch (view.getId()){
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
        if(type==1){
            type=2;
            tv_type.setText("手机登录");
            ll_pwd.setVisibility(View.VISIBLE);
            ll_phone.setVisibility(View.GONE);
        }else{
            type=1;
            tv_type.setText("密码登录");
            ll_phone.setVisibility(View.VISIBLE);
            ll_pwd.setVisibility(View.GONE);
        }
    }

    private void initView() {

        ArrayList<CustomTabEntity> customTabEntities=new ArrayList<>();
        customTabEntities.add(new TabTitle("用户端"));
        customTabEntities.add(new TabTitle("服务端"));
        tab_type.setTabData(customTabEntities);
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
