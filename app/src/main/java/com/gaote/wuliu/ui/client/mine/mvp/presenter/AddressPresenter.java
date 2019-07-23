package com.gaote.wuliu.ui.client.mine.mvp.presenter;

import com.gaote.wuliu.ui.client.mine.mvp.model.Address;
import com.gaote.wuliu.ui.client.mine.mvp.model.AddressModel;
import com.gaote.wuliu.ui.client.mine.mvp.model.AddressRequest;
import com.gaote.wuliu.ui.client.mine.mvp.view.AddressView;

import java.util.List;

public class AddressPresenter implements AddressModel.AddressResult{
    AddressView addressView;
    AddressModel addressModel;

    public AddressPresenter(AddressView addressView, AddressModel addressModel) {
        this.addressView = addressView;
        this.addressModel = addressModel;
    }
    public void getAddress(){
        addressView.showLoading();
        addressModel.getAddress(this);
    }
    public void deleteAddress(String id){
        addressView.showLoading();
        addressModel.delAddress(id,this);
    }
    public void setDefault(String id){
        addressView.showLoading();
        addressModel.setDefaultAddress(id,this);
    }
    public void addAddress(AddressRequest addressRequest){
        addressModel.addAddress(addressRequest,this);
    }
    public void aupdateAddress(AddressRequest addressRequest){
        addressModel.updateAddress(addressRequest,this);
    }
    @Override
    public void onResult(List<Address> addresses) {
        addressView.dissLoading();
        addressView.setData(addresses);
    }

    @Override
    public void onDelete() {
        addressView.dissLoading();
        addressView.afterDelete();
    }

    @Override
    public void onDefault() {
        addressView.dissLoading();
        addressView.afterDefault();
    }

    @Override
    public void onAdd() {
        addressView.onAdd();
    }

    @Override
    public void onUpdate() {
        addressView.onUpdate();
    }
}
