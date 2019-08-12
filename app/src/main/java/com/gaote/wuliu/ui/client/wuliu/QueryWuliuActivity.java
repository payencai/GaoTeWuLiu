package com.gaote.wuliu.ui.client.wuliu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.gaote.wuliu.R;
import com.gaote.wuliu.net.MyPath;
import com.gaote.wuliu.tools.CheckDoubleClick;

import butterknife.ButterKnife;
import butterknife.OnClick;
@Route(path = MyPath.Wuliu.QueryWuliu)
public class QueryWuliuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_query_wuliu);
        ButterKnife.bind(this);
    }
    @OnClick({R.id.ll_company,R.id.iv_back,R.id.btn_search})
    void OnClick(View view){
        if(CheckDoubleClick.isFastDoubleClick()){
            return;
        }
        switch (view.getId()){
            case R.id.ll_company:
                ARouter.getInstance().build(MyPath.Wuliu.ChooseCompany).navigation();
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.btn_search:
                break;
        }
    }
}
