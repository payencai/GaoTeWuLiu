package com.gaote.wuliu.ui.wuliudriver;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gaote.wuliu.MyApp;
import com.gaote.wuliu.R;
import com.gaote.wuliu.net.MyPath;
import com.gaote.wuliu.tools.CheckDoubleClick;
import com.gaote.wuliu.ui.wuliudriver.adapter.WuliuNetAdapter;
import com.gaote.wuliu.ui.wuliudriver.mvp.model.WuliuNet;
import com.gaote.wuliu.ui.wuliudriver.mvp.model.WuliuNetModel;
import com.gaote.wuliu.ui.wuliudriver.mvp.presenter.WuliuNetPresenter;
import com.gaote.wuliu.ui.wuliudriver.mvp.view.WuliuOrderView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

@Route(path = MyPath.WuliuDriver.WuliuNetWorks)
public class WuliuNetActivity extends AppCompatActivity implements WuliuOrderView {
    WuliuNetPresenter netOrderPresenter;
    @BindView(R.id.rv_net)
    RecyclerView rv_net;
    @BindView(R.id.tv_title)
    TextView tv_title;
    WuliuNetAdapter netOrderAdapter;

    List<WuliuNet> beanListBeans;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wuliu_net);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        beanListBeans=new ArrayList<>();
        netOrderAdapter=new WuliuNetAdapter(beanListBeans);
        netOrderAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(CheckDoubleClick.isFastDoubleClick()){
                    return;
                }
                WuliuNet wuliuNet= (WuliuNet) adapter.getData().get(position);
                ARouter.getInstance().build(MyPath.WuliuDriver.WuliuDriverNetOrder).withSerializable("data",wuliuNet).navigation();
            }
        });
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_title.setText("网点接货");
        rv_net.setLayoutManager(new LinearLayoutManager(this));
        rv_net.setAdapter(netOrderAdapter);
        netOrderPresenter=new WuliuNetPresenter(this,new WuliuNetModel());
        netOrderPresenter.getNetWorks(MyApp.getInstance().getaMapLocation().getLatitude()+"",MyApp.getInstance().getaMapLocation().getLongitude()+"");
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
        if(wuliuNets==null||wuliuNets.size()==0){
            netOrderAdapter.loadMoreEnd(true);
        }else{
            netOrderAdapter.loadMoreComplete();
        }
    }
}
