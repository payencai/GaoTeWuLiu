package com.gaote.wuliu.ui.client.mine.order;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.SPUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gaote.wuliu.MyApp;
import com.gaote.wuliu.R;
import com.gaote.wuliu.net.Api;
import com.gaote.wuliu.ui.client.mine.mvp.model.PinhuoOrder;
import com.gaote.wuliu.ui.client.mine.mvp.model.PinhuoOrderModel;
import com.gaote.wuliu.ui.client.mine.mvp.presenter.PinhuoOrderPresenter;
import com.gaote.wuliu.ui.client.mine.mvp.view.PinhuoOrderView;
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
public class PinhuoOrderFragment extends Fragment implements PinhuoOrderView {
    PinhuoOrderAdapter pinhuoOrderAdapter;
    int type = 0;
    PinhuoOrderPresenter pinhuoOrderPresenter;
    List<PinhuoOrder> pinhuoOrderList;
    @BindView(R.id.rv_order)
    RecyclerView rv_order;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    int page = 1;
    String url;
    KProgressHUD kProgressHUD;
    boolean isLoadMore=false;
    public PinhuoOrderFragment() {
        // Required empty public constructor
    }

    public static PinhuoOrderFragment newInstance(int type) {
        PinhuoOrderFragment pinhuoOrderFragment = new PinhuoOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        pinhuoOrderFragment.setArguments(bundle);
        return pinhuoOrderFragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pinhuo_order, container, false);
        type = getArguments().getInt("type", 0);
        ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        initAdapter();
        pinhuoOrderPresenter = new PinhuoOrderPresenter(new PinhuoOrderModel(), this);
        pinhuoOrderPresenter.getOrder(page, url, type);
    }
    private void initAdapter() {
        pinhuoOrderList = new ArrayList<>();
        pinhuoOrderAdapter = new PinhuoOrderAdapter(pinhuoOrderList);
        pinhuoOrderAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                PinhuoOrder pinhuoOrder= (PinhuoOrder) adapter.getItem(position);
                Intent intent=new Intent(getContext(),PinhuoOrderDetailActivity.class);
                intent.putExtra("data",pinhuoOrder);
                startActivity(intent);
            }
        });
        pinhuoOrderAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {

            }
        });
        rv_order.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_order.setAdapter(pinhuoOrderAdapter);
        if ("2".equals(SPUtils.getInstance().getString("role"))) {
            url = Api.BASE_URL + Api.Order.URL_DEMAND_GET_LCL_ORDER;
        } else {
            url = Api.BASE_URL + Api.Order.URL_GET_ALL_ORDER;
        }
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page=1;
                pinhuoOrderList.clear();
                pinhuoOrderPresenter.getOrder(page, url, type);
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                isLoadMore=true;
                pinhuoOrderPresenter.getOrder(page, url, type);
            }
        });
    }
    @Override
    public void setData(List<PinhuoOrder> pinhuoOrders) {
        refreshLayout.finishRefresh();
        pinhuoOrderList.addAll(pinhuoOrders);
        pinhuoOrderAdapter.setNewData(pinhuoOrderList);
        if(isLoadMore){
            refreshLayout.finishLoadMore();
            isLoadMore=false;
        }
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
        if(kProgressHUD!=null&&kProgressHUD.isShowing()){
            kProgressHUD.dismiss();
        }
    }


}
