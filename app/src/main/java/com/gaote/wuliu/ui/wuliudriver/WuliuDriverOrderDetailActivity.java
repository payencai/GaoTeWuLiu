package com.gaote.wuliu.ui.wuliudriver;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.gaote.wuliu.R;
import com.gaote.wuliu.net.MyPath;
import com.gaote.wuliu.ui.wuliudriver.mvp.model.WuliuDriverOrder;
import com.gaote.wuliu.ui.wuliudriver.mvp.model.WuliuDriverOrderModel;
import com.gaote.wuliu.ui.wuliudriver.mvp.presenter.WuliuDriverOrderPresenter;
import com.gaote.wuliu.ui.wuliudriver.mvp.view.WuliuDriverOrderView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = MyPath.WuliuDriver.WuliuDriverOrderDetail)
public class WuliuDriverOrderDetailActivity extends AppCompatActivity implements WuliuDriverOrderView {
    WuliuDriverOrderPresenter WuliuOrderPresenter;
    @Autowired(name = "data")
    public WuliuDriverOrder wuliuDriverOrder;
    @BindView(R.id.tv_order_desc)
    TextView tvOrderDesc;

    @BindView(R.id.tv_order_time)
    TextView tvOrderTime;

    @BindView(R.id.tv_warehouse)
    TextView tvWareHouse;

    @BindView(R.id.tv_warehouse_address)
    TextView tvWareHouseAddr;

    @BindView(R.id.tv_network)
    TextView tvNetWork;

    @BindView(R.id.tv_network_addr)
    TextView tvNetWorkAddr;

    @BindView(R.id.tv_goods_count)
    TextView tvGoodCount;

    @BindView(R.id.tv_title)
    TextView tvTitle;
    
    @BindView(R.id.tv_get_good_time)
    TextView tvGetGoodTime;

    @BindView(R.id.tv_good_addr)
    TextView tvGoodAddr;


    @BindView(R.id.tv_distance)
    TextView tvDistance;

    @BindView(R.id.item_btn_left)
    Button btnLeft;

    @BindView(R.id.item_btn_right)
    Button btnRight;

    @BindView(R.id.item_rl_bottom)
    RelativeLayout rlBottom;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wuliu_driver_order_detail);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);

        initView();
    }

    private void initView() {

        tvTitle.setText("订单详情");
        tvWareHouse.setText("运往仓库:" + wuliuDriverOrder.getWarehouseName());
        tvWareHouseAddr.setText("仓库地址:" + wuliuDriverOrder.getWarehouseAdress());

        tvNetWork.setText("服务网点:" + wuliuDriverOrder.getNetworkName());
        tvNetWorkAddr.setText("网点地址:" + wuliuDriverOrder.getNetworkAdress());

        tvGoodCount.setText("货物件数:" + wuliuDriverOrder.getCount() + "件");

        int type = Integer.valueOf(wuliuDriverOrder.getDriverStatus());
        switch (type)  {
            case 2:
                tvOrderDesc.setText("等待服务网点确认交货");
                tvOrderTime.setText(wuliuDriverOrder.getUpdateTime());
                tvGetGoodTime.setText("接单时间:" + wuliuDriverOrder.getUpdateTime());
                tvGoodAddr.setText("装货地址:" + wuliuDriverOrder.getNetworkAdress());
                tvDistance.setText("离网点距离:" + wuliuDriverOrder.getDistanceDriver() + "km");
                btnRight.setText("确认收货");
                if("1".equals(wuliuDriverOrder.getIsConfirm())){
                    rlBottom.setVisibility(View.VISIBLE);
                }else{
                    rlBottom.setVisibility(View.GONE);
                }
                break;
            case 3:
                tvOrderDesc.setText("等待运往仓库");
                tvOrderTime.setText(wuliuDriverOrder.getUpdateTime());
                tvGetGoodTime.setText("派送时间:" + wuliuDriverOrder.getPickTime());
                tvGoodAddr.setText("收货地址:" + wuliuDriverOrder.getWarehouseAdress());
                tvDistance.setText("离仓库距离:" + wuliuDriverOrder.getDistanceDriver() + "km");
                rlBottom.setVisibility(View.VISIBLE);
                btnRight.setText("确认送达");
                break;
            default:
                tvOrderDesc.setText("订单已完成，收货仓库:" + wuliuDriverOrder.getWarehouseName());
                tvOrderTime.setText(wuliuDriverOrder.getUpdateTime());
                tvGetGoodTime.setText("交货时间:" + wuliuDriverOrder.getReachwarehouseTime());
                tvGoodAddr.setText("交货地址:" + wuliuDriverOrder.getWarehouseAdress());
                tvDistance.setText("离仓库距离:" + 0 + "km");
                rlBottom.setVisibility(View.GONE);
                break;
        }
        WuliuOrderPresenter=new WuliuDriverOrderPresenter(this,new WuliuDriverOrderModel());
    }
    private void showOrderDialog(WuliuDriverOrder wuliuDriverOrder,int type) {
        final Dialog dialog = new Dialog(this, R.style.CustomDialog);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_order, null);
        TextView tvTitle = view.findViewById(R.id.tv_title);
        TextView tv_cancel = view.findViewById(R.id.tv_cancel);
        TextView tv_confirm = view.findViewById(R.id.tv_confirm);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if(type==1)
                    WuliuOrderPresenter.onConfirm(wuliuDriverOrder.getNetworkId(),wuliuDriverOrder.getUpdateTime());
                else{
                    WuliuOrderPresenter.onSend(wuliuDriverOrder.getNetworkId(),wuliuDriverOrder.getUpdateTime());
                }
            }
        });
        if(type==1){
            tvTitle.setText("确认收货吗？");
        }else{
            tvTitle.setText("确认送达吗？");
        }
        dialog.setContentView(view);
        dialog.show();
        Window window = dialog.getWindow();
        WindowManager windowManager =getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = (int) (display.getWidth() * 0.8);
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);

    }
    @OnClick({R.id.iv_back,R.id.item_btn_left,R.id.item_btn_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.item_btn_left:
                finish();
                break;
            case R.id.item_btn_right:
                handleOrderByStatus();
                break;

        }
    }

    private void handleOrderByStatus() {
        if (Integer.valueOf(wuliuDriverOrder.getDriverStatus()) == 2) {
            showOrderDialog(wuliuDriverOrder,1);
            //确认
        } else if (Integer.valueOf(wuliuDriverOrder.getDriverStatus()) == 3) {
            //送达
            showOrderDialog(wuliuDriverOrder,2);
        }
    }

    @Override
    public void showLoading() {
        
    }

    @Override
    public void dissLoading() {

    }

    @Override
    public void onAfterConfirm() {

    }

    @Override
    public void onAfterSend() {

    }

    @Override
    public void getMyOrder(List<WuliuDriverOrder> wuliuDriverOrders) {

    }
}
