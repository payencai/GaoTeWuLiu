package com.gaote.wuliu.ui.client.mine;


import android.icu.lang.UScript;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.utilcode.util.GsonUtils;
import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.fingerth.supdialogutils.SYSDiaLogUtils;
import com.gaote.wuliu.MyApp;
import com.gaote.wuliu.R;
import com.gaote.wuliu.bean.ResultPage;
import com.gaote.wuliu.net.Api;
import com.gaote.wuliu.net.NetUtils;
import com.gaote.wuliu.net.OnMessageReceived;
import com.gaote.wuliu.tools.CheckDoubleClick;
import com.gaote.wuliu.ui.client.mine.adapter.CouponAdapter;
import com.gaote.wuliu.ui.client.mine.adapter.GetCouponAdapter;
import com.gaote.wuliu.ui.client.mine.bean.Coupon;
import com.gaote.wuliu.ui.client.mine.bean.GetCoupon;
import com.gaote.wuliu.ui.client.wuliu.model.WuliuCompany;
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
public class GetCouponFragment extends Fragment {
    @BindView(R.id.rv_item)
    RecyclerView rv_item;
    GetCouponAdapter couponAdapter;
    List<GetCoupon> coupons;
    int type;
    int page=1;
    boolean isLoadMore=false;
    public GetCouponFragment() {
        // Required empty public constructor
    }

    public static GetCouponFragment newInstance(int type) {
        GetCouponFragment myCouponFragment = new GetCouponFragment();
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
        couponAdapter=new GetCouponAdapter(coupons);
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
                GetCoupon getCoupon= (GetCoupon) baseQuickAdapter.getItem(i);
                if(CheckDoubleClick.isFastDoubleClick()){
                    return;
                }
                if(view.getId()==R.id.tv_use){
                    String id=getCoupon.getId();
                    useCoupon(id);
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
        NetUtils.getInstance().get(MyApp.token, Api.BASE_URL + Api.Pinhuo.getCouponByCanGet,httpParams, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                ResultPage<GetCoupon> result= GsonUtils.fromJson(response,ResultPage.class);
                LogUtils.e(result.getData().getPageSize()+"");
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    int code=jsonObject.getInt("resultCode");
                    if(code==0){
                        jsonObject=jsonObject.getJSONObject("data");
                        List<GetCoupon> couponList=new ArrayList<>();
                        JSONArray jsonArray=jsonObject.getJSONArray("beanList");
                        for (int i = 0; i <jsonArray.length() ; i++) {
                            JSONObject item=jsonArray.getJSONObject(i);
                            GetCoupon coupon=new Gson().fromJson(item.toString(),GetCoupon.class);
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
    private void useCoupon(String id){
        HttpParams httpParams=new HttpParams();
        httpParams.put("id",id);
        NetUtils.getInstance().post(Api.BASE_URL + Api.Pinhuo.getCoupon, MyApp.token, httpParams, new OnMessageReceived() {
            @Override
            public void onSuccess(String response) {
                LogUtils.e(response);
                SYSDiaLogUtils.showSuccessDialog(getActivity(), "操作成功", "恭喜你，你已领取该优惠券！", "确认", false);
            }

            @Override
            public void onError(String error) {

            }
        });
    }

}
