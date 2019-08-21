package com.gaote.wuliu.ui.pinhuodriver.mvp.mdoel;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gaote.wuliu.MyApp;
import com.gaote.wuliu.bean.Result;
import com.gaote.wuliu.net.Api;
import com.gaote.wuliu.net.NetUtils;
import com.gaote.wuliu.net.OnMessageReceived;
import com.gaote.wuliu.tools.GsonUtil;
import com.gaote.wuliu.ui.net.mvp.model.NetConfirmOrder;
import com.lzy.okgo.model.HttpParams;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PinhuoOrderModel {
    public static final boolean isTest=false;
    public interface RequestResult {
        void onSuccess(List<PinhuoOrder.BeanListBean> wuliuOrders);
        void onConfirm();
    }
    //获取待确认订单
    public void getOrder(RequestResult requestResult,  int page) {
        String url = Api.BASE_URL + Api.PinhuoDriver.URL_GET_AVAILABLE_ORDER;
        HttpParams httpParams = new HttpParams();
        httpParams.put("page", page + "");
        NetUtils.getInstance().post(url,MyApp.token, httpParams, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                Result<PinhuoOrder> result=GsonUtil.fromJsonObject(response,PinhuoOrder.class);
                if (result.getResultCode() == 0) {
                    requestResult.onSuccess(result.getData().getBeanList());
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

    //确认订单
    public void confirmNetOrder( RequestResult requestResult,String id) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("pdriverOrderId", id);
        httpParams.put("status", "1");
        NetUtils.getInstance().post(Api.BASE_URL + Api.PinhuoDriver.URL_DRIVER_UPDATE_ORDER_STATUS, MyApp.token, httpParams, new OnMessageReceived() {
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

}
