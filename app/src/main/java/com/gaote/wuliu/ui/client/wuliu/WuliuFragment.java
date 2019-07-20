package com.gaote.wuliu.ui.client.wuliu;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gaote.wuliu.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class WuliuFragment extends Fragment {


    public WuliuFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_wuliu, container, false);
    }

}
