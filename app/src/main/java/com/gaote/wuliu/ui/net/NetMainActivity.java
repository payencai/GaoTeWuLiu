package com.gaote.wuliu.ui.net;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gaote.wuliu.R;
import com.gaote.wuliu.net.MyPath;
import com.gaote.wuliu.tools.CheckDoubleClick;
import com.gaote.wuliu.ui.net.adapter.NetOrderAdapter;
import com.gaote.wuliu.ui.net.mvp.model.NetOrder;
import com.gaote.wuliu.ui.net.mvp.model.NetOrderModel;
import com.gaote.wuliu.ui.net.mvp.presenter.NetOrderPresenter;
import com.gaote.wuliu.ui.net.mvp.view.NetOrderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
@Route(path = MyPath.Net.Main)
public class NetMainActivity extends AppCompatActivity implements NetOrderView {
    NetOrderPresenter netOrderPresenter;
    @BindView(R.id.rv_net)
    RecyclerView rv_net;
    NetOrderAdapter netOrderAdapter;
    int page=1;
    View header;
    List<NetOrder.ExpressConfirmedItem> expressConfirmedItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        header= LayoutInflater.from(this).inflate(R.layout.item_server_net_main,null);
        expressConfirmedItems=new ArrayList<>();
        netOrderAdapter=new NetOrderAdapter(expressConfirmedItems);
        netOrderAdapter.addHeaderView(header);
        netOrderAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                netOrderPresenter.getNetOrder(page);
            }
        });
        rv_net.setLayoutManager(new LinearLayoutManager(this));
        rv_net.setAdapter(netOrderAdapter);
        ButterKnife.bind(this,header);
        netOrderPresenter=new NetOrderPresenter(this,new NetOrderModel());
        netOrderPresenter.getNetOrder(page);
    }

    @OnClick({R.id.ll_exit,R.id.ll_shop,R.id.iv_door,R.id.iv_get,R.id.iv_confirm,R.id.iv_add,R.id.iv_chat})
    void OnClick(View view){
        if(CheckDoubleClick.isFastDoubleClick()){
            return;
        }
        switch (view.getId()){
            case R.id.iv_chat:
                break;
            case R.id.ll_exit:
                finish();
                break;
            case R.id.ll_shop:
                ARouter.getInstance().build(MyPath.Net.Shop).navigation();
                break;
            case R.id.iv_door:
                ARouter.getInstance().build(MyPath.Net.DoorOrder).navigation();
                break;
            case R.id.iv_add:
                ARouter.getInstance().build(MyPath.Net.AddOrder).navigation();
                break;
            case R.id.iv_get:
                ARouter.getInstance().build(MyPath.Net.RecordOrder).navigation();
                break;
            case R.id.iv_confirm:
                ARouter.getInstance().build(MyPath.Net.ConfirmOrder).navigation();
                break;
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dissLoading() {

    }

    @Override
    public void onSuccess(List<NetOrder.ExpressConfirmedItem> expressConfirmedItems) {
        expressConfirmedItems.addAll(expressConfirmedItems);
        netOrderAdapter.setNewData(expressConfirmedItems);
        if(expressConfirmedItems==null||expressConfirmedItems.size()==0){
            netOrderAdapter.loadMoreEnd(true);
        }else{
            netOrderAdapter.loadMoreComplete();
        }
    }
}
