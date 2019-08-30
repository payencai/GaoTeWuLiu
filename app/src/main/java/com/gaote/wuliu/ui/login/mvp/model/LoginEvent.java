package com.gaote.wuliu.ui.login.mvp.model;

public class LoginEvent {
    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public LoginEvent(int code) {
        this.code = code;
    }
}
