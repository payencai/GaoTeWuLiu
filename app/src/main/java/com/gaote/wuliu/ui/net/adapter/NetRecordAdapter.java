package com.gaote.wuliu.ui.net.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gaote.wuliu.R;
import com.gaote.wuliu.ui.net.mvp.model.NetRecordOrder;

import java.util.List;

public class NetRecordAdapter extends BaseQuickAdapter<NetRecordOrder.ListBean, BaseViewHolder> {
    public NetRecordAdapter( @Nullable List<NetRecordOrder.ListBean> data) {
        super(R.layout.item_express_record, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, NetRecordOrder.ListBean item) {
        TextView tvNumber;
        TextView tvTakeOn;
        tvNumber = (TextView)helper.getView(R.id.item_tv_number);
        tvTakeOn = (TextView) helper.getView(R.id.item_tv_takeon);
        tvNumber.setText("运单编号:" + item.getOrderNumber());
        if(item.getStatus().equals("4")) {
            tvTakeOn.setText("确认\n收录");
            helper.addOnClickListener(R.id.item_btn_detail);
        } else if(item.getStatus().equals("5")){
          tvTakeOn.setText("已经\n收录");
        }
    }
}
