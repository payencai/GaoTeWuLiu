package com.gaote.wuliu.ui.client.pinhuo;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.android.arouter.core.LogisticsCenter;
import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.amap.api.maps.MapView;
import com.gaote.wuliu.MyApp;
import com.gaote.wuliu.R;
import com.gaote.wuliu.base.BaseActivity;
import com.gaote.wuliu.bean.AddrBean;
import com.gaote.wuliu.net.MyPath;
import com.gaote.wuliu.tools.CheckDoubleClick;
import com.gaote.wuliu.tools.MapUtil;
import com.gaote.wuliu.tools.MathUtils;
import com.gaote.wuliu.ui.client.mine.mvp.model.Address;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = MyPath.Pinhuo.DETAIL)
public class PinhuoDetailActivity extends BaseActivity {
    @Autowired(name = "send")
    Address addrSendBean;
    @Autowired(name = "get")
    Address addrGetBean;
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
    int isAddServer = 2;
    Double gratuity;
    int currentMoney = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinhuo_detail);
        ButterKnife.bind(this);
        initView();
    }

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

    @OnClick({R.id.rl_add, R.id.rl_send, R.id.rl_get, R.id.rl_small})
    void OnClcik(View view) {
        if (CheckDoubleClick.isFastDoubleClick()) {
            return;
        }
        Postcard postcard = ARouter.getInstance()
                .build(MyPath.Mine.Address);
        Intent intent;
        switch (view.getId()) {
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
            } else if (requestCode == 2) {
                addrGetBean = (Address) data.getSerializableExtra("data");
                tv_get.setText(addrGetBean.getName() + " " + addrGetBean.getTelephone());
                tv_get_address.setText(addrGetBean.getProvince() + addrGetBean.getCity() + addrGetBean.getArea() + addrGetBean.getAddress());
            }
        }
    }

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
                } else {
                    isAddServer = 2;
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
                    if(TextUtils.isEmpty(et_money.getEditableText().toString())){
                        gratuity = null;
                        currentMoney = 0;
                        tv_money1.setTextColor(Color.parseColor("#333333"));
                        tv_money1.setBackgroundResource(R.drawable.shape_grey_price);
                    }

                } else {
                    tv_money1.setTextColor(Color.parseColor("#00C1DE"));
                    tv_money1.setBackgroundResource(R.drawable.shape_blue_price);
                    currentMoney=1;
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
                    if(TextUtils.isEmpty(et_money.getEditableText().toString())){
                    gratuity = null;
                    currentMoney = 0;
                    tv_money2.setTextColor(Color.parseColor("#333333"));
                    tv_money2.setBackgroundResource(R.drawable.shape_grey_price);}
                } else {
                    tv_money2.setTextColor(Color.parseColor("#00C1DE"));
                    tv_money2.setBackgroundResource(R.drawable.shape_blue_price);
                    currentMoney=2;
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
                    if(TextUtils.isEmpty(et_money.getEditableText().toString())){
                    gratuity = null;
                    currentMoney = 0;
                    tv_money3.setTextColor(Color.parseColor("#333333"));
                    tv_money3.setBackgroundResource(R.drawable.shape_grey_price);}
                } else {
                    tv_money3.setTextColor(Color.parseColor("#00C1DE"));
                    tv_money3.setBackgroundResource(R.drawable.shape_blue_price);
                    currentMoney=3;
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

    private void initView() {
        initAddress();
    }
}
