package com.gaote.wuliu.ui.client.mine.mvp.model;

import java.io.Serializable;

public class Address implements Serializable {

    /**
     * id : 631a9863-e2b7-4dab-8942-0ae18f7eb317
     * name : 小马
     * telephone : 13202908144
     * area : 番禺区
     * address : 广东省广州市番禺区大学城内环东路208广州大学城体育中心
     * defaultAddress : 1
     * isCancel : 1
     * userId : 216dac93-e1d3-45ee-ba13-5e623442e8a1
     * lng1 : 23.05429
     * lat1 : 113.39125
     * province : 广东省
     * city : 广州市
     */

    private String id;
    private String name;
    private String telephone;
    private String area;
    private String address;
    private int defaultAddress;
    private int isCancel;
    private String userId;
    private String lng1;
    private String lat1;
    private String province;
    private String city;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(int defaultAddress) {
        this.defaultAddress = defaultAddress;
    }

    public int getIsCancel() {
        return isCancel;
    }

    public void setIsCancel(int isCancel) {
        this.isCancel = isCancel;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getLng1() {
        return lng1;
    }

    public void setLng1(String lng1) {
        this.lng1 = lng1;
    }

    public String getLat1() {
        return lat1;
    }

    public void setLat1(String lat1) {
        this.lat1 = lat1;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
