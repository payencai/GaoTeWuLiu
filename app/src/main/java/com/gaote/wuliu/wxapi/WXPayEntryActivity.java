package com.gaote.wuliu.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.blankj.utilcode.util.LogUtils;
import com.gaote.wuliu.alipay.WxPayConfig;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.xgr.easypay.activity.WXPayEntryBaseActivity;

public class WXPayEntryActivity extends WXPayEntryBaseActivity {
    @Override
    public String getWXAppId() {
        return WxPayConfig.APP_ID;
    }
}