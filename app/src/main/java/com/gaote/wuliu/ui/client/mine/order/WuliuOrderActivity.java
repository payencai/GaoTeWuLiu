package com.gaote.wuliu.ui.client.mine.order;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.flyco.tablayout.SlidingTabLayout;
import com.gaote.wuliu.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WuliuOrderActivity extends AppCompatActivity {

    @BindView(R.id.tab_order)
    SlidingTabLayout tab_order;
    @BindView(R.id.vp_order)
    ViewPager vp_order;
    ArrayList<Fragment> mFragments;
    String []mTitles={"全部","待接单","待接货","待发往仓库","运输中","待签收","已完成"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wuliu_order);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mFragments=new ArrayList<>();
        for (int i = 0; i <7 ; i++) {
            WuliuOrderFragment orderListFragment=WuliuOrderFragment.newInstance(i);
            mFragments.add(orderListFragment);
        }
        tab_order.setViewPager(vp_order,mTitles,this,mFragments);
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
