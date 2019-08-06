package com.gaote.wuliu.ui.client.mine;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gaote.wuliu.R;
import com.gaote.wuliu.ui.client.mine.bean.Coupon;
import com.gaote.wuliu.ui.client.mine.order.PinhuoOrderFragment;

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
        for (int i = 0; i <10 ; i++) {
            coupons.add(new Coupon());
        }
        couponAdapter=new CouponAdapter(coupons);
        rv_item.setLayoutManager(new LinearLayoutManager(getContext()));
        rv_item.setAdapter(couponAdapter);
    }

}
