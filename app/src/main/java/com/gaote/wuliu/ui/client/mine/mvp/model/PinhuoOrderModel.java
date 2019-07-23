package com.gaote.wuliu.ui.client.mine.mvp.model;

import android.text.TextUtils;

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

public class PinhuoOrderModel {
    public interface RequestResult {
        void getData(List<PinhuoOrder> pinhuoOrders);
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
                Result<Data> result = GsonUtil.fromJsonObject(response, Data.class);
                int code = result.getResultCode();
                if (code == 0) {
                    Data data = result.getData();
                    requestResult.getData(data.getBeanList());
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
