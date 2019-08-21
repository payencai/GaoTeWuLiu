package com.gaote.wuliu.ui.pinhuodriver;

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
import com.gaote.wuliu.tools.CheckDoubleClick;
import com.gaote.wuliu.ui.pinhuodriver.adapter.PinhuoMainOrderAdapter;
import com.gaote.wuliu.ui.pinhuodriver.mvp.mdoel.PinhuoOrder;
import com.gaote.wuliu.ui.pinhuodriver.mvp.mdoel.PinhuoOrderModel;
import com.gaote.wuliu.ui.pinhuodriver.mvp.presenter.PinhuoOrderPresenter;
import com.gaote.wuliu.ui.pinhuodriver.mvp.view.PinhuoOrderView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
@Route(path = MyPath.PinhuoDriver.FindOrder)
public class FindPinhuoOrderActivity extends AppCompatActivity implements PinhuoOrderView, View.OnClickListener {
    PinhuoOrderPresenter netOrderPresenter;
    @BindView(R.id.rv_confirm)
    RecyclerView rv_net;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    PinhuoMainOrderAdapter netOrderAdapter;
    int page=1;

    List<PinhuoOrder.BeanListBean> beanListBeans;
    int clickPosition=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pinhuo_order);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        tv_title.setText("寻找货源");
        beanListBeans=new ArrayList<>();
        netOrderAdapter=new PinhuoMainOrderAdapter(beanListBeans);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                page=1;
                beanListBeans.clear();
                netOrderAdapter.setNewData(beanListBeans);
                netOrderPresenter.getConifrmOrder(page);
            }
        });
        netOrderAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                netOrderPresenter.getConifrmOrder(page);
            }
        });
        netOrderAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                if(CheckDoubleClick.isFastDoubleClick()){
                    return;
                }
                if(view.getId()==R.id.nearby_cargo_rl_rob){
                    clickPosition=position;
                    showConfirmDialog(netOrderAdapter.getData().get(position).getId());
                }
            }
        });
        rv_net.setLayoutManager(new LinearLayoutManager(this));
        rv_net.setAdapter(netOrderAdapter);
        netOrderPresenter=new PinhuoOrderPresenter(this,new PinhuoOrderModel());
        netOrderPresenter.getConifrmOrder(page);
    }

    @OnClick({R.id.iv_back})
    void OnViewClick(View view){
        if(CheckDoubleClick.isFastDoubleClick()){
            return;
        }
        switch (view.getId()){
            case R.id.iv_back:
                finish();
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
    public void onOrderConfirm() {
        netOrderAdapter.remove(clickPosition);
    }

    @Override
    public void onSuccess(List<PinhuoOrder.BeanListBean> listBeans) {
        netOrderAdapter.addData(listBeans);
        if(listBeans==null||listBeans.size()==0){
            netOrderAdapter.loadMoreEnd(true);
        }else{
            netOrderAdapter.loadMoreComplete();
        }
        refreshLayout.finishRefresh();
    }



    @Override
    public void onClick(View v) {
        OnViewClick(v);
    }

    private void showConfirmDialog(String id) {
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
                netOrderPresenter.confirmOrder(id);
            }
        });
        tvTitle.setText("确认接单吗？");
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
}
