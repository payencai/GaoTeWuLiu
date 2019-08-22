package com.gaote.wuliu.ui.pinhuodriver.mvp.presenter;

import com.gaote.wuliu.ui.client.mine.mvp.model.PinhuoOrder;
import com.gaote.wuliu.ui.client.mine.mvp.model.PinhuoOrderModel;
import com.gaote.wuliu.ui.client.mine.mvp.view.PinhuoOrderView;
import com.gaote.wuliu.ui.pinhuodriver.mvp.mdoel.PinhuoDriverOrderModel;
import com.gaote.wuliu.ui.pinhuodriver.mvp.view.PinhuoDriverOrderView;

import java.util.List;

public class PinhuoDriverOrderPresenter implements PinhuoDriverOrderModel.RequestResult{

    PinhuoDriverOrderModel pinhuoOrderModel;
    PinhuoDriverOrderView pinhuoOrderView;

    public PinhuoDriverOrderPresenter(PinhuoDriverOrderModel pinhuoOrderModel, PinhuoDriverOrderView pinhuoOrderView) {
        this.pinhuoOrderModel = pinhuoOrderModel;
        this.pinhuoOrderView = pinhuoOrderView;
    }
    public void getOrder(int page,int type){
        pinhuoOrderView.showLoading();
        pinhuoOrderModel.getPinhuoDriverOrder(this, page, type);
    }

    public void handlePinhuoDriverOrder(String id,int status){
        pinhuoOrderModel.onHandleOrder(id,status,this);
    }
    @Override
    public void getData(List<PinhuoOrder> pinhuoOrders) {
        pinhuoOrderView.dissLoading();
        pinhuoOrderView.setData(pinhuoOrders);
    }

    @Override
    public void onOrderHandle(int type) {
        pinhuoOrderView.onHandleOrder();
    }


}
