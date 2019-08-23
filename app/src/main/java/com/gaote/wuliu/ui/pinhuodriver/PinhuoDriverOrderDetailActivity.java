package com.gaote.wuliu.ui.pinhuodriver;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.MyLocationStyle;
import com.blankj.utilcode.util.SPUtils;
import com.gaote.wuliu.R;
import com.gaote.wuliu.net.MyPath;
import com.gaote.wuliu.ui.client.mine.mvp.model.PinhuoOrder;
import com.gaote.wuliu.ui.client.mine.order.PinhuoOrderDetailActivity;
import com.gaote.wuliu.ui.client.mine.order.WuliuDetailActivity;
import com.gaote.wuliu.widget.MapContainer;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = MyPath.PinhuoDriver.PinhuoDriverOrderDetail)
public class PinhuoDriverOrderDetailActivity extends AppCompatActivity {
    AMap aMap;
    MyLocationStyle myLocationStyle;
    @BindView(R.id.map)
    MapView mMapView;
    @BindView(R.id.scrollview)
    ScrollView scrollview;
    @BindView(R.id.map_container)
    MapContainer mapContainer;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_area1)
    TextView tv_area1;
    @BindView(R.id.tv_city1)
    TextView tv_city1;
    @BindView(R.id.tv_area2)
    TextView tv_area2;
    @BindView(R.id.tv_city2)
    TextView tv_city2;
    @BindView(R.id.tv_order_time)
    TextView tv_order_time;
    @BindView(R.id.tv_status)
    TextView tv_status;
    @BindView(R.id.tv_send)
    TextView tv_send;
    @BindView(R.id.tv_phone1)
    TextView tv_phone1;
    @BindView(R.id.tv_addr1)
    TextView tv_addr1;
    @BindView(R.id.tv_get)
    TextView tv_get;
    @BindView(R.id.tv_phone2)
    TextView tv_phone2;
    @BindView(R.id.tv_addr2)
    TextView tv_addr2;
    @BindView(R.id.tv_status_desc)
    TextView tv_status_desc;
    @BindView(R.id.tv_good_name)
    TextView tv_good_name;
    @BindView(R.id.tv_good_weight)
    TextView tv_good_weight;
    @BindView(R.id.tv_good_volume)
    TextView tv_good_volume;
    @BindView(R.id.tv_good_count)
    TextView tv_good_count;
    @BindView(R.id.tv_car_type)
    TextView tv_car_type;
    @BindView(R.id.tv_get_good_time)
    TextView tv_get_good_time;
    @BindView(R.id.tv_get_good_addr)
    TextView tv_get_good_addr;

    @BindView(R.id.rl_reback)
    RelativeLayout rl_reback;
    @BindView(R.id.item_btn_mid)
    Button btnCancel;
    @BindView(R.id.item_btn_right)
    Button btnRight;
    @BindView(R.id.item_rl_bottom)
    RelativeLayout rlBottom;
    @Autowired(name = "data")
    public PinhuoOrder pinhuoOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinhuo_driver_order_detail);
        ButterKnife.bind(this);
        mMapView.onCreate(savedInstanceState);
        ARouter.getInstance().inject(this);
        initView();
    }

    private void initView() {
        mapContainer.setScrollView(scrollview);
        initMapView();
        initData();
    }
    private void initData() {
        if (pinhuoOrder != null) {
            initFirstLine();
            initSecondLine();
            initBottomLayout();
            initGoodInform();
        }
    }
    private void initBottomLayout() {
        int type = Integer.valueOf(pinhuoOrder.getType());

        if (type == 2) {//待接货
            rlBottom.setVisibility(View.VISIBLE);
            btnRight.setText("确认接货");
            btnCancel.setVisibility(View.VISIBLE);
            mapContainer.setVisibility(View.VISIBLE);
        } else if (type == 3) {//待送货
            rlBottom.setVisibility(View.VISIBLE);
            btnRight.setText("确认送达");
            mapContainer.setVisibility(View.VISIBLE);
        } else if(type==6){  //待签收
            rl_reback.setVisibility(View.VISIBLE);
        } else{

        }




    }
    private void initSecondLine() {
        tv_send.setText("寄件人：" + pinhuoOrder.getAddress().getName());
        tv_phone1.setText(pinhuoOrder.getAddress().getTelephone());
        tv_addr1.setText("寄件地址：" + pinhuoOrder.getAddress().getAddress());
        tv_get.setText("收件人：" + pinhuoOrder.getConsignee());
        tv_phone2.setText(pinhuoOrder.getConsigneeTelephone());
        tv_addr2.setText("收件地址：" + pinhuoOrder.getConsigneeAddress());
    }

    private void initFirstLine() {
        tv_title.setText("订单详情");
        tv_area1.setText(pinhuoOrder.getAddress().getArea());
        tv_city1.setText(pinhuoOrder.getAddress().getProvince() + " " + pinhuoOrder.getAddress().getCity());
        tv_area2.setText(pinhuoOrder.getConsigneeArea());
        tv_city2.setText(pinhuoOrder.getConsigneeProvince() + " " + pinhuoOrder.getConsigneeCity());
        tv_order_time.setText(pinhuoOrder.getOrderTime());
        int type = Integer.valueOf(pinhuoOrder.getType());
        switch (type) {

            case 2:
                tv_status.setText("待接货");
                tv_status_desc.setText("等待司机接货");
                break;
            case 3:
                tv_status.setText("待送达");
                tv_status_desc.setText("等待司机送达");
                break;
            case 4:
                tv_status.setText("待签收");
                tv_status_desc.setText("等待用户签收");
                break;
            case 5:
                tv_status.setText("已完成");
                tv_status_desc.setText("该订单已签收");
                break;
            case 6:
                tv_status.setText("退款中");
                tv_status_desc.setText("等待退款中");
                break;
            default:
                break;
        }
    }

    private String getSizeByCar(String car) {
        if (car == null) {
            return "";
        } else if (car.equals("摩托车")) {
            return "(1.9*0.8*1.1)米";
        } else if (car.equals("小轿车")) {
            return "(3.5*1.5*1.5)米";
        } else if (car.equals("三轮车")) {
            return "(3.5*1.2*1.8)米";
        } else if (car.equals("小面包车")) {
            return "(1.8*1.3*1.1)米";
        } else if (car.equals("中面包车")) {
            return "(2.7*1.4*1.2)米";
        } else if (car.equals("小货车")) {
            return "(2.7*1.5*1.7)米";
        } else if (car.equals("中货车")) {
            return "(4.2*1.8*1.8)米";
        } else {
            return "";
        }
    }

    private void initGoodInform() {
        tv_good_name.setText("货品：" + pinhuoOrder.getArticleName());
        tv_good_weight.setText("重量：" + pinhuoOrder.getWeight() + "kg");
        tv_good_volume.setText("体积：" + pinhuoOrder.getVolume() + "m³");
        tv_good_count.setText("数量：" + pinhuoOrder.getNum() + "件");
        tv_car_type.setText("所需车型：" + pinhuoOrder.getAnticipantCar() + getSizeByCar(pinhuoOrder.getAnticipantCar()));
        int type = Integer.valueOf(pinhuoOrder.getType());
        switch (type) {
            case 1:
                tv_get_good_time.setText("下单时间：" + pinhuoOrder.getOrderTime());
                tv_get_good_addr.setText("取货地址：" + pinhuoOrder.getPickupAddress());
                break;
            case 2:
                tv_get_good_time.setText("取货时间：" + pinhuoOrder.getAnticipantTime());
                tv_get_good_addr.setText("取货地址：" + pinhuoOrder.getPickupAddress());
                break;
            case 3:
                tv_get_good_time.setText("取货时间：" + pinhuoOrder.getGetTime());
                tv_get_good_addr.setText("取货地址：" + pinhuoOrder.getPickupAddress());
                break;
            case 4:
                tv_get_good_time.setText("派送时间：" + pinhuoOrder.getSendTime());
                tv_get_good_addr.setText("收货地址：" + pinhuoOrder.getConsigneeAddress());
                break;
            case 5:
                tv_get_good_time.setText("签收时间：" + pinhuoOrder.getEndTime());
                tv_get_good_addr.setText("收货地址：" + pinhuoOrder.getConsigneeAddress());
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
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.mipmap.gt_detail_map));
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
    @OnClick({R.id.item_btn_left, R.id.item_btn_mid, R.id.item_btn_right, R.id.iv_back, R.id.ll_wuliu})
    void OnClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ll_wuliu://物流详情
                intent = new Intent(PinhuoDriverOrderDetailActivity.this, WuliuDetailActivity.class);
                intent.putExtra("data", pinhuoOrder);
                intent.putExtra("type", 3);
                startActivity(intent);
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.item_btn_left://导航

                break;
            case R.id.item_btn_mid: //取消

                break;
            case R.id.item_btn_right://确认，送达

                break;
        }
    }
}
