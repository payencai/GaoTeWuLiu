package com.gaote.wuliu.bean;

import java.io.Serializable;

public class AddrBean implements Serializable {
    private String firaddr="";
    private String secaddr="";
    private double lat=0.0;
    private String name="";
    private String province;
    private String city;

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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    private String area;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    private String phone;
    public String getFiraddr() {
        return firaddr;
    }

    public void setFiraddr(String firaddr) {
        this.firaddr = firaddr;
    }

    private double lon=0.0;

    public String getSecaddr() {
        return secaddr;
    }

    public void setSecaddr(String secaddr) {
        this.secaddr = secaddr;
    }

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
}
