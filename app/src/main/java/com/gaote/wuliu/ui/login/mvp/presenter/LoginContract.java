package com.gaote.wuliu.ui.login.mvp.presenter;

import com.gaote.wuliu.ui.login.mvp.model.BaseModel;
import com.gaote.wuliu.ui.login.mvp.view.BaseView;

public interface LoginContract {
    interface View extends BaseView {

        /**
         * 登录成功
         * @param result
         */
        void loginSuccess(String result);
    }
    interface Model extends BaseModel {

        /**
         * 登录
         * @param mobile
         * @param password
         */
        void  login(String mobile, String password);

    }

}
