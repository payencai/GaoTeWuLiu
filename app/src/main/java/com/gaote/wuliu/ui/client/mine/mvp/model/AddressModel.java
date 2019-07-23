package com.gaote.wuliu.ui.client.mine.mvp.model;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gaote.wuliu.MyApp;
import com.gaote.wuliu.bean.Result;
import com.gaote.wuliu.net.Api;
import com.gaote.wuliu.net.NetUtils;
import com.gaote.wuliu.net.OnMessageReceived;
import com.gaote.wuliu.tools.GsonUtil;
import com.lzy.okgo.model.HttpParams;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.HTTP;

public class AddressModel {
    public interface AddressResult {
        void onResult(List<Address> addresses);
        void onDelete();
        void onDefault();
        void onAdd();
        void onUpdate();
    }
    public void addAddress(AddressRequest request,AddressResult addressResult){
        HttpParams httpParams=new HttpParams();
        httpParams.put("name",request.getName());
        httpParams.put("defaultAddress",request.getDefaultAddress());
        httpParams.put("telephone",request.getTelephone());
        httpParams.put("address",request.getAddress());
        httpParams.put("lat1",request.getLat1());
        httpParams.put("lng1",request.getLng1());
        httpParams.put("province",request.getProvince());
        httpParams.put("area",request.getArea());
        httpParams.put("city",request.getCity());
        NetUtils.getInstance().post(Api.BASE_URL + Api.Address.URL_ADD_NEW_ADDRESS, MyApp.token, httpParams,new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                LogUtils.e(response);
                Result result = GsonUtil.fromJsonObject(response,Result.class);
                if (result.getResultCode() == 0)
                    addressResult.onAdd();
                else {
                    ToastUtils.showShort(result.getMessage());
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }
    public void updateAddress(AddressRequest request,AddressResult addressResult){
        HttpParams httpParams=new HttpParams();
        httpParams.put("id",request.getId());
        httpParams.put("name",request.getName());
        httpParams.put("defaultAddress",request.getDefaultAddress());
        httpParams.put("telephone",request.getTelephone());
        httpParams.put("address",request.getAddress());
        httpParams.put("lat1",request.getLat1());
        httpParams.put("lng1",request.getLng1());
        httpParams.put("province",request.getProvince());
        httpParams.put("area",request.getArea());
        httpParams.put("city",request.getCity());
        NetUtils.getInstance().post(Api.BASE_URL + Api.Address.URL_UPDATE_ADDRESS, MyApp.token, httpParams,new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                LogUtils.e(response);
                Result result = GsonUtil.fromJsonObject(response,Result.class);
                if (result.getResultCode() == 0)
                    addressResult.onUpdate();
                else {
                    ToastUtils.showShort(result.getMessage());
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }
    public void setDefaultAddress(String id,AddressResult addressResult){
        HttpParams httpParams=new HttpParams();
        httpParams.put("id",id);
        httpParams.put("defaultAddress","1");
        NetUtils.getInstance().post(Api.BASE_URL + Api.Address.URL_UPDATE_ADDRESS, MyApp.token, httpParams,new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                LogUtils.e(response);
                Result result = GsonUtil.fromJsonObject(response,Result.class);
                if (result.getResultCode() == 0)
                    addressResult.onDefault();
                else {
                    ToastUtils.showShort(result.getMessage());
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }
    public void delAddress(String id,AddressResult addressResult){
        HttpParams httpParams=new HttpParams();
        httpParams.put("id",id);
        NetUtils.getInstance().post(Api.BASE_URL + Api.Address.URL_DELETE_ADDRESS, MyApp.token, httpParams,new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                LogUtils.e(response);
                Result result = GsonUtil.fromJsonObject(response,Result.class);
                if (result.getResultCode() == 0)
                    addressResult.onDelete();
                else {
                    ToastUtils.showShort(result.getMessage());
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }
    public void getAddress(AddressResult addressResult) {
        NetUtils.getInstance().post(Api.BASE_URL + Api.Address.URL_GET_ALL_ADDRESS, "", MyApp.token, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                LogUtils.e(response);
                Result<List<Address>> result = GsonUtil.fromJsonArray(response, Address.class);
                if (result.getResultCode() == 0)
                    addressResult.onResult(result.getData());
                else {
                    ToastUtils.showShort(result.getMessage());
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }
}
