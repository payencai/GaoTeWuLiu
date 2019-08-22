package com.gaote.wuliu.ui.wuliudriver;


import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gaote.wuliu.R;
import com.gaote.wuliu.net.MyPath;
import com.gaote.wuliu.tools.CheckDoubleClick;
import com.gaote.wuliu.ui.client.mine.mvp.model.WuliuOrder;
import com.gaote.wuliu.ui.client.mine.mvp.model.WuliuOrderModel;
import com.gaote.wuliu.ui.client.mine.mvp.presenter.WuliuOrderPresenter;
import com.gaote.wuliu.ui.client.mine.mvp.view.WuliuOrderView;
import com.gaote.wuliu.ui.client.mine.order.PinhuoOrderFragment;
import com.gaote.wuliu.ui.client.mine.order.WuliuDetailActivity;
import com.gaote.wuliu.ui.client.mine.order.WuliuOrderAdapter;
import com.gaote.wuliu.ui.client.mine.order.WuliuOrderDetailActivity;
import com.gaote.wuliu.ui.client.mine.order.WuliuOrderFragment;
import com.gaote.wuliu.ui.wuliudriver.adapter.WuliuDriverOrderAdapter;
import com.gaote.wuliu.ui.wuliudriver.adapter.WuliuNetOrderAdapter;
import com.gaote.wuliu.ui.wuliudriver.mvp.model.OrderEvent;
import com.gaote.wuliu.ui.wuliudriver.mvp.model.WuliuDriverOrder;
import com.gaote.wuliu.ui.wuliudriver.mvp.model.WuliuDriverOrderModel;
import com.gaote.wuliu.ui.wuliudriver.mvp.presenter.WuliuDriverOrderPresenter;
import com.gaote.wuliu.ui.wuliudriver.mvp.view.WuliuDriverOrderView;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class WuliuDriverOrderFragment extends Fragment implements WuliuDriverOrderView {
    WuliuDriverOrderAdapter WuliuOrderAdapter;
    int type = 0;
    WuliuDriverOrderPresenter WuliuOrderPresenter;
    List<WuliuDriverOrder> WuliuOrderList;
    @BindView(R.id.rv_order)
    RecyclerView rv_order;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    int page = 1;

    boolean isLoadMore = false;
    View header;
    int clickPos=0;
    public WuliuDriverOrderFragment() {
        // Required empty public constructor
    }

    public static WuliuDriverOrderFragment newInstance(int type) {
        WuliuDriverOrderFragment WuliuOrderFragment = new WuliuDriverOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        WuliuOrderFragment.setArguments(bundle);
        return WuliuOrderFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wuliu_driver_order, container, false);
        type = getArguments().getInt("type", 0);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        initView();
        return view;
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMsg(OrderEvent orderEvent){
        if(orderEvent.msg==200){
            if(WuliuOrderAdapter!=null)
                refresh();
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void initView() {
        header = LayoutInflater.from(getContext()).inflate(R.layout.empty_no_order, null);
        initAdapter();
        WuliuOrderPresenter = new WuliuDriverOrderPresenter( this,new WuliuDriverOrderModel());
        WuliuOrderPresenter.getMyOrder(page, type+"");
    }

    private void showOrderDialog(WuliuDriverOrder wuliuDriverOrder,int type) {
        final Dialog dialog = new Dialog(getContext(), R.style.CustomDialog);
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_order, null);
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
        WindowManager windowManager = getActivity().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = (int) (display.getWidth() * 0.8);
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);

    }


    private void initAdapter() {
        WuliuOrderList = new ArrayList<>();
        WuliuOrderAdapter = new WuliuDriverOrderAdapter(WuliuOrderList);
        WuliuOrderAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(CheckDoubleClick.isFastDoubleClick()){
                    return;
                }
                WuliuDriverOrder wuliuDriverOrder = (WuliuDriverOrder) adapter.getItem(position);
                ARouter.getInstance().build(MyPath.WuliuDriver.WuliuDriverOrderDetail).withSerializable("data",wuliuDriverOrder).navigation();
            }
        });
        WuliuOrderAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                WuliuDriverOrder wuliuOrder = (WuliuDriverOrder) adapter.getItem(position);
                if(view.getId()==R.id.item_btn_bottom){
                    clickPos=position;
                    switch (Integer.parseInt(wuliuOrder.getDriverStatus())) {
                        case 2://收货
                            showOrderDialog(wuliuOrder,1);
                            break;
                        case 3://送达，
                            showOrderDialog(wuliuOrder,2);

                            break;
                    }
                }

            }
        });
        rv_order.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_order.setAdapter(WuliuOrderAdapter);

        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refresh();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                isLoadMore = true;
                WuliuOrderPresenter.getMyOrder(page, type+"");
            }
        });
    }

    private void refresh() {
        page = 1;
        WuliuOrderList.clear();
        WuliuOrderPresenter.getMyOrder(page, type+"");
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        refresh();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dissLoading() {

    }

    @Override
    public void onAfterConfirm() {
        WuliuOrderAdapter.remove(clickPos);
        EventBus.getDefault().post(new OrderEvent(200));
    }

    @Override
    public void onAfterSend() {
        WuliuOrderAdapter.remove(clickPos);
        EventBus.getDefault().post(new OrderEvent(200));
    }

    @Override
    public void getMyOrder(List<WuliuDriverOrder> wuliuDriverOrders) {
        refreshLayout.finishRefresh();
        if (wuliuDriverOrders != null) {
            WuliuOrderList.addAll(wuliuDriverOrders);
            WuliuOrderAdapter.setNewData(WuliuOrderList);
        }
        if (isLoadMore) {
            refreshLayout.finishLoadMore();
            isLoadMore = false;
        }
        if (page == 1 && (wuliuDriverOrders == null || wuliuDriverOrders.size() == 0)) {
            WuliuOrderAdapter.setEmptyView(header);
        }
    }


}

