package com.gaote.wuliu.ui.net.mvp.view;

import com.gaote.wuliu.ui.net.mvp.model.NetDoorOrder;
import com.gaote.wuliu.ui.net.mvp.model.NetOrder;

import java.util.List;

public interface NetOrderView {
    public void showLoading();
    public void dissLoading();
    public void onSuccess(List<NetOrder.ListBean> listBeans);
}
