package com.gaote.wuliu.ui.client.mine.order;

import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.SPUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gaote.wuliu.R;
import com.gaote.wuliu.ui.client.mine.mvp.model.PinhuoOrder;

import java.util.List;

public class PinhuoOrderAdapter extends BaseQuickAdapter<PinhuoOrder, BaseViewHolder> {
    public PinhuoOrderAdapter(@Nullable List<PinhuoOrder> data) {
        super(R.layout.item_pinhuo_order, data);
    }

    @Override
    protected void convert( BaseViewHolder helper, PinhuoOrder item) {
        RelativeLayout rl_bottom=helper.getView(R.id.item_rl_bottom);
        TextView tv_status=helper.getView(R.id.tv_status);
        TextView tv_time=helper.getView(R.id.tv_time);
        TextView tv_gettime=helper.getView(R.id.tv_gettime);
        TextView tv_distance=helper.getView(R.id.tv_distance);
        TextView tv_name=helper.getView(R.id.tv_name);
        TextView tv_car=helper.getView(R.id.tv_car);
        TextView tv_address=helper.getView(R.id.tv_address);
        TextView tv_area1=helper.getView(R.id.tv_area1);
        TextView tv_city1=helper.getView(R.id.tv_city1);
        TextView tv_area2=helper.getView(R.id.tv_area2);
        TextView tv_city2=helper.getView(R.id.tv_city2);
        Button btn_left=helper.getView(R.id.item_btn_left);
        Button btn_mid=helper.getView(R.id.item_btn_mid);
        Button btn_right=helper.getView(R.id.item_btn_right);
        helper.addOnClickListener(R.id.item_btn_right).addOnClickListener(R.id.item_btn_mid).addOnClickListener(R.id.item_btn_left);
        tv_time.setText(item.getOrderTime());
        tv_area1.setText(item.getAddress().getArea());
        tv_city1.setText(item.getAddress().getProvince() + " " + item.getAddress().getCity());
        tv_distance.setText(String.format("%.2f", Double.valueOf(item.getDistance())) + "km");
        tv_area2.setText(item.getConsigneeArea());
        tv_city2.setText(item.getConsigneeProvince() + " " + item.getConsigneeCity());
        tv_name.setText(item.getArticleName() + ":");
        tv_car.setText(item.getNum() + "件 " + item.getWeight() + "kg " + item.getVolume() + "m³");
        tv_gettime.setText(item.getAnticipantTime());
        tv_address.setText(item.getPickupAddress());
        setStatus(tv_status,item);

        int type = Integer.valueOf(item.getType());
        if ("2".equals(SPUtils.getInstance().getString("role"))) {//需求方
            if (type == 1) {
                rl_bottom.setVisibility(View.VISIBLE);
                btn_right.setVisibility(View.GONE);
                btn_left.setText("取消订单");
            } else if (type == 2) {//待接货
               rl_bottom.setVisibility(View.VISIBLE);
            } else if (type ==3) {//待收货
               rl_bottom.setVisibility(View.VISIBLE);
                btn_right.setVisibility(View.GONE);
                btn_left.setVisibility(View.GONE);
            } else if (type == 4) {//待签收
               rl_bottom.setVisibility(View.VISIBLE);
                btn_left.setVisibility(View.GONE);
                btn_right.setText("确认签收");
            } else if (type == 5){//已完成
               rl_bottom.setVisibility(View.VISIBLE);
                btn_right.setVisibility(View.GONE);
                btn_left.setVisibility(View.GONE);
            }else{
                //退款
            }
            btn_mid.setVisibility(View.VISIBLE);
        } else {
            if (type == 2) {//待接货
               rl_bottom.setVisibility(View.VISIBLE);
                btn_right.setText("确认接货");
            } else if (type == 3) {//待送货
               rl_bottom.setVisibility(View.VISIBLE);
                btn_left.setVisibility(View.GONE);
                btn_right.setText("确认送达");
            } else {
               rl_bottom.setVisibility(View.GONE);
            }
            btn_mid.setVisibility(View.GONE);
        }

    
    }


    private void setStatus(TextView textView, PinhuoOrder item){
        int type = Integer.valueOf(item.getType());
        switch (type) {
            case 1:
                textView.setBackground(mContext.getResources().getDrawable(R.drawable.order_green));
                textView.setText("待接单");
                break;
            case 2:
                textView.setBackground(mContext.getResources().getDrawable(R.drawable.order_purple));
                textView.setText("待接货");
                break;
            case 3:
                textView.setBackground(mContext.getResources().getDrawable(R.drawable.order_red));
                if ("2".equals(SPUtils.getInstance().getString("role"))) {
                    textView.setText("待收货");
                } else {
                    textView.setText("待送货");
                }
                break;
            case 4:
                textView.setBackground(mContext.getResources().getDrawable(R.drawable.order_orangle));
                textView.setText("待签收");
                break;
            case 5:
                textView.setBackground(mContext.getResources().getDrawable(R.drawable.order_blue));
                textView.setText("已完成");
                break;
            case 6:
                break;
            default:
                break;
        }
    }


}
