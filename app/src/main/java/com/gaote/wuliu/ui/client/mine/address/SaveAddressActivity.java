package com.gaote.wuliu.ui.client.mine.address;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.gaote.wuliu.R;
import com.gaote.wuliu.tools.CheckDoubleClick;
import com.gaote.wuliu.ui.client.mine.mvp.model.Address;
import com.gaote.wuliu.ui.client.mine.mvp.model.AddressBean;
import com.gaote.wuliu.ui.client.mine.mvp.model.AddressModel;
import com.gaote.wuliu.ui.client.mine.mvp.model.AddressRequest;
import com.gaote.wuliu.ui.client.mine.mvp.presenter.AddressPresenter;
import com.gaote.wuliu.ui.client.mine.mvp.view.AddressView;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SaveAddressActivity extends AppCompatActivity implements AddressView {
    AddressBean addressBean;
    Address address;
    @BindView(R.id.et_name)
    EditText et_name;
    @BindView(R.id.et_phone)
    EditText et_phone;
    @BindView(R.id.tv_address)
    TextView tv_address;
    @BindView(R.id.et_address)
    EditText et_address;
    @BindView(R.id.ch_default)
    CheckBox ch_default;
    @BindView(R.id.tv_title)
    TextView tv_title;
    AddressPresenter addressPresenter;
    AddressRequest addressRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_address);
        ButterKnife.bind(this);
        address = (Address) getIntent().getSerializableExtra("data");
        initView();
    }

    private void setAddress() {
        et_name.setText(address.getName());
        et_phone.setText(address.getTelephone());
        tv_address.setText(address.getProvince() + address.getCity() + address.getArea());
        et_address.setText(address.getAddress());
        if (address.getDefaultAddress() == 1) {
            ch_default.setChecked(true);
        } else {
            ch_default.setChecked(false);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (data != null) {
            addressBean = (AddressBean) data.getSerializableExtra("address");
            tv_address.setText(addressBean.getProvince() + addressBean.getCityname() + addressBean.getDistrict());
            et_address.setText(addressBean.getPoiaddress());
        }
    }

    @OnClick({R.id.tv_save, R.id.iv_back, R.id.rl_address})
    void OnClick(View view) {
        if (CheckDoubleClick.isFastDoubleClick()) {
            return;
        }
        switch (view.getId()) {
            case R.id.rl_address:
                startActivityForResult(new Intent(SaveAddressActivity.this, WebviewAddressActivity.class), 2);
                break;
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_save:
                if (checkInput()) {
                    setRequestData();
                    if (address == null) {
                        addressPresenter.addAddress(addressRequest);
                    } else {
                        addressPresenter.aupdateAddress(addressRequest);
                    }
                }
                break;
        }
    }

    private void setRequestData() {
        if (ch_default.isChecked()) {
            addressRequest.setDefaultAddress("1");
        } else {
            addressRequest.setDefaultAddress("2");
        }
        addressRequest.setName(et_name.getEditableText().toString());
        addressRequest.setTelephone(et_phone.getEditableText().toString());
        addressRequest.setAddress(et_address.getText().toString());
        if (address == null) {
            addressRequest.setProvince(addressBean.getProvince());
            addressRequest.setCity(addressBean.getCityname());
            addressRequest.setArea(addressBean.getDistrict());
            addressRequest.setLat1(addressBean.getLatlng().getLat() + "");
            addressRequest.setLng1(addressBean.getLatlng().getLng() + "");
        } else {
            addressRequest.setId(address.getId());
            if (addressBean == null) {
                addressRequest.setProvince(address.getProvince());
                addressRequest.setCity(address.getCity());
                addressRequest.setArea(address.getArea());
                addressRequest.setLat1(address.getLat1());
                addressRequest.setLng1(address.getLng1());
            } else {
                addressRequest.setProvince(addressBean.getProvince());
                addressRequest.setCity(addressBean.getCityname());
                addressRequest.setArea(addressBean.getDistrict());
                addressRequest.setLat1(addressBean.getLatlng().getLat() + "");
                addressRequest.setLng1(addressBean.getLatlng().getLng() + "");
            }
        }
    }

    private boolean checkInput() {
        boolean isOk = true;
        if (TextUtils.isEmpty(et_name.getEditableText().toString())) {
            isOk = false;
            ToastUtils.showShort("请输入姓名");
            return isOk;
        }
        if (TextUtils.isEmpty(et_phone.getEditableText().toString())) {
            isOk = false;
            ToastUtils.showShort("请输入手机号");
            return isOk;
        }
        if (TextUtils.isEmpty(et_address.getEditableText().toString())) {
            isOk = false;
            ToastUtils.showShort("请输入详细地址");
            return isOk;
        }
        if (address == null && addressBean == null) {
            isOk = false;
            ToastUtils.showShort("请选择所在地区");
            return isOk;
        }

        return isOk;
    }

    private void initView() {
        addressRequest = new AddressRequest();
        addressPresenter = new AddressPresenter(this, new AddressModel());
        if (address != null) {
            setAddress();
            tv_title.setText("编辑地址");
        } else {
            tv_title.setText("新增地址");
        }
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void dissLoading() {

    }

    @Override
    public void setData(List<Address> addressList) {

    }

    @Override
    public void afterDelete() {

    }

    @Override
    public void afterDefault() {

    }

    @Override
    public void onAdd() {
        EventBus.getDefault().post("address");
        finish();
    }

    @Override
    public void onUpdate() {
        EventBus.getDefault().post("address");
        finish();
    }
}
