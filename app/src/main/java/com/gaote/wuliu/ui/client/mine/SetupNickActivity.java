package com.gaote.wuliu.ui.client.mine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gaote.wuliu.MyApp;
import com.gaote.wuliu.R;
import com.gaote.wuliu.bean.Result;
import com.gaote.wuliu.net.Api;
import com.gaote.wuliu.net.NetUtils;
import com.gaote.wuliu.net.OnMessageReceived;
import com.gaote.wuliu.tools.GsonUtil;
import com.lzy.okgo.model.HttpParams;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SetupNickActivity extends AppCompatActivity {
    @BindView(R.id.et_nick)
    EditText etNick;
    String nick;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_nick);
        ButterKnife.bind(this);
        nick=getIntent().getStringExtra("nick");
        etNick.setText(nick);
    }
    @OnClick({R.id.iv_back,R.id.tv_finish,R.id.fr_del})
    void OnClick(View view){
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_finish:
                nick=etNick.getEditableText().toString();
                if(TextUtils.isEmpty(nick)){
                    ToastUtils.showShort("昵称不能为空！");
                    return;
                }
                updateNick();
                break;
            case R.id.fr_del:
                etNick.setText("");
                break;
        }
    }
    private void updateNick(){
        HttpParams httpParams=new HttpParams();
        httpParams.put("name",nick);
        NetUtils.getInstance().post(Api.BASE_URL + Api.URL_UPDATE_NAME, MyApp.token, httpParams, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                LogUtils.e(response);
                Result<String> result= GsonUtil.fromJsonObject(response,String.class);
                if(result.getResultCode()==0){
                    ToastUtils.showShort("更新成功");
                    EventBus.getDefault().post("mine");
                    EventBus.getDefault().post("nick");
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
