package com.gaote.wuliu.ui.client.mine.order;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gaote.wuliu.R;
import com.gaote.wuliu.ui.client.mine.mvp.model.PinhuoOrder;
import com.gaote.wuliu.ui.client.mine.mvp.model.WuliuOrder;
import com.xyz.step.FlowViewVertical;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WuliuDetailActivity extends AppCompatActivity {
    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.item_tv_order_time)
    TextView tvOrderTime;
    @BindView(R.id.item_rl_status)
    RelativeLayout rlStatus;
    @BindView(R.id.item_tv_status)
    TextView tvStatus;
    @BindView(R.id.item_tv_mail_district)
    TextView tvMailDistrict;
    @BindView(R.id.item_tv_mail_province_city)
    TextView tvMailProvinceCity;
    @BindView(R.id.item_tv_distance)
    TextView tvDistance;
    @BindView(R.id.item_tv_pick_district)
    TextView tvPickDistrict;
    @BindView(R.id.item_tv_pick_province_city)
    TextView tvPickProvinceCity;
    @BindView(R.id.item_tv_cargo_name)
    TextView tvCargoName;
    @BindView(R.id.item_tv_cargo_inform)
    TextView tvCargoInform;
    @BindView(R.id.item_tv_express_company)
    TextView tvExpressCompany;
    @BindView(R.id.item_tv_express_number)
    TextView tvExpressNumber;
    @BindView(R.id.lcl_order_detail_vsv)
    FlowViewVertical verticalStepView;

    int mStartType;//1为需求方物流，2为物流司机方物流，3为拼货订单物流
    WuliuOrder wuliuOrder;
    Context mContext;
  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinhuo_wuliu);
        ButterKnife.bind(this);
        mContext=this;
        initView();
    }

    private void initView() {
        initData();
        initTitleBar();
    }


    @OnClick({R.id.iv_back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            default:
                break;
        }
    }

    private void initData() {
        mStartType = getIntent().getIntExtra("type", 1);
        if (mStartType == 1) {
            setViewByDemandLogis();
        } else if(mStartType == 2) {
            //setViewByLogisDriver();
        } else if(mStartType == 3) {
            setViewByLCLOrder();
        } else if(mStartType == 4) {
            //setViewByLogisticOrder();
        }
    }
    private void initStepView(PinhuoOrder bean, int status) {
        List<String> stepList = new ArrayList<>();

        switch (status) {
            case 1:
                stepList.add("您已经发布货源订单，请等待拼货司机接单\n" + bean.getOrderTime());
                break;
            case 2:
                stepList.add("您已经发布货源订单，请等待拼货司机接单\n" + bean.getOrderTime());
                stepList.add("拼货司机已接单，正在开往货源始发地，请耐心等候\n" + bean.getGetTime());
                break;
            case 3:
                stepList.add("您已经发布货源订单，请等待拼货司机接单\n" + bean.getOrderTime());
                stepList.add("拼货司机已接单，正在开往货源始发地，请耐心等候\n" + bean.getGetTime());
                stepList.add("拼货司机已接货，正在运往目的地\n" + bean.getPickupTime());
                break;
            case 4:
                stepList.add("您已经发布货源订单，请等待拼货司机接单\n" + bean.getOrderTime());
                stepList.add("拼货司机已接单，正在开往货源始发地，请耐心等候\n" + bean.getGetTime());
                stepList.add("拼货司机已接货，正在运往目的地\n" + bean.getPickupTime());
                stepList.add("货物已送达目的地，等待签收中\n" + bean.getSendTime());
                break;
            case 5:
                stepList.add("您已经发布货源订单，请等待拼货司机接单\n" + bean.getOrderTime());
                stepList.add("拼货司机已接单，正在开往货源始发地，请耐心等候\n" + bean.getGetTime());
                stepList.add("拼货司机已接货，正在运往目的地\n" + bean.getPickupTime());
                stepList.add("货物已送达目的地，等待签收中\n" + bean.getSendTime());
                stepList.add("快件已签收，签收人：" + bean.getConsignee() + "，感谢您使用高特App，期待再次为您服务！\n" + bean.getEndTime());
                break;
            default:
                break;
        }

        verticalStepView.setProgress(stepList.size() - 1, stepList.size(), stepList.toArray(new String[0]), null);
    }
    private void setViewByLCLOrder() {
        PinhuoOrder bean = (PinhuoOrder) getIntent().getSerializableExtra("data");
        if(bean != null) {
            tvOrderTime.setText(bean.getOrderTime());
            int type = Integer.valueOf(bean.getType());
            switch (type) {
                case 1:
                    rlStatus.setBackground(mContext.getResources().getDrawable(R.drawable.order_green));
                    tvStatus.setText("待接单");
                    initStepView(bean, type);
                    break;
                case 2:
                    rlStatus.setBackground(mContext.getResources().getDrawable(R.drawable.order_purple));
                    tvStatus.setText("待接货");
                    initStepView(bean, type);
                    break;
                case 3:
                    rlStatus.setBackground(mContext.getResources().getDrawable(R.drawable.order_purple));
                    tvStatus.setText("待收货");
                    initStepView(bean, type);
                    break;
                case 4:
                    rlStatus.setBackground(mContext.getResources().getDrawable(R.drawable.order_orangle));
                    tvStatus.setText("待签收");
                    initStepView(bean, type);
                    break;
                case 5:
                    rlStatus.setBackground(mContext.getResources().getDrawable(R.drawable.order_blue));
                    tvStatus.setText("已完成");
                    initStepView(bean, type);
                    break;
                default:
                    break;
            }
            tvMailDistrict.setText(bean.getAddress().getArea());
            tvMailProvinceCity.setText(bean.getAddress().getProvince() + " " + bean.getAddress().getCity());
            tvDistance.setText(String.format("%.2f", Double.valueOf(bean.getDistance())) + "km");
            tvPickDistrict.setText(bean.getConsigneeArea());
            tvPickProvinceCity.setText(bean.getConsigneeProvince() + " " + bean.getConsigneeCity());

            tvCargoName.setText(bean.getArticleName() + ":");
            tvCargoInform.setText(bean.getNum() + "件 " + bean.getWeight() + "kg " + bean.getVolume() + "m³");

            tvExpressCompany.setText("取货时间:" + bean.getAnticipantTime());
            tvExpressNumber.setText("取货地址:" + bean.getPickupAddress());
        }
    }
    private void initStepView(WuliuOrder bean, int status) {
        List<String> stepList = new ArrayList<>();

        switch (status) {
            case 1:
                stepList.add("服务网点已录入订单，等待物流司机接单\n" + bean.getCreateTime());
                break;
            case 2:
                stepList.add("服务网点已录入订单，等待物流司机接单\n" + bean.getCreateTime());
                stepList.add("物流司机已接单，等待司机接货\n" + bean.getTakeorderTime());
                break;
            case 3:
                stepList.add("服务网点已录入订单，等待物流司机接单\n" + bean.getCreateTime());
                stepList.add("物流司机已接单，等待司机接货\n" + bean.getTakeorderTime());
                stepList.add("司机已接货，等待发往仓库\n" + bean.getPickTime());
                break;
            case 4:
                stepList.add("服务网点已录入订单，等待物流司机接单\n" + bean.getCreateTime());
                stepList.add("物流司机已接单，等待司机接货\n" + bean.getTakeorderTime());
                stepList.add("司机已接货，等待发往仓库\n" + bean.getPickTime());
                stepList.add("快件到达仓库，物流公司运输中\n" + bean.getReachwarehouseTime());

                break;
            case 5:
                stepList.add("服务网点已录入订单，等待物流司机接单\n" + bean.getCreateTime());
                stepList.add("物流司机已接单，等待司机接货\n" + bean.getTakeorderTime());
                stepList.add("司机已接货，等待发往仓库\n" + bean.getPickTime());
                stepList.add("快件到达仓库，物流公司运输中\n" + bean.getReachwarehouseTime());
                stepList.add("快件已送达，等待用户签收中\n" + bean.getArriveTime());
                break;
            case 6:
                stepList.add("服务网点已录入订单，等待物流司机接单\n" + bean.getCreateTime());
                stepList.add("物流司机已接单，等待司机接货\n" + bean.getTakeorderTime());
                stepList.add("司机已接货，等待发往仓库\n" + bean.getPickTime());
                stepList.add("快件到达仓库，物流公司运输中\n" + bean.getReachwarehouseTime());
                stepList.add("快件已送达，等待用户签收\n" + bean.getArriveTime());
                stepList.add("快件已签收，签收人：" + bean.getNameTo() + ",感谢您使用高特App，期待再次为您服务！\n" +
                        bean.getSignTime());
                break;
            default:
                break;
        }



        verticalStepView.setProgress(stepList.size() - 1, stepList.size(), stepList.toArray(new String[0]), null);

    }
    private void setViewByDemandLogis() {
        WuliuOrder bean = (WuliuOrder) getIntent().getSerializableExtra("data");
        if (bean != null) {
            tvOrderTime.setText(bean.getCreateTime());
            switch (Integer.valueOf(bean.getStatus())) {
                case 1:
                    rlStatus.setBackground(mContext.getResources().getDrawable(R.drawable.order_green));
                    tvStatus.setText("待接单");
                    initStepView(bean, 1);
                    break;
                case 2:
                    rlStatus.setBackground(mContext.getResources().getDrawable(R.drawable.order_red));
                    tvStatus.setText("待接货");
                    initStepView(bean, 2);
                    break;
                case 3:
                    rlStatus.setBackground(mContext.getResources().getDrawable(R.drawable.order_purple));
                    tvStatus.setText("待发往");
                    initStepView(bean, 3);
                    break;
                case 4:
                    rlStatus.setBackground(mContext.getResources().getDrawable(R.drawable.order_light_blue));
                    tvStatus.setText("运输中");
                    initStepView(bean, 4);
                    break;
                case 5:
                    rlStatus.setBackground(mContext.getResources().getDrawable(R.drawable.order_orangle));
                    tvStatus.setText("待签收");
                    initStepView(bean,5);
                    break;
                case 6:
                    rlStatus.setBackground(mContext.getResources().getDrawable(R.drawable.order_blue));
                    tvStatus.setText("已完成");
                    initStepView(bean,6);
                    break;
                default:
                    break;
            }
            tvMailDistrict.setText(bean.getAdressFromDistrict());
            tvMailProvinceCity.setText(bean.getAdressFromProvince() + " " + bean.getAdressFromCity());
            tvDistance.setText(String.format("%.2f", Double.valueOf(bean.getDistanceTotal())) + "km");
            tvPickDistrict.setText(bean.getAdressToDistrict());
            tvPickProvinceCity.setText(bean.getAdressToProvince() + " " + bean.getAdressToCity());

            tvCargoName.setText(bean.getGoodsName() + ":");
            tvCargoInform.setText(bean.getGoodsQuantity() + "件 " + bean.getGoodsMass() + "kg " + bean.getGoodsSize() + "m³");

            tvExpressCompany.setText("物流公司:" + bean.getLogisticsCompanyName());
            tvExpressNumber.setText("物流单号:" + bean.getOrderNumber());

        }
    }
    private void initTitleBar() {
        mTvTitle.setText("查看物流");
    }
}
