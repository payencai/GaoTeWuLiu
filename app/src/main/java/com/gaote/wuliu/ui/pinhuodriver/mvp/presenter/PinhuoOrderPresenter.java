package com.gaote.wuliu.ui.pinhuodriver.mvp.presenter;

import com.gaote.wuliu.ui.net.mvp.model.NetConfirmOrder;
import com.gaote.wuliu.ui.net.mvp.model.NetConfirmOrderModel;
import com.gaote.wuliu.ui.net.mvp.view.NetConfirmOrderView;
import com.gaote.wuliu.ui.pinhuodriver.mvp.mdoel.PinhuoOrder;
import com.gaote.wuliu.ui.pinhuodriver.mvp.mdoel.PinhuoOrderModel;
import com.gaote.wuliu.ui.pinhuodriver.mvp.view.PinhuoOrderView;

import java.util.List;

public class PinhuoOrderPresenter implements PinhuoOrderModel.RequestResult {
    PinhuoOrderView netConfirmOrderView;
    PinhuoOrderModel netConfirmOrderModel;

    public PinhuoOrderPresenter(PinhuoOrderView netConfirmOrderView, PinhuoOrderModel netConfirmOrderModel) {
        this.netConfirmOrderView = netConfirmOrderView;
        this.netConfirmOrderModel = netConfirmOrderModel;
    }
    public void getConifrmOrder(int page){
        netConfirmOrderView.showLoading();
        netConfirmOrderModel.getOrder(this, page);
    }
    public void confirmOrder(String id){
        netConfirmOrderModel.confirmNetOrder(this,id);
    }
    @Override
    public void onSuccess(List<PinhuoOrder.BeanListBean> listBeans) {
        netConfirmOrderView.dissLoading();
        netConfirmOrderView.onSuccess(listBeans);
    }

    @Override
    public void onConfirm() {
        netConfirmOrderView.onOrderConfirm();
    }
}
