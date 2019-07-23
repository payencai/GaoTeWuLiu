package com.gaote.wuliu.ui.client.mine.mvp.model;

import java.io.Serializable;

public class PinhuoOrder implements Serializable {


    /**
     * id : 3c28391d-b339-47de-ac5b-d241a3a73b0e
     * receiverAddressId : 631a9863-e2b7-4dab-8942-0ae18f7eb317
     * consignee : 小马
     * consigneeTelephone : 13202908144
     * consigneeArea : 番禺区
     * consigneeAddress : 广东省广州市番禺区大学城内环东路208广州大学城体育中心
     * num : 3
     * weight : 2
     * volume : 32
     * articleName : 的方法
     * pickupAddress : 4天天头疼
     * anticipantCar : 中货车
     * anticipantTime : 2019-07-20 15:44:00
     * type : 2
     * orderTime : 2019-07-20 15:45:09
     * pickupTime : null
     * endTime : null
     * sendTime : null
     * isDel : 1
     * getTime : 2019-07-21 11:18:19
     * distance : 0.0
     * consigneeProvince : 广东省
     * consigneeCity : 广州市
     * address : {"id":"631a9863-e2b7-4dab-8942-0ae18f7eb317","name":"小医院","telephone":"13202908144","area":"番禺区","address":"广东省广州市番禺区大学城内环东路208广州大学城体育中心","defaultAddress":2,"isCancel":2,"userId":"216dac93-e1d3-45ee-ba13-5e623442e8a1","lng1":"23.05429","lat1":"113.39125","province":"广东省","city":"广州市"}
     * driverTelephone : 13202908144
     * userId : 216dac93-e1d3-45ee-ba13-5e623442e8a1
     * driverId : 0f9c0e25-9a88-49e3-ae6f-34193197bdd7
     * remarks : null
     */

    private String id;
    private String receiverAddressId;
    private String consignee;
    private String consigneeTelephone;
    private String consigneeArea;
    private String consigneeAddress;
    private int num;
    private int weight;
    private int volume;
    private String articleName;
    private String pickupAddress;
    private String anticipantCar;
    private String anticipantTime;
    private String type;
    private String orderTime;
    private Object pickupTime;
    private Object endTime;
    private Object sendTime;
    private String isDel;
    private String getTime;
    private String distance;
    private String consigneeProvince;
    private String consigneeCity;
    private AddressBean address;
    private String driverTelephone;
    private String userId;
    private String driverId;
    private Object remarks;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReceiverAddressId() {
        return receiverAddressId;
    }

    public void setReceiverAddressId(String receiverAddressId) {
        this.receiverAddressId = receiverAddressId;
    }

    public String getConsignee() {
        return consignee;
    }

    public void setConsignee(String consignee) {
        this.consignee = consignee;
    }

    public String getConsigneeTelephone() {
        return consigneeTelephone;
    }

    public void setConsigneeTelephone(String consigneeTelephone) {
        this.consigneeTelephone = consigneeTelephone;
    }

    public String getConsigneeArea() {
        return consigneeArea;
    }

    public void setConsigneeArea(String consigneeArea) {
        this.consigneeArea = consigneeArea;
    }

    public String getConsigneeAddress() {
        return consigneeAddress;
    }

    public void setConsigneeAddress(String consigneeAddress) {
        this.consigneeAddress = consigneeAddress;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public String getPickupAddress() {
        return pickupAddress;
    }

    public void setPickupAddress(String pickupAddress) {
        this.pickupAddress = pickupAddress;
    }

    public String getAnticipantCar() {
        return anticipantCar;
    }

    public void setAnticipantCar(String anticipantCar) {
        this.anticipantCar = anticipantCar;
    }

    public String getAnticipantTime() {
        return anticipantTime;
    }

    public void setAnticipantTime(String anticipantTime) {
        this.anticipantTime = anticipantTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public Object getPickupTime() {
        return pickupTime;
    }

    public void setPickupTime(Object pickupTime) {
        this.pickupTime = pickupTime;
    }

    public Object getEndTime() {
        return endTime;
    }

    public void setEndTime(Object endTime) {
        this.endTime = endTime;
    }

    public Object getSendTime() {
        return sendTime;
    }

    public void setSendTime(Object sendTime) {
        this.sendTime = sendTime;
    }

    public String getIsDel() {
        return isDel;
    }

    public void setIsDel(String isDel) {
        this.isDel = isDel;
    }

    public String getGetTime() {
        return getTime;
    }

    public void setGetTime(String getTime) {
        this.getTime = getTime;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getConsigneeProvince() {
        return consigneeProvince;
    }

    public void setConsigneeProvince(String consigneeProvince) {
        this.consigneeProvince = consigneeProvince;
    }

    public String getConsigneeCity() {
        return consigneeCity;
    }

    public void setConsigneeCity(String consigneeCity) {
        this.consigneeCity = consigneeCity;
    }

    public AddressBean getAddress() {
        return address;
    }

    public void setAddress(AddressBean address) {
        this.address = address;
    }

    public String getDriverTelephone() {
        return driverTelephone;
    }

    public void setDriverTelephone(String driverTelephone) {
        this.driverTelephone = driverTelephone;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public Object getRemarks() {
        return remarks;
    }

    public void setRemarks(Object remarks) {
        this.remarks = remarks;
    }

    public static class AddressBean implements Serializable{
        /**
         * id : 631a9863-e2b7-4dab-8942-0ae18f7eb317
         * name : 小医院
         * telephone : 13202908144
         * area : 番禺区
         * address : 广东省广州市番禺区大学城内环东路208广州大学城体育中心
         * defaultAddress : 2
         * isCancel : 2
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
}
