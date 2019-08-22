package com.gaote.wuliu.ui.wuliudriver.mvp.view;

import com.gaote.wuliu.ui.wuliudriver.mvp.model.WuliuDriverOrder;
import com.gaote.wuliu.ui.wuliudriver.mvp.model.WuliuNet;

import java.util.List;

public interface WuliuDriverOrderView {
    public void showLoading();
    public void dissLoading();
    public void onAfterConfirm();
    public void onAfterSend();
    public void getMyOrder(List<WuliuDriverOrder> wuliuDriverOrders);
}
