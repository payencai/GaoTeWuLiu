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

public class NetDoorOrderModel {
    public static final boolean isTest=false;
    public interface RequestResult {
        void onSuccess(List<NetDoorOrder> wuliuOrders);
    }
    //获取待确认订单
    public void getDoorOrder(RequestResult requestResult,  int page) {
        requestResult.onSuccess(getTestData());

//        String url = Api.BASE_URL + Api.Net.URL_GET_NOT_CONFIRMED_ORDER;
//        HttpParams httpParams = new HttpParams();
//        httpParams.put("pageNum", page + "");
//        NetUtils.getInstance().get(MyApp.token, url, httpParams, new OnMessageReceived() {
//            @Override
//            public void onSuccess(String response) {
//                Result<List<NetDoorOrder>> result=GsonUtil.fromJsonArray(response,NetDoorOrder.class);
//                if(isTest){
//                    requestResult.onSuccess(getTestData());
//                    return;
//                }
//                if (result.getResultCode() == 0) {
//                    requestResult.onSuccess(result.getData());
//                } else {
//                    String msg=result.getMessage();
//                    ToastUtils.showShort(msg);
//                }
//            }
//
//            @Override
//            public void onError(String error) {
//
//            }
//        });
    }


    public List<NetDoorOrder> getTestData(){
        List<NetDoorOrder> listBeans=new ArrayList<>();
        for (int i = 0; i <10 ; i++) {
            NetDoorOrder listBean=new NetDoorOrder();
            listBeans.add(listBean);
        }
        return listBeans;
    }
}
