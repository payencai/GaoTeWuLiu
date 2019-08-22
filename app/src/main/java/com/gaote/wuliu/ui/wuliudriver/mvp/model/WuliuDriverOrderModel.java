package com.gaote.wuliu.ui.wuliudriver.mvp.model;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gaote.wuliu.MyApp;
import com.gaote.wuliu.bean.Result;
import com.gaote.wuliu.net.Api;
import com.gaote.wuliu.net.NetUtils;
import com.gaote.wuliu.net.OnMessageReceived;
import com.gaote.wuliu.tools.GsonUtil;
import com.gaote.wuliu.ui.client.mine.mvp.model.WuliuOrderModel;
import com.lzy.okgo.model.HttpParams;

import java.util.List;

public class WuliuDriverOrderModel {
    public static final boolean isTest=false;
    public interface RequestResult {
        void onSuccess(List<WuliuDriverOrder> wuliuOrders);
        void onAfterConfirm();
        void onAfterSend();
    }
    //获取订单
    public void getMyOrder(RequestResult requestResult,  int   page,String  status) {
        String url = Api.BASE_URL + Api.WuliuDriver.URL_GET_MY_ORDER;
        HttpParams httpParams = new HttpParams();
        httpParams.put("pageNum", page + "");
        if(!"1".equals(status))
          httpParams.put("status", status);
        NetUtils.getInstance().get(MyApp.token,url, httpParams, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                Result<List<WuliuDriverOrder>> result=GsonUtil.fromJsonArray(response,WuliuDriverOrder.class);
                if (result.getResultCode() == 0) {
                    requestResult.onSuccess(result.getData());
                } else {
                    String msg=result.getMessage();
                    ToastUtils.showShort(msg);
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }
    //确认收货
    public void driverConfirmOrder(String networkId,String confirmTime, RequestResult requestResult) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("networkId", networkId);
        httpParams.put("confirmTime", confirmTime);
        NetUtils.getInstance().post(Api.BASE_URL + Api.WuliuDriver.URL_CONFIRM_ORDER, MyApp.token, httpParams, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                LogUtils.e(response);
                Result result = GsonUtil.fromJsonObject(response, Result.class);
                int code = result.getResultCode();
                if (code == 0) {
                    requestResult.onAfterConfirm();
                } else {
                    ToastUtils.showShort(result.getMessage());
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }
    //确认送达
    public void driverSendOrder(String networkId,String updateTime, RequestResult requestResult) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("networkId", networkId);
        httpParams.put("pickTime", updateTime);
        NetUtils.getInstance().post(Api.BASE_URL + Api.WuliuDriver.URL_DILIVER, MyApp.token, httpParams, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                LogUtils.e(response);
                Result result = GsonUtil.fromJsonObject(response, Result.class);
                if (result.getResultCode() == 0) {
                    requestResult.onAfterSend();
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
