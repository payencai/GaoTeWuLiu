package com.gaote.wuliu.ui.client.mine.mvp.model;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gaote.wuliu.MyApp;
import com.gaote.wuliu.bean.Result;
import com.gaote.wuliu.net.Api;
import com.gaote.wuliu.net.NetUtils;
import com.gaote.wuliu.net.OnMessageReceived;
import com.gaote.wuliu.tools.GsonUtil;
import com.google.gson.Gson;
import com.lzy.okgo.model.HttpParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WuliuOrderModel {
    public interface RequestResult {
        void onSuccess(List<WuliuOrder> wuliuOrders);
        void onConfirm();
        void onCancel();
        void onDriver(int type);
    }

    public void getWuliuOrder(RequestResult requestResult, int status, int page) {
        String url = Api.BASE_URL + Api.Order.URL_DEMAND_GET_LOG_ORDER;
        HttpParams httpParams = new HttpParams();
        if (status > 0)
            httpParams.put("status", status + "");
        httpParams.put("pageNum", page + "");
        NetUtils.getInstance().get(MyApp.token, url, httpParams, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {
                        JSONObject object=jsonObject.getJSONObject("data");
                        JSONArray  data=object.getJSONArray("list");
                        List<WuliuOrder> wuliuOrders=new ArrayList<>();
                        for (int i = 0; i < data.length(); i++) {
                            WuliuOrder wuliuOrder=new Gson().fromJson(data.getJSONObject(i).toString(),WuliuOrder.class);
                            wuliuOrders.add(wuliuOrder);
                        }
                        requestResult.onSuccess(wuliuOrders);

                    } else {
                        String msg=jsonObject.getString("message");
                        ToastUtils.showShort(msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                LogUtils.e(response);

            }

            @Override
            public void onError(String error) {

            }
        });
    }
    public void userCancelOrder(String id, RequestResult requestResult){
        HttpParams httpParams = new HttpParams();
        httpParams.put("pdriverOrderId", id);
        NetUtils.getInstance().post(Api.BASE_URL+Api.Order.URL_USER_ORDER_CANCEL, MyApp.token, httpParams, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                LogUtils.e(response);
                Result result = GsonUtil.fromJsonObject(response, Result.class);
                int code = result.getResultCode();
                if (code == 0) {
                    requestResult.onCancel();
                } else {
                    ToastUtils.showShort(result.getMessage());
                }

            }

            @Override
            public void onError(String error) {

            }
        });
    }

    public void userConfirmOrder(String id, RequestResult requestResult) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("pdriverOrderId", id);
        NetUtils.getInstance().post(Api.BASE_URL + Api.Order.URL_FINISH_ORDER_BY_USER, MyApp.token, httpParams, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                LogUtils.e(response);
                Result result = GsonUtil.fromJsonObject(response, Result.class);
                int code = result.getResultCode();
                if (code == 0) {
                    requestResult.onConfirm();
                } else {
                    ToastUtils.showShort(result.getMessage());
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }
    public void driverHandleOrder(String id, int status, RequestResult requestResult){
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
                    requestResult.onDriver(1);
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
