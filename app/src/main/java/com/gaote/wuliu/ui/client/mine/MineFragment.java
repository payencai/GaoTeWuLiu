package com.gaote.wuliu.ui.client.mine;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.gaote.wuliu.MyApp;
import com.gaote.wuliu.R;
import com.gaote.wuliu.net.Api;
import com.gaote.wuliu.net.MyPath;
import com.gaote.wuliu.net.NetUtils;
import com.gaote.wuliu.net.OnMessageReceived;
import com.gaote.wuliu.tools.CheckDoubleClick;
import com.gaote.wuliu.ui.client.mine.address.MyAddressActivity;
import com.gaote.wuliu.ui.client.mine.mvp.model.UserInfo;
import com.gaote.wuliu.ui.client.mine.order.PinhuoOrderActivity;
import com.gaote.wuliu.ui.client.mine.order.WuliuOrderActivity;
import com.gaote.wuliu.ui.login.LoginActivity;
import com.gaote.wuliu.ui.login.mvp.model.ClientUser;
import com.gaote.wuliu.ui.login.mvp.model.ServiceUser;
import com.google.gson.Gson;
import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.components.ImmersionFragment;
import com.gyf.immersionbar.components.SimpleImmersionFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends ImmersionFragment {

    @BindView(R.id.tv_name)
    TextView tv_name;
    @BindView(R.id.tv_login)
    TextView tv_login;
    @BindView(R.id.iv_head)
    ImageView iv_head;
    public MineFragment() {
        // Required empty public constructor
    }
    UserInfo userInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        initView();
        return view;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMsg(String name) {
        if (name.equals("mine")) {
            getUserInfo();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        if (MyApp.isLogin)
            getUserInfo();
    }

    private void getUserInfo() {
        NetUtils.getInstance().get(MyApp.token, Api.BASE_URL + Api.Client.getUserInfo, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                LogUtils.e(response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        userInfo = new Gson().fromJson(data.toString(), UserInfo.class);
                        setUI();
                    } else {
                        String msg = jsonObject.getString("message");
                        ToastUtils.showShort(msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }
    private void setUI(){
        tv_name.setText(userInfo.getName());
        tv_login.setVisibility(View.GONE);
        if(!TextUtils.isEmpty(userInfo.getPortraint()))
        Glide.with(getContext()).load(userInfo.getPortraint()).apply(RequestOptions.circleCropTransform()).into(iv_head);
    }
    @OnClick({R.id.rl_wuliu,R.id.rl_address,R.id.tv_login, R.id.ll_settings, R.id.iv_head, R.id.rl_reback, R.id.rl_driver, R.id.rl_pinhuo,R.id.rl_coupon})
    void OnClcik(View view) {
        if (CheckDoubleClick.isFastDoubleClick()) {
            return;
        }
        if(!MyApp.isLogin){
            ARouter.getInstance().build(MyPath.Mine.Login).navigation();
            return;
        }
        switch (view.getId()) {
            case R.id.rl_coupon:
                ARouter.getInstance().build(MyPath.Mine.MyCoupon).navigation();
                break;
            case R.id.rl_wuliu:
                startActivity(new Intent(getContext(), WuliuOrderActivity.class));
                break;
            case R.id.rl_address:
                ARouter.getInstance().build(MyPath.Mine.Address).navigation();
                break;
            case R.id.rl_pinhuo:
                startActivity(new Intent(getContext(), PinhuoOrderActivity.class));
                break;
            case R.id.rl_driver:
                startActivity(new Intent(getContext(), BecomeDriverActivity.class));
                break;
            case R.id.rl_reback:
                startActivity(new Intent(getContext(), HelpRebackActivity.class));
                break;
            case R.id.iv_head:
                startActivity(new Intent(getContext(), UserInfoActivity.class));
                break;
            case R.id.ll_settings:
                startActivity(new Intent(getContext(), SettingsActivity.class));
                break;
            case R.id.tv_login:
                startActivity(new Intent(getContext(), LoginActivity.class));
                break;
        }
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this).statusBarColor(R.color.mine_header).fitsSystemWindows(true).statusBarDarkFont(true).navigationBarDarkIcon(true).init();
    }

}
