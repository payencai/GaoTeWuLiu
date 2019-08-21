package com.gaote.wuliu.ui.net.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gaote.wuliu.R;
import com.gaote.wuliu.constant.MyConstant;
import com.gaote.wuliu.ui.net.mvp.model.NetDoorOrder;
import com.gaote.wuliu.ui.net.mvp.model.NetOrder;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NetOrderAdapter extends BaseQuickAdapter<NetOrder.ListBean, BaseViewHolder> {
    public NetOrderAdapter(@Nullable List<NetOrder.ListBean> data) {
        super(R.layout.item_server_net_main, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NetOrder.ListBean item) {
        TextView tvDriverName;
        TextView tvCount;
        TextView tvTime;
        CircleImageView civHead;
        civHead = (CircleImageView) helper.getView(R.id.item_net_main_iv_header);
        tvDriverName = (TextView) helper.getView(R.id.item_tv_driver_name);
        tvCount = (TextView) helper.getView(R.id.item_tv_count);
        tvTime = (TextView) helper.getView(R.id.item_tv_time);
        if(TextUtils.isEmpty(item.getPortraitUrl())){
            Glide.with(mContext).load(MyConstant.DEFAULT_HEAD).into(civHead);
        }else{
            Glide.with(mContext).load(item.getPortraitUrl()).into(civHead);
        }
       tvDriverName.setText(item.getDriverName());
       tvCount.setText("揽货件数:" + item.getCount() + "件");
       tvTime.setText("确认时间:" + item.getConfirmTime());
    }
}
