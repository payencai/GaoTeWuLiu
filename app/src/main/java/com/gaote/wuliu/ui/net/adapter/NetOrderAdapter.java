package com.gaote.wuliu.ui.net.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gaote.wuliu.R;
import com.gaote.wuliu.ui.net.mvp.model.NetDoorOrder;
import com.gaote.wuliu.ui.net.mvp.model.NetOrder;

import java.util.List;

public class NetOrderAdapter extends BaseQuickAdapter<NetOrder.ExpressConfirmedItem, BaseViewHolder> {
    public NetOrderAdapter(@Nullable List<NetOrder.ExpressConfirmedItem> data) {
        super(R.layout.item_server_net_main, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NetOrder.ExpressConfirmedItem item) {


    }
}
