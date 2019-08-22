package com.gaote.wuliu.ui.wuliudriver.adapter;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gaote.wuliu.R;
import com.gaote.wuliu.ui.wuliudriver.mvp.model.SelectOrderEvent;
import com.gaote.wuliu.ui.wuliudriver.mvp.model.WuliuNet;
import com.gaote.wuliu.ui.wuliudriver.mvp.model.WuliuNetOrder;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

public class WuliuNetOrderAdapter extends BaseQuickAdapter<WuliuNetOrder.ListBean, BaseViewHolder> {

    public WuliuNetOrderAdapter(@Nullable List<WuliuNetOrder.ListBean> data) {
        super(R.layout.item_net_detail, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, WuliuNetOrder.ListBean item) {
        CheckBox cbSelect;
        TextView tvNumber;
        TextView tvMailDistrict;
        TextView tvMailProvinceCity;
        TextView tvDistance;
        TextView tvPickDistrict;
        TextView tvPickProvinceCity;
        TextView tvCargoInform;
        TextView mailNamePhone;
        TextView mailAddress;
        TextView pickNamePhone;
        TextView pickAddress;
        cbSelect = (CheckBox) helper.getView(R.id.item_cb_select);
        tvNumber = (TextView) helper.getView(R.id.item_tv_number);
        tvMailDistrict = (TextView) helper.getView(R.id.item_tv_mail_district);
        tvMailProvinceCity = (TextView) helper.getView(R.id.item_tv_mail_province_city);
        tvDistance = (TextView) helper.getView(R.id.item_tv_distance);
        tvPickDistrict = (TextView) helper.getView(R.id.item_tv_pick_district);
        tvPickProvinceCity = (TextView) helper.getView(R.id.item_tv_pick_province_city);
        tvCargoInform = (TextView) helper.getView(R.id.item_tv_cargo_inform);
        mailNamePhone = (TextView) helper.getView(R.id.item_tv_mail_name_phone);
        mailAddress = (TextView) helper.getView(R.id.item_tv_mail_address);
        pickNamePhone = (TextView) helper.getView(R.id.item_tv_pick_name_phone);
        pickAddress = (TextView) helper.getView(R.id.item_tv_pick_address);
        tvCargoInform.setText("货物详情    " + item.getGoodsName() + "   " + item.getGoodsQuantity() +
                "件  " + item.getGoodsMass() + "kg  " + item.getGoodsSize() + "m³");
        pickNamePhone.setText(item.getNameTo() + "  " + item.getReceiverTelnum());
        pickAddress.setText(item.getAdressTo());
        mailNamePhone.setText(item.getNameFrom() + "  " + item.getDemanderTelnum());
        mailAddress.setText(item.getAdressFrom());
        tvMailDistrict.setText(item.getAdressFromDistrict());
        tvMailProvinceCity.setText(item.getAdressFromProvince() + " " + item.getAdressFromCity());
        tvDistance.setText(String.format("%.2f", Double.valueOf(item.getDistanceTotal())) + "km");
        tvPickDistrict.setText(item.getAdressToDistrict());
        tvPickProvinceCity.setText(item.getAdressToProvince() + " " + item.getAdressToCity());
        tvNumber.setText("运单编号:" + item.getOrderNumber());
        cbSelect.setOnCheckedChangeListener(null);
        cbSelect.setChecked(item.isCheck());
        cbSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    EventBus.getDefault().post(new SelectOrderEvent(true, item.getId(),helper.getAdapterPosition()));
                } else {
                    EventBus.getDefault().post(new SelectOrderEvent(false, item.getId(),helper.getAdapterPosition()));
                }

            }
        });
    }
  
}
