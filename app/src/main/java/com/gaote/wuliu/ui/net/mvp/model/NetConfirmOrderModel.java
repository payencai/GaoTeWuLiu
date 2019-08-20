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

public class NetConfirmOrderModel {
    public static final boolean isTest=false;
    public interface RequestResult {
        void onSuccess(List<NetConfirmOrder.ListBean> wuliuOrders);
        void onConfirm();
    }
    //获取待确认订单
    public void getNetConfirmOrder(RequestResult requestResult,  int page) {
        String url = Api.BASE_URL + Api.Net.URL_GET_NOT_CONFIRMED_ORDER;
        HttpParams httpParams = new HttpParams();
        httpParams.put("pageNum", page + "");
        NetUtils.getInstance().get(MyApp.token, url, httpParams, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                Result<NetConfirmOrder> result=GsonUtil.fromJsonObject(response,NetConfirmOrder.class);
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

    //确认订单
    public void confirmNetOrder( RequestResult requestResult,String id,String time) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("driverId", id);
        httpParams.put("takeorderTime", time);
        NetUtils.getInstance().post(Api.BASE_URL + Api.Net.URL_CONFIRM_ORDER, MyApp.token, httpParams, new OnMessageReceived() {
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
    public List<NetConfirmOrder.ListBean> getTestData(){
        List<NetConfirmOrder.ListBean> listBeans=new ArrayList<>();
        for (int i = 0; i <10 ; i++) {
            NetConfirmOrder.ListBean listBean=new NetConfirmOrder.ListBean();
            listBean.setCount(3);
            listBean.setDriverId("12");
            listBean.setPortraitUrl("http://imgnews.mumayi.com/file/2019/08/05/387453e70bb0e70fd4f71dc50ac3a05d.png");
            listBean.setTakeorderTime(new Date().toString());
            listBean.setDriverName("李白");
            listBeans.add(listBean);
        }
        return listBeans;
    }
}
