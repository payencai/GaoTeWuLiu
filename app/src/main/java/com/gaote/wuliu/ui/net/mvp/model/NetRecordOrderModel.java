package com.gaote.wuliu.ui.net.mvp.model;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gaote.wuliu.MyApp;
import com.gaote.wuliu.bean.Result;
import com.gaote.wuliu.net.Api;
import com.gaote.wuliu.net.NetUtils;
import com.gaote.wuliu.net.OnMessageReceived;
import com.gaote.wuliu.tools.GsonUtil;
import com.lzy.okgo.model.HttpParams;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class NetRecordOrderModel {
    public static final boolean isTest=false;
    public interface RequestResult {
        void onSuccess(List<NetRecordOrder.ListBean> wuliuOrders);
        void onConfirm();
    }
    //获取待待收录订单
    public void getNetRecordOrder(RequestResult requestResult,  int page) {
        String url = Api.BASE_URL + Api.Net.URL_GET_UNARRIVED_ORDERS;
        HttpParams httpParams = new HttpParams();
        httpParams.put("pageNum", page + "");
        NetUtils.getInstance().get(MyApp.token, url, httpParams, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                Result<NetRecordOrder> result=GsonUtil.fromJsonObject(response,NetRecordOrder.class);
                if(isTest){
                    requestResult.onSuccess(getTestData());
                    return;
                }
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

    //收录订单
    public void recordNetOrder( RequestResult requestResult,String id) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("orderId", id);
        NetUtils.getInstance().post(Api.BASE_URL + Api.Net.URL_TAKE_ON_ORDER, MyApp.token, httpParams, new OnMessageReceived() {
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
    public List<NetRecordOrder.ListBean> getTestData(){
        List<NetRecordOrder.ListBean> listBeans=new ArrayList<>();
        for (int i = 0; i <10 ; i++) {
            NetRecordOrder.ListBean listBean=new NetRecordOrder.ListBean();
            listBeans.add(listBean);
        }
        return listBeans;
    }
}
