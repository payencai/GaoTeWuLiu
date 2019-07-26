package com.gaote.wuliu.ui.client.mine.mvp.presenter;

public interface BasePresenter {
    public void userOrderConfirm(String id);
    public void getOrder(int page,int status);
    public void handleDriver(String id,int status);
    public void onUserCancel(String id);

}
