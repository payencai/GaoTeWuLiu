package com.gaote.wuliu.ui.client.mine.mvp.presenter;

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
    @Override
    public void getData(List<PinhuoOrder> pinhuoOrders) {
        pinhuoOrderView.dissLoading();
        pinhuoOrderView.setData(pinhuoOrders);
    }
}
