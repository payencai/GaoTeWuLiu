package com.gaote.wuliu.ui.client.mine.order;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gaote.wuliu.R;

import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class PinhuoOrderFragment extends Fragment {

    int type = 0;

    public PinhuoOrderFragment() {
        // Required empty public constructor
    }

    public static PinhuoOrderFragment newInstance(int type) {
        PinhuoOrderFragment pinhuoOrderFragment = new PinhuoOrderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        pinhuoOrderFragment.setArguments(bundle);
        return pinhuoOrderFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_pinhuo_order, container, false);
        type=getArguments().getInt("type",0);
        ButterKnife.bind(this, view);
        return view;
    }

}
