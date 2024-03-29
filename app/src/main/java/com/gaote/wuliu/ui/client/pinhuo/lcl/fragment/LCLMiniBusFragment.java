package com.gaote.wuliu.ui.client.pinhuo.lcl.fragment;


import com.gaote.wuliu.R;
import com.gaote.wuliu.ui.client.pinhuo.lcl.constant.LCLConstants;

/**
 * 小面包车
 * Created by ckerv on 2018/1/12.
 */

public class LCLMiniBusFragment extends LCLBaseDetailFragment{
    @Override
    protected int getCarTypeResId() {
        return R.drawable.lcl_xiaomianbaoche;
    }

    @Override
    protected String getCarSize() {
        return "1.8*1.3*1.1米";
    }

    @Override
    protected String getCarVolume() {
        return "2.6方";
    }


    @Override
    protected String getCarWeight() {
        return "500公斤";
    }

    @Override
    protected int getType() {
        return LCLConstants.TYPE_MINI_BUS;
    }

    @Override
    protected String getTypeStr() {
        return LCLConstants.TYPE_STR_MINI_BUS;
    }
}
