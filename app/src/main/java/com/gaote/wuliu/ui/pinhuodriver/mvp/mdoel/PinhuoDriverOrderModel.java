package com.gaote.wuliu.ui.pinhuodriver.mvp.mdoel;

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
import com.gaote.wuliu.ui.client.mine.mvp.model.PinhuoData;
import com.gaote.wuliu.ui.client.mine.mvp.model.PinhuoOrder;
import com.google.gson.Gson;
import com.lzy.okgo.model.HttpParams;

import java.util.List;

public class PinhuoDriverOrderModel {
    public interface RequestResult {
        void getData(List<ClientPinhuoOrder> pinhuoOrders);
        void onOrderHandle(int type);
    }
    //获取拼货司机订单
    public void getPinhuoDriverOrder(RequestResult requestResult, int page, int type) {
        HttpParams httpParams = new HttpParams();
        if (type>0)
            httpParams.put("type", type+"");
        httpParams.put("page", page);
        NetUtils.getInstance().post( Api.BASE_URL + Api.Order.URL_GET_ALL_ORDER, MyApp.token, httpParams, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                LogUtils.e(response);
                ResultPage<ClientPinhuoOrder> result = new Gson().fromJson(response,ResultPage.class);
                int code = result.getResultCode();
                if (code == 0) {
                    requestResult.getData(result.getData().getBeanList());
                } else {
                    ToastUtils.showShort(result.getMessage());
                }

            }

            @Override
            public void onError(String error) {

            }
        });
    }
    //处理拼货司机订单，取消，确认，送达
    public void onHandleOrder(String id,int status,RequestResult requestResult){
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
                    requestResult.onOrderHandle(status);
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
