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
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gaote.wuliu.R;
import com.gaote.wuliu.net.MyPath;
import com.gaote.wuliu.ui.net.adapter.NetDoorOrderAdapter;
import com.gaote.wuliu.ui.net.adapter.NetRecordAdapter;
import com.gaote.wuliu.ui.net.mvp.model.NetDoorOrder;
import com.gaote.wuliu.ui.net.mvp.model.NetDoorOrderModel;
import com.gaote.wuliu.ui.net.mvp.model.NetRecordOrder;
import com.gaote.wuliu.ui.net.mvp.model.NetRecordOrderModel;
import com.gaote.wuliu.ui.net.mvp.presenter.NetDoorOrderPresenter;
import com.gaote.wuliu.ui.net.mvp.presenter.NetRecordPresenter;
import com.gaote.wuliu.ui.net.mvp.view.NetDoorOrderView;
import com.gaote.wuliu.ui.net.mvp.view.NetRecordOrderView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = MyPath.Net.DoorOrder)
public class NetDoorActivity extends AppCompatActivity implements NetDoorOrderView {
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.rv_confirm)
    RecyclerView rv_confirm;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    NetDoorOrderPresenter netConfirmPresenter;
    List<NetDoorOrder> listBeanList;
    NetDoorOrderAdapter netConfirmAdapter;
    int page=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door_getgoods);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tv_title.setText("上门取件");
        listBeanList=new ArrayList<>();
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        netConfirmAdapter=new NetDoorOrderAdapter(listBeanList);
        netConfirmAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                netConfirmPresenter.getDoorOrder(page);
            }
        });
        netConfirmAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ARouter.getInstance().build(MyPath.Net.NetDoorDetail).navigation();
            }
        });
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                listBeanList.clear();
                page=1;
                netConfirmAdapter.setNewData(listBeanList);
                netConfirmPresenter.getDoorOrder(page);
            }
        });
        rv_confirm.setLayoutManager(new LinearLayoutManager(this));
        rv_confirm.setAdapter(netConfirmAdapter);
        netConfirmPresenter=new NetDoorOrderPresenter(this,new NetDoorOrderModel());
        netConfirmPresenter.getDoorOrder(page);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dissLoading() {

    }


    @Override
    public void onSuccess(List<NetDoorOrder> listBeans) {
        if(refreshLayout!=null)
            refreshLayout.finishRefresh();
        listBeanList.addAll(listBeans);
        netConfirmAdapter.addData(listBeans);
        if(listBeans==null||listBeans.size()==0){
            netConfirmAdapter.loadMoreEnd(true);
        }else{
            netConfirmAdapter.loadMoreComplete();
        }
    }
}
