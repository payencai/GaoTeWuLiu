package com.gaote.wuliu.ui.wuliudriver;

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
import com.gaote.wuliu.MyApp;
import com.gaote.wuliu.R;
import com.gaote.wuliu.net.MyPath;
import com.gaote.wuliu.tools.CheckDoubleClick;
import com.gaote.wuliu.ui.pinhuodriver.adapter.PinhuoMainOrderAdapter;
import com.gaote.wuliu.ui.pinhuodriver.mvp.mdoel.PinhuoOrder;
import com.gaote.wuliu.ui.pinhuodriver.mvp.mdoel.PinhuoOrderModel;
import com.gaote.wuliu.ui.pinhuodriver.mvp.presenter.PinhuoOrderPresenter;
import com.gaote.wuliu.ui.pinhuodriver.mvp.view.PinhuoOrderView;
import com.gaote.wuliu.ui.wuliudriver.adapter.WuliuNetAdapter;
import com.gaote.wuliu.ui.wuliudriver.mvp.model.WuliuDriverOrder;
import com.gaote.wuliu.ui.wuliudriver.mvp.model.WuliuNet;
import com.gaote.wuliu.ui.wuliudriver.mvp.model.WuliuNetModel;
import com.gaote.wuliu.ui.wuliudriver.mvp.presenter.WuliuNetPresenter;
import com.gaote.wuliu.ui.wuliudriver.mvp.view.WuliuOrderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = MyPath.WuliuDriver.Main)
public class WuliuMainActivity extends AppCompatActivity implements WuliuOrderView, View.OnClickListener {
    WuliuNetPresenter netOrderPresenter;
    @BindView(R.id.rv_net)
    RecyclerView rv_net;
    WuliuNetAdapter netOrderAdapter;
    View headerView;
    List<WuliuNet> beanListBeans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wuliu_main);
        ButterKnife.bind(this);
        initView();
    }

    private void initHeader() {
        headerView = LayoutInflater.from(this).inflate(R.layout.header_wuliu_main, null);
        ButterKnife.findById(headerView, R.id.iv_net).setOnClickListener(this);
        ButterKnife.findById(headerView, R.id.iv_order).setOnClickListener(this);
        ButterKnife.findById(headerView, R.id.iv_driver).setOnClickListener(this);

    }

    private void initView() {
        initHeader();
        beanListBeans = new ArrayList<>();
        netOrderAdapter = new WuliuNetAdapter(beanListBeans);
        netOrderAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                WuliuNet wuliuNet = (WuliuNet) adapter.getData().get(position);
                ARouter.getInstance().build(MyPath.WuliuDriver.WuliuDriverNetOrder).withSerializable("data", wuliuNet).navigation();
            }
        });
        netOrderAdapter.addHeaderView(headerView);
        rv_net.setLayoutManager(new LinearLayoutManager(this));
        rv_net.setAdapter(netOrderAdapter);
        netOrderPresenter = new WuliuNetPresenter(this, new WuliuNetModel());
        netOrderPresenter.getNetWorks(MyApp.getInstance().getaMapLocation().getLatitude() + "", MyApp.getInstance().getaMapLocation().getLongitude() + "");
    }

    @OnClick({R.id.ll_exit})
    void OnViewClick(View view) {
        if (CheckDoubleClick.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.ll_exit:
                showExitDialog();
                break;
            case R.id.iv_net:
                ARouter.getInstance().build(MyPath.WuliuDriver.WuliuNetWorks).navigation();
                break;
            case R.id.iv_order:
                ARouter.getInstance().build(MyPath.WuliuDriver.WuliuDriverOrder).navigation();
                break;
            case R.id.iv_driver:
                ARouter.getInstance().build(MyPath.WuliuDriver.WuliuDriverInfo).navigation();
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
    public void onSuccess(List<WuliuNet> wuliuNets) {
        netOrderAdapter.addData(wuliuNets);
        if (wuliuNets == null || wuliuNets.size() == 0) {
            netOrderAdapter.loadMoreEnd(true);
        } else {
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
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = (int) (display.getWidth() * 0.7);
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);
    }

}
