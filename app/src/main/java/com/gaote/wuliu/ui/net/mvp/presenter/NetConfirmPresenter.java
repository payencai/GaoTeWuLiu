package com.gaote.wuliu.ui.net.mvp.presenter;

import com.gaote.wuliu.ui.client.mine.mvp.model.Address;
import com.gaote.wuliu.ui.client.mine.mvp.model.AddressModel;
import com.gaote.wuliu.ui.client.mine.mvp.model.AddressRequest;
import com.gaote.wuliu.ui.client.mine.mvp.view.AddressView;
import com.gaote.wuliu.ui.net.mvp.model.NetConfirmOrder;
import com.gaote.wuliu.ui.net.mvp.model.NetConfirmOrderModel;
import com.gaote.wuliu.ui.net.mvp.view.NetConfirmOrderView;

import java.util.List;

public class NetConfirmPresenter implements NetConfirmOrderModel.RequestResult {
    NetConfirmOrderView netConfirmOrderView;
    NetConfirmOrderModel netConfirmOrderModel;

    public NetConfirmPresenter(NetConfirmOrderView netConfirmOrderView, NetConfirmOrderModel netConfirmOrderModel) {
        this.netConfirmOrderView = netConfirmOrderView;
        this.netConfirmOrderModel = netConfirmOrderModel;
    }
    public void getConifrmOrder(int page){
        netConfirmOrderView.showLoading();
        netConfirmOrderModel.getNetConfirmOrder(this, page);
    }
    public void confirmOrder(String id,String time){
        netConfirmOrderModel.confirmNetOrder(this,id,time);
    }
    @Override
    public void onSuccess(List<NetConfirmOrder.ListBean> listBeans) {
        netConfirmOrderView.dissLoading();
        netConfirmOrderView.onSuccess(listBeans);
    }

    @Override
    public void onConfirm() {
        netConfirmOrderView.onNetOrderConfirm();
    }
}
