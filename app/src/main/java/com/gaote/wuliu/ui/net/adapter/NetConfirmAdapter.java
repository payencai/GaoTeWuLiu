package com.gaote.wuliu.ui.net.adapter;

import android.support.annotation.Nullable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gaote.wuliu.R;
import com.gaote.wuliu.ui.net.mvp.model.NetConfirmOrder;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NetConfirmAdapter extends BaseQuickAdapter<NetConfirmOrder.ListBean, BaseViewHolder> {
    public NetConfirmAdapter(@Nullable List<NetConfirmOrder.ListBean> data) {
        super(R.layout.item_express_to_confirm, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NetConfirmOrder.ListBean item) {
        TextView tvDriverName;
        TextView tvCount;
        TextView tvTime;
        RelativeLayout rlConfirm;
        CircleImageView civHeader;
        civHeader = (CircleImageView) helper.getView(R.id.item_express_to_confrim_iv_header);
        tvDriverName = (TextView) helper.getView(R.id.item_tv_driver_name);
        tvCount = (TextView) helper.getView(R.id.item_tv_count);
        tvTime = (TextView) helper.getView(R.id.item_tv_time);
        helper.addOnClickListener(R.id.item_rl_confirm);
        tvDriverName.setText(item.getDriverName());
        tvCount.setText("揽货件数:" + item.getCount() + "件");
        tvTime.setText("揽货时间:" +item.getTakeorderTime());
        Glide.with(mContext).load(item.getPortraitUrl()).into(civHeader);

    }
}
