package com.gaote.wuliu.ui.login.mvp.view;

import com.gaote.wuliu.ui.login.mvp.model.ClientUser;
import com.gaote.wuliu.ui.login.mvp.model.ServiceUser;

public interface LoginView {
    public void showLoading();
    public void dissmissLoading();
    public void saveClientUser(ClientUser clientUser);
    public void saveServiceUser(ServiceUser serviceUser);
}
