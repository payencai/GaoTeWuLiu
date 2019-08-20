package com.gaote.wuliu.ui.net.mvp.view;

import com.gaote.wuliu.ui.net.mvp.model.NetConfirmOrder;
import com.gaote.wuliu.ui.net.mvp.model.NetRecordOrder;

import java.util.List;

public interface NetRecordOrderView {
    public void showLoading();
    public void dissLoading();
    public void onNetOrderConfirm();
    public void onSuccess(List<NetRecordOrder.ListBean> listBeans);
}
