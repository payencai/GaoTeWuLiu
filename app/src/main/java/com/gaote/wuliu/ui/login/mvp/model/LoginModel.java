package com.gaote.wuliu.ui.login.mvp.model;

import android.appwidget.AppWidgetProviderInfo;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gaote.wuliu.MyApp;
import com.gaote.wuliu.base.even.WuliuEvent;
import com.gaote.wuliu.bean.Result;
import com.gaote.wuliu.net.Api;
import com.gaote.wuliu.net.MyPath;
import com.gaote.wuliu.net.NetUtils;
import com.gaote.wuliu.net.OnMessageReceived;
import com.gaote.wuliu.tools.GsonUtil;
import com.google.gson.Gson;
import com.lzy.okgo.model.HttpParams;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class LoginModel implements BaseModel{
    LoginRequest loginRequest;

    public interface LoginResultImpl {
        public void onReturnClient(ClientUser clientUser);

        public void onReturnService(ServiceUser serviceUser);
        public void onSendCode(String phone);
    }

    @Override
    public void login(LoginRequest loginRequest, LoginResultImpl loginResult) {
        LogUtils.e(loginRequest.getLoginType()+"");
        this.loginRequest = loginRequest;
        switch (loginRequest.getLoginType()) {
            case 1:
                loginByPhone(loginResult);
                break;
            case 2:
                loginByPwd(loginResult);
                break;
            case 3:
                loginByQQ(loginResult);
                break;
            case 4:
                loginByWeixin(loginResult);
                break;
            case 5:
                break;
        }
    }

    @Override
    public void loginByPwd(LoginResultImpl loginResult) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("type", loginRequest.getType());
        httpParams.put("telephone", loginRequest.getUsername());
        NetUtils.getInstance().post(Api.BASE_URL + Api.Manage.URL_LOGIN, httpParams, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                LogUtils.e(response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int code = jsonObject.getInt("resultCode");
                    if (code == 0) {
                        JSONObject data = jsonObject.getJSONObject("data");
                        if (loginRequest.getType() == 2) { //客户端登录
                            ClientUser clientUser = new Gson().fromJson(data.toString(), ClientUser.class);
                            MyApp.setClientUser(clientUser);
                            MyApp.isLogin = true;
                            MyApp.token=clientUser.getToken();
                            EventBus.getDefault().post(new WuliuEvent(200));
                            loginResult.onReturnClient(clientUser);
                        } else {     //服务器端登录，分为3种情况：网点，物流司机，拼货司机
                            ServiceUser serviceUser = new Gson().fromJson(data.toString(), ServiceUser.class);
                            MyApp.isLogin = true;
                            MyApp.token=serviceUser.getToken();
                            MyApp.setServiceUser(serviceUser);
                            //发出通知让MainActivity关闭
                            loginResult.onReturnService(serviceUser);
                        }
                    } else {
                        String msg = jsonObject.getString("message");
                        ToastUtils.showShort(msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(String error) {

            }
        }, loginRequest.getPassword());
    }
    @Override
    public void loginByPhone(LoginResultImpl loginResult) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("telephone", loginRequest.getUsername());
        httpParams.put("code", loginRequest.getCode());
        LogUtils.e(loginRequest.getType());
        NetUtils.getInstance().post(Api.BASE_URL + Api.Manage.loginByTelephoneCode,MyApp.token, httpParams, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                LogUtils.e(response);
                if(!response.contains("data")){
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        int code=jsonObject.getInt("resultCode");
                        if(code==7004){ //微信

                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        int code = jsonObject.getInt("resultCode");
                        if (code == 0) {
                            JSONObject data = jsonObject.getJSONObject("data");
                            if (loginRequest.getType() == 2) { //客户端登录
                                ClientUser clientUser = new Gson().fromJson(data.toString(), ClientUser.class);
                                MyApp.setClientUser(clientUser);
                                MyApp.isLogin = true;
                                MyApp.token=clientUser.getToken();
                                EventBus.getDefault().post(new WuliuEvent(200));
                                loginResult.onReturnClient(clientUser);
                            } else {     //服务器端登录，分为3种情况：网点，物流司机，拼货司机
//                                ServiceUser serviceUser = new Gson().fromJson(data.toString(), ServiceUser.class);
//                                MyApp.isLogin = true;
//                                MyApp.token=serviceUser.getToken();
//                                MyApp.setServiceUser(serviceUser);
//                                //发出通知让MainActivity关闭
//                                loginResult.onReturnService(serviceUser);
                            }
                        } else {
                            String msg = jsonObject.getString("message");
                            ToastUtils.showShort(msg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }

            @Override
            public void onError(String error) {
                ToastUtils.showShort(error);
            }
        });
    }

    @Override
    public void loginByQQ(LoginResultImpl loginResult) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("qqId", loginRequest.getOpenId());
        httpParams.put("type", loginRequest.getType());
        LogUtils.e("start");
        NetUtils.getInstance().post(Api.BASE_URL + Api.Manage.LoginByQqId, httpParams, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                LogUtils.e(response);
                if(!response.contains("data")){
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        int code=jsonObject.getInt("resultCode");
                        if(code==7001){ //QQ未绑定，跳转绑定界面
                            ARouter.getInstance().build(MyPath.Login.BindAccount)
                                    .withSerializable("data",loginRequest)
                                  .navigation();

                        }else if(code==7002){ //微博

                        }else if(code==7000){ //微信
                            ARouter.getInstance().build(MyPath.Login.BindAccount)
                                    .withSerializable("data",loginRequest)
                                    .navigation();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        int code = jsonObject.getInt("resultCode");
                        if (code == 0) {
                            JSONObject data = jsonObject.getJSONObject("data");
                            if (loginRequest.getType() == 2) { //客户端登录
                                ClientUser clientUser = new Gson().fromJson(data.toString(), ClientUser.class);
                                MyApp.setClientUser(clientUser);
                                MyApp.isLogin = true;
                                MyApp.token=clientUser.getToken();
                                EventBus.getDefault().post(new WuliuEvent(200));
                                loginResult.onReturnClient(clientUser);
                            } else {     //服务器端登录，分为3种情况：网点，物流司机，拼货司机
                                ServiceUser serviceUser = new Gson().fromJson(data.toString(), ServiceUser.class);
                                MyApp.isLogin = true;
                                MyApp.token=serviceUser.getToken();
                                MyApp.setServiceUser(serviceUser);
                                //发出通知让MainActivity关闭
                                loginResult.onReturnService(serviceUser);
                            }
                        } else {
                            String msg = jsonObject.getString("message");
                            ToastUtils.showShort(msg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }

            @Override
            public void onError(String error) {
               ToastUtils.showShort(error);
            }
        }, loginRequest.getPassword());
    }

    @Override
    public void loginByWeixin(LoginResultImpl loginResult) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("wxId", loginRequest.getOpenId());
        httpParams.put("type", loginRequest.getType());
        LogUtils.e(loginRequest.getType());
        NetUtils.getInstance().post(Api.BASE_URL + Api.Manage.LoginByWxId, httpParams, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                LogUtils.e(response);
                if(!response.contains("data")){
                    try {
                        JSONObject jsonObject=new JSONObject(response);
                        int code=jsonObject.getInt("resultCode");
                       if(code==7000){ //微信
                            ARouter.getInstance().build(MyPath.Login.BindAccount)
                                    .withSerializable("data",loginRequest)
                                    .navigation();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        int code = jsonObject.getInt("resultCode");
                        if (code == 0) {
                            JSONObject data = jsonObject.getJSONObject("data");
                            if (loginRequest.getType() == 2) { //客户端登录
                                ClientUser clientUser = new Gson().fromJson(data.toString(), ClientUser.class);
                                MyApp.setClientUser(clientUser);
                                MyApp.isLogin = true;
                                MyApp.token=clientUser.getToken();
                                EventBus.getDefault().post(new WuliuEvent(200));
                                loginResult.onReturnClient(clientUser);
                            } else {     //服务器端登录，分为3种情况：网点，物流司机，拼货司机
                                ServiceUser serviceUser = new Gson().fromJson(data.toString(), ServiceUser.class);
                                MyApp.isLogin = true;
                                MyApp.token=serviceUser.getToken();
                                MyApp.setServiceUser(serviceUser);
                                //发出通知让MainActivity关闭
                                loginResult.onReturnService(serviceUser);
                            }
                        } else {
                            String msg = jsonObject.getString("message");
                            ToastUtils.showShort(msg);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }


            }

            @Override
            public void onError(String error) {
                ToastUtils.showShort(error);
            }
        }, loginRequest.getPassword());
    }

    @Override
    public void getCode(LoginResultImpl loginResult,String phone) {
        HttpParams httpParams=new HttpParams();
        httpParams.put("telephone",phone);
        NetUtils.getInstance().post(Api.BASE_URL + Api.Manage.getCodeByTelephone, MyApp.token, httpParams, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                Result<String> result= GsonUtil.fromJsonObject(response,String.class);
                if(result.getResultCode()==0){
                    ToastUtils.showShort("验证码发送成功，请注意查收");
                    loginResult.onSendCode(phone);
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

    public static class LoginRequest implements Serializable {
        private int type; //1.服务器2 用户端
        private int loginType;//1 手机 2 密码 3 qq 4 wechat 5 blog
        private String openId;

        public String getOpenId() {
            return openId;
        }

        public void setOpenId(String openId) {
            this.openId = openId;
        }

        public int getLoginType() {
            return loginType;
        }

        public void setLoginType(int loginType) {
            this.loginType = loginType;
        }

        private String password;
        private String username;
        private String code;
        private String name;
        private String headUrl;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHeadUrl() {
            return headUrl;
        }

        public void setHeadUrl(String headUrl) {
            this.headUrl = headUrl;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }

}
