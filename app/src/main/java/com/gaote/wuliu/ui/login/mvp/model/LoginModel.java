package com.gaote.wuliu.ui.login.mvp.model;

import android.appwidget.AppWidgetProviderInfo;

import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gaote.wuliu.MyApp;
import com.gaote.wuliu.base.even.WuliuEvent;
import com.gaote.wuliu.net.Api;
import com.gaote.wuliu.net.NetUtils;
import com.gaote.wuliu.net.OnMessageReceived;
import com.google.gson.Gson;
import com.lzy.okgo.model.HttpParams;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginModel implements BaseModel{
    LoginRequest loginRequest;

    public interface LoginResultImpl {
        public void onReturnClient(ClientUser clientUser);

        public void onReturnService(ServiceUser serviceUser);
    }

    @Override
    public void login(LoginRequest loginRequest, LoginResultImpl loginResult) {
        this.loginRequest = loginRequest;
        switch (loginRequest.getLoginType()) {
            case 1:
                loginByPhone(loginResult);
                break;
            case 2:
                loginByPwd(loginResult);
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
                            EventBus.getDefault().post(new WuliuEvent(200));
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

    }

    public static class LoginRequest {
        private int type; //1.服务器2 用户端
        private int loginType;//1 手机 2 密码 3 qq 4 wechat 5 blog

        public int getLoginType() {
            return loginType;
        }

        public void setLoginType(int loginType) {
            this.loginType = loginType;
        }

        private String password;
        private String username;
        private String code;

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
