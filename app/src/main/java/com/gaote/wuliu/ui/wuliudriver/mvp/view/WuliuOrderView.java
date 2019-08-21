package com.gaote.wuliu.ui.wuliudriver.mvp.view;

import com.gaote.wuliu.ui.pinhuodriver.mvp.mdoel.PinhuoOrder;
import com.gaote.wuliu.ui.wuliudriver.mvp.model.WuliuNet;

import java.util.List;

public interface WuliuOrderView {
    public void showLoading();
    public void dissLoading();
    public void onSuccess(List<WuliuNet> wuliuNets);
}
