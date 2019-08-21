package com.gaote.wuliu.ui.wuliudriver.mvp.presenter;

import com.gaote.wuliu.ui.pinhuodriver.mvp.mdoel.PinhuoOrder;
import com.gaote.wuliu.ui.pinhuodriver.mvp.mdoel.PinhuoOrderModel;
import com.gaote.wuliu.ui.pinhuodriver.mvp.view.PinhuoOrderView;
import com.gaote.wuliu.ui.wuliudriver.mvp.model.WuliuNet;
import com.gaote.wuliu.ui.wuliudriver.mvp.model.WuliuNetModel;
import com.gaote.wuliu.ui.wuliudriver.mvp.view.WuliuOrderView;

import java.util.List;

public class WuliuNetPresenter implements WuliuNetModel.RequestResult {
    WuliuOrderView netConfirmOrderView;
    WuliuNetModel netConfirmOrderModel;

    public WuliuNetPresenter(WuliuOrderView netConfirmOrderView, WuliuNetModel netConfirmOrderModel) {
        this.netConfirmOrderView = netConfirmOrderView;
        this.netConfirmOrderModel = netConfirmOrderModel;
    }
    public void getNetWorks(String lat,String lng){
        netConfirmOrderView.showLoading();
        netConfirmOrderModel.getNetWorks(this, lat,lng);
    }


    @Override
    public void onSuccess(List<WuliuNet> wuliuOrders) {
        netConfirmOrderView.dissLoading();
        netConfirmOrderView.onSuccess(wuliuOrders);
    }
}
