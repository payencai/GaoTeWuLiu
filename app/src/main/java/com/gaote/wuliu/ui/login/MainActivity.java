package com.gaote.wuliu.ui.login;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.gaote.wuliu.MyApp;
import com.gaote.wuliu.R;
import com.gaote.wuliu.base.even.PinhuoEvent;
import com.gaote.wuliu.base.even.WuliuEvent;
import com.gaote.wuliu.bean.NormalEvent;
import com.gaote.wuliu.net.MyPath;
import com.gaote.wuliu.ui.client.mine.MineFragment;
import com.gaote.wuliu.ui.client.pinhuo.NewPinHuoFragment;
import com.gaote.wuliu.ui.client.pinhuo.PinHuoFragment;
import com.gaote.wuliu.ui.client.wuliu.WuliuFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
@Route(path = MyPath.Pinhuo.Main)
public class MainActivity extends AppCompatActivity implements AMapLocationListener {
    @BindView(R.id.fr_container)
    FrameLayout fr_container;
    @BindView(R.id.iv_tab_pinhuo)
    ImageView iv_tab_pinhuo;
    @BindView(R.id.iv_tab_wuliu)
    ImageView iv_tab_wuliu;
    @BindView(R.id.iv_tab_mine)
    ImageView iv_tab_mine;
    @BindView(R.id.tv_tab_pinhuo)
    TextView tv_tab_pinhuo;
    @BindView(R.id.tv_tab_wuliu)
    TextView tv_tab_wuliu;
    @BindView(R.id.tv_tab_mine)
    TextView tv_tab_mine;
    @BindView(R.id.ll_tab_pinhuo)
    LinearLayout ll_tab_pinhuo;
    @BindView(R.id.ll_tab_wuliu)
    LinearLayout ll_tab_wuliu;
    @BindView(R.id.ll_tab_mine)
    LinearLayout ll_tab_mine;
    FragmentManager fm;
    List<Fragment> fragments;
    NewPinHuoFragment pinHuoFragment;
    WuliuFragment wuliuFragment;
    MineFragment mineFragment;
    //声明mlocationClient对象
    public AMapLocationClient mlocationClient;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNotify(NormalEvent normalEvent){
        switch (normalEvent.getMsg()){
            case "main":
                finish();
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        fragments=new ArrayList<>();
        intiLocation();
        setHome();
    }
    private void setHome(){
        pinHuoFragment = new NewPinHuoFragment();
        fragments.add(pinHuoFragment);
        fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.fr_container, pinHuoFragment).commit();
        showFragment(pinHuoFragment);
    }
    private void intiLocation(){
        mlocationClient = new AMapLocationClient(this);
        mLocationOption = new AMapLocationClientOption();
        mlocationClient.setLocationListener(this);
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mLocationOption.setOnceLocation(true);
        mlocationClient.setLocationOption(mLocationOption);
        mlocationClient.startLocation();
    }
    private void reset() {
        tv_tab_pinhuo.setTextColor(ContextCompat.getColor(this, R.color.color_999));
        iv_tab_pinhuo.setImageResource(R.drawable.pinhuo_unselected);
        tv_tab_wuliu.setTextColor(ContextCompat.getColor(this, R.color.color_999));
        iv_tab_wuliu.setImageResource(R.drawable.logistics_unselected);
        tv_tab_mine.setTextColor(ContextCompat.getColor(this, R.color.color_999));
        iv_tab_mine.setImageResource(R.drawable.mine_unselected);
    }

    @OnClick({R.id.ll_tab_pinhuo, R.id.ll_tab_wuliu,  R.id.ll_tab_mine})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_tab_pinhuo:
                reset();
                hideAllFragment();
                tv_tab_pinhuo.setTextColor(ContextCompat.getColor(this, R.color.color_333));
                iv_tab_pinhuo.setImageResource(R.drawable.pinhuo_selected);
                showFragment(pinHuoFragment);
                break;
            case R.id.ll_tab_wuliu:
                if(wuliuFragment==null){
                    wuliuFragment = new WuliuFragment();
                    fragments.add(wuliuFragment);
                    fm.beginTransaction().add(R.id.fr_container, wuliuFragment).commit();
                }
                reset();
                hideAllFragment();
                tv_tab_wuliu.setTextColor(ContextCompat.getColor(this, R.color.color_333));
                iv_tab_wuliu.setImageResource(R.drawable.logistics_selected);
                showFragment(wuliuFragment);
                break;

            case R.id.ll_tab_mine:
                if(mineFragment==null){
                    mineFragment = new MineFragment();
                    fragments.add(mineFragment);
                    fm.beginTransaction().add(R.id.fr_container, mineFragment).commit();
                }
                reset();
                hideAllFragment();
                tv_tab_mine.setTextColor(ContextCompat.getColor(this, R.color.color_333));
                iv_tab_mine.setImageResource(R.drawable.mine_selected);
                showFragment(mineFragment);
                break;
        }
    }
    private void hideAllFragment() {
        for (Fragment fragment : fragments) {
            if (fragment != null)
                fm.beginTransaction().hide(fragment).commit();
        }

    }
    private void showFragment(Fragment fragment) {
        fm.beginTransaction().show(fragment).commit();
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        Log.e("AmapError","location Error, ErrCode:"
                + aMapLocation.getAddress() + ", errInfo:"
                + aMapLocation.getCity());
        MyApp.getInstance().setaMapLocation(aMapLocation);
        EventBus.getDefault().post(new PinhuoEvent(200));
        EventBus.getDefault().post(new WuliuEvent(200));
    }
}
