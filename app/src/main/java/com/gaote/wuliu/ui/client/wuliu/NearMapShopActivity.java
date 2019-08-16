package com.gaote.wuliu.ui.client.wuliu;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gaote.wuliu.MyApp;
import com.gaote.wuliu.R;
import com.gaote.wuliu.bean.Result;
import com.gaote.wuliu.net.Api;
import com.gaote.wuliu.net.MyPath;
import com.gaote.wuliu.net.NetUtils;
import com.gaote.wuliu.net.OnMessageReceived;
import com.gaote.wuliu.tools.CheckDoubleClick;
import com.gaote.wuliu.tools.GsonUtil;
import com.gaote.wuliu.tools.MapUtil;
import com.gaote.wuliu.ui.client.wuliu.model.MapPoint;
import com.lzy.okgo.model.HttpParams;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = MyPath.Wuliu.Nearshops)
public class NearMapShopActivity extends AppCompatActivity {
    AMap aMap;
    @BindView(R.id.map)
    MapView mMapView;
    List<MapPoint> mapPointList;
    MyLocationStyle myLocationStyle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_shop);
        ButterKnife.bind(this);
        mMapView.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        mapPointList = new ArrayList<>();
        initMapView();
        getWuliuNetWorks();
    }
    private void getWuliuNetWorks(){
        HttpParams httpParams=new HttpParams();
        httpParams.put("longitude", MyApp.getInstance().getaMapLocation().getLongitude()+"");
        httpParams.put("latitude", MyApp.getInstance().getaMapLocation().getLatitude()+"");
        NetUtils.getInstance().get(MyApp.token, Api.BASE_URL + Api.Wuliu.getWuliuNetworks,httpParams, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                LogUtils.e(response);
                Result<List<MapPoint>> result= GsonUtil.fromJsonArray(response,MapPoint.class);
                if(result.getResultCode()==0){
                    mapPointList.addAll(result.getData());
                    for (int i = 0; i <mapPointList.size() ; i++) {
                        MapPoint mapPoint=mapPointList.get(i);
                        MapUtil.setPaperMarker(NearMapShopActivity.this,aMap,new LatLng(Double.parseDouble(mapPoint.getLatitude()),Double.parseDouble(mapPoint.getLongitude())));
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
    @OnClick({R.id.tv_list,R.id.rl_address,R.id.iv_back})
    void OnClick(View view){
        if(CheckDoubleClick.isFastDoubleClick()){
            return;
        }
        switch (view.getId()){
            case R.id.tv_list:
                ARouter.getInstance().build(MyPath.Wuliu.NearListshops).navigation();
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_address:
                //ARouter.getInstance().build(MyPath.Wuliu.SendGoods).navigation();
                break;

        }
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
}
