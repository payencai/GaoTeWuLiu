package com.gaote.wuliu.ui.wuliudriver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.gaote.wuliu.MyApp;
import com.gaote.wuliu.R;
import com.gaote.wuliu.bean.Result;
import com.gaote.wuliu.net.Api;
import com.gaote.wuliu.net.MyPath;
import com.gaote.wuliu.net.NetUtils;
import com.gaote.wuliu.net.OnMessageReceived;
import com.gaote.wuliu.tools.GsonUtil;
import com.gaote.wuliu.ui.wuliudriver.mvp.model.WuliuDriverInfo;
import com.gaote.wuliu.ui.wuliudriver.mvp.model.WuliuNetOrderModel;
import com.lzy.okgo.model.HttpParams;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

@Route(path = MyPath.WuliuDriver.WuliuDriverInfo)
public class WuliuDriverinfoActivity extends AppCompatActivity {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.civ_avatar)
    CircleImageView civAvatar;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_plateNum)
    TextView tvPlateNum;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_volume)
    TextView tvVolume;
    @BindView(R.id.tv_count)
    TextView tvCount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wuliu_driverinfo);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tv_title.setText("他的信息");
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        getInfo();
    }
    private void initData(WuliuDriverInfo driverInform) {
        if(!TextUtils.isEmpty(driverInform.getPortrait())){
            Glide.with(this).load(driverInform.getPortrait()).into(civAvatar);
        }
        tvName.setText(driverInform.getName());
        tvPhone.setText(driverInform.getTelnum());
        tvPlateNum.setText(driverInform.getPlateNum());
        tvType.setText(driverInform.getType());
        tvVolume.setText(driverInform.getSize());
        tvCount.setText((driverInform.getCount() == null ? "0单" : driverInform.getCount() + "单"));
    }
    public void getInfo() {
        NetUtils.getInstance().get( MyApp.token,Api.BASE_URL + Api.WuliuDriver.URL_LOGISTICS_DRIVER_INFORM, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                LogUtils.e(response);
                Result<WuliuDriverInfo> result = GsonUtil.fromJsonObject(response, WuliuDriverInfo.class);
                int code = result.getResultCode();
                if (code == 0) {
                     initData(result.getData());
                } else {
                    ToastUtils.showShort(result.getMessage());
                }
            }



            @Override
            public void onError(String error) {

            }
        });
    }
}
