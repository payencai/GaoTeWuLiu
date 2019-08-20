package com.gaote.wuliu.ui.net.mvp.presenter;

import com.gaote.wuliu.ui.net.mvp.model.NetConfirmOrder;
import com.gaote.wuliu.ui.net.mvp.model.NetConfirmOrderModel;
import com.gaote.wuliu.ui.net.mvp.model.NetDoorOrder;
import com.gaote.wuliu.ui.net.mvp.model.NetDoorOrderModel;
import com.gaote.wuliu.ui.net.mvp.view.NetConfirmOrderView;
import com.gaote.wuliu.ui.net.mvp.view.NetDoorOrderView;

import java.util.List;

public class NetDoorOrderPresenter implements NetDoorOrderModel.RequestResult {
    NetDoorOrderView netConfirmOrderView;
    NetDoorOrderModel netConfirmOrderModel;

    public NetDoorOrderPresenter(NetDoorOrderView netConfirmOrderView, NetDoorOrderModel netConfirmOrderModel) {
        this.netConfirmOrderView = netConfirmOrderView;
        this.netConfirmOrderModel = netConfirmOrderModel;
    }
    public void getDoorOrder(int page){
        netConfirmOrderView.showLoading();
        netConfirmOrderModel.getDoorOrder(this, page);
    }

    @Override
    public void onSuccess(List<NetDoorOrder> listBeans) {
        netConfirmOrderView.dissLoading();
        netConfirmOrderView.onSuccess(listBeans);
    }


}
