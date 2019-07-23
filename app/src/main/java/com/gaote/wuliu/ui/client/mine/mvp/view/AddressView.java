package com.gaote.wuliu.ui.client.mine.mvp.view;

import com.gaote.wuliu.ui.client.mine.mvp.model.Address;

import java.util.List;

public interface AddressView {
    public void showLoading();
    public void dissLoading();
    public void setData(List<Address> addressList);
    public void afterDelete();
    public void afterDefault();
    public void onAdd();
    public void onUpdate();
}
