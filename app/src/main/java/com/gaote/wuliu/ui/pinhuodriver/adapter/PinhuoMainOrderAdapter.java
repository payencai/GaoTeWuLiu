package com.gaote.wuliu.ui.pinhuodriver.adapter;

import android.support.annotation.Nullable;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gaote.wuliu.R;
import com.gaote.wuliu.ui.pinhuodriver.mvp.mdoel.PinhuoOrder;

import java.util.List;

public class PinhuoMainOrderAdapter extends BaseQuickAdapter<PinhuoOrder.BeanListBean, BaseViewHolder> {
    public PinhuoMainOrderAdapter( @Nullable List<PinhuoOrder.BeanListBean> data) {
        super(R.layout.item_nearby_pinhuo, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PinhuoOrder.BeanListBean item) {
        helper.addOnClickListener(R.id.nearby_cargo_rl_rob);
        TextView tvOrderTime;
        TextView tvMailDistrict;
        TextView tvMailProvinceCity;
        TextView tvDistance;
        TextView tvPickDistrict;
        TextView tvPickProvinceCity;
        TextView tvCargoName;
        TextView tvCargoInform;
        TextView tvCar;
        TextView tvGetCargoTime;
        TextView tvGetCargoAddr;
        tvOrderTime = (TextView) helper.getView(R.id.item_tv_order_time);
        tvMailDistrict = (TextView) helper.getView(R.id.item_tv_mail_district);
        tvMailProvinceCity = (TextView) helper.getView(R.id.item_tv_mail_province_city);
        tvDistance = (TextView) helper.getView(R.id.item_tv_distance);
        tvPickDistrict = (TextView) helper.getView(R.id.item_tv_pick_district);
        tvPickProvinceCity = (TextView) helper.getView(R.id.item_tv_pick_province_city);
        tvCargoName = (TextView) helper.getView(R.id.item_tv_cargo_name);
        tvCargoInform = (TextView) helper.getView(R.id.item_tv_cargo_inform);
        tvCar = (TextView) helper.getView(R.id.item_tv_need_car);
        tvGetCargoTime = (TextView) helper.getView(R.id.item_tv_get_cargo_time);
        tvGetCargoAddr = (TextView) helper.getView(R.id.item_tv_get_cargo_addr);
        tvOrderTime.setText(item.getOrderTime());
        tvMailDistrict.setText(item.getAddress().getArea());
        tvMailProvinceCity.setText(item.getAddress().getProvince() + " " + item.getAddress().getCity());
        tvDistance.setText(String.format("%.2f", Double.valueOf(item.getDistance())) + "km");
        tvPickDistrict.setText(item.getConsigneeArea());
        tvPickProvinceCity.setText(item.getConsigneeProvince() + " " + item.getConsigneeCity());
        tvCargoName.setText(item.getArticleName() + ":");
        tvCargoInform.setText(item.getNum() + "件 " + item.getWeight() + "kg " + item.getVolume() + "m³");
        tvCar.setText(item.getAnticipantCar());
        tvGetCargoTime.setText(item.getAnticipantTime());
        tvGetCargoAddr.setText(item.getPickupAddress());
    }
  
}
