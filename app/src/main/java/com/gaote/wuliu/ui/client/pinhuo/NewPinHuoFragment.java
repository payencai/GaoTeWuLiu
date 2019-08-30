package com.gaote.wuliu.ui.client.pinhuo;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.alibaba.android.arouter.core.LogisticsCenter;
import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;
import com.flyco.tablayout.SlidingTabLayout;
import com.gaote.wuliu.MyApp;
import com.gaote.wuliu.R;
import com.gaote.wuliu.bean.AddrBean;
import com.gaote.wuliu.net.Api;
import com.gaote.wuliu.net.MyPath;
import com.gaote.wuliu.tools.CheckDoubleClick;
import com.gaote.wuliu.ui.client.mine.mvp.model.Address;
import com.gaote.wuliu.ui.client.pinhuo.lcl.constant.LCLConstants;
import com.gaote.wuliu.ui.client.pinhuo.lcl.fragment.LCLMiddleBusFragment;
import com.gaote.wuliu.ui.client.pinhuo.lcl.fragment.LCLMiddleTrackFragment;
import com.gaote.wuliu.ui.client.pinhuo.lcl.fragment.LCLMiniBusFragment;
import com.gaote.wuliu.ui.client.pinhuo.lcl.fragment.LCLMotorbikeFragment;
import com.gaote.wuliu.ui.client.pinhuo.lcl.fragment.LCLSedanFragment;
import com.gaote.wuliu.ui.client.pinhuo.lcl.fragment.LCLSmallTrackFragment;
import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.components.ImmersionFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class NewPinHuoFragment extends ImmersionFragment {
    Address addrSendBean;
    Address addrGetBean;
    @BindView(R.id.tv_get)
    TextView tv_get;
    @BindView(R.id.tv_send)
    TextView tv_send;
    @BindView(R.id.tv_send_address)
    TextView tv_send_address;
    @BindView(R.id.tv_get_address)
    TextView tv_get_address;
    public NewPinHuoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_new_pinhuo, container, false);
        ButterKnife.bind(this,view);
        initView();
        return view;
    }

    private void initView() {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data!=null){
            if(requestCode==1){
                addrSendBean= (Address) data.getSerializableExtra("data");
                tv_send.setText(addrSendBean.getName()+" "+addrSendBean.getTelephone());
                tv_send_address.setText(addrSendBean.getProvince()+addrSendBean.getCity()+addrSendBean.getArea()+addrSendBean.getAddress());
            }else if(requestCode==2){
                addrGetBean= (Address) data.getSerializableExtra("data");
                tv_get.setText(addrGetBean.getName()+" "+addrGetBean.getTelephone());
                tv_get_address.setText(addrGetBean.getProvince()+addrGetBean.getCity()+addrGetBean.getArea()+addrGetBean.getAddress());
            }
        }
    }

    @OnClick({R.id.btn_submit, R.id.rl_send,R.id.rl_get})
    void OnClcik(View view) {
        if (CheckDoubleClick.isFastDoubleClick()) {
            return;
        }
        Postcard postcard = ARouter.getInstance()
                .build(MyPath.Mine.Address);
        Intent intent;
        switch (view.getId()) {
            case R.id.btn_submit:
                ARouter.getInstance().build(MyPath.Pinhuo.DETAIL).withSerializable("send",addrSendBean).withSerializable("get",addrGetBean).navigation();
                break;
            case R.id.rl_send:
                if(!MyApp.isLogin){
                    ARouter.getInstance().build(MyPath.Mine.Login).navigation();
                    return;
                }
                LogisticsCenter.completion(postcard);
                intent = new Intent(getContext(), postcard.getDestination());
                intent.putExtras(postcard.getExtras());
                startActivityForResult(intent, 1);
                break;
            case R.id.rl_get:
                if(!MyApp.isLogin){
                    ARouter.getInstance().build(MyPath.Mine.Login).navigation();
                    return;
                }
                LogisticsCenter.completion(postcard);
                intent = new Intent(getContext(), postcard.getDestination());
                intent.putExtras(postcard.getExtras());
                startActivityForResult(intent, 2);
                break;
        }
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this).statusBarColor(R.color.home_blue).fitsSystemWindows(true).statusBarDarkFont(false).navigationBarDarkIcon(true).init();
    }

}
