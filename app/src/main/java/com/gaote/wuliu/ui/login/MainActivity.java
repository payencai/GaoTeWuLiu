package com.gaote.wuliu.ui.login;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gaote.wuliu.R;
import com.gaote.wuliu.ui.client.mine.MineFragment;
import com.gaote.wuliu.ui.client.pinhuo.PinHuoFragment;
import com.gaote.wuliu.ui.client.wuliu.WuliuFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.fr_container)
    FrameLayout fr_container;
    @BindView(R.id.iv_tab_pinhuo)
    ImageView iv_tab_pinhuo;
    @BindView(R.id.iv_tab_wuliu)
    ImageView iv_tab_wuliu;
    @BindView(R.id.iv_tab_mine)
    ImageView iv_tab_mine;
    @BindView(R.id.tv_tab_pinhuo)
    TextView tv_tab_pinhuo;
    @BindView(R.id.tv_tab_wuliu)
    TextView tv_tab_wuliu;
    @BindView(R.id.tv_tab_mine)
    TextView tv_tab_mine;
    @BindView(R.id.ll_tab_pinhuo)
    LinearLayout ll_tab_pinhuo;
    @BindView(R.id.ll_tab_wuliu)
    LinearLayout ll_tab_wuliu;
    @BindView(R.id.ll_tab_mine)
    LinearLayout ll_tab_mine;
    FragmentManager fm;
    List<Fragment> fragments;
    PinHuoFragment pinHuoFragment;
    WuliuFragment wuliuFragment;
    MineFragment mineFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initView();
    }

    private void initView() {
        fragments=new ArrayList<>();
        setHome();
    }
    private void setHome(){
        pinHuoFragment = new PinHuoFragment();
        fragments.add(pinHuoFragment);
        fm = getSupportFragmentManager();
        fm.beginTransaction().add(R.id.fr_container, pinHuoFragment).commit();
        showFragment(pinHuoFragment);
    }

    private void reset() {
        tv_tab_pinhuo.setTextColor(ContextCompat.getColor(this, R.color.color_999));
        iv_tab_pinhuo.setImageResource(R.drawable.pinhuo_unselected);
        tv_tab_wuliu.setTextColor(ContextCompat.getColor(this, R.color.color_999));
        iv_tab_wuliu.setImageResource(R.drawable.logistics_unselected);
        tv_tab_mine.setTextColor(ContextCompat.getColor(this, R.color.color_999));
        iv_tab_mine.setImageResource(R.drawable.mine_unselected);
    }

    @OnClick({R.id.ll_tab_pinhuo, R.id.ll_tab_wuliu,  R.id.ll_tab_mine})
    void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_tab_pinhuo:
                reset();
                hideAllFragment();
                tv_tab_pinhuo.setTextColor(ContextCompat.getColor(this, R.color.color_333));
                iv_tab_pinhuo.setImageResource(R.drawable.pinhuo_selected);
                showFragment(pinHuoFragment);
                break;
            case R.id.ll_tab_wuliu:
                if(wuliuFragment==null){
                    wuliuFragment = new WuliuFragment();
                    fragments.add(wuliuFragment);
                    fm.beginTransaction().add(R.id.fr_container, wuliuFragment).commit();
                }
                reset();
                hideAllFragment();
                tv_tab_wuliu.setTextColor(ContextCompat.getColor(this, R.color.color_333));
                iv_tab_wuliu.setImageResource(R.drawable.logistics_selected);
                showFragment(wuliuFragment);
                break;

            case R.id.ll_tab_mine:
                if(mineFragment==null){
                    mineFragment = new MineFragment();
                    fragments.add(mineFragment);
                    fm.beginTransaction().add(R.id.fr_container, mineFragment).commit();
                }
                reset();
                hideAllFragment();
                tv_tab_mine.setTextColor(ContextCompat.getColor(this, R.color.color_333));
                iv_tab_mine.setImageResource(R.drawable.mine_selected);
                showFragment(mineFragment);
                break;
        }
    }
    private void hideAllFragment() {
        for (Fragment fragment : fragments) {
            if (fragment != null)
                fm.beginTransaction().hide(fragment).commit();
        }

    }
    private void showFragment(Fragment fragment) {
        fm.beginTransaction().show(fragment).commit();
    }
}
