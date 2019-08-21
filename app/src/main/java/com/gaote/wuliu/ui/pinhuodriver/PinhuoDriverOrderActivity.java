package com.gaote.wuliu.ui.pinhuodriver;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.flyco.tablayout.SlidingTabLayout;
import com.gaote.wuliu.R;
import com.gaote.wuliu.net.MyPath;
import com.gaote.wuliu.ui.client.mine.order.PinhuoOrderFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
@Route(path = MyPath.PinhuoDriver.MyOrder)
public class PinhuoDriverOrderActivity extends AppCompatActivity {

    @BindView(R.id.tab_order)
    SlidingTabLayout tab_order;
    @BindView(R.id.vp_order)
    ViewPager vp_order;
    ArrayList<Fragment> mFragments;
    String []mTitles={"全部","待接货","待送达","待签收","已完成","退款"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pinhuo_driver_order);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        mFragments=new ArrayList<>();
        PinhuoOrderFragment orderListFragment=PinhuoOrderFragment.newInstance(0);
        mFragments.add(orderListFragment);
        for (int i =2; i <7 ; i++) {
            PinhuoOrderFragment fragment=PinhuoOrderFragment.newInstance(i);
            mFragments.add(fragment);
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
