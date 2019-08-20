package com.gaote.wuliu.ui.net.mvp.presenter;

import com.gaote.wuliu.ui.net.mvp.model.NetDoorOrder;
import com.gaote.wuliu.ui.net.mvp.model.NetDoorOrderModel;
import com.gaote.wuliu.ui.net.mvp.model.NetOrder;
import com.gaote.wuliu.ui.net.mvp.model.NetOrderModel;
import com.gaote.wuliu.ui.net.mvp.view.NetDoorOrderView;
import com.gaote.wuliu.ui.net.mvp.view.NetOrderView;

import java.util.List;

public class NetOrderPresenter implements NetOrderModel.RequestResult {
    NetOrderView netOrderView;
    NetOrderModel netOrderModel;

    public NetOrderPresenter(NetOrderView netConfirmOrderView, NetOrderModel netConfirmOrderModel) {
        this.netOrderView = netConfirmOrderView;
        this.netOrderModel = netConfirmOrderModel;
    }
    public void getNetOrder(int page){
        netOrderView.showLoading();
        netOrderModel.getNetOrder(this, page);
    }

    @Override
    public void onSuccess(List<NetOrder.ExpressConfirmedItem> listBeans) {
        netOrderView.dissLoading();
        netOrderView.onSuccess(listBeans);
    }


}
