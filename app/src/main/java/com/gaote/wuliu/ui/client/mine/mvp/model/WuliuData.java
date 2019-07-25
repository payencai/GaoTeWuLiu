package com.gaote.wuliu.ui.client.mine.mvp.model;



import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ckerv on 2018/2/8.
 */

public class WuliuData {


    /**
     * pageNum : 1
     * pageSize : 15
     * size : 1
     * startRow : 1
     * endRow : 1
     * total : 1
     * pages : 1
     * list : [{"driverStatus":"2","updateTime":"2018-06-08 16:07:13","toNetworkId":null,"toNetworkName":null,"toNetworkAdress":null,"toNetworkLatitude":null,"toNetworkLongitude":null,"driverIsFinished":"0","confirmTime":"2018-06-08 16:07:13","warehouseName":"如家快捷酒店(广州番禺大石长隆北门店)","isConfirm":"1","networkName":"123哈哈啊哈","logisticsCompanyId":"8","logisticsCompanyName":"百世","distanceTotal":0,"distanceDriver":1.99,"id":"66db0a23-43ab-4910-ac9b-ac4aeac9eadd","networkId":"8bf3236d-85de-4da4-9f3d-1c6012b6de4c","driverId":"6c46220d-7502-4c66-ae69-b484cd364ec1","demanderId":"f5db40d0-5370-4a5c-8906-149dc2980101","demanderTelnum":"17688947788","receiverTelnum":"13632917995","orderNumber":"201806081134514559","networkAdressLatitude":"23.050192","networkAdressLongitude":"113.33928","networkAdress":"新滘中路168号","warehouseAdress":"大石南大路269号(南大路与欢乐北路交叉口)","warehouseAdressLatitude":"23.010803","warehouseAdressLongitude":"113.330515","nameFrom":"雅虎","adressFrom":"番禺区广州大学城信息枢纽楼","adressFromProvince":"广东省","adressFromCity":"广州市","adressFromDistrict":"番禺区","adressFromLongitude":"113.396957","adressFromLatitude":"23.045219","nameTo":"你是个好人","adressTo":"广东省广州市番禺区大学城","adressToProvince":"广东省","adressToCity":"广州市","adressToDistrict":"番禺区","adressToLongitude":"113.396957","adressToLatitude":"23.045219","goodsName":"去","goodsMass":646,"goodsSize":133,"goodsQuantity":1,"freight":null,"paymentMethod":"1","createTime":"2018-06-08 11:34:51","takeorderTime":"2018-06-08 14:08:29","pickTime":null,"reachwarehouseTime":null,"arriveTime":null,"signTime":null,"status":"2"}]
     * prePage : 0
     * nextPage : 0
     * isFirstPage : true
     * isLastPage : true
     * hasPreviousPage : false
     * hasNextPage : false
     * navigatePages : 2
     * navigatepageNums : [1]
     * navigateFirstPage : 1
     * navigateLastPage : 1
     * lastPage : 1
     * firstPage : 1
     */

    private int pageNum;
    private int pageSize;
    private int size;
    private int startRow;
    private int endRow;
    private int total;
    private int pages;
    private int prePage;
    private int nextPage;
    private boolean isFirstPage;
    private boolean isLastPage;
    private boolean hasPreviousPage;
    private boolean hasNextPage;
    private int navigatePages;
    private int navigateFirstPage;
    private int navigateLastPage;
    private int lastPage;
    private int firstPage;
    @SerializedName("list")
    List<WuliuOrder> wuliuOrders;

    public boolean isFirstPage() {
        return isFirstPage;
    }

    public void setFirstPage(boolean firstPage) {
        isFirstPage = firstPage;
    }

    public boolean isLastPage() {
        return isLastPage;
    }

    public void setLastPage(boolean lastPage) {
        isLastPage = lastPage;
    }

    public List<WuliuOrder> getWuliuOrders() {
        return wuliuOrders;
    }

    public void setWuliuOrders(List<WuliuOrder> wuliuOrders) {
        this.wuliuOrders = wuliuOrders;
    }

    private List<Integer> navigatepageNums;

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

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getStartRow() {
        return startRow;
    }

    public void setStartRow(int startRow) {
        this.startRow = startRow;
    }

    public int getEndRow() {
        return endRow;
    }

    public void setEndRow(int endRow) {
        this.endRow = endRow;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getPrePage() {
        return prePage;
    }

    public void setPrePage(int prePage) {
        this.prePage = prePage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }

    public boolean isIsFirstPage() {
        return isFirstPage;
    }

    public void setIsFirstPage(boolean isFirstPage) {
        this.isFirstPage = isFirstPage;
    }

    public boolean isIsLastPage() {
        return isLastPage;
    }

    public void setIsLastPage(boolean isLastPage) {
        this.isLastPage = isLastPage;
    }

    public boolean isHasPreviousPage() {
        return hasPreviousPage;
    }

    public void setHasPreviousPage(boolean hasPreviousPage) {
        this.hasPreviousPage = hasPreviousPage;
    }

    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public int getNavigatePages() {
        return navigatePages;
    }

    public void setNavigatePages(int navigatePages) {
        this.navigatePages = navigatePages;
    }

    public int getNavigateFirstPage() {
        return navigateFirstPage;
    }

    public void setNavigateFirstPage(int navigateFirstPage) {
        this.navigateFirstPage = navigateFirstPage;
    }

    public int getNavigateLastPage() {
        return navigateLastPage;
    }

    public void setNavigateLastPage(int navigateLastPage) {
        this.navigateLastPage = navigateLastPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public int getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(int firstPage) {
        this.firstPage = firstPage;
    }


    public List<Integer> getNavigatepageNums() {
        return navigatepageNums;
    }

    public void setNavigatepageNums(List<Integer> navigatepageNums) {
        this.navigatepageNums = navigatepageNums;
    }


}
