package com.gaote.wuliu;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDexApplication;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.amap.api.location.AMapLocation;
import com.amap.api.maps.AMap;
import com.gaote.wuliu.net.NetUtils;
import com.gaote.wuliu.ui.login.mvp.model.ClientUser;
import com.gaote.wuliu.ui.login.mvp.model.ServiceUser;

import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.tencent.smtt.sdk.QbSdk;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

public class MyApp extends MultiDexApplication {
    public static boolean isLogin=false;
    public static String token="";
    public static MyApp context;
    private static ClientUser clientUser;
    private static ServiceUser serviceUser;
    public static IWXAPI mWxApi;
    public static ServiceUser getServiceUser() {
        return serviceUser;
    }

    public static void setServiceUser(ServiceUser serviceUser) {
        MyApp.serviceUser = serviceUser;
    }

    private  AMapLocation aMapLocation;

    public AMapLocation getaMapLocation() {
        return aMapLocation;
    }

    public void setaMapLocation(AMapLocation aMapLocation) {
        this.aMapLocation = aMapLocation;
    }

    public static MyApp getInstance(){
        return context;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        init();

        initRouter(this);
    }
    private void init(){
        context=this;

        NetUtils.getInstance().initNetWorkUtils(this);//okgo初始化
        UMConfigure.init(this,"5d6347594ca3572fd2000703"
                ,"umeng",UMConfigure.DEVICE_TYPE_PHONE,"");
        UMShareAPI.get(this);//初始化sdk
        initX5();//腾讯h5

    }
    private void initRouter(Application context){

        ARouter.openLog();     // Print log
        ARouter.openDebug();   // Turn on debugging mode (If you are running in InstantRun mode, you must turn on debug mode! Online version needs to be closed, otherwise there is a security risk)
        ARouter.init(context); // As early as possible, it is recommended to initialize in the Application
    }

    private void initX5(){
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d("app", " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }
    public static ClientUser getClientUser() {
        return clientUser;
    }

    public static void setClientUser(ClientUser clientUser) {
        MyApp.clientUser = clientUser;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        ARouter.getInstance().destroy();
    }

    //各个平台的配置
    {
        //微信68eda45477ace8ea88ad0a7b13160ab6  17e5ce624f31ce7aca02062a5f7f4c53
        PlatformConfig.setWeixin("wx16d6577fe6f1893a", "d9542a55489b6f21bf9ebd04263186d1");
        //新浪微博(第三个参数为回调地址)
        PlatformConfig.setSinaWeibo("1276917432", "c0f499aef70c22b827b0f4878ba867f0","http://sns.whalecloud.com/sina2/callback");
        //QQ
        PlatformConfig.setQQZone("101729546", "1fb005e4abf633f9aba757f10480c034");
    }
}
