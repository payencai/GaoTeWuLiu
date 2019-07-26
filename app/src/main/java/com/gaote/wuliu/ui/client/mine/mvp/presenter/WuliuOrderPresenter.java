package com.gaote.wuliu.ui.client.mine.mvp.presenter;

import com.gaote.wuliu.ui.client.mine.mvp.model.WuliuOrder;
import com.gaote.wuliu.ui.client.mine.mvp.model.WuliuOrderModel;
import com.gaote.wuliu.ui.client.mine.mvp.view.WuliuOrderView;

import java.util.List;

public class WuliuOrderPresenter implements BasePresenter, WuliuOrderModel.RequestResult {
    WuliuOrderModel wuliuOrderModel;
    WuliuOrderView wuliuOrderView;
    public WuliuOrderPresenter(WuliuOrderModel wuliuOrderModel, WuliuOrderView wuliuOrderView) {
        this.wuliuOrderModel = wuliuOrderModel;
        this.wuliuOrderView = wuliuOrderView;
    }
    @Override
    public void userOrderConfirm(String id) {
        wuliuOrderView.showLoading();
        wuliuOrderModel.userConfirmOrder(id,this);
    }

    @Override
    public void getOrder(int page,int status) {
        wuliuOrderView.showLoading();
        wuliuOrderModel.getWuliuOrder(this,status,page);
    }

    @Override
    public void handleDriver(String id, int status) {
        wuliuOrderView.showLoading();
        wuliuOrderModel.driverHandleOrder(id,status,this);
    }

    @Override
    public void onUserCancel(String id) {
        wuliuOrderView.showLoading();
        wuliuOrderModel.userCancelOrder(id,this);
    }

    @Override
    public void onSuccess(List<WuliuOrder> wuliuOrders) {
        wuliuOrderView.dissLoading();
        wuliuOrderView.setData(wuliuOrders);
    }

    @Override
    public void onConfirm() {
        wuliuOrderView.dissLoading();
        wuliuOrderView.onUserConfirm();
    }

    @Override
    public void onCancel() {
        wuliuOrderView.dissLoading();
        wuliuOrderView.onUserCancel();
    }

    @Override
    public void onDriver(int type) {
        wuliuOrderView.dissLoading();
        wuliuOrderView.onDriver(type);
    }
}
