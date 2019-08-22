package com.gaote.wuliu.ui.wuliudriver.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gaote.wuliu.R;
import com.gaote.wuliu.ui.pinhuodriver.mvp.mdoel.PinhuoOrder;
import com.gaote.wuliu.ui.wuliudriver.mvp.model.WuliuNet;

import java.util.List;

public class WuliuNetAdapter extends BaseQuickAdapter<WuliuNet, BaseViewHolder> {
    public WuliuNetAdapter(@Nullable List<WuliuNet> data) {
        super(R.layout.item_nearyby_net, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WuliuNet item) {
        TextView tvName;
        TextView tvAddress;
        TextView tvDistance;
        ImageView ivIcon;
        tvName = (TextView) helper.getView(R.id.item_tv_name);
        tvAddress = (TextView) helper.getView(R.id.item_tv_address);
        tvDistance = (TextView) helper.getView(R.id.item_tv_distance);
        ivIcon = (ImageView) helper.getView(R.id.item_nearby_net_iv_icon);
        tvName.setText(item.getName());
        tvAddress.setText(item.getAdress());
        tvDistance.setText(String.valueOf(item.getDistance()) + "km");
        if(!TextUtils.isEmpty(item.getNetworkPic()))
        Glide.with(mContext).load(item.getNetworkPic()).into(ivIcon);
    }
  
}
