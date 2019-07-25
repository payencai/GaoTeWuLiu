package com.gaote.wuliu.ui.client.mine.mvp.view;

import com.gaote.wuliu.ui.client.mine.mvp.model.PinhuoOrder;
import com.gaote.wuliu.ui.client.mine.mvp.model.WuliuOrder;

import java.util.List;

public interface WuliuOrderView extends BaseView{
    public void onUserConfirm();
    public void setData(List<WuliuOrder> wuliuOrders);
}
