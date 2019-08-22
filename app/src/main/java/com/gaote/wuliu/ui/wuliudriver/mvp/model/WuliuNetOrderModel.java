package com.gaote.wuliu.ui.wuliudriver.mvp.model;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gaote.wuliu.MyApp;
import com.gaote.wuliu.bean.Result;
import com.gaote.wuliu.net.Api;
import com.gaote.wuliu.net.NetUtils;
import com.gaote.wuliu.net.OnMessageReceived;
import com.gaote.wuliu.tools.GsonUtil;
import com.lzy.okgo.model.HttpParams;

import java.util.List;

public class WuliuNetOrderModel {
    public static final boolean isTest=false;
    public interface RequestResult {
        void onSuccess(List<WuliuNetOrder.ListBean> wuliuOrders);
        void onAfterConfirm();
    }
    //获取待确认订单
    public void getNetOrder(RequestResult requestResult,  String id ,int page) {
        String url = Api.BASE_URL + Api.WuliuDriver.URL_GET_NET_DETAIL_LIST;
        HttpParams httpParams = new HttpParams();
        httpParams.put("networkId", id);
        httpParams.put("pageNum", page + "");
        NetUtils.getInstance().get(MyApp.token,url, httpParams, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                Result<WuliuNetOrder> result=GsonUtil.fromJsonObject(response,WuliuNetOrder.class);
                if (result.getResultCode() == 0) {
                    requestResult.onSuccess(result.getData().getList());
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
    //揽活
    public void wuliuConfirmOrder(String ids, RequestResult requestResult) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("ids", ids);
        NetUtils.getInstance().post(Api.BASE_URL + Api.WuliuDriver.URL_TAKE_ORDER, MyApp.token, httpParams, new OnMessageReceived() {
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

}
