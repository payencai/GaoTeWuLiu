package com.gaote.wuliu.ui.login.mvp.component;

import com.gaote.wuliu.ui.login.LoginActivity;
import com.gaote.wuliu.ui.login.mvp.module.LoginModule;

import dagger.Component;

@Component(modules = LoginModule.class)
public interface LoginComponent {
    void inject(LoginActivity loginActivity);
}
