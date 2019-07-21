package com.gaote.wuliu.ui.client.mine;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.gaote.wuliu.R;
import com.gaote.wuliu.tools.CheckDoubleClick;
import com.gaote.wuliu.widget.StepView;

import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BecomeDriverActivity extends AppCompatActivity {
    @BindView(R.id.step_view)
    StepView mStepView;
    //第一步
    @BindView(R.id.et_name)
    EditText mEtName;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.et_id_card)
    EditText mEtIdCard;

    //第二步
    @BindView(R.id.tv_car_color)
    TextView mTvCarColor;
    @BindView(R.id.et_car_number)
    EditText mEtCarNum;
    @BindView(R.id.tv_car_type)
    TextView mTvCarType;

    //第三步
    @BindView(R.id.iv_img1)
    ImageView ivImg1;
    @BindView(R.id.iv_img2)
    ImageView ivImg2;
    @BindView(R.id.iv_img3)
    ImageView ivImg3;
    @BindView(R.id.iv_img4)
    ImageView ivImg4;
    @BindView(R.id.btn_next)
    Button btnNext;

    @BindView(R.id.lcl_driver_vg_step1)
    ViewGroup mVgStep1;

    @BindView(R.id.lcl_driver_vg_step2)
    ViewGroup mVgStep2;

    @BindView(R.id.lcl_driver_vg_step3)
    ViewGroup mVgStep3;
    private int mCurrentStep = 1;

    private int currentIvIndex = 1;
    private String mStrCarColor="蓝色";
    private String mStrCarType="1";
    private String mStrCarSize;
    private String mStrCarWeight;
    private String mStrCarVolume;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_become_driver);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        initStepView();
    }
    @OnClick({R.id.btn_next, R.id.iv_back,R.id.rl_cartype,R.id.rl_color})
    void OnClcik(View view) {
        if (CheckDoubleClick.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.rl_color:
                showColorDialog();
                break;
            case R.id.rl_cartype:
                startActivity(new Intent(BecomeDriverActivity.this,ChooseCartypeActivity.class));
                break;
            case R.id.btn_next:
                if(mCurrentStep < 3) {
                    showNextStep();
                }
                break;
            case R.id.iv_back:
                finish();
                break;
        }
    }
    private void initStepView() {
        List<String> steps = Arrays.asList(new String[]{"个人信息", "车辆信息", "照片信息"});
        mStepView.setSteps(steps);
        mStepView.selectedStep(mCurrentStep);
    }
    private void showColorDialog(){
        final Dialog dialog = new Dialog(this, R.style.BottomDialog);
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_color, null);
        dialog.setContentView(view);
        dialog.show();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        window.setAttributes(layoutParams);
    }

    private void showNextStep() {
        if (!checkCanShowNextStep(mCurrentStep)) {
            return;
        }
        mCurrentStep++;
        switch (mCurrentStep) {
            case 2:
                mVgStep1.setVisibility(View.GONE);
                mVgStep2.setVisibility(View.VISIBLE);
                mStepView.selectedStep(mCurrentStep);
                break;
            case 3:
                mVgStep2.setVisibility(View.GONE);
                mVgStep3.setVisibility(View.VISIBLE);
                btnNext.setText("确认上传");
                mStepView.selectedStep(mCurrentStep);
            default:
                break;
        }
    }

    private boolean checkCanShowNextStep(int currentStep) {
        if (currentStep == 1) {
            if (TextUtils.isEmpty(mEtName.getText().toString())
                    || TextUtils.isEmpty(mEtPhone.getText().toString())
                    || TextUtils.isEmpty(mEtIdCard.getText().toString())) {
                ToastUtils.showShort("信息填写不完整，请检查。");
                return false;
            }
            return true;
        } else if (currentStep == 2) {
            if (TextUtils.isEmpty(mStrCarColor)
                    || TextUtils.isEmpty(mEtCarNum.getText().toString())
                    || TextUtils.isEmpty(mStrCarType)) {
                ToastUtils.showShort("信息填写不完整，请检查。");
                return false;
            }
            return true;
        }
        return true;
    }
}
