package com.gaote.wuliu.ui.client.mine.mvp.presenter;

import com.gaote.wuliu.ui.client.mine.bean.ClientPinhuoOrder;
import com.gaote.wuliu.ui.client.mine.mvp.model.PinhuoOrder;
import com.gaote.wuliu.ui.client.mine.mvp.model.PinhuoOrderModel;
import com.gaote.wuliu.ui.client.mine.mvp.view.PinhuoOrderView;

import java.util.List;

public class PinhuoOrderPresenter implements PinhuoOrderModel.RequestResult{

    PinhuoOrderModel pinhuoOrderModel;
    PinhuoOrderView pinhuoOrderView;

    public PinhuoOrderPresenter(PinhuoOrderModel pinhuoOrderModel, PinhuoOrderView pinhuoOrderView) {
        this.pinhuoOrderModel = pinhuoOrderModel;
        this.pinhuoOrderView = pinhuoOrderView;
    }
    public void getOrder(int page,String url,int type){
        pinhuoOrderView.showLoading();
        pinhuoOrderModel.getPinhuoOrder(this, page, url, type);
    }
    public void userOrderCancel(String id){
        pinhuoOrderModel.userCancelOrder(id,this);
    }
    public void userOrderConfirm(String id){
        pinhuoOrderModel.userConfirmOrder(id,this);
    }
    public void driverHandleOrder(String id,int status){
        pinhuoOrderModel.driverHandleOrder(id,status,this);
    }
    @Override
    public void getData(List<ClientPinhuoOrder> pinhuoOrders) {
        pinhuoOrderView.dissLoading();
        pinhuoOrderView.setData(pinhuoOrders);
    }

    @Override
    public void onUserCancel() {
        pinhuoOrderView.onUserCancel();
    }

    @Override
    public void onUserConfirm() {
        pinhuoOrderView.onUserConfirm();
    }

    @Override
    public void onDriverHandle() {
        pinhuoOrderView.onDriverHandle();
    }
}
