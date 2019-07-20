package com.gaote.wuliu.ui.client.mine;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gaote.wuliu.R;
import com.gaote.wuliu.tools.CheckDoubleClick;
import com.gaote.wuliu.ui.login.LoginActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends Fragment {


    public MineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick({R.id.tv_login, R.id.ll_settings, R.id.iv_head})
    void OnClcik(View view) {
        if (CheckDoubleClick.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.iv_head:
                startActivity(new Intent(getContext(), UserInfoActivity.class));
                break;
            case R.id.ll_settings:
                startActivity(new Intent(getContext(), SettingsActivity.class));
                break;
            case R.id.tv_login:
                startActivity(new Intent(getContext(), LoginActivity.class));
                break;
        }
    }
}
