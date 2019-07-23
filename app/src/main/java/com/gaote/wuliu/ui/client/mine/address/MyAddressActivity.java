package com.gaote.wuliu.ui.client.mine.address;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gaote.wuliu.MyApp;
import com.gaote.wuliu.R;
import com.gaote.wuliu.tools.CheckDoubleClick;
import com.gaote.wuliu.ui.client.mine.mvp.model.Address;
import com.gaote.wuliu.ui.client.mine.mvp.model.AddressModel;
import com.gaote.wuliu.ui.client.mine.mvp.presenter.AddressPresenter;
import com.gaote.wuliu.ui.client.mine.mvp.view.AddressView;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyAddressActivity extends AppCompatActivity implements AddressView {
    AddressPresenter addressPresenter;
    AddressAdapter addressAdapter;
    List<Address> addresses;
    @BindView(R.id.rv_address)
    RecyclerView rv_address;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    KProgressHUD kProgressHUD;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_address);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
        initView();
    }
    @OnClick({R.id.btn_add,R.id.iv_back})
    void OnClick(View view){
        if(CheckDoubleClick.isFastDoubleClick()){
            return;
        }
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_add:
                startActivity(new Intent(MyAddressActivity.this,SaveAddressActivity.class));
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onReceive(String  msg){
        if("address".equals(msg)){
            addressPresenter.getAddress();
        }
    }
    private void initAdapter(){
        addresses=new ArrayList<>();
        addressAdapter=new AddressAdapter(addresses);
        addressAdapter.setOnCheckListener(new AddressAdapter.OnCheckListener() {
            @Override
            public void onCheck(String id) {
                addressPresenter.setDefault(id);
            }
        });
        addressAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Address address= (Address) adapter.getItem(position);
                Intent intent=new Intent();
                intent.putExtra("data",address);
                setResult(RESULT_OK,intent);
                finish();
            }
        });
        addressAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Address address= (Address) adapter.getItem(position);
                Intent intent;
                switch (view.getId()){
                    case R.id.ll_del:
                        addressPresenter.deleteAddress(address.getId());
                        break;
                    case R.id.ll_edit:
                        intent=new Intent(MyAddressActivity.this,SaveAddressActivity.class);
                        intent.putExtra("data",address);
                        startActivity(intent);
                        break;
                }
            }
        });
        rv_address.setLayoutManager(new LinearLayoutManager(this));
        rv_address.setAdapter(addressAdapter);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                addressPresenter.getAddress();//重新请求数据
            }
        });
    }
    private void initView() {
        initAdapter();
        addressPresenter=new AddressPresenter(this,new AddressModel());
        addressPresenter.getAddress();//开始请求数据
    }

    @Override
    public void showLoading() {
        kProgressHUD = KProgressHUD.create(this)
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

    @Override
    public void setData(List<Address> addressList) {
        refreshLayout.finishRefresh();
        if(addressList!=null&&addressList.size()>0){
            addressAdapter.setNewData(addressList);
        }
    }

    @Override
    public void afterDelete() {
        addressPresenter.getAddress();
    }

    @Override
    public void afterDefault() {
        addressPresenter.getAddress();
    }

    @Override
    public void onAdd() {

    }

    @Override
    public void onUpdate() {

    }
}
