package com.gaote.wuliu.tools;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.util.Log;
import android.widget.Toast;

import com.blankj.utilcode.util.LogUtils;

import java.io.IOException;
import java.util.List;

public class GetAddressUtil {
    Context context;

    public GetAddressUtil(Context context) {
        this.context = context;
    }

    public String getArea(double lnt , double lat){
        Geocoder geocoder = new Geocoder(context);
        StringBuilder stringBuilder = new StringBuilder();
        try {
            List<Address> addresses = geocoder.getFromLocation(lat , lnt, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                stringBuilder.append(address.getSubLocality());//经度
//                stringBuilder.append(address.getCountryName()).append("_");//国家
//                stringBuilder.append(address.getFeatureName()).append("_");//周边地址
//                stringBuilder.append(address.getLocality()).append("_");//市
//                stringBuilder.append(address.getPostalCode()).append("_");
//                stringBuilder.append(address.getCountryCode()).append("_");//国家编码
//                stringBuilder.append(address.getAdminArea()).append("_");//省份
//                stringBuilder.append(address.getSubAdminArea()).append("_");
//                stringBuilder.append(address.getThoroughfare()).append("_");//道路
//                stringBuilder.append(address.getSubLocality()).append("_");//香洲区
//                stringBuilder.append(address.getLatitude()).append("_");//经度
//                stringBuilder.append(address.getSubLocality()).append("_");//经度
//                stringBuilder.append(address.getLongitude());//维度

            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            Toast.makeText(context, "报错", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
        return stringBuilder.toString();

    }
}

