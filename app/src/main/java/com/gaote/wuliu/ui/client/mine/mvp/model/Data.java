package com.gaote.wuliu.ui.client.mine.mvp.model;



import java.io.Serializable;
import java.util.List;

/**
 * Created by ckerv on 2018/2/8.
 */

public class Data {

    /**
     * beanList : [{"address":{"address":"string","area":"string","city":"string","defaultAddress":0,"id":"string","isCancel":0,"lat1":"string","lng1":"string","name":"string","province":"string","telephone":"string","userId":"string"},"anticipantCar":"string","anticipantTime":"2018-02-07T12:18:33.240Z","articleName":"string","consignee":"string","consigneeAddress":"string","consigneeArea":"string","consigneeCity":"string","consigneeProvince":"string","consigneeTelephone":"string","distance":"string","driverId":"string","driverTelephone":"string","endTime":"2018-02-07T12:18:33.240Z","getTime":"2018-02-07T12:18:33.240Z","id":"string","isDel":"string","num":0,"orderTime":"2018-02-07T12:18:33.240Z","pickupAddress":"string","pickupTime":"2018-02-07T12:18:33.240Z","receiverAddressId":"string","sendTime":"2018-02-07T12:18:33.240Z","type":"string","userId":"string","volume":0,"weight":0}]
     * pageNum : 0
     * pageSize : 0
     * tp : 0
     * tr : 0
     */

    private int pageNum;
    private int pageSize;
    private int tp;
    private int tr;
    private List<PinhuoOrder> beanList;

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTp() {
        return tp;
    }

    public void setTp(int tp) {
        this.tp = tp;
    }

    public int getTr() {
        return tr;
    }

    public void setTr(int tr) {
        this.tr = tr;
    }

    public List<PinhuoOrder> getBeanList() {
        return beanList;
    }

    public void setBeanList(List<PinhuoOrder> beanList) {
        this.beanList = beanList;
    }


}
