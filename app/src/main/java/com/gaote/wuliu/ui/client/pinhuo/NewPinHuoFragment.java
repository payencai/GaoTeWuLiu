package com.gaote.wuliu.ui.client.pinhuo;


import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.core.LogisticsCenter;
import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;
import com.flyco.tablayout.SlidingTabLayout;
import com.gaote.wuliu.MyApp;
import com.gaote.wuliu.R;
import com.gaote.wuliu.base.even.PinhuoEvent;
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
import com.zaaach.citypicker.CityPicker;
import com.zaaach.citypicker.adapter.OnPickListener;
import com.zaaach.citypicker.model.City;
import com.zaaach.citypicker.model.HotCity;
import com.zaaach.citypicker.model.LocateState;
import com.zaaach.citypicker.model.LocatedCity;

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
    @BindView(R.id.tv_city)
    TextView tvCity;
    public NewPinHuoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_new_pinhuo, container, false);
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
    public void OnLocationSuceess(PinhuoEvent pinhuoEvent){
        if(pinhuoEvent.getMsg()==200){
            tvCity.setText(MyApp.getInstance().getaMapLocation().getCity());
        }
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
    private void selectCity(){
        List<HotCity> hotCities = new ArrayList<>();
        hotCities.add(new HotCity("北京", "北京", "101010100")); //code为城市代码
        hotCities.add(new HotCity("上海", "上海", "101020100"));
        hotCities.add(new HotCity("广州", "广东", "101280101"));
        hotCities.add(new HotCity("深圳", "广东", "101280601"));
        hotCities.add(new HotCity("杭州", "浙江", "101210101"));

        CityPicker.from(this) //activity或者fragment
                .enableAnimation(true)	//启用动画效果，默认无
                .setLocatedCity(new LocatedCity("杭州", "浙江", "101210101"))
                .setHotCities(hotCities)	//指定热门城市
                .setOnPickListener(new OnPickListener() {
                    @Override
                    public void onPick(int position, City data) {

                    }

                    @Override
                    public void onCancel(){

                    }

                    @Override
                    public void onLocate() {

                    }
                })
                .show();
    }
    @OnClick({R.id.btn_submit, R.id.rl_send,R.id.rl_get,R.id.tv_city})
    void OnClcik(View view) {
        if (CheckDoubleClick.isFastDoubleClick()) {
            return;
        }
        Postcard postcard = ARouter.getInstance()
                .build(MyPath.Mine.Address);
        Intent intent;
        switch (view.getId()) {
            case R.id.tv_city:
                selectCity();
                break;
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
        ImmersionBar.with(this).init();
    }

}
