package com.gaote.wuliu.ui.client.mine;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gaote.wuliu.MyApp;
import com.gaote.wuliu.R;
import com.gaote.wuliu.bean.Result;
import com.gaote.wuliu.net.Api;
import com.gaote.wuliu.net.NetUtils;
import com.gaote.wuliu.net.OnMessageReceived;
import com.gaote.wuliu.tools.GsonUtil;
import com.gaote.wuliu.ui.client.mine.bean.Coupon;
import com.gaote.wuliu.ui.client.mine.order.PinhuoOrderFragment;
import com.gaote.wuliu.ui.pinhuodriver.mvp.mdoel.PinhuoOrder;
import com.google.gson.Gson;
import com.lzy.okgo.model.HttpParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyCouponFragment extends Fragment {
    @BindView(R.id.rv_item)
    RecyclerView rv_item;
    CouponAdapter couponAdapter;
    List<Coupon> coupons;
    int type;
    int page=1;
    boolean isLoadMore=false;
    public MyCouponFragment() {
        // Required empty public constructor
    }

    public static MyCouponFragment newInstance(int type) {
        MyCouponFragment myCouponFragment = new MyCouponFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        myCouponFragment.setArguments(bundle);
        return myCouponFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_my_coupon, container, false);
        ButterKnife.bind(this,view);
        initView();
        return view;
    }

    private void initView() {
        initAdapter();
    }
    private void initAdapter(){
        coupons=new ArrayList<>();
        couponAdapter=new CouponAdapter(coupons);
        couponAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                page++;
                isLoadMore=true;
                getData();
            }
        },rv_item);
        rv_item.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_item.setAdapter(couponAdapter);
        getData();
    }
    private void getData(){
        HttpParams httpParams=new HttpParams();
        httpParams.put("page",page);
        NetUtils.getInstance().get(MyApp.token, Api.BASE_URL + Api.Pinhuo.getCouponByUse,httpParams, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {

                Result result=GsonUtil.fromJsonObject(response,Result.class);
                String json=new Gson().toJson(result.getData());
                List<Coupon> couponList= GsonUtil.jsonToList(json,Coupon.class);
                if(result.getResultCode()==0){
                    couponAdapter.addData(couponList);
                    if(isLoadMore){
                        isLoadMore=false;
                        if(couponList==null||couponList.size()==0){
                            couponAdapter.loadMoreEnd(true);
                        }else{
                            couponAdapter.loadMoreComplete();
                        }
                    }else{
                        couponAdapter.loadMoreComplete();
                    }
                }else{
                    ToastUtils.showShort(result.getMessage());
                }
            }

            @Override
            public void onError(String error) {

            }
        });
    }

}
