package com.gaote.wuliu.ui.client.mine;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gaote.wuliu.MyApp;
import com.gaote.wuliu.R;
import com.gaote.wuliu.net.Api;
import com.gaote.wuliu.net.MyPath;
import com.gaote.wuliu.net.NetUtils;
import com.gaote.wuliu.net.OnMessageReceived;
import com.gaote.wuliu.ui.client.mine.adapter.CouponAdapter;
import com.gaote.wuliu.ui.client.mine.bean.Coupon;
import com.google.gson.Gson;
import com.lzy.okgo.model.HttpParams;

import org.json.JSONArray;
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
    int center;
    int page=1;
    int orderType=0;
    boolean isLoadMore=false;
    public MyCouponFragment() {
        // Required empty public constructor
    }

    public static MyCouponFragment newInstance(int center,int orderType) {
        MyCouponFragment myCouponFragment = new MyCouponFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("center", center);
        bundle.putInt("orderType",orderType);
        myCouponFragment.setArguments(bundle);
        return myCouponFragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_my_coupon, container, false);
        ButterKnife.bind(this,view);
        center=getArguments().getInt("center");
        orderType=getArguments().getInt("orderType");
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
        couponAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                Coupon coupon= (Coupon) baseQuickAdapter.getItem(i);
                if(view.getId()==R.id.tv_use){
                    if(coupon.getIsCenter()==1){
                         if(coupon.getType()==1){ //跳转拼货订单页面
                             ARouter.getInstance().build(MyPath.Pinhuo.DETAIL).withSerializable("coupon",coupon).navigation();
                         }else{  //跳转物流订单页面

                         }
                    }else{
                        //返回选择结果
                        if(coupon.getType()==1&&orderType==1){
                            Intent intent=new Intent();
                            intent.putExtra("data",coupon);
                            getActivity().setResult(Activity.RESULT_OK,intent);
                            getActivity().finish();
                        }
                        if(coupon.getType()==2&&orderType==2){
                            Intent intent=new Intent();
                            intent.putExtra("data",coupon);
                            getActivity().setResult(Activity.RESULT_OK,intent);
                            getActivity().finish();
                        }

                    }
                }
            }
        });
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
                LogUtils.e(response);
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    int code=jsonObject.getInt("resultCode");
                    if(code==0){
                        jsonObject=jsonObject.getJSONObject("data");
                        List<Coupon> couponList=new ArrayList<>();
                        JSONArray jsonArray=jsonObject.getJSONArray("beanList");
                        for (int i = 0; i <jsonArray.length() ; i++) {
                            JSONObject item=jsonArray.getJSONObject(i);
                            Coupon coupon=new Gson().fromJson(item.toString(),Coupon.class);
                            coupon.setIsCenter(center);
                            couponList.add(coupon);
                        }
                        if(isLoadMore){
                            isLoadMore=false;
                            if(couponList==null||couponList.size()==0){
                                couponAdapter.loadMoreEnd(true);
                            }else{
                                couponAdapter.addData(couponList);
                                couponAdapter.loadMoreComplete();
                            }
                        }else{
                            couponAdapter.setNewData(couponList);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(String error) {

            }
        });
    }

}
