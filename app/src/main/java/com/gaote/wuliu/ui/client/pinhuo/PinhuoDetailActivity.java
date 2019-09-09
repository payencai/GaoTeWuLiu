package com.gaote.wuliu.ui.client.pinhuo;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.core.LogisticsCenter;
import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.amap.api.maps.MapView;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gaote.wuliu.MyApp;
import com.gaote.wuliu.R;
import com.gaote.wuliu.alipay.PayReponse;
import com.gaote.wuliu.alipay.PaymentHelper;
import com.gaote.wuliu.alipay.WxPayConfig;
import com.gaote.wuliu.base.BaseActivity;
import com.gaote.wuliu.bean.AddrBean;
import com.gaote.wuliu.bean.Result;
import com.gaote.wuliu.net.Api;
import com.gaote.wuliu.net.MyPath;
import com.gaote.wuliu.net.NetUtils;
import com.gaote.wuliu.net.OnMessageReceived;
import com.gaote.wuliu.tools.CheckDoubleClick;
import com.gaote.wuliu.tools.GsonUtil;
import com.gaote.wuliu.tools.MapUtil;
import com.gaote.wuliu.tools.MathUtils;
import com.gaote.wuliu.tools.NumberUtils;
import com.gaote.wuliu.ui.client.mine.bean.Coupon;
import com.gaote.wuliu.ui.client.mine.mvp.model.Address;
import com.gyf.immersionbar.ImmersionBar;
import com.lzy.okgo.model.HttpParams;
import com.xgr.easypay.EasyPay;
import com.xgr.easypay.alipay.AliPay;
import com.xgr.easypay.alipay.AlipayInfoImpli;
import com.xgr.easypay.callback.IPayCallback;
import com.xgr.easypay.wxpay.WXPay;
import com.xgr.easypay.wxpay.WXPayInfoImpli;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


@Route(path = MyPath.Pinhuo.DETAIL)
public class PinhuoDetailActivity extends AppCompatActivity {
    @Autowired(name = "send")
    Address addrSendBean;
    @Autowired(name = "get")
    Address addrGetBean;
    @Autowired (name = "coupon")
    Coupon coupon;
    @BindView(R.id.tv_get)
    TextView tv_get;
    @BindView(R.id.tv_send)
    TextView tv_send;
    @BindView(R.id.tv_send_address)
    TextView tv_send_address;
    @BindView(R.id.tv_get_address)
    TextView tv_get_address;
    @BindView(R.id.tv_money)
    TextView tv_money;
    @BindView(R.id.tv_price)
    TextView tv_price;
    @BindView(R.id.tv_need)
    TextView tv_need;
    @BindView(R.id.tv_item1)
    TextView tv_item1;
    @BindView(R.id.tv_item2)
    TextView tv_item2;
    @BindView(R.id.et_remarks)
    EditText et_remarks;
    @BindView(R.id.et_weight)
    EditText et_weight;
    @BindView(R.id.et_volume)
    EditText et_volume;
    @BindView(R.id.et_num)
    EditText et_num;
    @BindView(R.id.tv_coupon)
    TextView tv_coupon;
    @BindView(R.id.et_name)
    EditText et_name;
    String orderId;
    int isAddServer = 2;
    Double gratuity;
    int currentMoney = 1;
    int payType = 1;
    int isCarpooling=1;
    Double total;
    String price;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinhuo_detail);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        ImmersionBar.with(this).init();
        initView();
    }
    //初始化地址
    private void initAddress() {
        if (addrSendBean != null) {
            tv_send.setText(addrSendBean.getName() + " " + addrSendBean.getTelephone());
            tv_send_address.setText(addrSendBean.getProvince() + addrSendBean.getCity() + addrSendBean.getArea() + addrSendBean.getAddress());
        }
        if (addrGetBean != null) {
            tv_get.setText(addrGetBean.getName() + " " + addrGetBean.getTelephone());
            tv_get_address.setText(addrGetBean.getProvince() + addrGetBean.getCity() + addrGetBean.getArea() + addrGetBean.getAddress());
        }
    }

    @OnClick({R.id.iv_back,R.id.rl_add, R.id.rl_send, R.id.rl_get, R.id.rl_small, R.id.btn_submit,R.id.rl_coupon,R.id.tv_item1,R.id.tv_item2})
    void OnClcik(View view) {
        if (CheckDoubleClick.isFastDoubleClick()) {
            return;
        }
        Postcard postcard = ARouter.getInstance()
                .build(MyPath.Mine.Address);
        Intent intent;
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_item2:
                tv_item1.setBackgroundResource(R.drawable.shape_white_left);
                tv_item2.setBackgroundResource(R.drawable.shape_grey_right);
                isCarpooling=2;
                break;
            case R.id.tv_item1:
                tv_item2.setBackgroundResource(R.drawable.shape_white_right);
                tv_item1.setBackgroundResource(R.drawable.shape_grey_left);
                isCarpooling=1;
                break;
            case R.id.rl_coupon:
                if(MyApp.isLogin)
                   ARouter.getInstance().build(MyPath.Mine.MyCoupon).withInt("orderType",1).navigation(this,3);
                else
                    ARouter.getInstance().build(MyPath.Mine.Login).navigation();
                break;
            case R.id.btn_submit:
                if(MyApp.isLogin){
                    if(checkInput())
                       getOrderId();
                } else{
                    ARouter.getInstance().build(MyPath.Mine.Login).navigation();
                }
                break;
            case R.id.rl_small:
                showSmallDialog();
                break;
            case R.id.rl_add:
                showAddValueDialog();
                break;
            case R.id.rl_send:
                if (!MyApp.isLogin) {
                    ARouter.getInstance().build(MyPath.Mine.Login).navigation();
                    return;
                }
                LogisticsCenter.completion(postcard);
                intent = new Intent(this, postcard.getDestination());
                intent.putExtras(postcard.getExtras());
                startActivityForResult(intent, 1);
                break;
            case R.id.rl_get:
                if (!MyApp.isLogin) {
                    ARouter.getInstance().build(MyPath.Mine.Login).navigation();
                    return;
                }
                LogisticsCenter.completion(postcard);
                intent = new Intent(this, postcard.getDestination());
                intent.putExtras(postcard.getExtras());
                startActivityForResult(intent, 2);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            if (requestCode == 1) {
                addrSendBean = (Address) data.getSerializableExtra("data");
                tv_send.setText(addrSendBean.getName() + " " + addrSendBean.getTelephone());
                tv_send_address.setText(addrSendBean.getProvince() + addrSendBean.getCity() + addrSendBean.getArea() + addrSendBean.getAddress());
                calPrice();
            } else if (requestCode == 2) {
                addrGetBean = (Address) data.getSerializableExtra("data");
                tv_get.setText(addrGetBean.getName() + " " + addrGetBean.getTelephone());
                tv_get_address.setText(addrGetBean.getProvince() + addrGetBean.getCity() + addrGetBean.getArea() + addrGetBean.getAddress());
                calPrice();
            }else if(requestCode==3){
                coupon= (Coupon) data.getSerializableExtra("data");
                if(coupon!=null){
                    tv_coupon.setText(coupon.getName());
                }
                calPrice();
            }
        }
    }
    //增值服务弹窗
    private void showAddValueDialog() {
        final Dialog dialog = new Dialog(this, R.style.BottomDialog);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_value, null);
        TextView tv_confirm = view.findViewById(R.id.tv_confirm);
        CheckBox checkBox = view.findViewById(R.id.checkbox);
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (checkBox.isChecked()) {
                    isAddServer = 1;
                    tv_need.setText("需要搬运");
                } else {
                    isAddServer = 2;
                    tv_need.setText("");
                }
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
    //小费弹窗
    private void showSmallDialog() {
        final Dialog dialog = new Dialog(this, R.style.BottomDialog);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_small_money, null);
        TextView tv_confirm = view.findViewById(R.id.tv_confirm);
        EditText et_money = view.findViewById(R.id.et_money);
        TextView tv_money1 = view.findViewById(R.id.tv_money1);
        TextView tv_money2 = view.findViewById(R.id.tv_money2);
        TextView tv_money3 = view.findViewById(R.id.tv_money3);

        et_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString())) {
                    if (MathUtils.isNumber(s.toString())) {
                        gratuity = Double.parseDouble(s.toString());
                        tv_money1.setTextColor(Color.parseColor("#333333"));
                        tv_money1.setBackgroundResource(R.drawable.shape_grey_price);
                        tv_money2.setTextColor(Color.parseColor("#333333"));
                        tv_money2.setBackgroundResource(R.drawable.shape_grey_price);
                        tv_money3.setTextColor(Color.parseColor("#333333"));
                        tv_money3.setBackgroundResource(R.drawable.shape_grey_price);
                    }
                } else {
                    if (currentMoney == 1) {
                        tv_money1.setTextColor(Color.parseColor("#00C1DE"));
                        tv_money1.setBackgroundResource(R.drawable.shape_blue_price);
                    } else if (currentMoney == 2) {
                        tv_money2.setTextColor(Color.parseColor("#00C1DE"));
                        tv_money2.setBackgroundResource(R.drawable.shape_blue_price);
                    } else if (currentMoney == 3) {
                        tv_money3.setTextColor(Color.parseColor("#00C1DE"));
                        tv_money3.setBackgroundResource(R.drawable.shape_blue_price);
                    }
                }
            }
        });


        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (TextUtils.isEmpty(et_money.getEditableText().toString())) {
                    if (currentMoney == 1) {
                        gratuity = 10.0;
                    } else if (currentMoney == 2) {
                        gratuity = 20.0;
                    } else if (currentMoney == 3) {
                        gratuity = 30.0;
                    } else {
                        gratuity = null;
                    }
                } else {
                    gratuity = Double.valueOf(et_money.getEditableText().toString());
                }
                if (gratuity != null)
                    tv_money.setText("￥" + gratuity);
                else {
                    tv_money.setText("");
                }
            }
        });
        tv_money1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tv_money2.setTextColor(Color.parseColor("#333333"));
                tv_money2.setBackgroundResource(R.drawable.shape_grey_price);
                tv_money3.setTextColor(Color.parseColor("#333333"));
                tv_money3.setBackgroundResource(R.drawable.shape_grey_price);
                if (currentMoney == 1) {
                    if (TextUtils.isEmpty(et_money.getEditableText().toString())) {
                        gratuity = null;
                        currentMoney = 0;
                        tv_money1.setTextColor(Color.parseColor("#333333"));
                        tv_money1.setBackgroundResource(R.drawable.shape_grey_price);
                    }

                } else {
                    tv_money1.setTextColor(Color.parseColor("#00C1DE"));
                    tv_money1.setBackgroundResource(R.drawable.shape_blue_price);
                    currentMoney = 1;
                }
                et_money.setText("");
            }
        });
        tv_money2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tv_money1.setTextColor(Color.parseColor("#333333"));
                tv_money1.setBackgroundResource(R.drawable.shape_grey_price);
                tv_money3.setTextColor(Color.parseColor("#333333"));
                tv_money3.setBackgroundResource(R.drawable.shape_grey_price);
                if (currentMoney == 2) {
                    if (TextUtils.isEmpty(et_money.getEditableText().toString())) {
                        gratuity = null;
                        currentMoney = 0;
                        tv_money2.setTextColor(Color.parseColor("#333333"));
                        tv_money2.setBackgroundResource(R.drawable.shape_grey_price);
                    }
                } else {
                    tv_money2.setTextColor(Color.parseColor("#00C1DE"));
                    tv_money2.setBackgroundResource(R.drawable.shape_blue_price);
                    currentMoney = 2;
                }
                et_money.setText("");
            }
        });
        tv_money3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                tv_money1.setTextColor(Color.parseColor("#333333"));
                tv_money1.setBackgroundResource(R.drawable.shape_grey_price);
                tv_money2.setTextColor(Color.parseColor("#333333"));
                tv_money2.setBackgroundResource(R.drawable.shape_grey_price);
                if (currentMoney == 3) {
                    if (TextUtils.isEmpty(et_money.getEditableText().toString())) {
                        gratuity = null;
                        currentMoney = 0;
                        tv_money3.setTextColor(Color.parseColor("#333333"));
                        tv_money3.setBackgroundResource(R.drawable.shape_grey_price);
                    }
                } else {
                    tv_money3.setTextColor(Color.parseColor("#00C1DE"));
                    tv_money3.setBackgroundResource(R.drawable.shape_blue_price);
                    currentMoney = 3;
                }
                et_money.setText("");
            }
        });
        view.findViewById(R.id.tv_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);
    }
    //支付方式弹窗
    private void showPayMethodDialog() {
        payType = 1;
        final Dialog dialog = new Dialog(this, R.style.BottomDialog);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_pay_method, null);
        TextView tv_confirm = view.findViewById(R.id.tv_confirm);
        TextView tv_total = view.findViewById(R.id.tv_total);
        RelativeLayout rlAliPay = view.findViewById(R.id.rl_alipay);
        RelativeLayout rlWechat = view.findViewById(R.id.rl_wechat);
        ImageView iv_alipay = view.findViewById(R.id.iv_alipay);
        ImageView iv_wechat = view.findViewById(R.id.iv_wechat);
        tv_total.setText(price);
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
                if (payType == 1) {
                     getAliPayParams();
                } else {
                    getWechatPayParams();
                }
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
        ImageView iv_del=view.findViewById(R.id.iv_del);
        iv_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        view.findViewById(R.id.tv_see).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ARouter.getInstance().build(MyPath.Mine.MyPinhuoOrder).navigation();
            }
        });
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
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
        calPrice();
        HttpParams httpParams = new HttpParams();
        httpParams.put("consignee", addrGetBean.getName());
        httpParams.put("consigneeTelephone", addrGetBean.getTelephone());
        httpParams.put("consigneeArea", addrGetBean.getArea());
        httpParams.put("consigneeProvince", addrGetBean.getProvince());
        httpParams.put("consigneeCity", addrGetBean.getCity());
        httpParams.put("consigneeAddress", addrGetBean.getAddress());

        httpParams.put("nameFrom", addrSendBean.getName());
        httpParams.put("demanderTelnum", addrSendBean.getTelephone());
        httpParams.put("adressFromDistrict", addrSendBean.getArea());
        httpParams.put("adressFromProvince", addrSendBean.getProvince());
        httpParams.put("adressFromCity", addrSendBean.getCity());
        httpParams.put("adressFrom", addrSendBean.getAddress());

        httpParams.put("consigneeLongitude", addrSendBean.getLng1());
        httpParams.put("consigneeLatitude", addrSendBean.getLat1());
        httpParams.put("adressFromLongitude", addrGetBean.getLng1());
        httpParams.put("adressFromLatitude", addrGetBean.getLat1());

        httpParams.put("num", Integer.parseInt(et_num.getEditableText().toString()));
        httpParams.put("weight", Double.parseDouble(et_weight.getEditableText().toString()));
        httpParams.put("volume", Double.parseDouble(et_volume.getEditableText().toString()));
        httpParams.put("articleName", et_name.getEditableText().toString());

        httpParams.put("remarks", et_remarks.getEditableText().toString());
        httpParams.put("isAddServer", isAddServer);
        httpParams.put("isCarpooling", isCarpooling);
        httpParams.put("gratuity", gratuity);
        httpParams.put("discountPrice", coupon.getPrice());
        httpParams.put("realPrice", total);
        NetUtils.getInstance().post(Api.BASE_URL + Api.Pinhuo.addPdriverOrder, MyApp.token, httpParams, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                LogUtils.e(response);
                Result<String> result = GsonUtil.fromJsonObject(response, String.class);
                if (result.getResultCode() == 0) {
                    orderId = result.getData();
                    showPayMethodDialog();
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
        NetUtils.getInstance().post(Api.BASE_URL + Api.Pay.PinhuoWechat, MyApp.token, httpParams, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                LogUtils.e(response);
                Result<PayReponse> result = GsonUtil.fromJsonObject(response, PayReponse.class);
                if (result.getResultCode() == 0) {

                    PayReponse payReponse = result.getData();
                    WXPay wxPay = WXPay.getInstance(PinhuoDetailActivity.this, WxPayConfig.APP_ID);
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
                    EasyPay.pay(wxPay, PinhuoDetailActivity.this, wxPayInfoImpli, new IPayCallback() {
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
        NetUtils.getInstance().post(Api.BASE_URL + Api.Pay.PinhuoAlipay, MyApp.token, httpParams, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                LogUtils.e(response);
                Result<String> result = GsonUtil.fromJsonObject(response, String.class);
                if (result.getResultCode() == 0) {


                            //实例化支付宝支付策略
                            AliPay aliPay = new AliPay();
                            //构造支付宝订单实体。一般都是由服务端直接返回。
                            AlipayInfoImpli alipayInfoImpli = new AlipayInfoImpli();
                            alipayInfoImpli.setOrderInfo(result.getData());
                            //策略场景类调起支付方法开始支付，以及接收回调。
                            EasyPay.pay(aliPay, PinhuoDetailActivity.this, alipayInfoImpli, new IPayCallback() {
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
    private void initView() {
        initAddress();
        if(coupon!=null){
            tv_coupon.setText(coupon.getName());
        }

        et_weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String value=s.toString();
                if(!TextUtils.isEmpty(value)&&MathUtils.isNumber(value)){
                    if(isCarpooling==1){
                        calPrice();
                    }
                }else{
                    if(isCarpooling==1)
                        tv_price.setText("0");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        et_volume.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String value=s.toString();
                if(!TextUtils.isEmpty(value)&&MathUtils.isNumber(value)){
                    if(isCarpooling==1){
                        calPrice();
                    }
                }else{
                    if(isCarpooling==1)
                        tv_price.setText("0");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
    private void calPrice(){
        if(addrSendBean==null){
            return;
        }
        if(addrGetBean==null){
            return;
        }
        String url;
        HttpParams httpParams=new HttpParams();
        httpParams.put("consigneeLongitude",addrSendBean.getLng1());
        httpParams.put("consigneeLatitude",addrSendBean.getLat1());
        httpParams.put("adressFromLongitude",addrGetBean.getLng1());
        httpParams.put("adressFromLatitude",addrGetBean.getLat1());
        if(isCarpooling==1){
            String weight=et_weight.getEditableText().toString();
            String volume=et_volume.getEditableText().toString();
            if(TextUtils.isEmpty(weight)){
                return;
            }
            if(TextUtils.isEmpty(volume)){
                return;
            }
            url=Api.BASE_URL+Api.Pinhuo.getCarpoolingFreight;
            httpParams.put("weight",Double.parseDouble(weight));
            httpParams.put("volume",Double.parseDouble(volume));
        }else{
            url=Api.BASE_URL+Api.Pinhuo.getCharteredCarFreight;
        }
        NetUtils.getInstance().get( MyApp.token,url, httpParams, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                LogUtils.e(response);
                Result<String> result=GsonUtil.fromJsonObject(response,String.class);
                if(result.getResultCode()==0){
                    total= Double.parseDouble(result.getData());

                    if(coupon!=null){
                        price= NumberUtils.substract(total+"",coupon.getPrice()+"",2);
                        tv_price.setText(price+"");
                    }else{
                        tv_price.setText(total+"");
                    }

                }else{
                    ToastUtils.showShort(result.getMessage());
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }
    private boolean checkInput(){
        boolean isOk=true;
        if(TextUtils.isEmpty(et_name.getEditableText().toString())){
            ToastUtils.showShort("请输入货物名称");
            isOk=false;
            return isOk;
        }
        if(TextUtils.isEmpty(et_weight.getEditableText().toString())){
            ToastUtils.showShort("请输入重量");
            isOk=false;
            return isOk;
        }
        if(TextUtils.isEmpty(et_volume.getEditableText().toString())){
            ToastUtils.showShort("请输入体积");
            isOk=false;
            return isOk;
        }
        if(TextUtils.isEmpty(et_num.getEditableText().toString())){
            ToastUtils.showShort("请输入数量");
            isOk=false;
            return isOk;
        }
        if(addrGetBean==null){
            ToastUtils.showShort("请选择地址");
            isOk=false;
            return isOk;
        }
        if(addrSendBean==null){
            ToastUtils.showShort("请选择地址");
            isOk=false;
            return isOk;
        }
        return isOk;
    }

}
