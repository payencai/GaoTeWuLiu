package com.gaote.wuliu.ui.wuliudriver.mvp.model;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gaote.wuliu.MyApp;
import com.gaote.wuliu.bean.Result;
import com.gaote.wuliu.net.Api;
import com.gaote.wuliu.net.NetUtils;
import com.gaote.wuliu.net.OnMessageReceived;
import com.gaote.wuliu.tools.GsonUtil;
import com.gaote.wuliu.ui.pinhuodriver.mvp.mdoel.PinhuoOrder;
import com.gaote.wuliu.ui.wuliudriver.mvp.model.WuliuNet;
import com.lzy.okgo.model.HttpParams;

import java.util.List;

public class WuliuNetModel {
    public static final boolean isTest=false;
    public interface RequestResult {
        void onSuccess(List<WuliuNet> wuliuOrders);

    }
    //获取待确认订单
    public void getNetWorks(RequestResult requestResult,  String  lat,String lng) {
        String url = Api.BASE_URL + Api.WuliuDriver.URL_GET_NET_ORDER_LIST;
        HttpParams httpParams = new HttpParams();
        httpParams.put("latitude", lat + "");
        httpParams.put("longitude", lng + "");
        NetUtils.getInstance().get(MyApp.token,url, httpParams, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                Result<List<WuliuNet>> result=GsonUtil.fromJsonArray(response,WuliuNet.class);
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


}
