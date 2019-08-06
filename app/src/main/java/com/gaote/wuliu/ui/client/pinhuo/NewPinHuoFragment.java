package com.gaote.wuliu.ui.client.pinhuo;


import android.app.Dialog;
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

import com.alibaba.android.arouter.launcher.ARouter;
import com.flyco.tablayout.SlidingTabLayout;
import com.gaote.wuliu.R;
import com.gaote.wuliu.net.Api;
import com.gaote.wuliu.net.MyPath;
import com.gaote.wuliu.tools.CheckDoubleClick;
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

    @OnClick({R.id.btn_submit, R.id.rl_send,R.id.rl_get})
    void OnClcik(View view) {
        if (CheckDoubleClick.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.btn_submit:
                ARouter.getInstance().build(MyPath.Pinhuo.DETAIL).navigation();
                break;
            case R.id.rl_send:
                break;
            case R.id.rl_get:
                break;
        }
    }

    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this).statusBarColor(R.color.home_blue).fitsSystemWindows(true).statusBarDarkFont(false).navigationBarDarkIcon(true).init();
    }

}
