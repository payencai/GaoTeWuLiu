package com.gaote.wuliu.alipay;

import android.app.Activity;

import com.blankj.utilcode.util.ToastUtils;
import com.gaote.wuliu.MyApp;
import com.tencent.mm.sdk.modelpay.PayReq;


public class PaymentHelper {
    /**
     * 微信支付
     */
    public static void startWeChatPay(Activity activity, PayReponse payReponse) {
        if (activity == null || payReponse == null)
            return;
        if (!WxPayConfig.APP_ID.equals(payReponse.getAppid()))
            return;


        // 将该app注册到微信

        if (!MyApp.mWxApi.isWXAppInstalled()) {
            ToastUtils.showShort("你没有安装微信");
            return;
        }
        //我们把请求到的参数全部给微信
        PayReq req = new PayReq(); //调起微信APP的对象
        req.appId = payReponse.getAppid();
        req.partnerId = payReponse.getPartnerid();
        req.prepayId = payReponse.getPrepayid();
        req.nonceStr = payReponse.getNoncestr();
        req.timeStamp = payReponse.getTimestamp();
        req.packageValue = payReponse.getPackageX(); //Sign=WXPay
        req.sign = payReponse.getSign();

        MyApp.mWxApi.sendReq(req); //发送调起微信的请求
    }
}
