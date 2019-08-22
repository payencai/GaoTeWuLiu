package com.gaote.wuliu.ui.wuliudriver.mvp.view;

import com.gaote.wuliu.ui.wuliudriver.mvp.model.WuliuNet;
import com.gaote.wuliu.ui.wuliudriver.mvp.model.WuliuNetOrder;

import java.util.List;

public interface WuliuNetOrderView {
    public void showLoading();
    public void dissLoading();
    public void onAfterConfirm();
    public void onSuccess(List<WuliuNetOrder.ListBean> wuliuNets);

}
