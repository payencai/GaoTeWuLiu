package com.gaote.wuliu.base;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.gaote.wuliu.MyApp;
import com.gaote.wuliu.R;
import com.gaote.wuliu.base.adapter.PoiAdapter;
import com.gaote.wuliu.bean.AddrBean;
import com.gaote.wuliu.net.MyPath;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = MyPath.Pinhuo.SelectAddress)
public class SelectAddressActivity extends AppCompatActivity {


    PoiSearch mPoiSearch;
    PoiSearch.Query mQuery;
    List<PoiBean> mPoiBeanList = new ArrayList<>();
    AMap aMap;
    private UiSettings mUiSettings;//定义一个UiSettings对象
    GeocodeSearch mGeocodeSearch;
    PoiAdapter mAdapter;
    String cityCode = "";
    public AMapLocationClient mlocationClient;
    public AMapLocationClientOption mLocationOption = null;
    @BindView(R.id.select_listview)
    ListView mListView;
    @BindView(R.id.et_search)
    EditText et_search;
    @BindView(R.id.select_mapview)
    MapView mMapView;
    boolean isInput = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_address);
        ButterKnife.bind(this);
        mMapView.onCreate(savedInstanceState);// 此方法须覆写，虚拟机需要在很多情况下保存地图绘制的当前状态。
        init();
    }

    @OnClick({R.id.iv_back, R.id.iv_location})
    void OnClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.iv_location:
                break;
        }
    }

    private void init() {
        initMapView();
        mAdapter = new PoiAdapter(SelectAddressActivity.this, R.layout.item_select_address, mPoiBeanList);
        mListView.setAdapter(mAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PoiBean poiBean = mPoiBeanList.get(position);
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                AddrBean addrBean = new AddrBean();
                addrBean.setFiraddr(poiBean.getName());
                addrBean.setSecaddr(poiBean.getAddress());
                addrBean.setLat(poiBean.getLat());
                addrBean.setLon(poiBean.getLon());
                bundle.putSerializable("addrbean", addrBean);
                intent.putExtras(bundle);
                setResult(1, intent);
                finish();

            }
        });
        mGeocodeSearch = new GeocodeSearch(this);
        mGeocodeSearch.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {
                searchPoi(regeocodeResult.getRegeocodeAddress().getFormatAddress());//搜索poi
            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

            }
        });
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String word = editable.toString();
                isInput = true;
                searchPoi(word);
            }
        });
        locate();
    }


    private void searchPoi(String keyWord) {


        mQuery = new PoiSearch.Query(keyWord, "", cityCode);
        mQuery.setPageSize(20);// 设置每页最多返回多少条poiitem
        mQuery.setPageNum(1);//设置查询页码
        mPoiSearch = new PoiSearch(this, mQuery);
        mPoiSearch.searchPOIAsyn();
        mPoiSearch.setOnPoiSearchListener(new PoiSearch.OnPoiSearchListener() {
            @Override
            public void onPoiSearched(PoiResult poiResult, int i) {
                if (isInput) {
                    isInput=false;
                    if (poiResult!=null&&poiResult.getPois()!=null&&poiResult.getPois().size() > 0) {
                        mPoiBeanList.clear();
                        setMapCenter(poiResult.getPois().get(0).getLatLonPoint().getLatitude(),poiResult.getPois().get(0).getLatLonPoint().getLongitude());

                    }
                } else {
                    mPoiBeanList.clear();
                    for (PoiItem poiItem : poiResult.getPois()) {
                        PoiBean poiBean = new PoiBean();
                        poiBean.setLon(poiItem.getLatLonPoint().getLongitude());
                        poiBean.setLat(poiItem.getLatLonPoint().getLatitude());
                        poiBean.setAddress(poiItem.getSnippet());
                        poiBean.setName(poiItem.getTitle());
                        poiBean.setIsupdateColor(true);
                        mPoiBeanList.add(poiBean);
                    }
                    mAdapter.notifyDataSetChanged();
                }


            }

            @Override
            public void onPoiItemSearched(PoiItem poiItem, int i) {

                // poiItem.getTitle();
            }
        });

    }

    public void getAddress(LatLonPoint latLonPoint) {
        // 第二参数表示范围200米，第三个参数表示是火系坐标系还是GPS原生坐标系
        RegeocodeQuery query = new RegeocodeQuery(latLonPoint, 2000, GeocodeSearch.AMAP);
        mGeocodeSearch.getFromLocationAsyn(query);// 设置同步逆地理编码请求
    }

    private void initMapView() {

        aMap = mMapView.getMap();
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);
        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {

                getAddress(new LatLonPoint(cameraPosition.target.latitude, cameraPosition.target.longitude));//获取移动到的地点名称

            }
        });
        mUiSettings = aMap.getUiSettings();//实例化UiSettings类对象
        mUiSettings.setZoomControlsEnabled(false);
        mUiSettings.setScaleControlsEnabled(false);
    }

    private void locate() {

        mlocationClient = new AMapLocationClient(this);
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置一次定位
        mLocationOption.setOnceLocation(true);
        mlocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                if (aMapLocation != null) {
                    if (aMapLocation.getErrorCode() == 0) {
                        cityCode = aMapLocation.getCityCode();//城市编码
                        setMapCenter(aMapLocation.getLatitude(), aMapLocation.getLongitude());//设置中心点，会触发地图拖动监听
                    }
                }

            }
        });
        mlocationClient.setLocationOption(mLocationOption);
        mlocationClient.startLocation();
    }

    private void setMapCenter(double lat, double lng) {
        LatLng marker1 = new LatLng(lat, lng);
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(marker1));
        aMap.moveCamera(CameraUpdateFactory.zoomTo(18));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
        mMapView.onDestroy();
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
