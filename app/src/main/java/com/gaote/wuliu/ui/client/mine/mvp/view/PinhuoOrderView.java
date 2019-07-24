package com.gaote.wuliu.ui.client.mine.mvp.view;

import com.gaote.wuliu.ui.client.mine.mvp.model.Address;
import com.gaote.wuliu.ui.client.mine.mvp.model.PinhuoOrder;

import java.util.List;

public interface PinhuoOrderView {
    public void showLoading();
    public void dissLoading();
    public void onUserCancel();
    public void onUserConfirm();
    public void onDriverHandle();
    public void setData(List<PinhuoOrder> pinhuoOrders);
}
