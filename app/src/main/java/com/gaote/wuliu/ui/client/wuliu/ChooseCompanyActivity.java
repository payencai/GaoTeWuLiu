package com.gaote.wuliu.ui.client.wuliu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.LogUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.gaote.wuliu.R;
import com.gaote.wuliu.bean.Result;
import com.gaote.wuliu.net.Api;
import com.gaote.wuliu.net.MyPath;
import com.gaote.wuliu.net.NetUtils;
import com.gaote.wuliu.net.OnMessageReceived;
import com.gaote.wuliu.tools.CheckDoubleClick;
import com.gaote.wuliu.tools.GsonUtil;
import com.gaote.wuliu.ui.client.wuliu.adapter.MyIndexStickyViewAdapter;
import com.gaote.wuliu.ui.client.wuliu.model.ExpressCompanyItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.ittiger.indexlist.IndexStickyView;
import cn.ittiger.indexlist.listener.OnItemClickListener;

@Route(path = MyPath.Wuliu.ChooseCompany)
public class ChooseCompanyActivity extends AppCompatActivity {
    @BindView(R.id.indexStickyView)
    IndexStickyView indexStickyView;
    MyIndexStickyViewAdapter myIndexStickyViewAdapter;
    private List<ExpressCompanyItem> mDataList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_company);
        ButterKnife.bind(this);
        initView();
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
    private void initView() {
         mDataList.clear();
         getCompany();
    }
    private void getCompany(){
        NetUtils.getInstance().get(Api.BASE_URL + Api.Wuliu.getAllWuliu, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                LogUtils.e(response);
                Result<List<ExpressCompanyItem>> result= GsonUtil.fromJsonArray(response,ExpressCompanyItem.class);
                mDataList.addAll(result.getData());
                initList();
            }

            @Override
            public void onError(String error) {

            }
        });
    }
    private void initList(){
        myIndexStickyViewAdapter = new MyIndexStickyViewAdapter(mDataList,this);
        indexStickyView.setAdapter(myIndexStickyViewAdapter);
        myIndexStickyViewAdapter.setOnItemClickListener(new OnItemClickListener<ExpressCompanyItem>() {
            @Override
            public void onItemClick(View childView, int position, ExpressCompanyItem item) {
                //EventBus.getInstance().post(new ChooseExpressCompanyEvent(item.getName(),item.getCode()));
                finish();
            }
        });
    }
}