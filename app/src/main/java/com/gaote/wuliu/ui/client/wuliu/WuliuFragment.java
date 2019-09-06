package com.gaote.wuliu.ui.client.wuliu;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.gaote.wuliu.MyApp;
import com.gaote.wuliu.R;
import com.gaote.wuliu.base.even.PinhuoEvent;
import com.gaote.wuliu.base.even.WuliuEvent;
import com.gaote.wuliu.bean.Result;
import com.gaote.wuliu.net.Api;
import com.gaote.wuliu.net.MyPath;
import com.gaote.wuliu.net.NetUtils;
import com.gaote.wuliu.net.OnMessageReceived;
import com.gaote.wuliu.tools.CheckDoubleClick;
import com.gaote.wuliu.tools.GsonUtil;
import com.gaote.wuliu.tools.MapUtil;
import com.gaote.wuliu.ui.client.wuliu.model.MapPoint;
import com.gyf.immersionbar.ImmersionBar;
import com.gyf.immersionbar.components.ImmersionFragment;
import com.lzy.okgo.model.HttpParams;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class WuliuFragment extends ImmersionFragment {
    AMap aMap;
    MyLocationStyle myLocationStyle;
    @BindView(R.id.map)
    MapView mMapView;
    @BindView(R.id.tv_city)
    TextView tv_city;
    List<MapPoint> mapPointList;
    boolean isInit=false;
    public WuliuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_wuliu, container, false);
        ButterKnife.bind(this,view);
        EventBus.getDefault().register(this);
        isInit=true;
        mMapView.onCreate(savedInstanceState);
        initView();
        return view;
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLocation(PinhuoEvent wuliuEvent){
        switch (wuliuEvent.getMsg()){
            case 200:
                tv_city.setText(MyApp.getInstance().getaMapLocation().getCity());
                break;
        }
    }
    private void initView() {
       mapPointList = new ArrayList<>();
       initMapView();
    }
    private void getWuliuNetWorks(){
        HttpParams httpParams=new HttpParams();
        httpParams.put("longitude", MyApp.getInstance().getaMapLocation().getLongitude()+"");
        httpParams.put("latitude", MyApp.getInstance().getaMapLocation().getLatitude()+"");
        NetUtils.getInstance().get(MyApp.token,Api.BASE_URL + Api.Wuliu.getWuliuNetworks,httpParams, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                LogUtils.e(response);
                Result<List<MapPoint>> result= GsonUtil.fromJsonArray(response,MapPoint.class);
                if(result.getResultCode()==0){
                    mapPointList.addAll(result.getData());
                    for (int i = 0; i <mapPointList.size() ; i++) {
                        MapPoint mapPoint=mapPointList.get(i);
                        MapUtil.setMarker(getContext(),aMap,new LatLng(Double.parseDouble(mapPoint.getLatitude()),Double.parseDouble(mapPoint.getLongitude())),mapPoint.getNetworkPic());
                    }
                }else{
                    ToastUtils.showShort(result.getMessage());
                }

            }

            @Override
            public void onError(String error) {

            }
        });
    }

    private void initMapView(){
        aMap=mMapView.getMap();
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//只定位一次。
        myLocationStyle.showMyLocation(true);
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 自定义精度范围的圆形边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));//圆圈的颜色,设为透明的时候就可以去掉园区区域了
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.gt_location_wuliu));
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        aMap.getUiSettings().setZoomControlsEnabled(false);
        aMap.getUiSettings().setMyLocationButtonEnabled(true);//设置默认定位按钮是否显示，非必需设置。
        aMap.setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Override
    public void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mMapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mMapView.onPause();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mMapView.onSaveInstanceState(outState);
    }
    @Override
    public void initImmersionBar() {
        ImmersionBar.with(this).statusBarColor(R.color.white).fitsSystemWindows(true).statusBarDarkFont(true).navigationBarDarkIcon(true).init();
    }


    @OnClick({R.id.rl_wuliu,R.id.rl_send,R.id.rl_near,R.id.iv_know})
    void OnClick(View view){
        if(CheckDoubleClick.isFastDoubleClick()){
            return;
        }
        switch (view.getId()){
            case R.id.iv_know:
                ARouter.getInstance().build(MyPath.Wuliu.SeeLongtPhoto).navigation();
                break;
            case R.id.rl_wuliu:
                if(MyApp.isLogin)
                   ARouter.getInstance().build(MyPath.Wuliu.QueryWuliu).navigation();
                else{
                    ARouter.getInstance().build(MyPath.Mine.Login).navigation();
                }
                break;
            case R.id.rl_send:
                if(MyApp.isLogin)
                    ARouter.getInstance().build(MyPath.Wuliu.SendGoods).navigation();
                else{
                    ARouter.getInstance().build(MyPath.Mine.Login).navigation();
                }

                break;
            case R.id.rl_near:
                if(MyApp.isLogin)
                    ARouter.getInstance().build(MyPath.Wuliu.Nearshops).navigation();
                else{
                    ARouter.getInstance().build(MyPath.Mine.Login).navigation();
                }
                break;
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onNotify(WuliuEvent wuliuEvent){
        switch (wuliuEvent.getMsg()){
            case 200:
                getWuliuNetWorks();
                break;
        }
    }

}
