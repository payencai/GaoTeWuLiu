package com.gaote.wuliu.ui.login.mvp.module;

import com.gaote.wuliu.ui.login.mvp.model.LoginModel;
import com.gaote.wuliu.ui.login.mvp.view.LoginView;

import dagger.Module;
import dagger.Provides;

@Module
public class LoginModule {
    private LoginView loginView;

    public LoginModule(LoginView loginView) {
        this.loginView = loginView;
    }

    @Provides
    LoginModel getLoginModule(){
        return new LoginModel();
    }
    @Provides
    LoginView getLoginView(){
        return loginView;
    }
}
