package com.gaote.wuliu.ui.wuliudriver;

import android.os.Handler;
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
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
@Route(path = MyPath.WuliuDriver.WuliuDriverOrder)
public class WuliuDriverOrderActivity extends AppCompatActivity {


    @BindView(R.id.tab_order)
    SlidingTabLayout tab_order;
    @BindView(R.id.vp_order)
    ViewPager vp_order;
    ArrayList<Fragment> mFragments;
    String []mTitles={"全部","待确认","待送达","待签收"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wuliu_driver_order);
        ButterKnife.bind(this);
        initView();
    }
    KProgressHUD kProgressHUD;
    private void initView() {
        kProgressHUD = KProgressHUD.create(this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setCancellable(true)
                .show();

        mFragments=new ArrayList<>();
        for (int i =1; i <5 ; i++) {
            WuliuDriverOrderFragment fragment=WuliuDriverOrderFragment.newInstance(i);
            mFragments.add(fragment);
        }
        tab_order.setViewPager(vp_order,mTitles,this,mFragments);
        findViewById(R.id.iv_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (kProgressHUD != null && kProgressHUD.isShowing()) {
                            kProgressHUD.dismiss();
                        }
                    }
                });

            }
        },500);
    }
}
