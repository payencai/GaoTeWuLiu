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

import com.flyco.tablayout.SlidingTabLayout;
import com.gaote.wuliu.MyApp;
import com.gaote.wuliu.R;
import com.gaote.wuliu.base.even.PinhuoEvent;
import com.gaote.wuliu.tools.CheckDoubleClick;
import com.gaote.wuliu.ui.client.mine.BindPhoneActivity;
import com.gaote.wuliu.ui.client.mine.UpdatePhoneActivity;
import com.gaote.wuliu.ui.client.pinhuo.lcl.constant.LCLConstants;
import com.gaote.wuliu.ui.client.pinhuo.lcl.fragment.LCLMiddleBusFragment;
import com.gaote.wuliu.ui.client.pinhuo.lcl.fragment.LCLMiddleTrackFragment;
import com.gaote.wuliu.ui.client.pinhuo.lcl.fragment.LCLMiniBusFragment;
import com.gaote.wuliu.ui.client.pinhuo.lcl.fragment.LCLMotorbikeFragment;
import com.gaote.wuliu.ui.client.pinhuo.lcl.fragment.LCLSedanFragment;
import com.gaote.wuliu.ui.client.pinhuo.lcl.fragment.LCLSmallTrackFragment;
import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.components.ImmersionFragment;
import com.gyf.immersionbar.components.SimpleImmersionFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class PinHuoFragment extends ImmersionFragment {
    private ArrayList<Fragment> mFragments;
    @BindView(R.id.tab_car)
    SlidingTabLayout tab_car;
    @BindView(R.id.vp_car)
    ViewPager vp_car;
    @BindView(R.id.tv_city)
    TextView tv_city;
    public PinHuoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_pin_huo, container, false);
        ButterKnife.bind(this,view);
        EventBus.getDefault().register(this);
        initView();
        return view;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNotify(PinhuoEvent pinhuoEvent){
        switch (pinhuoEvent.getMsg()){
            case 200:
                tv_city.setText(MyApp.getInstance().getaMapLocation().getCity());
                break;
        }
    }
    private void initView() {
        initTab();
    }
    private void initTab(){
        mFragments=new ArrayList<>();
        mFragments.add(new LCLMotorbikeFragment());
        mFragments.add(new LCLSedanFragment());
        mFragments.add(new LCLMiniBusFragment());
        mFragments.add(new LCLMiddleBusFragment());
        mFragments.add(new LCLSmallTrackFragment());
        mFragments.add(new LCLMiddleTrackFragment());
        tab_car.setViewPager(vp_car, LCLConstants.LCL_TAB_NAMES,getActivity(),mFragments);
    }
    @OnClick({R.id.btn_submit, R.id.ll_get})
    void OnClcik(View view) {
        if (CheckDoubleClick.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.btn_submit:
                if(checkInput())
                    takeOrder();
                break;
            case R.id.ll_get:
                break;
        }
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this).statusBarColor(R.color.white).fitsSystemWindows(true).statusBarDarkFont(true).navigationBarDarkIcon(true).init();
    }
    private boolean checkInput(){
        boolean isOk=true;
        return isOk;
    }
    private void showPayMethodDialog(){
        final Dialog dialog = new Dialog(getContext(), R.style.BottomDialog);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_pay_method, null);
        TextView tv_confirm=view.findViewById(R.id.tv_confirm);
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                showPayFinishDialog();
            }
        });
        dialog.setContentView(view);
        dialog.show();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);
    }
    private void showPayFinishDialog(){
        final Dialog dialog = new Dialog(getContext(), R.style.CustomDialog);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_pay_finish, null);
        dialog.setContentView(view);
        dialog.show();
        Window window = dialog.getWindow();
        WindowManager windowManager = getActivity().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = (int) (display.getWidth() * 0.6);
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);
    }
    private void takeOrder(){
        showPayMethodDialog();
    }
}
