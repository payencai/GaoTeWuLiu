package com.gaote.wuliu.ui.client.mine.mvp.model;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gaote.wuliu.MyApp;
import com.gaote.wuliu.bean.Result;
import com.gaote.wuliu.bean.ResultPage;
import com.gaote.wuliu.net.Api;
import com.gaote.wuliu.net.NetUtils;
import com.gaote.wuliu.net.OnMessageReceived;
import com.gaote.wuliu.tools.GsonUtil;
import com.gaote.wuliu.ui.client.mine.bean.ClientPinhuoOrder;
import com.gaote.wuliu.ui.client.mine.bean.Coupon;
import com.google.gson.Gson;
import com.lzy.okgo.model.HttpParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PinhuoOrderModel {
    public interface RequestResult {
        void getData(List<ClientPinhuoOrder> pinhuoOrders);
        void onUserCancel();
        void onUserConfirm();
        void onDriverHandle();
    }

    public void getPinhuoOrder(RequestResult requestResult, int page, String url, int type) {
        HttpParams httpParams = new HttpParams();
        if (type>0)
            httpParams.put("type", type+"");
        httpParams.put("page", page);
        NetUtils.getInstance().post(url, MyApp.token, httpParams, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {

                LogUtils.e(response);
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    int code=jsonObject.getInt("resultCode");
                    if(code==0){
                        jsonObject=jsonObject.getJSONObject("data");
                        List<ClientPinhuoOrder> clientPinhuoOrders=new ArrayList<>();
                        JSONArray jsonArray=jsonObject.getJSONArray("beanList");
                        for (int i = 0; i <jsonArray.length() ; i++) {
                            JSONObject item=jsonArray.getJSONObject(i);
                            ClientPinhuoOrder clientPinhuoOrder=new Gson().fromJson(item.toString(),ClientPinhuoOrder.class);
                            clientPinhuoOrders.add(clientPinhuoOrder);
                        }
                        requestResult.getData(clientPinhuoOrders);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }

            @Override
            public void onError(String error) {

            }
        });
    }

    public void userCancelOrder(String id,RequestResult requestResult){
        HttpParams httpParams = new HttpParams();
        httpParams.put("pdriverOrderId", id);
        NetUtils.getInstance().post(Api.BASE_URL+Api.Order.URL_USER_ORDER_CANCEL, MyApp.token, httpParams, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                LogUtils.e(response);
                Result result = GsonUtil.fromJsonObject(response, Result.class);
                int code = result.getResultCode();
                if (code == 0) {
                    requestResult.onUserCancel();
                } else {
                    ToastUtils.showShort(result.getMessage());
                }

            }

            @Override
            public void onError(String error) {

            }
        });
    }
    public void userConfirmOrder(String id,RequestResult requestResult){
        HttpParams httpParams = new HttpParams();
        httpParams.put("pdriverOrderId", id);
        NetUtils.getInstance().post(Api.BASE_URL+Api.Order.URL_FINISH_ORDER_BY_USER, MyApp.token, httpParams, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                LogUtils.e(response);
                Result result = GsonUtil.fromJsonObject(response, Result.class);
                int code = result.getResultCode();
                if (code == 0) {
                    requestResult.onUserConfirm();
                } else {
                    ToastUtils.showShort(result.getMessage());
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }
    public void driverHandleOrder(String id,int status,RequestResult requestResult){
        HttpParams httpParams = new HttpParams();
        httpParams.put("pdriverOrderId", id);
        httpParams.put("status",status+"");
        NetUtils.getInstance().post(Api.BASE_URL+Api.Order.URL_DRIVER_UPDATE_ORDER_STATUS, MyApp.token, httpParams, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                LogUtils.e(response);
                Result result = GsonUtil.fromJsonObject(response, Result.class);
                int code = result.getResultCode();
                if (code == 0) {
                    requestResult.onDriverHandle();
                } else {
                    ToastUtils.showShort(result.getMessage());
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }
}
