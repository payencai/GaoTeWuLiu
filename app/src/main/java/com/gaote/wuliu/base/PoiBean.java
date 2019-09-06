package com.gaote.wuliu.base;

public class PoiBean {
    private String address;
    private double lat;
    private String province;
    private String city;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    private String area;
    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    private double lon;
    private String name;

    private boolean isupdateColor=false;

    public boolean isIsupdateColor() {
        return isupdateColor;
    }

    public void setIsupdateColor(boolean isupdateColor) {
        this.isupdateColor = isupdateColor;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "PoiBean{" +
                "address='" + address + '\'' +
                ", lat=" + lat +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", area='" + area + '\'' +
                ", lon=" + lon +
                ", name='" + name + '\'' +
                ", isupdateColor=" + isupdateColor +
                '}';
    }
}
