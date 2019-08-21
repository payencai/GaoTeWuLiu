package com.gaote.wuliu.ui.net;

import android.app.Dialog;
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
public class NetMainActivity extends AppCompatActivity implements NetOrderView, View.OnClickListener {
    NetOrderPresenter netOrderPresenter;
    @BindView(R.id.rv_net)
    RecyclerView rv_net;
    NetOrderAdapter netOrderAdapter;
    int page=1;
    View headerView;
    List<NetOrder.ListBean> expressConfirmedItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_main);
        ButterKnife.bind(this);
        initView();
    }
    private void initHeader(){
       headerView=LayoutInflater.from(this).inflate(R.layout.header_net_main,null);
       ButterKnife.findById(headerView, R.id.iv_add).setOnClickListener(this);
       ButterKnife.findById(headerView, R.id.iv_get).setOnClickListener(this);
       ButterKnife.findById(headerView, R.id.iv_door).setOnClickListener(this);
       ButterKnife.findById(headerView, R.id.iv_confirm).setOnClickListener(this);
   }
    private void initView() {
        initHeader();
        expressConfirmedItems=new ArrayList<>();
        netOrderAdapter=new NetOrderAdapter(expressConfirmedItems);
        netOrderAdapter.addHeaderView(headerView);
        netOrderAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                netOrderPresenter.getNetOrder(page);
            }
        });
        rv_net.setLayoutManager(new LinearLayoutManager(this));
        rv_net.setAdapter(netOrderAdapter);

        netOrderPresenter=new NetOrderPresenter(this,new NetOrderModel());
        netOrderPresenter.getNetOrder(page);
    }

    @OnClick({R.id.ll_exit,R.id.ll_shop,R.id.iv_chat})
    void OnViewClick(View view){
        if(CheckDoubleClick.isFastDoubleClick()){
            return;
        }
        switch (view.getId()){
            case R.id.iv_chat:
                break;
            case R.id.ll_exit:
                showExitDialog();
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
    public void onSuccess(List<NetOrder.ListBean> listBeans) {
        netOrderAdapter.addData(listBeans);
        if(expressConfirmedItems==null||listBeans.size()==0){
            netOrderAdapter.loadMoreEnd(true);
        }else{
            netOrderAdapter.loadMoreComplete();
        }
    }

    @Override
    public void onClick(View v) {
        OnViewClick(v);
    }
    private void showExitDialog() {
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
                finish();
            }
        });
        tvTitle.setText("确认退出吗？");
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
