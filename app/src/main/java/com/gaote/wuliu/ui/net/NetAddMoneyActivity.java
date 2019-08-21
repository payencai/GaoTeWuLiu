package com.gaote.wuliu.ui.net;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bigkoo.pickerview.adapter.ArrayWheelAdapter;
import com.contrarywind.listener.OnItemSelectedListener;
import com.contrarywind.view.WheelView;
import com.gaote.wuliu.R;
import com.gaote.wuliu.net.MyPath;
import com.gaote.wuliu.tools.CheckDoubleClick;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = MyPath.Net.AddMoney)
public class NetAddMoneyActivity extends AppCompatActivity {
    String mon="08";
    String year="2019";
    String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_net_money_detail);
        ButterKnife.bind(this);
    }
    @OnClick({R.id.iv_time,R.id.iv_back,R.id.tv_type})
    void OnClick(View view){
        if(CheckDoubleClick.isFastDoubleClick()){
            return;
        }
        switch (view.getId()){
            case R.id.iv_time:
                showTimeDialog();
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_type:
                showPayDialog();
                break;
        }
    }


    private void showTimeDialog() {
        final Dialog dialog = new Dialog(this, R.style.BottomDialog);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_time, null);
        TextView cancel=view.findViewById(R.id.tv_cancel);
        TextView confirm=view.findViewById(R.id.tv_confirm);
        WheelView wheelView=view.findViewById(R.id.wheelview);
        WheelView wheelView2=view.findViewById(R.id.wheelview2);
        wheelView2.setDividerColor(getResources().getColor(R.color.white));
        wheelView2.setCyclic(false);
        wheelView.setCyclic(false);
        final List<Integer> mOptionsItems2 = new ArrayList<>();
        for(int i=1;i<=12;i++){
            mOptionsItems2.add(i);
        }
        final List<String> mOptionsItems = new ArrayList<>();
        mOptionsItems.add("2019");
        mOptionsItems.add("2020");
        mOptionsItems.add("2021");
        mOptionsItems.add("2022");
        mOptionsItems.add("2023");
        mOptionsItems.add("2024");
        wheelView2.setAdapter(new ArrayWheelAdapter(mOptionsItems2));
        wheelView.setAdapter(new ArrayWheelAdapter(mOptionsItems));

        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                year=mOptionsItems.get(wheelView.getCurrentItem());
                mon=mOptionsItems2.get(wheelView2.getCurrentItem())+"";
            }
        });
        wheelView.setDividerColor(getResources().getColor(R.color.white));
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);
    }
    private void showPayDialog() {
        final Dialog dialog = new Dialog(this, R.style.BottomDialog);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_paytype, null);
        TextView cancel=view.findViewById(R.id.tv_cancel);
        TextView confirm=view.findViewById(R.id.tv_confirm);
        WheelView wheelView=view.findViewById(R.id.wheelview);

        wheelView.setCyclic(false);
        final List<Integer> mOptionsItems2 = new ArrayList<>();
        for(int i=1;i<=12;i++){
            mOptionsItems2.add(i);
        }
        final List<String> mOptionsItems = new ArrayList<>();
        mOptionsItems.add("全部类型");
        mOptionsItems.add("银联支付");
        mOptionsItems.add("支付宝");
        mOptionsItems.add("微信");
        wheelView.setAdapter(new ArrayWheelAdapter(mOptionsItems));
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                type=mOptionsItems.get(wheelView.getCurrentItem());
            }
        });
        wheelView.setDividerColor(getResources().getColor(R.color.white));
        dialog.setContentView(view);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setCancelable(false);
        dialog.show();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);
    }
}
