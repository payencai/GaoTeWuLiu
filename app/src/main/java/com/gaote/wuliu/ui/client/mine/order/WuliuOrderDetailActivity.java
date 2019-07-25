package com.gaote.wuliu.ui.client.mine.order;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.gaote.wuliu.R;
import com.gaote.wuliu.ui.client.mine.mvp.model.WuliuOrder;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WuliuOrderDetailActivity extends AppCompatActivity {
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
    Button btnMid;
    @BindView(R.id.item_btn_right)
    Button btnRight;
    @BindView(R.id.item_rl_bottom)
    RelativeLayout rlBottom;
    WuliuOrder wuliuOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wuliu_order_detail);
        ButterKnife.bind(this);
        wuliuOrder= (WuliuOrder) getIntent().getSerializableExtra("data");
        initView();
    }

    private void initView() {
        initTitleBar();
        initFirstLine();
        initSecondLine();
        initThirdLine();
        initGoodInform();
        initBottomLayout();
    }

    private void initFirstLine() {
        tv_area1.setText(wuliuOrder.getAdressToDistrict());
        tv_city1.setText(wuliuOrder.getAdressToProvince() + " " + wuliuOrder.getAdressToCity());
        tv_area2.setText(wuliuOrder.getAdressFromDistrict());
        tv_city2.setText(wuliuOrder.getAdressFromProvince() + " " + wuliuOrder.getAdressFromCity());
        int type = Integer.valueOf(wuliuOrder.getStatus());
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
                tv_status.setText("待发往");
                tv_status_desc.setText("运往仓库途中");
                break;
            case 4:
                tv_status.setText("运输中");
                tv_status_desc.setText("等待物流公司取件");
                break;
            case 5:
                tv_status.setText("派送中");
                tv_status_desc.setText("等待用户签收");
                break;
            case 6:
                tv_status.setText("已签收");
                tv_status_desc.setText("快件已经签收");
                break;
            default:
                break;
        }
    }

    private void initSecondLine() {
        int type = Integer.valueOf(wuliuOrder.getStatus());
        switch (type) {
            case 1:
                tv_status_desc.setText("等待物流司机接单");
                tv_order_time.setText(wuliuOrder.getTakeorderTime());
                break;
            case 2:
                tv_status_desc.setText("等待物流司机接货");
                tv_order_time.setText(wuliuOrder.getPickTime());
                break;
            case 3:
                tv_status_desc.setText("订单正运往仓库，请耐心等候");
                tv_order_time.setText(wuliuOrder.getReachwarehouseTime());
                break;
            case 4:
                tv_status_desc.setText("订单到达仓库，等待物流公司取件");
                tv_order_time.setText(wuliuOrder.getArriveTime());
                break;
            case 5:
                tv_status_desc.setText("订单正在派送中");
                tv_order_time.setText(wuliuOrder.getConfirmTime());
                break;
            case 6:
                tv_status_desc.setText("订单已签收，签收人：" + wuliuOrder.getDemanderId() + " " + wuliuOrder.getDemanderTelnum());
                tv_order_time.setText(wuliuOrder.getSignTime());
                break;
            default:
                break;
        }
    }

    private void initThirdLine() {
        tv_send.setText("寄件人：" + wuliuOrder.getNameFrom() + " " + wuliuOrder.getDemanderTelnum());
        tv_addr1.setText("寄件地址：" + wuliuOrder.getAdressFrom());
        tv_phone1.setText(wuliuOrder.getDemanderTelnum());
        tv_get.setText("收件人：" + wuliuOrder.getNameTo());
        tv_addr2.setText("收件地址：" + wuliuOrder.getAdressTo());
        tv_phone2.setText(wuliuOrder.getReceiverTelnum());


    }

    private void initGoodInform() {
        tv_good_name.setText("货品：" + wuliuOrder.getGoodsName());
        tv_good_weight.setText("重量：" + wuliuOrder.getGoodsMass() + "kg");
        tv_good_volume.setText("体积：" + wuliuOrder.getGoodsSize() + "m³");
        tv_good_count.setText("运费（快递）：￥" + (wuliuOrder.getFreight() == null ? "0.00" : wuliuOrder.getFreight()));
        tv_car_type.setText("物流公司：" + wuliuOrder.getLogisticsCompanyName());
        tv_get_good_time.setText("物流单号：" + wuliuOrder.getOrderNumber());
        tv_get_good_addr.setText("发布时间：" + wuliuOrder.getCreateTime());
    }

    private void initBottomLayout() {
        int type = Integer.valueOf(wuliuOrder.getStatus());
        if("2".equals(SPUtils.getInstance().getString("role"))) {//需求方
            if(type == 1) {
                rlBottom.setVisibility(View.VISIBLE);
                btnRight.setVisibility(View.GONE);
                btnLeft.setText("取消订单");
            }
            else if (type == 2) {//待接货
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
            btnMid.setVisibility(View.VISIBLE);
        } else  {
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
            btnMid.setVisibility(View.GONE);
        }

    
    }
    private void initTitleBar() {
        tv_title.setText("订单详情");
    }
    private void diallPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }
    @OnClick({R.id.item_btn_left, R.id.item_btn_mid, R.id.item_btn_right, R.id.iv_back, R.id.ll_wuliu})
    void OnClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.ll_wuliu:
                intent = new Intent(WuliuOrderDetailActivity.this, WuliuDetailActivity.class);
                intent.putExtra("data", wuliuOrder);
                intent.putExtra("type", 3);
                startActivity(intent);
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.item_btn_left:
                //showOrderDialog(1, pinhuoOrder);
                break;
            case R.id.item_btn_mid:
                intent = new Intent(WuliuOrderDetailActivity.this, WuliuDetailActivity.class);
                intent.putExtra("data", wuliuOrder);
                intent.putExtra("type", 3);
                startActivity(intent);
                break;
            case R.id.item_btn_right:
//                if ("2".equals(SPUtils.getInstance().getString("role"))) {
//                    if (pinhuoOrder.getType().equals("4")) {
//                        showOrderDialog(2, pinhuoOrder);
//                    } else {
//                        callPhone(pinhuoOrder.getDriverTelephone());
//                    }
//                } else {
//                    showOrderDialog(2, pinhuoOrder);
//                }
                break;
        }
    }

}
