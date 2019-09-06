package com.gaote.wuliu.ui.client.mine.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gaote.wuliu.R;
import com.gaote.wuliu.ui.client.mine.bean.Coupon;

import java.util.List;

public class CouponAdapter extends BaseQuickAdapter<Coupon, BaseViewHolder> {
    public CouponAdapter(@Nullable List<Coupon> data) {
        super(R.layout.item_my_coupon, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Coupon item) {
        helper.addOnClickListener(R.id.tv_use);
        TextView tvTime=helper.getView(R.id.tv_time);
        TextView tv_use=helper.getView(R.id.tv_use);
        TextView tv_type=helper.getView(R.id.tv_type);
        TextView tv_price=helper.getView(R.id.tv_price);
        RelativeLayout rlMy=helper.getView(R.id.rl_my);
        rlMy.setVisibility(View.VISIBLE);
        tv_price.setText(item.getPrice()+"元优惠券");
        if(item.getType()==1){
            tv_type.setText("拼货订单可用");
        }else{
            tv_type.setText("物流订单可用");
        }
        if(item.getIsUse()==0){
            tv_use.setText("立即使用");
            tv_use.setEnabled(true);
        }else {
            tv_use.setText("已使用");
            tv_use.setEnabled(false);
        }
        tvTime.setText(item.getOverTime()+"到期");
    }
}
