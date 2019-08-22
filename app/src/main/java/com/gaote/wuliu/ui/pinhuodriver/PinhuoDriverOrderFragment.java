package com.gaote.wuliu.ui.pinhuodriver;


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
import com.blankj.utilcode.util.SPUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gaote.wuliu.R;
import com.gaote.wuliu.base.even.PinhuoEvent;
import com.gaote.wuliu.net.Api;
import com.gaote.wuliu.net.MyPath;
import com.gaote.wuliu.tools.CheckDoubleClick;
import com.gaote.wuliu.ui.client.mine.mvp.model.PinhuoOrder;
import com.gaote.wuliu.ui.client.mine.mvp.model.PinhuoOrderModel;
import com.gaote.wuliu.ui.client.mine.mvp.presenter.PinhuoOrderPresenter;
import com.gaote.wuliu.ui.client.mine.mvp.view.PinhuoOrderView;
import com.gaote.wuliu.ui.client.mine.order.PinhuoOrderAdapter;
import com.gaote.wuliu.ui.client.mine.order.PinhuoOrderDetailActivity;
import com.gaote.wuliu.ui.client.mine.order.PinhuoOrderFragment;
import com.gaote.wuliu.ui.client.mine.order.WuliuDetailActivity;
import com.gaote.wuliu.ui.pinhuodriver.mvp.mdoel.PinhuoDriverOrderModel;
import com.gaote.wuliu.ui.pinhuodriver.mvp.mdoel.PinhuoOrderEvent;
import com.gaote.wuliu.ui.pinhuodriver.mvp.presenter.PinhuoDriverOrderPresenter;
import com.gaote.wuliu.ui.pinhuodriver.mvp.view.PinhuoDriverOrderView;
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
public class PinhuoDriverOrderFragment extends Fragment implements PinhuoDriverOrderView {
    PinhuoOrderAdapter pinhuoOrderAdapter;
    int type = 0;
    PinhuoDriverOrderPresenter pinhuoOrderPresenter;
    List<PinhuoOrder> pinhuoOrderList;
    @BindView(R.id.rv_order)
    RecyclerView rv_order;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    int page = 1;
    String url;
    KProgressHUD kProgressHUD;
    boolean isLoadMore = false;
    View header;
    int clickPos=0;
    public PinhuoDriverOrderFragment() {
        // Required empty public constructor
    }

    public static PinhuoDriverOrderFragment newInstance(int type) {
        PinhuoDriverOrderFragment pinhuoOrderFragment = new PinhuoDriverOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        pinhuoOrderFragment.setArguments(bundle);
        return pinhuoOrderFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pinhuo_driver_order, container, false);
        type = getArguments().getInt("type", 0);
        ButterKnife.bind(this, view);
        EventBus.getDefault().register(this);
        initView();
        return view;
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMsg(PinhuoOrderEvent pinhuoEvent){
        if(pinhuoEvent.code==200){
            if(type!=pinhuoEvent.type) {//当前页面不重新调用接口获取，而是删除当前item
                if(pinhuoOrderAdapter!=null)
                   refresh();
            }
        }
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void callPhone(String phoneNum) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        Uri data = Uri.parse("tel:" + phoneNum);
        intent.setData(data);
        startActivity(intent);
    }

    private void initView() {
        header = LayoutInflater.from(getContext()).inflate(R.layout.empty_no_order, null);
        initAdapter();
        pinhuoOrderPresenter = new PinhuoDriverOrderPresenter(new PinhuoDriverOrderModel(), this);
        pinhuoOrderPresenter.getOrder(page, type);
    }

    private void showOrderDialog(int type, PinhuoOrder pinhuoOrder) {
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
                if (type == 1) {
                    pinhuoOrderPresenter.handlePinhuoDriverOrder(pinhuoOrder.getId(), 4);//取消
                } else {
                    if (pinhuoOrder.getType().equals("2"))
                        pinhuoOrderPresenter.handlePinhuoDriverOrder(pinhuoOrder.getId(), 2);//确认
                    else if (pinhuoOrder.getType().equals("3")) {
                        pinhuoOrderPresenter.handlePinhuoDriverOrder(pinhuoOrder.getId(), 3);//送达
                    }

                }

            }
        });
        if (type == 1) {
            tvTitle.setText("确认取消吗？");
        } else if (type == 2) {
            if (pinhuoOrder.getType().equals("2")) {
                tvTitle.setText("确认接货吗？");
            } else if (pinhuoOrder.getType().equals("3")) {
                tvTitle.setText("确认送达吗？");
            }
        }
        dialog.setContentView(view);
        dialog.show();
        Window window = dialog.getWindow();
        WindowManager windowManager = getActivity().getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = (int) (display.getWidth() * 0.7);
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);
    }

    private void initAdapter() {
        pinhuoOrderList = new ArrayList<>();
        pinhuoOrderAdapter = new PinhuoOrderAdapter(pinhuoOrderList);
        pinhuoOrderAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(CheckDoubleClick.isFastDoubleClick()){
                    return;
                }
                PinhuoOrder pinhuoOrder = (PinhuoOrder) adapter.getItem(position);
                ARouter.getInstance().build(MyPath.PinhuoDriver.PinhuoDriverOrderDetail).withSerializable("data",pinhuoOrder).navigation();
            }
        });
        pinhuoOrderAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                clickPos=position;
                PinhuoOrder pinhuoOrder = (PinhuoOrder) adapter.getItem(position);
                switch (view.getId()) {
                    case R.id.item_btn_left://取消
                        showOrderDialog(1, pinhuoOrder);
                        break;
                    case R.id.item_btn_mid://物流
                        //startActivity(new Intent(getContext(), WuliuDetailActivity.class));
                        break;
                    case R.id.item_btn_right://联系司机，确认签收，
                        showOrderDialog(2, pinhuoOrder);
                        break;
                }
            }
        });
        rv_order.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_order.setAdapter(pinhuoOrderAdapter);

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
                pinhuoOrderPresenter.getOrder(page, type);
            }
        });
    }

    private void refresh() {
        page = 1;
        pinhuoOrderList.clear();
        pinhuoOrderPresenter.getOrder(page, type);
    }

    @Override
    public void setData(List<PinhuoOrder> pinhuoOrders) {
        refreshLayout.finishRefresh();
        pinhuoOrderList.addAll(pinhuoOrders);
        pinhuoOrderAdapter.setNewData(pinhuoOrderList);
        if (isLoadMore) {
            refreshLayout.finishLoadMore();
            isLoadMore = false;
        }
        if (page == 1 && (pinhuoOrders == null || pinhuoOrders.size() == 0)) {
            pinhuoOrderAdapter.setEmptyView(header);
        }
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
    public void onHandleOrder() {
        pinhuoOrderAdapter.remove(clickPos);
        EventBus.getDefault().post(new PinhuoOrderEvent(200,type));
    }


}
