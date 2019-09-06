package com.gaote.wuliu.ui.client.wuliu;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gaote.wuliu.MyApp;
import com.gaote.wuliu.R;
import com.gaote.wuliu.alipay.PayReponse;
import com.gaote.wuliu.alipay.WxPayConfig;
import com.gaote.wuliu.base.even.WuliuEvent;
import com.gaote.wuliu.bean.AddrBean;
import com.gaote.wuliu.bean.Result;
import com.gaote.wuliu.net.Api;
import com.gaote.wuliu.net.MyPath;
import com.gaote.wuliu.net.NetUtils;
import com.gaote.wuliu.net.OnMessageReceived;
import com.gaote.wuliu.tools.CheckDoubleClick;
import com.gaote.wuliu.tools.GsonUtil;
import com.gaote.wuliu.tools.MathUtils;
import com.gaote.wuliu.ui.client.mine.bean.Coupon;
import com.gaote.wuliu.ui.client.pinhuo.PinhuoDetailActivity;
import com.gaote.wuliu.ui.client.wuliu.model.WuliuCompany;
import com.google.gson.Gson;
import com.lzy.okgo.model.HttpParams;
import com.umeng.commonsdk.debug.D;
import com.xgr.easypay.EasyPay;
import com.xgr.easypay.alipay.AliPay;
import com.xgr.easypay.alipay.AlipayInfoImpli;
import com.xgr.easypay.callback.IPayCallback;
import com.xgr.easypay.wxpay.WXPay;
import com.xgr.easypay.wxpay.WXPayInfoImpli;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = MyPath.Wuliu.SendGoods)
public class WuliuSendActivity extends AppCompatActivity {
    WuliuCompany wuliuCompany;
    Coupon coupon;
    @BindView(R.id.tv_company)
    TextView tv_company;
    @BindView(R.id.tv_send_address)
    TextView tv_send_address;
    @BindView(R.id.tv_get_address)
    TextView tv_get_address;
    @BindView(R.id.et_send_name)
    EditText et_send_name;
    @BindView(R.id.et_get_name)
    EditText et_get_name;
    @BindView(R.id.et_get_phone)
    EditText et_get_phone;
    @BindView(R.id.et_send_phone)
    EditText et_send_phone;
    @BindView(R.id.tv_coupon)
    TextView tv_coupon;
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.et_weight)
    EditText et_weight;
    @BindView(R.id.et_volume)
    EditText et_volume;
    @BindView(R.id.et_num)
    EditText et_num;
    @BindView(R.id.et_name)
    EditText et_name;
    boolean isYunda = false;
    AddrBean addrGet;
    AddrBean addrSend;
    double realPrice=0;
    int payType=1;
    String orderId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wuliu_send);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
    }

    private void initView() {
        et_weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                  String value=s.toString();
                  if(!TextUtils.isEmpty(value)&& MathUtils.isNumber(value)){
                      calMoney();
                  }else{
                      realPrice=0;
                      tv_price.setText("0");
                  }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //ToastUtils.showShort("success");
        if (data != null) {
            if (requestCode == 2) {
                wuliuCompany = (WuliuCompany) data.getSerializableExtra("data");
                tv_company.setText(wuliuCompany.getExName());
                calMoney();
            }
            else if (requestCode == 3) {
                addrSend = (AddrBean) data.getSerializableExtra("addrbean");
                tv_send_address.setText(addrSend.getProvince()+addrSend.getCity()+addrSend.getArea()+addrSend.getSecaddr());
            }
            else if (requestCode == 4) {
                addrGet = (AddrBean) data.getSerializableExtra("addrbean");
                tv_get_address.setText(addrGet.getProvince()+addrGet.getCity()+addrGet.getArea()+addrGet.getSecaddr());
                calMoney();

            }else if(requestCode==5){
                coupon = (Coupon) data.getSerializableExtra("data");
                tv_coupon.setText(coupon.getName());
                calMoney();
            }
        }
    }

    private void calMoney() {
        if(wuliuCompany==null){
            return;
        }
        if(TextUtils.isEmpty(et_weight.getEditableText().toString())){
            return;
        }
        if(addrGet==null){
            return;
        }
        if(!"韵达快递".equals(wuliuCompany.getExName()))
            getPrice();
        else{
            getYundaPrice();
        }
    }

    @OnClick({R.id.iv_back, R.id.rl_company, R.id.btn_submit, R.id.tv_search, R.id.rl_coupon, R.id.tv_detail, R.id.tv_send_address, R.id.tv_get_address})
    void OnClick(View view) {
        if (CheckDoubleClick.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.tv_get_address:
                ARouter.getInstance().build(MyPath.Pinhuo.SelectAddress).navigation(WuliuSendActivity.this, 4);
                break;
            case R.id.tv_send_address:
                ARouter.getInstance().build(MyPath.Pinhuo.SelectAddress).navigation(WuliuSendActivity.this, 3);
                break;
            case R.id.tv_detail:
                break;
            case R.id.rl_coupon:
                ARouter.getInstance().build(MyPath.Mine.MyCoupon).withInt("orderType",2).navigation(this,5);
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_company:
                ARouter.getInstance().build(MyPath.Wuliu.ChooseCompany).navigation(WuliuSendActivity.this, 2);
                break;
            case R.id.btn_submit:
                if (checkInput()) {
                   showPayMethodDialog();
                }
                break;
            case R.id.tv_search:
                ARouter.getInstance().build(MyPath.Wuliu.NetSearch).navigation();
                break;
        }
    }

    private boolean checkInput() {
        boolean isOk = true;
        return isOk;
    }

    //支付方式弹窗
    private void showPayMethodDialog() {
        payType = 1;
        final Dialog dialog = new Dialog(this, R.style.BottomDialog);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_pay_method, null);
        TextView tv_confirm = view.findViewById(R.id.tv_confirm);
        RelativeLayout rlAliPay = view.findViewById(R.id.rl_alipay);
        RelativeLayout rlWechat = view.findViewById(R.id.rl_wechat);
        ImageView iv_alipay = view.findViewById(R.id.iv_alipay);
        ImageView iv_wechat = view.findViewById(R.id.iv_wechat);
        rlWechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payType = 2;
                iv_wechat.setImageResource(R.mipmap.ic_check);
                iv_alipay.setImageResource(R.mipmap.ic_uncheck);
            }
        });
        rlAliPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payType = 1;
                iv_alipay.setImageResource(R.mipmap.ic_check);
                iv_wechat.setImageResource(R.mipmap.ic_uncheck);
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                getOrderId();
                //showPayFinishDialog();
            }
        });
        dialog.setContentView(view);
        dialog.show();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);
    }
    //支付成功弹窗
    private void showPayFinishDialog() {
        final Dialog dialog = new Dialog(this, R.style.CustomDialog);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_pay_finish, null);
        dialog.setContentView(view);
        dialog.show();
        Window window = dialog.getWindow();
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = (int) (display.getWidth() * 0.6);
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);
    }
    //生成订单
    private void getOrderId() {
        HttpParams httpParams = new HttpParams();
        if(wuliuCompany!=null){
            httpParams.put("logisticsCompanyNo",wuliuCompany.getExNo());
            httpParams.put("logisticsCompanyName", wuliuCompany.getExName());
        }
        httpParams.put("demanderTelnum", et_send_phone.getEditableText().toString());
        httpParams.put("nameFrom",et_send_name.getEditableText().toString());
        httpParams.put("adressFrom", addrSend.getSecaddr());
        httpParams.put("adressFromProvince", addrSend.getProvince());
        httpParams.put("adressFromCity", addrSend.getCity());
        httpParams.put("adressFromDistrict", addrSend.getArea());
        httpParams.put("adressFromLongitude", addrSend.getLon());
        httpParams.put("adressFromLatitude", addrSend.getLat());

        httpParams.put("receiverTelnum", et_get_phone.getEditableText().toString());
        httpParams.put("nameTo", et_get_name.getEditableText().toString());
        httpParams.put("adressTo", addrGet.getSecaddr());
        httpParams.put("adressToProvince",addrGet.getProvince());
        httpParams.put("adressToCity", addrGet.getCity());
        httpParams.put("adressToDistrict", addrGet.getArea());
        httpParams.put("adressToLongitude", addrGet.getLon()+"");
        httpParams.put("adressToLatitude", addrGet.getLat()+"");

        httpParams.put("goodsName", et_name.getEditableText().toString());
        httpParams.put("goodsMass", Double.parseDouble(et_weight.getEditableText().toString()));
        httpParams.put("goodsSize", Double.parseDouble(et_volume.getEditableText().toString()));
        httpParams.put("goodsQuantity", Integer.parseInt(et_num.getEditableText().toString()));
        httpParams.put("payMethod", payType);

        if(coupon!=null){
            httpParams.put("couponDetailId", coupon.getId());
            httpParams.put("discountPrice", coupon.getPrice());
        }
        LogUtils.e(new Gson().toJson(httpParams));
        NetUtils.getInstance().post(Api.BASE_URL + Api.Wuliu.addLogisticsOrder, MyApp.token, httpParams, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                LogUtils.e(response);
                Result<String> result = GsonUtil.fromJsonObject(response, String.class);
                if (result.getResultCode() == 0) {
                    orderId = result.getData();
                    if(payType==1){
                        getAliPayParams();
                    }else{
                        getWechatPayParams();
                    }
                }
            }

            @Override
            public void onError(String error) {

            }
        });

    }
    private void getWechatPayParams() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("orderId", orderId);
        NetUtils.getInstance().post(Api.BASE_URL + Api.Pay.WuliuWechat, MyApp.token, httpParams, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                LogUtils.e(response);
                Result<PayReponse> result = GsonUtil.fromJsonObject(response, PayReponse.class);
                if (result.getResultCode() == 0) {

                    PayReponse payReponse = result.getData();
                    WXPay wxPay = WXPay.getInstance(WuliuSendActivity.this, WxPayConfig.APP_ID);
                    //构造微信订单实体。一般都是由服务端直接返回。
                    WXPayInfoImpli wxPayInfoImpli = new WXPayInfoImpli();
                    wxPayInfoImpli.setTimestamp(payReponse.getTimestamp());
                    wxPayInfoImpli.setSign(payReponse.getSign());
                    wxPayInfoImpli.setPrepayId(payReponse.getPrepayid());
                    wxPayInfoImpli.setPartnerid(payReponse.getPartnerid());
                    wxPayInfoImpli.setAppid(payReponse.getAppid());
                    wxPayInfoImpli.setNonceStr(payReponse.getNoncestr());
                    wxPayInfoImpli.setPackageValue(payReponse.getPackageX());
                    //策略场景类调起支付方法开始支付，以及接收回调。
                    EasyPay.pay(wxPay, WuliuSendActivity.this, wxPayInfoImpli, new IPayCallback() {
                        @Override
                        public void success() {
                            showPayFinishDialog();
                        }

                        @Override
                        public void failed() {
                            ToastUtils.showShort("支付失败");
                        }

                        @Override
                        public void cancel() {
                            ToastUtils.showShort("支付取消");
                        }
                    });


                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }
    private void getAliPayParams() {
        HttpParams httpParams = new HttpParams();
        httpParams.put("orderId", orderId);
        NetUtils.getInstance().post(Api.BASE_URL + Api.Pay.WuliuAlipay, MyApp.token, httpParams, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                LogUtils.e(response);
                Result<String> result = GsonUtil.fromJsonObject(response, String.class);
                if (result.getResultCode() == 0) {
                    String pay=result.getData();

                    //实例化支付宝支付策略
                    AliPay aliPay = new AliPay();
                    //构造支付宝订单实体。一般都是由服务端直接返回。
                    AlipayInfoImpli alipayInfoImpli = new AlipayInfoImpli();
                    alipayInfoImpli.setOrderInfo(result.getData());
                    //策略场景类调起支付方法开始支付，以及接收回调。
                    EasyPay.pay(aliPay, WuliuSendActivity.this, alipayInfoImpli, new IPayCallback() {
                        @Override
                        public void success() {
                            showPayFinishDialog();
                        }

                        @Override
                        public void failed() {
                            ToastUtils.showShort("支付失败");
                        }

                        @Override
                        public void cancel() {
                            ToastUtils.showShort("支付取消");
                        }
                    });


                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }
    private void getPrice(){
        HttpParams httpParams=new HttpParams();
        httpParams.put("goodsMass",Double.parseDouble(et_weight.getEditableText().toString()));
        httpParams.put("province",addrGet.getProvince());
        httpParams.put("city",addrGet.getCity());
        httpParams.put("district",addrGet.getArea());
        httpParams.put("mold",1);
        NetUtils.getInstance().get(MyApp.token, Api.BASE_URL + Api.Wuliu.getFreightByRules, httpParams, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                LogUtils.e(response);
                Result<Double> result= GsonUtil.fromJsonObject(response, Double.class);
                if(result.getResultCode()==0){
                    realPrice=result.getData();
                    double price=realPrice;
                    if(coupon!=null){
                        price=price-coupon.getPrice();
                        tv_price.setText(price+"");
                    }else {
                        tv_price.setText(price+"");
                    }
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }
    private void getYundaPrice(){
        HttpParams httpParams=new HttpParams();
        httpParams.put("goodsMass",Double.parseDouble(et_weight.getEditableText().toString()));
        httpParams.put("province",addrGet.getProvince());
        httpParams.put("mold",1);
        NetUtils.getInstance().get(MyApp.token, Api.BASE_URL + Api.Wuliu.getFreightByRulesYunda, httpParams, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                LogUtils.e(response);
                Result<Double> result= GsonUtil.fromJsonObject(response, Double.class);
                if(result.getResultCode()==0){
                    realPrice=result.getData();
                    double price=realPrice;
                    if(coupon!=null){
                        price=price-coupon.getPrice();
                        tv_price.setText(price+"");
                    }else {
                        tv_price.setText(price+"");
                    }
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }
}
