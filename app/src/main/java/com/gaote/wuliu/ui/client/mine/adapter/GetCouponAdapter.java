package com.gaote.wuliu.ui.client.mine.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gaote.wuliu.R;
import com.gaote.wuliu.ui.client.mine.bean.Coupon;
import com.gaote.wuliu.ui.client.mine.bean.GetCoupon;

import java.util.List;

public class GetCouponAdapter extends BaseQuickAdapter<GetCoupon, BaseViewHolder> {
    public GetCouponAdapter(@Nullable List<GetCoupon> data) {
        super(R.layout.item_my_coupon, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, GetCoupon item) {

        TextView tv_use=helper.getView(R.id.tv_use);
        helper.addOnClickListener(R.id.tv_use);
        TextView tv_price=helper.getView(R.id.tv_price);
        tv_price.setText(item.getPrice()+"元优惠券");

        tv_use.setText("立即领取");

    }
}
