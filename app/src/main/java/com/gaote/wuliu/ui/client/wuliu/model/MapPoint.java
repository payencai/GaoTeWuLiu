package com.gaote.wuliu.ui.client.wuliu.model;

public class MapPoint {

    /**
     * id : 0d4e3c46-c892-44f3-ad87-579ddc46fd72
     * name : 新哨服务点
     * adress : 云南省红河哈尼族彝族自治州弥勒市新哨镇惠康药店
     * networkPic : http://gaotedianshang.oss-cn-shenzhen.aliyuncs.com/%E4%B8%8A%E4%BC%A0/2018102711125010?Expires=1565604722&OSSAccessKeyId=LTAIjs0fwbrMyqNt&Signature=D5F0ZMnUO%2BmtIXYMbfMJjmr%2F82Q%3D
     * distance : 15.17
     * latitude : 23.059811
     * longitude : 113.397089
     * telephone : 13529814127
     */

    private String id;
    private String name;
    private String adress;
    private String networkPic;
    private double distance;
    private String latitude;
    private String longitude;
    private String telephone;

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

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public String getNetworkPic() {
        return networkPic;
    }

    public void setNetworkPic(String networkPic) {
        this.networkPic = networkPic;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
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

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
}
