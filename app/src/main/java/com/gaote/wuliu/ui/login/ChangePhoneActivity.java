package com.gaote.wuliu.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.ToastUtils;
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


public class ChangePhoneActivity extends AppCompatActivity {
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.et_account)
    EditText et_account;
    @BindView(R.id.et_pwd)
    EditText et_pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_phone);
        ButterKnife.bind(this);
    }
    @OnClick({R.id.iv_back,R.id.tv_submit})
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
                if(TextUtils.isEmpty(et_account.getEditableText().toString())){
                    ToastUtils.showShort("请输入账号！");
                    return;
                }
                if(TextUtils.isEmpty(et_pwd.getEditableText().toString())){
                    ToastUtils.showShort("请输入密码！");
                    return;
                }
                findAccount();
                break;


            case R.id.iv_back:
                finish();
                break;

        }
    }
    private void findAccount(){
        HttpParams httpParams=new HttpParams();
        httpParams.put("account",et_account.getEditableText().toString());
        httpParams.put("oldTelephone",et_phone.getEditableText().toString());
        httpParams.put("password",et_pwd.getEditableText().toString());
        NetUtils.getInstance().post(Api.BASE_URL + Api.Manage.verificationUserByInfo,httpParams, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                Result<String> result= GsonUtil.fromJsonObject(response,String.class);
                if(result.getResultCode()==0){
                    ToastUtils.showShort("修改成功");
                    Intent intent=new Intent(ChangePhoneActivity.this,ChangeConfirmActivity.class);
                    intent.putExtra("phone",et_phone.getEditableText().toString());
                    startActivity(intent);
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
