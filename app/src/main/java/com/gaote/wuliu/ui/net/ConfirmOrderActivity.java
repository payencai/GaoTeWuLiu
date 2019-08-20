package com.gaote.wuliu.ui.net;

import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.blankj.utilcode.util.SPUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gaote.wuliu.R;
import com.gaote.wuliu.net.MyPath;
import com.gaote.wuliu.ui.client.mine.mvp.model.PinhuoOrder;
import com.gaote.wuliu.ui.net.adapter.NetConfirmAdapter;
import com.gaote.wuliu.ui.net.adapter.NetRecordAdapter;
import com.gaote.wuliu.ui.net.mvp.model.NetConfirmOrder;
import com.gaote.wuliu.ui.net.mvp.model.NetConfirmOrderModel;
import com.gaote.wuliu.ui.net.mvp.presenter.NetConfirmPresenter;
import com.gaote.wuliu.ui.net.mvp.view.NetConfirmOrderView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
@Route(path = MyPath.Net.ConfirmOrder)
public class ConfirmOrderActivity extends AppCompatActivity implements NetConfirmOrderView {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.rv_confirm)
    RecyclerView rv_confirm;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    NetConfirmPresenter netConfirmPresenter;
    List<NetConfirmOrder.ListBean> listBeanList;
    NetConfirmAdapter netConfirmAdapter;
    int page=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tv_title.setText("待确认件");
        listBeanList=new ArrayList<>();
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        netConfirmAdapter=new NetConfirmAdapter(listBeanList);
        netConfirmAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                netConfirmPresenter.getConifrmOrder(page);
            }
        });
        netConfirmAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if(view.getId()==R.id.item_rl_confirm){
                    NetConfirmOrder.ListBean listBean=listBeanList.get(position);
                    showOrderDialog(listBean.getDriverId(),listBean.getTakeorderTime());
                }
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                listBeanList.clear();
                page=1;
                netConfirmPresenter.getConifrmOrder(page);
            }
        });
        rv_confirm.setLayoutManager(new LinearLayoutManager(this));
        rv_confirm.setAdapter(netConfirmAdapter);
        netConfirmPresenter=new NetConfirmPresenter(this,new NetConfirmOrderModel());
        netConfirmPresenter.getConifrmOrder(page);
    }
    private void showOrderDialog(String id,String time) {
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
                netConfirmPresenter.confirmOrder(id,time);
            }
        });
        tvTitle.setText("确认交货吗？");
        dialog.setContentView(view);
        dialog.show();
        Window window = dialog.getWindow();
        WindowManager windowManager =getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = (int) (display.getWidth() * 0.7);
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);
    }
    @Override
    public void showLoading() {

    }

    @Override
    public void dissLoading() {

    }

    @Override
    public void onNetOrderConfirm() {
        page=1;
        listBeanList.clear();
        netConfirmPresenter.getConifrmOrder(page);
    }

    @Override
    public void onSuccess(List<NetConfirmOrder.ListBean> listBeans) {
        if(refreshLayout!=null)
            refreshLayout.finishRefresh();
        listBeanList.addAll(listBeans);
        netConfirmAdapter.setNewData(listBeanList);
        if(listBeans==null||listBeans.size()==0){
            netConfirmAdapter.loadMoreEnd(true);
        }else{
            netConfirmAdapter.loadMoreComplete();
        }
    }
}
