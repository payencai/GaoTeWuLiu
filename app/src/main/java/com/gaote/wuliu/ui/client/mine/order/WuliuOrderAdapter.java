package com.gaote.wuliu.ui.client.mine.order;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;

import com.chad.library.adapter.base.BaseViewHolder;
import com.gaote.wuliu.R;
import com.gaote.wuliu.ui.client.mine.mvp.model.PinhuoOrder;
import com.gaote.wuliu.ui.client.mine.mvp.model.WuliuOrder;

import java.util.List;

public class WuliuOrderAdapter extends BaseQuickAdapter<WuliuOrder, BaseViewHolder> {
    public WuliuOrderAdapter(@Nullable List<WuliuOrder> data) {
        super(R.layout.item_wuliu_order, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WuliuOrder item) {
        helper.addOnClickListener(R.id.item_btn_left).addOnClickListener(R.id.item_btn_right);
        TextView tvOrderTime = (TextView) helper.getView(R.id.item_tv_order_time);
        RelativeLayout rlStatus = (RelativeLayout) helper.getView(R.id.item_rl_status);
        TextView tvStatus = (TextView) helper.getView(R.id.item_tv_status);
        TextView tvMailDistrict = (TextView) helper.getView(R.id.item_tv_mail_district);
        TextView tvMailProvinceCity = (TextView) helper.getView(R.id.item_tv_mail_province_city);
        TextView tvDistance = (TextView) helper.getView(R.id.item_tv_distance);
        TextView tvPickDistrict = (TextView) helper.getView(R.id.item_tv_pick_district);
        TextView tvPickProvinceCity = (TextView) helper.getView(R.id.item_tv_pick_province_city);
        RelativeLayout rlLoc = (RelativeLayout) helper.getView(R.id.item_rl_loc);
        TextView tvOne = (TextView) helper.getView(R.id.item_tv_one);
        TextView tvTwo = (TextView) helper.getView(R.id.item_tv_two);
        TextView tvThree = (TextView) helper.getView(R.id.item_tv_three);
        TextView tvFour = (TextView) helper.getView(R.id.item_tv_four);
        RelativeLayout rlBottom = (RelativeLayout) helper.getView(R.id.item_rl_bottom);
        Button btnRight = (Button) helper.getView(R.id.item_btn_right);
        tvOrderTime.setText(item.getCreateTime());
        rlLoc.setVisibility(View.GONE);
        tvTwo.setText("物流公司:" + item.getLogisticsCompanyName());
        tvThree.setText("物流单号:" + item.getLogisticsCompanyId());
        tvFour.setText(item.getGoodsName() + ":" + item.getGoodsQuantity()  + "件  " + item.getGoodsMass() + "kg  " + item.getGoodsSize() + "m³");
        tvMailDistrict.setText(item.getAdressFromDistrict());
        tvMailProvinceCity.setText(item.getAdressFromProvince() + " " + item.getAdressFromCity());
        tvDistance.setText(String.format("%.2f", Double.valueOf(item.getDistanceTotal())) + "km");
        tvPickDistrict.setText(item.getAdressToDistrict());
        tvPickProvinceCity.setText(item.getAdressToProvince() + " " + item.getAdressToCity());
        if("5".equals(item.getStatus())) {
            rlBottom.setVisibility(View.VISIBLE);
            btnRight.setVisibility(View.VISIBLE);
        } else {
            rlBottom.setVisibility(View.VISIBLE);
            btnRight.setVisibility(View.GONE);
        }
        switch (Integer.parseInt(item.getStatus())) {
            case 1:
                tvOne.setText("快件录入时间:" + item.getCreateTime());
                rlStatus.setBackground(mContext.getResources().getDrawable(R.drawable.order_green));
                tvStatus.setText("待接单");
                break;
            case 2:
                tvOne.setText("服务录入网点:" + item.getNetworkName());
                rlStatus.setBackground(mContext.getResources().getDrawable(R.drawable.order_red));
                tvStatus.setText("待接货");
                break;
            case 3:
                tvOne.setText("司机接货时间:" + item.getPickTime());
                rlStatus.setBackground(mContext.getResources().getDrawable(R.drawable.order_purple));
                tvStatus.setText("待发往");
                break;
            case 4:
                tvOne.setText("收货仓库:" + item.getWarehouseName());
                rlStatus.setBackground(mContext.getResources().getDrawable(R.drawable.order_light_blue));
                tvStatus.setText("运输中");
                break;
            case 5:
                tvOne.setText("到达仓库时间:" + item.getReachwarehouseTime());
                rlStatus.setBackground(mContext.getResources().getDrawable(R.drawable.order_orangle));
                tvStatus.setText("待签收");
                break;
            case 6:
                tvOne.setText("用户签收时间:" + item.getSignTime());
                rlStatus.setBackground(mContext.getResources().getDrawable(R.drawable.order_blue));
                tvStatus.setText("已完成");
                break;
            default:
                break;
        }
    }
}
