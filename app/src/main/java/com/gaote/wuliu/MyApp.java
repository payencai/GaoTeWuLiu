package com.gaote.wuliu;

import android.app.Application;

import com.gaote.wuliu.net.NetUtils;
import com.gaote.wuliu.ui.login.mvp.model.ClientUser;

public class MyApp extends Application {
    public static boolean isLogin=false;
    private static ClientUser clientUser;
    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    public static ClientUser getClientUser() {
        return clientUser;
    }

    public static void setClientUser(ClientUser clientUser) {
        MyApp.clientUser = clientUser;
    }

    private void init(){
        NetUtils.getInstance().initNetWorkUtils(this);//okgo初始化
    }
}
