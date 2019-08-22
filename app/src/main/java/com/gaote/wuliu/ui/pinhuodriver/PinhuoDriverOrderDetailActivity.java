package com.gaote.wuliu.ui.pinhuodriver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ScrollView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.maps.MapView;
import com.gaote.wuliu.R;
import com.gaote.wuliu.net.MyPath;
import com.gaote.wuliu.widget.MapContainer;

import butterknife.BindView;
import butterknife.ButterKnife;
@Route(path = MyPath.PinhuoDriver.PinhuoDriverOrderDetail)
public class PinhuoDriverOrderDetailActivity extends AppCompatActivity {
    @BindView(R.id.map)
    MapView mMapView;
    @BindView(R.id.scrollview)
    ScrollView scrollview;
    @BindView(R.id.map_container)
    MapContainer mapContainer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinhuo_driver_order_detail);
        ButterKnife.bind(this);
        mMapView.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        mapContainer.setScrollView(scrollview);
    }
}
