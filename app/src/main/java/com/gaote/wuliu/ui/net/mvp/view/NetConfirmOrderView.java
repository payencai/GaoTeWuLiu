package com.gaote.wuliu.ui.net.mvp.view;

import com.gaote.wuliu.ui.net.mvp.model.NetConfirmOrder;

import java.util.List;

public interface NetConfirmOrderView {
    public void showLoading();
    public void dissLoading();
    public void onNetOrderConfirm();
    public void onSuccess(List<NetConfirmOrder.ListBean> listBeans);
}
