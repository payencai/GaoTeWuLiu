package com.gaote.wuliu.ui.client.mine.order;


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

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gaote.wuliu.R;
import com.gaote.wuliu.ui.client.mine.mvp.model.WuliuOrder;
import com.gaote.wuliu.ui.client.mine.mvp.model.WuliuOrderModel;
import com.gaote.wuliu.ui.client.mine.mvp.presenter.WuliuOrderPresenter;
import com.gaote.wuliu.ui.client.mine.mvp.view.WuliuOrderView;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class WuliuOrderFragment extends Fragment implements WuliuOrderView {
    WuliuOrderAdapter WuliuOrderAdapter;
    int type = 0;
    WuliuOrderPresenter WuliuOrderPresenter;
    List<WuliuOrder> WuliuOrderList;
    @BindView(R.id.rv_order)
    RecyclerView rv_order;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    int page = 1;
    KProgressHUD kProgressHUD;
    boolean isLoadMore = false;
    View header;

    public WuliuOrderFragment() {
        // Required empty public constructor
    }

    public static WuliuOrderFragment newInstance(int type) {
        WuliuOrderFragment WuliuOrderFragment = new WuliuOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        WuliuOrderFragment.setArguments(bundle);
        return WuliuOrderFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_wuliu_order, container, false);
        type = getArguments().getInt("type", 0);
        ButterKnife.bind(this, view);
        initView();
        return view;
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
        WuliuOrderPresenter = new WuliuOrderPresenter(new WuliuOrderModel(), this);
        WuliuOrderPresenter.getOrder(page, type);
    }

    private void showOrderDialog(WuliuOrder WuliuOrder) {
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
                WuliuOrderPresenter.userOrderConfirm(WuliuOrder.getId());

            }
        });
        tvTitle.setText("确认签收吗？");
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
        WuliuOrderList = new ArrayList<>();
        WuliuOrderAdapter = new WuliuOrderAdapter(WuliuOrderList);
        WuliuOrderAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                WuliuOrder WuliuOrder = (WuliuOrder) adapter.getItem(position);
                Intent intent = new Intent(getContext(), WuliuOrderDetailActivity.class);
                intent.putExtra("data", WuliuOrder);
                startActivityForResult(intent, 1);
            }
        });
        WuliuOrderAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                WuliuOrder wuliuOrder = (WuliuOrder) adapter.getItem(position);
                Intent intent;
                switch (view.getId()) {
                    case R.id.item_btn_left://物流
                        intent = new Intent(getContext(), WuliuDetailActivity.class);
                        intent.putExtra("data",wuliuOrder );
                        intent.putExtra("type", 1);
                        startActivity(intent);
                        break;
                    case R.id.item_btn_right://确认签收，
                        if(Integer.valueOf(wuliuOrder.getStatus()) == 5) {
                            showOrderDialog( wuliuOrder);
                        }

                        break;
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
                WuliuOrderPresenter.getOrder(page, type);
            }
        });
    }

    private void refresh() {
        page = 1;
        WuliuOrderList.clear();
        WuliuOrderPresenter.getOrder(page, type);
    }

    @Override
    public void setData(List<WuliuOrder> wuliuOrders) {
        refreshLayout.finishRefresh();
        if(wuliuOrders!=null){
            WuliuOrderList.addAll(wuliuOrders);
            WuliuOrderAdapter.setNewData(WuliuOrderList);
        }
        if (isLoadMore) {
            refreshLayout.finishLoadMore();
            isLoadMore = false;
        }
        if (page == 1 && (wuliuOrders == null || wuliuOrders.size() == 0)) {
            WuliuOrderAdapter.setEmptyView(header);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        refresh();
    }

    @Override
    public void showLoading() {
        kProgressHUD = KProgressHUD.create(getContext())
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .show();
    }

    @Override
    public void dissLoading() {
        if (kProgressHUD != null && kProgressHUD.isShowing()) {
            kProgressHUD.dismiss();
        }
    }


    @Override
    public void onUserConfirm() {
        refresh();
    }

    @Override
    public void onUserCancel() {

    }

    @Override
    public void onDriver(int type) {

    }


}
