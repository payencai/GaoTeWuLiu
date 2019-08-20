package com.gaote.wuliu.ui.net.mvp.view;

import com.gaote.wuliu.ui.net.mvp.model.NetDoorOrder;
import com.gaote.wuliu.ui.net.mvp.model.NetRecordOrder;

import java.util.List;

public interface NetDoorOrderView {
    public void showLoading();
    public void dissLoading();
    public void onSuccess(List<NetDoorOrder> netDoorOrders);
}
