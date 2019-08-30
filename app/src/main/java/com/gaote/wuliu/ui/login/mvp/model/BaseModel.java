package com.gaote.wuliu.ui.login.mvp.model;

public interface BaseModel {
    void login(LoginModel.LoginRequest loginRequest, LoginModel.LoginResultImpl loginResult);
    void loginByPwd(LoginModel.LoginResultImpl loginResult);
    void loginByPhone(LoginModel.LoginResultImpl loginResult);
    void loginByQQ(LoginModel.LoginResultImpl loginResult);
    void loginByWeixin(LoginModel.LoginResultImpl loginResult);
    void getCode(LoginModel.LoginResultImpl loginResult,String phone);
}
