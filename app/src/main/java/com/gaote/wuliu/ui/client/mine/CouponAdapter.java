package com.gaote.wuliu.ui.client.mine;

import android.support.annotation.Nullable;

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

    }
}
