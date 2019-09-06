package com.gaote.wuliu.ui.client.mine.order;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.gaote.wuliu.R;
import com.gaote.wuliu.ui.client.mine.bean.ClientPinhuoOrder;
import com.gaote.wuliu.ui.client.mine.mvp.model.PinhuoOrder;
import com.gaote.wuliu.ui.client.mine.mvp.model.PinhuoOrderModel;
import com.gaote.wuliu.ui.client.mine.mvp.presenter.PinhuoOrderPresenter;
import com.gaote.wuliu.ui.client.mine.mvp.view.PinhuoOrderView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PinhuoOrderDetailActivity extends AppCompatActivity implements PinhuoOrderView {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.tv_area1)
    TextView tv_area1;
    @BindView(R.id.tv_city1)
    TextView tv_city1;
    @BindView(R.id.tv_area2)
    TextView tv_area2;
    @BindView(R.id.tv_city2)
    TextView tv_city2;
    @BindView(R.id.tv_order_time)
    TextView tv_order_time;
    @BindView(R.id.tv_status)
    TextView tv_status;
    @BindView(R.id.tv_send)
    TextView tv_send;
    @BindView(R.id.tv_phone1)
    TextView tv_phone1;
    @BindView(R.id.tv_addr1)
    TextView tv_addr1;
    @BindView(R.id.tv_get)
    TextView tv_get;
    @BindView(R.id.tv_phone2)
    TextView tv_phone2;
    @BindView(R.id.tv_addr2)
    TextView tv_addr2;
    @BindView(R.id.tv_status_desc)
    TextView tv_status_desc;
    @BindView(R.id.tv_good_name)
    TextView tv_good_name;
    @BindView(R.id.tv_good_weight)
    TextView tv_good_weight;
    @BindView(R.id.tv_good_volume)
    TextView tv_good_volume;
    @BindView(R.id.tv_good_count)
    TextView tv_good_count;
    @BindView(R.id.tv_car_type)
    TextView tv_car_type;
    @BindView(R.id.tv_get_good_time)
    TextView tv_get_good_time;
    @BindView(R.id.tv_get_good_addr)
    TextView tv_get_good_addr;

    @BindView(R.id.item_btn_left)
    Button btnLeft;
    @BindView(R.id.item_btn_mid)
    Button item_btn_mid;
    @BindView(R.id.item_btn_right)
    Button btnRight;
    @BindView(R.id.item_rl_bottom)
    RelativeLayout rlBottom;
    ClientPinhuoOrder pinhuoOrder;
    PinhuoOrderPresenter pinhuoOrderPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinhuo_order_detail);
        ButterKnife.bind(this);
        pinhuoOrder = (ClientPinhuoOrder) getIntent().getSerializableExtra("data");
        initView();
    }

    private void initView() {
        tv_title.setText("订单详情");
        pinhuoOrderPresenter = new PinhuoOrderPresenter(new PinhuoOrderModel(), this);
        initData();
    }

    private void initBottomLayout() {
        int type = Integer.valueOf(pinhuoOrder.getType());
        if ("2".equals(SPUtils.getInstance().getString("role"))) {//需求方
            if (type == 1) {
                rlBottom.setVisibility(View.VISIBLE);
                btnRight.setVisibility(View.GONE);
                btnLeft.setText("取消订单");
            } else if (type == 2) {//待接货
                rlBottom.setVisibility(View.VISIBLE);
            } else if (type == 3) {//待收货
                rlBottom.setVisibility(View.VISIBLE);
                btnRight.setVisibility(View.GONE);
                btnLeft.setVisibility(View.GONE);
            } else if (type == 4) {//待签收
                rlBottom.setVisibility(View.VISIBLE);
                btnLeft.setVisibility(View.GONE);
                btnRight.setText("确认签收");
            } else {//已完成
                rlBottom.setVisibility(View.VISIBLE);
                btnRight.setVisibility(View.GONE);
                btnLeft.setVisibility(View.GONE);
            }
            item_btn_mid.setVisibility(View.VISIBLE);
        } else {
            if (type == 2) {//待接货
                rlBottom.setVisibility(View.VISIBLE);
                btnRight.setText("确认接货");
            } else if (type == 3) {//待送货
                rlBottom.setVisibility(View.VISIBLE);
                btnLeft.setVisibility(View.GONE);
                btnRight.setText("确认送达");
            } else {
                rlBottom.setVisibility(View.GONE);
            }
            item_btn_mid.setVisibility(View.GONE);
        }


    }

    private void initData() {
        if (pinhuoOrder != null) {
            initFirstLine();
            initSecondLine();
            initBottomLayout();
            initGoodInform();
        }
    }

    private void initSecondLine() {
        tv_send.setText("寄件人：" + pinhuoOrder.getConsignee());
        tv_phone1.setText(pinhuoOrder.getDriverTelephone());
        tv_addr1.setText("寄件地址：" + pinhuoOrder.getConsigneeAddress());
        tv_get.setText("收件人：" + pinhuoOrder.getConsignee());
        tv_phone2.setText(pinhuoOrder.getConsigneeTelephone());
        tv_addr2.setText("收件地址：" + pinhuoOrder.getConsigneeAddress());
    }

    private void initFirstLine() {
        tv_area1.setText(pinhuoOrder.getConsigneeArea());
        tv_city1.setText(pinhuoOrder.getConsigneeProvince() + " " + pinhuoOrder.getConsigneeCity());
        tv_area2.setText(pinhuoOrder.getConsigneeArea());
        tv_city2.setText(pinhuoOrder.getConsigneeProvince() + " " + pinhuoOrder.getConsigneeCity());
        tv_order_time.setText(pinhuoOrder.getOrderTime());
        int type = Integer.valueOf(pinhuoOrder.getType());
        switch (type) {
            case 1:
                tv_status.setText("发布中");
                tv_status_desc.setText("等待司机接单");
                break;
            case 2:
                tv_status.setText("待接货");
                tv_status_desc.setText("等待司机接货");
                break;
            case 3:
                if ("2".equals(SPUtils.getInstance().getString("role"))) {
                    tv_status.setText("待收货");
                } else {
                    tv_status.setText("待送货");
                }
                tv_status_desc.setText("运往签收地址");
                break;
            case 4:
                tv_status.setText("待签收");
                tv_status_desc.setText("等待用户签收");
                break;
            case 5:
                tv_status.setText("已完成");
                tv_status_desc.setText("该订单已签收");
                break;
            default:
                break;
        }
    }

    private String getSizeByCar(String car) {
        if (car == null) {
            return "";
        } else if (car.equals("摩托车")) {
            return "(1.9*0.8*1.1)米";
        } else if (car.equals("小轿车")) {
            return "(3.5*1.5*1.5)米";
        } else if (car.equals("三轮车")) {
            return "(3.5*1.2*1.8)米";
        } else if (car.equals("小面包车")) {
            return "(1.8*1.3*1.1)米";
        } else if (car.equals("中面包车")) {
            return "(2.7*1.4*1.2)米";
        } else if (car.equals("小货车")) {
            return "(2.7*1.5*1.7)米";
        } else if (car.equals("中货车")) {
            return "(4.2*1.8*1.8)米";
        } else {
            return "";
        }
    }

    private void initGoodInform() {
        tv_good_name.setText("货品：" + pinhuoOrder.getArticleName());
        tv_good_weight.setText("重量：" + pinhuoOrder.getWeight() + "kg");
        tv_good_volume.setText("体积：" + pinhuoOrder.getVolume() + "m³");
        tv_good_count.setText("数量：" + pinhuoOrder.getNum() + "件");
        tv_car_type.setText("所需车型：" + pinhuoOrder.getAnticipantCar() + getSizeByCar(pinhuoOrder.getAnticipantCar()));
        int type = Integer.valueOf(pinhuoOrder.getType());
        switch (type) {
            case 1:
                tv_get_good_time.setText("下单时间：" + pinhuoOrder.getOrderTime());
                tv_get_good_addr.setText("取货地址：" + pinhuoOrder.getPickupAddress());
                break;
            case 2:
                tv_get_good_time.setText("取货时间：" + pinhuoOrder.getAnticipantTime());
                tv_get_good_addr.setText("取货地址：" + pinhuoOrder.getPickupAddress());
                break;
            case 3:
                tv_get_good_time.setText("取货时间：" + pinhuoOrder.getGetTime());
                tv_get_good_addr.setText("取货地址：" + pinhuoOrder.getPickupAddress());
                break;
            case 4:
                tv_get_good_time.setText("派送时间：" + pinhuoOrder.getSendTime());
                tv_get_good_addr.setText("收货地址：" + pinhuoOrder.getConsigneeAddress());
                break;
            case 5:
                tv_get_good_time.setText("签收时间：" + pinhuoOrder.getEndTime());
                tv_get_good_addr.setText("收货地址：" + pinhuoOrder.getConsigneeAddress());
                break;
        }
    }

    private void showOrderDialog(int type, ClientPinhuoOrder pinhuoOrder) {
        final Dialog dialog = new Dialog(this, R.style.CustomDialog);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_order, null);
        TextView tvTitle = view.findViewById(R.id.tv_title);
        TextView tv_cancel = view.findViewById(R.id.tv_cancel);
        TextView tv_confirm = view.findViewById(R.id.tv_confirm);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (type == 1) {
                    if ("2".equals(SPUtils.getInstance().getString("role"))) {
                        pinhuoOrderPresenter.userOrderCancel(pinhuoOrder.getId());
                    } else {
                        pinhuoOrderPresenter.driverHandleOrder(pinhuoOrder.getId(), 4);
                    }
                } else {
                    if ("2".equals(SPUtils.getInstance().getString("role"))) {
                        pinhuoOrderPresenter.userOrderConfirm(pinhuoOrder.getId());
                    } else {
                        if (pinhuoOrder.getType().equals("2"))
                            pinhuoOrderPresenter.driverHandleOrder(pinhuoOrder.getId(), 2);
                        else if (pinhuoOrder.getType().equals("3")) {
                            pinhuoOrderPresenter.driverHandleOrder(pinhuoOrder.getId(), 3);
                        }
                    }
                }

            }
        });
        if (type == 1) {
            tvTitle.setText("确认取消吗？");
        } else if (type == 2) {
            if ("2".equals(SPUtils.getInstance().getString("role"))) {
                tvTitle.setText("确认签收吗？");
            } else {
                if (pinhuoOrder.getType().equals("2")) {
                    tvTitle.setText("确认接货吗？");
                } else if (pinhuoOrder.getType().equals("3")) {
                    tvTitle.setText("确认送达吗？");
                }
            }
        }
        dialog.setContentView(view);
        dialog.show();
        Window window = dialog.getWindow();
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = (int) (display.getWidth() * 0.7);
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);
    }

    @OnClick({R.id.item_btn_left, R.id.item_btn_mid, R.id.item_btn_right, R.id.iv_back, R.id.ll_wuliu})
    void OnClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ll_wuliu:
                intent = new Intent(PinhuoOrderDetailActivity.this, WuliuDetailActivity.class);
                intent.putExtra("data", pinhuoOrder);
                intent.putExtra("type", 3);
                startActivity(intent);
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.item_btn_left:
                showOrderDialog(1, pinhuoOrder);
                break;
            case R.id.item_btn_mid:
                intent = new Intent(PinhuoOrderDetailActivity.this, WuliuDetailActivity.class);
                intent.putExtra("data", pinhuoOrder);
                intent.putExtra("type", 3);
                startActivity(intent);
                break;
            case R.id.item_btn_right:
                if ("2".equals(SPUtils.getInstance().getString("role"))) {
                    if (pinhuoOrder.getType().equals("4")) {
                        showOrderDialog(2, pinhuoOrder);
                    } else {
                        callPhone(pinhuoOrder.getDriverTelephone());
                    }
                } else {
                    showOrderDialog(2, pinhuoOrder);
                }
                break;
        }
    }

    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dissLoading() {

    }

    @Override
    public void onUserCancel() {

    }

    @Override
    public void onUserConfirm() {

    }

    @Override
    public void onDriverHandle() {

    }

    @Override
    public void setData(List<ClientPinhuoOrder> pinhuoOrders) {

    }
}
