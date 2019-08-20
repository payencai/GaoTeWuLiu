package com.gaote.wuliu.ui.net.mvp.presenter;

import com.gaote.wuliu.ui.net.mvp.model.NetConfirmOrder;
import com.gaote.wuliu.ui.net.mvp.model.NetConfirmOrderModel;
import com.gaote.wuliu.ui.net.mvp.model.NetRecordOrder;
import com.gaote.wuliu.ui.net.mvp.model.NetRecordOrderModel;
import com.gaote.wuliu.ui.net.mvp.view.NetRecordOrderView;
import com.gaote.wuliu.ui.net.mvp.view.NetRecordOrderView;

import java.util.List;

public class NetRecordPresenter implements NetRecordOrderModel.RequestResult {
    NetRecordOrderView netRecordOrderView;
    NetRecordOrderModel netConfirmOrderModel;

    public NetRecordPresenter(NetRecordOrderView netRecordOrderView, NetRecordOrderModel netConfirmOrderModel) {
        this.netRecordOrderView = netRecordOrderView;
        this.netConfirmOrderModel = netConfirmOrderModel;
    }
    public void getConifrmOrder(int page){
        netRecordOrderView.showLoading();
        netConfirmOrderModel.getNetRecordOrder(this, page);
    }
    public void confirmOrder(String id){
        netConfirmOrderModel.recordNetOrder(this,id);
    }
    @Override
    public void onSuccess(List<NetRecordOrder.ListBean> listBeans) {
        netRecordOrderView.dissLoading();
        netRecordOrderView.onSuccess(listBeans);
    }

    @Override
    public void onConfirm() {
        netRecordOrderView.onNetOrderConfirm();
    }
}
