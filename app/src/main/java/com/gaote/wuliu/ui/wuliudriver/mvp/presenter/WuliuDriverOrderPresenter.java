package com.gaote.wuliu.ui.wuliudriver.mvp.presenter;

import com.gaote.wuliu.ui.wuliudriver.mvp.model.WuliuDriverOrder;
import com.gaote.wuliu.ui.wuliudriver.mvp.model.WuliuDriverOrderModel;
import com.gaote.wuliu.ui.wuliudriver.mvp.model.WuliuNet;
import com.gaote.wuliu.ui.wuliudriver.mvp.model.WuliuNetModel;
import com.gaote.wuliu.ui.wuliudriver.mvp.view.WuliuDriverOrderView;
import com.gaote.wuliu.ui.wuliudriver.mvp.view.WuliuOrderView;

import java.util.List;

public class WuliuDriverOrderPresenter implements WuliuDriverOrderModel.RequestResult {
    WuliuDriverOrderView netConfirmOrderView;
    WuliuDriverOrderModel netConfirmOrderModel;

    public WuliuDriverOrderPresenter(WuliuDriverOrderView netConfirmOrderView, WuliuDriverOrderModel netConfirmOrderModel) {
        this.netConfirmOrderView = netConfirmOrderView;
        this.netConfirmOrderModel = netConfirmOrderModel;
    }
    public void getMyOrder(int  page,String status){
        netConfirmOrderView.showLoading();
        netConfirmOrderModel.getMyOrder(this, page,status);
    }
    public void onConfirm(String  id,String time){
        netConfirmOrderModel.driverConfirmOrder(id,time,this);
    }
    public void onSend(String  id,String time){
        netConfirmOrderModel.driverSendOrder(id,time,this);
    }
    @Override
    public void onSuccess(List<WuliuDriverOrder> wuliuOrders) {
        netConfirmOrderView.dissLoading();
        netConfirmOrderView.getMyOrder(wuliuOrders);
    }

    @Override
    public void onAfterConfirm() {
        netConfirmOrderView.onAfterConfirm();
    }

    @Override
    public void onAfterSend() {
        netConfirmOrderView.onAfterSend();
    }
}
