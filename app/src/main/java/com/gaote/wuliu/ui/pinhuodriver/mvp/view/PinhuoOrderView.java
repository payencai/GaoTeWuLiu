package com.gaote.wuliu.ui.pinhuodriver.mvp.view;

import com.gaote.wuliu.ui.net.mvp.model.NetConfirmOrder;
import com.gaote.wuliu.ui.pinhuodriver.mvp.mdoel.PinhuoOrder;

import java.util.List;

public interface PinhuoOrderView {
    public void showLoading();
    public void dissLoading();
    public void onOrderConfirm();
    public void onSuccess(List<PinhuoOrder.BeanListBean> listBeans);
}
