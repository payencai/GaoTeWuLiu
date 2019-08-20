package com.gaote.wuliu.ui.net.adapter;

import android.support.annotation.Nullable;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gaote.wuliu.R;
import com.gaote.wuliu.ui.net.mvp.model.NetConfirmOrder;
import com.gaote.wuliu.ui.net.mvp.model.NetDoorOrder;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class NetDoorOrderAdapter extends BaseQuickAdapter<NetDoorOrder, BaseViewHolder> {
    public NetDoorOrderAdapter(@Nullable List<NetDoorOrder> data) {
        super(R.layout.item_door_service, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NetDoorOrder item) {


    }
}
