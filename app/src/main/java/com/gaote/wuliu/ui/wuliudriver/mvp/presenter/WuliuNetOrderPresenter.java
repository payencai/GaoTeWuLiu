package com.gaote.wuliu.ui.wuliudriver.mvp.presenter;

import com.gaote.wuliu.ui.wuliudriver.mvp.model.WuliuNet;
import com.gaote.wuliu.ui.wuliudriver.mvp.model.WuliuNetModel;
import com.gaote.wuliu.ui.wuliudriver.mvp.model.WuliuNetOrder;
import com.gaote.wuliu.ui.wuliudriver.mvp.model.WuliuNetOrderModel;
import com.gaote.wuliu.ui.wuliudriver.mvp.view.WuliuNetOrderView;
import com.gaote.wuliu.ui.wuliudriver.mvp.view.WuliuOrderView;

import java.util.List;

public class WuliuNetOrderPresenter implements WuliuNetOrderModel.RequestResult {
    WuliuNetOrderView netConfirmOrderView;
    WuliuNetOrderModel netConfirmOrderModel;

    public WuliuNetOrderPresenter(WuliuNetOrderView netConfirmOrderView, WuliuNetOrderModel netConfirmOrderModel) {
        this.netConfirmOrderView = netConfirmOrderView;
        this.netConfirmOrderModel = netConfirmOrderModel;
    }
    public void getNetWorks(String id,int  page){
        netConfirmOrderView.showLoading();
        netConfirmOrderModel.getNetOrder(this, id,page);
    }

    public void onConfirm(String ids){
        netConfirmOrderView.showLoading();
        netConfirmOrderModel.wuliuConfirmOrder(ids,this);
    }
    @Override
    public void onSuccess(List<WuliuNetOrder.ListBean> wuliuOrders) {
        netConfirmOrderView.dissLoading();
        netConfirmOrderView.onSuccess(wuliuOrders);
    }

    @Override
    public void onAfterConfirm() {
        netConfirmOrderView.onAfterConfirm();
    }
}
