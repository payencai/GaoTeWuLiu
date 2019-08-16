package com.gaote.wuliu.ui.client.wuliu;

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
import com.gaote.wuliu.R;
import com.gaote.wuliu.base.even.WuliuEvent;
import com.gaote.wuliu.net.MyPath;
import com.gaote.wuliu.tools.CheckDoubleClick;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = MyPath.Wuliu.SendGoods)
public class WuliuSendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wuliu_send);
        ButterKnife.bind(this);
    }
    @OnClick({R.id.iv_back,R.id.rl_company,R.id.btn_submit,R.id.tv_search,R.id.rl_coupon,R.id.tv_detail})
    void OnClick(View view){
        if(CheckDoubleClick.isFastDoubleClick()){
            return;
        }
        switch (view.getId()){
            case R.id.tv_detail:
                break;
            case R.id.rl_coupon:
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.rl_company:
                ARouter.getInstance().build(MyPath.Wuliu.ChooseCompany).navigation();
                break;
            case R.id.btn_submit:
                if(checkInput()){
                    takeOrder();
                }
                break;
            case R.id.tv_search:
                ARouter.getInstance().build(MyPath.Wuliu.NetSearch).navigation();
                break;
        }
    }
    private boolean checkInput(){
        boolean isOk=true;
        return isOk;
    }
    private void showPayMethodDialog(){
        final Dialog dialog = new Dialog(this, R.style.BottomDialog);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_pay_method, null);
        TextView tv_confirm=view.findViewById(R.id.tv_confirm);
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                showPayFinishDialog();
            }
        });
        dialog.setContentView(view);
        dialog.show();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);
    }
    private void showPayFinishDialog(){
        final Dialog dialog = new Dialog(this, R.style.CustomDialog);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_pay_finish, null);
        dialog.setContentView(view);
        dialog.show();
        Window window = dialog.getWindow();
        WindowManager windowManager = getWindowManager();
        Display display = windowManager.getDefaultDisplay();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = (int) (display.getWidth() * 0.6);
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);
    }
    private void takeOrder(){
        showPayMethodDialog();
    }
}
