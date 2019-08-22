package com.gaote.wuliu.ui.wuliudriver.mvp.model;

import java.io.Serializable;

public class WuliuNet implements Serializable {

    /**
     * adress : string
     * distance : 0
     * id : string
     * latitude : string
     * longitude : string
     * name : string
     * networkPic : string
     * telephone : string
     */

    private String adress;
    private double distance;
    private String id;
    private String latitude;
    private String longitude;
    private String name;
    private String networkPic;
    private String telephone;

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNetworkPic() {
        return networkPic;
    }

    public void setNetworkPic(String networkPic) {
        this.networkPic = networkPic;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
