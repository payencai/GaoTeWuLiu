package com.gaote.wuliu.ui.client.mine.address;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gaote.wuliu.R;
import com.gaote.wuliu.ui.client.mine.mvp.model.Address;

import java.util.List;

public class AddressAdapter extends BaseQuickAdapter<Address, BaseViewHolder> {
    public AddressAdapter(@Nullable List<Address> data) {
        super(R.layout.item_address, data);
    }
    private  OnCheckListener onCheckListener;

    public void setOnCheckListener(OnCheckListener onCheckListener) {
        this.onCheckListener = onCheckListener;
    }

    public interface OnCheckListener{
        void onCheck(String id);
    }
    @Override
    protected void convert(BaseViewHolder helper, Address item) {
        helper.setIsRecyclable(false);
        TextView tv_name=helper.getView(R.id.tv_name);
        TextView tv_phone=helper.getView(R.id.tv_phone);
        TextView tv_address=helper.getView(R.id.tv_address);
        CheckBox ch_default=helper.getView(R.id.ch_default);
        tv_name.setText(item.getName());
        tv_phone.setText(item.getTelephone());
        tv_address.setText(item.getAddress());
        if(item.getDefaultAddress()==1){
            ch_default.setChecked(true);
            ch_default.setText("默认地址");
            ch_default.setTextColor(Color.parseColor("#00c0df"));
        }else{
            ch_default.setChecked(false);
            ch_default.setTag("设为默认");
            ch_default.setTextColor(Color.parseColor("#333333"));
        }
        helper.addOnClickListener(R.id.ll_edit).addOnClickListener(R.id.ll_del).addOnClickListener(R.id.ch_default);
        ch_default.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(item.getDefaultAddress()==1){
                    return;
                }
                onCheckListener.onCheck(item.getId());
            }
        });
    }
}
