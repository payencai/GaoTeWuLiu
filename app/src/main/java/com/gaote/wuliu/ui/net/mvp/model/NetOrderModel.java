package com.gaote.wuliu.ui.net.mvp.model;

import com.blankj.utilcode.util.ToastUtils;
import com.gaote.wuliu.MyApp;
import com.gaote.wuliu.bean.Result;
import com.gaote.wuliu.net.Api;
import com.gaote.wuliu.net.NetUtils;
import com.gaote.wuliu.net.OnMessageReceived;
import com.gaote.wuliu.tools.GsonUtil;
import com.lzy.okgo.model.HttpParams;

import java.util.ArrayList;
import java.util.List;

public class NetOrderModel {
    public interface RequestResult {
        void onSuccess(List<NetOrder.ExpressConfirmedItem> wuliuOrders);
    }
    //获取已确认订单
    public void getNetOrder(RequestResult requestResult,  int page) {

        String url = Api.BASE_URL + Api.Net.URL_GET_CONFIRMED_ORDER;
        HttpParams httpParams = new HttpParams();
        httpParams.put("pageNum", page + "");
        NetUtils.getInstance().get(MyApp.token, url, httpParams, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                Result<NetOrder> result= GsonUtil.fromJsonObject(response,NetOrder.class);
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

}
