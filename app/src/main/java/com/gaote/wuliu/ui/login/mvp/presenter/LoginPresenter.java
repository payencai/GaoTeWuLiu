package com.gaote.wuliu.ui.login.mvp.presenter;

import com.gaote.wuliu.ui.login.mvp.model.ClientUser;
import com.gaote.wuliu.ui.login.mvp.model.LoginModel;
import com.gaote.wuliu.ui.login.mvp.model.ServiceUser;
import com.gaote.wuliu.ui.login.mvp.view.LoginView;

public class LoginPresenter implements LoginModel.LoginResult{
    LoginModel loginModel;
    LoginView loginView;

    public LoginPresenter(LoginModel loginModel, LoginView loginView) {
        this.loginModel = loginModel;
        this.loginView = loginView;
    }
    public  void start(LoginModel.LoginRequest loginRequest){
        loginView.showLoading();
        loginModel.login(loginRequest,this);
    }

    @Override
    public void onReturnClient(ClientUser clientUser) {
        loginView.dissmissLoading();
        loginView.saveClientUser(clientUser);

    }

    @Override
    public void onReturnService(ServiceUser serviceUser) {
        loginView.dissmissLoading();
        loginView.saveServiceUser(serviceUser);

    }
}
