package com.gaote.wuliu.ui.client.wuliu;

import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.ImageViewState;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.gaote.wuliu.R;
import com.gaote.wuliu.net.MyPath;
import com.gaote.wuliu.tools.CheckDoubleClick;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

@Route(path = MyPath.Wuliu.SeeLongtPhoto)
public class WuliuKnowledgeActivity extends AppCompatActivity {
    @BindView(R.id.ssiv_data)
    SubsamplingScaleImageView  mSsiv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wuliu_knowledge);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        initImageView();
    }
    @OnClick({R.id.iv_back})
    void OnClick(View view){
        if(CheckDoubleClick.isFastDoubleClick()){
            return;
        }
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;

        }
    }
    private void initImageView() {
        mSsiv.setMinimumScaleType(SubsamplingScaleImageView.SCALE_TYPE_CUSTOM);
        mSsiv.setMaxScale(10.0F);//最大显示比例（太大了图片显示会失真，因为一般微博长图的宽度不会太宽）
        mSsiv.setMinScale(2.0F);//最小显示比例
        mSsiv.setImage(ImageSource.resource(R.drawable.forbidden_obj));

    }
}
