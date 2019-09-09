package com.gaote.wuliu.ui.client.mine;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.flyco.tablayout.SlidingTabLayout;
import com.gaote.wuliu.R;
import com.gaote.wuliu.net.MyPath;
import com.gaote.wuliu.ui.client.mine.order.PinhuoOrderFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
@Route(path = MyPath.Mine.MyCoupon)
public class MyCouponActivity extends AppCompatActivity {

    @BindView(R.id.tab_order)
    SlidingTabLayout tab_order;
    @BindView(R.id.vp_order)
    ViewPager vp_order;
    ArrayList<Fragment> mFragments;
    @Autowired(name = "orderType")
    int orderType;//1.拼货订单跳转过来2.物流订单跳转过来
    @Autowired(name = "iscenter")
    int isCenter;//是不是个人中心跳转进来1是，0不是
    String []mTitles={"我的优惠券","可领取优惠券"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_coupon);
        ButterKnife.bind(this);
        ARouter.getInstance().inject(this);
        initView();
    }

    private void initView() {
        mFragments=new ArrayList<>();
        mFragments.add(MyCouponFragment.newInstance(isCenter,orderType));
        mFragments.add(GetCouponFragment.newInstance(2));

        tab_order.setViewPager(vp_order,mTitles,this,mFragments);
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
