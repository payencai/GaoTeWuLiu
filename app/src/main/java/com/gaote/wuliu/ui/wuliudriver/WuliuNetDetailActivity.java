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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gaote.wuliu.R;
import com.gaote.wuliu.net.MyPath;
import com.gaote.wuliu.tools.CheckDoubleClick;
import com.gaote.wuliu.tools.GsonUtil;
import com.gaote.wuliu.ui.wuliudriver.adapter.WuliuNetOrderAdapter;
import com.gaote.wuliu.ui.wuliudriver.mvp.model.SelectOrderEvent;
import com.gaote.wuliu.ui.wuliudriver.mvp.model.WuliuDriverOrder;
import com.gaote.wuliu.ui.wuliudriver.mvp.model.WuliuDriverOrderModel;
import com.gaote.wuliu.ui.wuliudriver.mvp.model.WuliuNet;
import com.gaote.wuliu.ui.wuliudriver.mvp.model.WuliuNetOrder;
import com.gaote.wuliu.ui.wuliudriver.mvp.model.WuliuNetOrderModel;
import com.gaote.wuliu.ui.wuliudriver.mvp.presenter.WuliuDriverOrderPresenter;
import com.gaote.wuliu.ui.wuliudriver.mvp.presenter.WuliuNetOrderPresenter;
import com.gaote.wuliu.ui.wuliudriver.mvp.presenter.WuliuNetPresenter;
import com.gaote.wuliu.ui.wuliudriver.mvp.view.WuliuNetOrderView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = MyPath.WuliuDriver.WuliuDriverNetOrder)
public class WuliuNetDetailActivity extends AppCompatActivity implements WuliuNetOrderView {
    private List<String> mSelectedOrderIds = new ArrayList<>();
    @Autowired(name = "data")
    public WuliuNet wuliuNet;
    @BindView(R.id.net_detail_tv_cargo_count)
    TextView mTvCount;
    @BindView(R.id.tv_title)
    TextView tv_title;
    @BindView(R.id.rv_order)
    RecyclerView rv_order;
    @BindView(R.id.net_detail_cb_selectAll)
    CheckBox checkBox;
    @BindView(R.id.net_detail_rl_bottom)
    RelativeLayout net_detail_rl_bottom;
    WuliuNetOrderPresenter wuliuDriverOrderPresenter;
    List<WuliuNetOrder.ListBean> listBeans;
    WuliuNetOrderAdapter wuliuNetOrderAdapter;
    int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wuliu_net_detail);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        EventBus.getDefault().register(this);
        initView();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onSelect(SelectOrderEvent selectOrderEvt) {
        if (selectOrderEvt.isSelected) {
            mSelectedOrderIds.add(selectOrderEvt.orderId);
            if (mSelectedOrderIds.size() == wuliuNetOrderAdapter.getData().size()) {
                checkBox.setChecked(true);
            }
        } else {
            mSelectedOrderIds.remove(selectOrderEvt.orderId);
            checkBox.setChecked(false);
        }

        mTvCount.setText(mSelectedOrderIds.size() + "件");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @OnClick({R.id.net_detail_tv_get_cargo, R.id.iv_back})
    void OnViewClick(View view) {
        if (CheckDoubleClick.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {

            case R.id.net_detail_tv_get_cargo:
                if(mSelectedOrderIds.size()>0)
                   showOrderDialog(GsonUtil.GsonString(mSelectedOrderIds));
                break;
            case R.id.iv_back:
                finish();
                break;

        }
    }
    private void showOrderDialog(String ids){
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
                wuliuDriverOrderPresenter.onConfirm(ids);
            }
        });
        tvTitle.setText("您确认揽货吗？");

        dialog.setContentView(view);
        dialog.show();
        Window window = dialog.getWindow();
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = (int) (display.getWidth() * 0.8);
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);

    }
    private void initView() {
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkBox.isChecked()){
                    mSelectedOrderIds.clear();
                    for(int i = 0; i < wuliuNetOrderAdapter.getData().size(); i++) {
                        wuliuNetOrderAdapter.getData().get(i).setCheck(false);
                    }
                }else{
                    for(int i = 0; i < wuliuNetOrderAdapter.getData().size(); i++) {
                        wuliuNetOrderAdapter.getData().get(i).setCheck(true);
                        if(!mSelectedOrderIds.contains(wuliuNetOrderAdapter.getData().get(i).getId())) {
                            mSelectedOrderIds.add(wuliuNetOrderAdapter.getData().get(i).getId());
                        }
                    }
                }
                mTvCount.setText(mSelectedOrderIds.size()+"件");
                wuliuNetOrderAdapter.setNewData(wuliuNetOrderAdapter.getData());
            }
        });
        listBeans = new ArrayList<>();
        tv_title.setText("网点订单");
        wuliuNetOrderAdapter = new WuliuNetOrderAdapter(listBeans);
        wuliuNetOrderAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                wuliuDriverOrderPresenter.getNetWorks(wuliuNet.getId(), page);
            }
        });
        rv_order.setLayoutManager(new LinearLayoutManager(this));
        rv_order.setAdapter(wuliuNetOrderAdapter);
        wuliuDriverOrderPresenter = new WuliuNetOrderPresenter(this, new WuliuNetOrderModel());

        wuliuDriverOrderPresenter.getNetWorks(wuliuNet.getId(), page);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dissLoading() {

    }

    @Override
    public void onAfterConfirm() {
        page=1;
        listBeans.clear();

        wuliuNetOrderAdapter.setNewData(listBeans);
        wuliuDriverOrderPresenter.getNetWorks(wuliuNet.getId(), page);
    }

    @Override
    public void onSuccess(List<WuliuNetOrder.ListBean> wuliuNets) {
        wuliuNetOrderAdapter.addData(wuliuNets);
        if (wuliuNets == null || wuliuNets.size() == 0) {
            wuliuNetOrderAdapter.loadMoreEnd(true);
        } else {
            wuliuNetOrderAdapter.loadMoreComplete();
        }
    }


}
