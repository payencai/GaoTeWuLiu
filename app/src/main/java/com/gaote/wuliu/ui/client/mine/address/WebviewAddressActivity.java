package com.gaote.wuliu.ui.client.mine.address;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.gaote.wuliu.R;
import com.gaote.wuliu.net.NetUtils;
import com.gaote.wuliu.net.OnMessageReceived;
import com.gaote.wuliu.ui.client.mine.mvp.model.AddressBean;
import com.gaote.wuliu.x5.MyX5WebView;
import com.tencent.smtt.export.external.interfaces.GeolocationPermissionsCallback;
import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebviewAddressActivity extends AppCompatActivity {
    @BindView(R.id.myweb)
    MyX5WebView myX5WebView;
    AddressBean addressBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_address);
        ButterKnife.bind(this);
        initWebView();

    }
    private void initWebView(){
        myX5WebView.initWebViewSettings();
        myX5WebView.setShowProgress(true);
        myX5WebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if (url.contains("addr")) {

                    String strUTF8 = null;
                    try {
                        strUTF8 = URLDecoder.decode(url, "UTF-8");
                        Uri uri = Uri.parse(strUTF8);
                        String latng= uri.getQueryParameter("latng");
                        String [] latlng=latng.split(",");
                        String lat=latlng[0];
                        String lng=latlng[1];
                        String addr= uri.getQueryParameter("addr");
                        String name= uri.getQueryParameter("name");
                        String city= uri.getQueryParameter("city");
                        addressBean=new AddressBean();
                        AddressBean.LatlngBean latlngBean=new AddressBean.LatlngBean();
                        latlngBean.setLat(Double.parseDouble(lat));
                        latlngBean.setLng(Double.parseDouble(lng));
                        addressBean.setLatlng(latlngBean);
                        addressBean.setPoiaddress(addr);
                        addressBean.setCityname(city);
                        addressBean.setPoiname(name);
                        getCity(addressBean.getLatlng().getLat(),addressBean.getLatlng().getLng());
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                    Log.e("address", strUTF8);
                    return true;
                } else {
                    return false;
                }
            }

            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }
        });
        myX5WebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onGeolocationPermissionsShowPrompt(String s, GeolocationPermissionsCallback geolocationPermissionsCallback) {
                geolocationPermissionsCallback.invoke(s, true, true);
            }
        });
        myX5WebView.loadUrl("https://apis.map.qq.com/tools/locpicker?search=1&type=0&backurl=http://3gimg.qq.com/lightmap/components/locationPicker2/back.html&key=OB4BZ-D4W3U-B7VVO-4PJWW-6TKDJ-WPB77&referer=myapp");
    }
    private void getCity(double latitude,double longitude){
        String url="https://restapi.amap.com/v3/geocode/regeo?output=json&location="+longitude+","+latitude+"&key=bbe8f89a8956d407bc8dec2b26a18ab9&extensions=all&batch=false";
        //String url="https://restapi.amap.com/v3/geocode/regeo?output=json&location=113.39679,23.04551&key=b3e064ba536662c892cd0c81169b5bfb&extensions=all&batch=false";
        NetUtils.getInstance().get("", url, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    JSONObject regeocode=jsonObject.getJSONObject("regeocode");
                    JSONObject addressComponent=regeocode.getJSONObject("addressComponent");
                    String province=addressComponent.getString("province");
                    String district=addressComponent.getString("district");
                    addressBean.setDistrict(district);
                    addressBean.setProvince(province);
                    Log.e("gaode",response);
                    Intent intent = new Intent();
                    intent.putExtra("address", addressBean);
                    setResult(1, intent);
                    finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }
}
