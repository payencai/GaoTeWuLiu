package com.gaote.wuliu.ui.wuliudriver.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gaote.wuliu.R;
import com.gaote.wuliu.ui.wuliudriver.mvp.model.WuliuDriverOrder;

import java.util.List;

public class WuliuDriverOrderAdapter extends BaseQuickAdapter<WuliuDriverOrder, BaseViewHolder> {
    public WuliuDriverOrderAdapter(@Nullable List<WuliuDriverOrder> data) {
        super(R.layout.item_wuliudriver_order, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WuliuDriverOrder item) {
        helper.addOnClickListener(R.id.item_btn_bottom);
        TextView tvCreateTime;
        RelativeLayout rlStatus;
        TextView tvStatus;
        TextView tvNetName;
        TextView tvDistance;
        TextView tvCargoCount;
        TextView tvAddress;
        TextView tvTime;
        RelativeLayout rlBottom;
        Button btnBottom;
        ImageView ivIcon;
        ivIcon = (ImageView) helper.getView(R.id.item_logis_driver_order_iv_icon);
        tvCreateTime = (TextView) helper.getView(R.id.item_tv_create_time);
        rlStatus = (RelativeLayout) helper.getView(R.id.item_rl_status);
        tvStatus = (TextView) helper.getView(R.id.item_tv_status);
        tvNetName = (TextView) helper.getView(R.id.item_tv_net_name);
        tvDistance = (TextView) helper.getView(R.id.item_tv_distance);
        tvCargoCount = (TextView) helper.getView(R.id.item_tv_cargo_count);
        tvAddress = (TextView) helper.getView(R.id.item_tv_address);
        tvTime = (TextView) helper.getView(R.id.item_tv_time);
        rlBottom = (RelativeLayout) helper.getView(R.id.item_rl_bottom);
        btnBottom = (Button) helper.getView(R.id.item_btn_bottom);

        tvCargoCount.setText(item.getCount() + "件");
        tvCreateTime.setText(item.getTakeorderTime());
        tvNetName.setText(item.getNetworkName());
        tvDistance.setText(String.valueOf(item.getDistanceDriver() + "km"));
        if(Integer.valueOf(item.getDriverStatus()) ==2) {
            tvTime.setText("揽货时间:" + item.getUpdateTime());
            rlStatus.setBackground(mContext.getResources().getDrawable(R.drawable.order_green));
            tvStatus.setText("待确认");
            tvAddress.setText("网点地址:" + item.getNetworkAdress());
            if(item.getIsConfirm().equals("1")) { //网点已确认
                rlBottom.setVisibility(View.VISIBLE);
                btnBottom.setText("确认收货");
            } else {
                rlBottom.setVisibility(View.GONE);
            }
        } else if(Integer.valueOf(item.getDriverStatus()) == 3) {
            rlBottom.setVisibility(View.VISIBLE);
            btnBottom.setText("确认送达");
            tvTime.setText("派送时间:" + item.getUpdateTime());
            tvAddress.setText("仓库地址:" + item.getWarehouseAdress());
            rlStatus.setBackground(mContext.getResources().getDrawable(R.drawable.order_purple));
            tvStatus.setText("待送达");
        } else {
           rlBottom.setVisibility(View.GONE);
            tvTime.setText("收货时间:" + item.getUpdateTime());
            tvAddress.setText("收货仓库:" + item.getWarehouseAdress());
            rlStatus.setBackground(mContext.getResources().getDrawable(R.drawable.order_blue));
            tvStatus.setText("已完成");
        }
    }

}
