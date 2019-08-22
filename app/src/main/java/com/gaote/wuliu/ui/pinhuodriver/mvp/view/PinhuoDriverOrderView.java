package com.gaote.wuliu.ui.pinhuodriver.mvp.view;

import com.gaote.wuliu.ui.client.mine.mvp.model.PinhuoOrder;

import java.util.List;

public interface PinhuoDriverOrderView {
    public void showLoading();
    public void dissLoading();
    public void onHandleOrder();
    public void setData(List<PinhuoOrder> pinhuoOrders);
}
