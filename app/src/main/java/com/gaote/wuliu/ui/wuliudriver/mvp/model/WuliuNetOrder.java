package com.gaote.wuliu.ui.wuliudriver.mvp.model;

import java.util.List;

public class WuliuNetOrder {

    /**
     * pageNum : 1
     * pageSize : 15
     * size : 1
     * startRow : 1
     * endRow : 1
     * total : 1
     * pages : 1
     * list : [{"driverStatus":null,"updateTime":null,"toNetworkId":null,"toNetworkName":null,"toNetworkAdress":null,"toNetworkLatitude":null,"toNetworkLongitude":null,"driverIsFinished":"0","confirmTime":null,"warehouseName":"高特仓库","isConfirm":null,"networkName":"1","logisticsCompanyId":null,"logisticsCompanyName":null,"distanceTotal":0.06,"distanceDriver":871.6,"id":"08402896-98c9-467d-be84-19b6026a6ef9","networkId":"48ca0583-cfe2-49c8-be53-b9be7d43eec9","driverId":null,"demanderId":"3a7e5f5a-0b00-419a-9246-9b0aee9efec7","demanderTelnum":"18813293138","receiverTelnum":"15512345678","orderNumber":"201802240618340090","networkAdressLatitude":"23.138494","networkAdressLongitude":"113.220381","networkAdress":"1","warehouseAdress":"广州大学城","warehouseAdressLatitude":"39.976004","warehouseAdressLongitude":"116.611813","nameFrom":"哈哈哈","adressFrom":"广东省广州市越秀区东风中路259号中山纪念堂","adressFromProvince":"广东省","adressFromCity":"广州市","adressFromDistrict":"越秀区","adressFromLongitude":"113.26571","adressFromLatitude":"23.131793","nameTo":"那你呢","adressTo":"广东省广州市越秀区东风中路305号广东省政府","adressToProvince":"广东省","adressToCity":"广州市","adressToDistrict":null,"adressToLongitude":"113.26627","adressToLatitude":"23.13171","goodsName":"搞搞搞","goodsMass":25,"goodsSize":2,"goodsQuantity":5,"freight":null,"paymentMethod":"1","createTime":"2018-02-24 18:18:34","takeorderTime":null,"pickTime":null,"reachwarehouseTime":null,"arriveTime":null,"signTime":null,"status":"1"}]
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
     * firstPage : 1
     * lastPage : 1
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
    private int firstPage;
    private int lastPage;
    private List<ListBean> list;
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

    public int getFirstPage() {
        return firstPage;
    }

    public void setFirstPage(int firstPage) {
        this.firstPage = firstPage;
    }

    public int getLastPage() {
        return lastPage;
    }

    public void setLastPage(int lastPage) {
        this.lastPage = lastPage;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public List<Integer> getNavigatepageNums() {
        return navigatepageNums;
    }

    public void setNavigatepageNums(List<Integer> navigatepageNums) {
        this.navigatepageNums = navigatepageNums;
    }

    public static class ListBean {
        /**
         * driverStatus : null
         * updateTime : null
         * toNetworkId : null
         * toNetworkName : null
         * toNetworkAdress : null
         * toNetworkLatitude : null
         * toNetworkLongitude : null
         * driverIsFinished : 0
         * confirmTime : null
         * warehouseName : 高特仓库
         * isConfirm : null
         * networkName : 1
         * logisticsCompanyId : null
         * logisticsCompanyName : null
         * distanceTotal : 0.06
         * distanceDriver : 871.6
         * id : 08402896-98c9-467d-be84-19b6026a6ef9
         * networkId : 48ca0583-cfe2-49c8-be53-b9be7d43eec9
         * driverId : null
         * demanderId : 3a7e5f5a-0b00-419a-9246-9b0aee9efec7
         * demanderTelnum : 18813293138
         * receiverTelnum : 15512345678
         * orderNumber : 201802240618340090
         * networkAdressLatitude : 23.138494
         * networkAdressLongitude : 113.220381
         * networkAdress : 1
         * warehouseAdress : 广州大学城
         * warehouseAdressLatitude : 39.976004
         * warehouseAdressLongitude : 116.611813
         * nameFrom : 哈哈哈
         * adressFrom : 广东省广州市越秀区东风中路259号中山纪念堂
         * adressFromProvince : 广东省
         * adressFromCity : 广州市
         * adressFromDistrict : 越秀区
         * adressFromLongitude : 113.26571
         * adressFromLatitude : 23.131793
         * nameTo : 那你呢
         * adressTo : 广东省广州市越秀区东风中路305号广东省政府
         * adressToProvince : 广东省
         * adressToCity : 广州市
         * adressToDistrict : null
         * adressToLongitude : 113.26627
         * adressToLatitude : 23.13171
         * goodsName : 搞搞搞
         * goodsMass : 25.0
         * goodsSize : 2.0
         * goodsQuantity : 5
         * freight : null
         * paymentMethod : 1
         * createTime : 2018-02-24 18:18:34
         * takeorderTime : null
         * pickTime : null
         * reachwarehouseTime : null
         * arriveTime : null
         * signTime : null
         * status : 1
         */

        private String driverStatus;
        private String updateTime;
        private String toNetworkId;
        private String toNetworkName;
        private String toNetworkAdress;
        private String toNetworkLatitude;
        private String toNetworkLongitude;
        private String driverIsFinished;
        private String confirmTime;
        private String warehouseName;
        private String isConfirm;
        private String networkName;
        private String logisticsCompanyId;
        private String logisticsCompanyName;
        private double distanceTotal;
        private double distanceDriver;
        private String id;
        private String networkId;
        private String driverId;
        private String demanderId;
        private String demanderTelnum;
        private String receiverTelnum;
        private String orderNumber;
        private String networkAdressLatitude;
        private String networkAdressLongitude;
        private String networkAdress;
        private String warehouseAdress;
        private String warehouseAdressLatitude;
        private String warehouseAdressLongitude;
        private String nameFrom;
        private String adressFrom;
        private String adressFromProvince;
        private String adressFromCity;
        private String adressFromDistrict;
        private String adressFromLongitude;
        private String adressFromLatitude;
        private String nameTo;
        private String adressTo;
        private String adressToProvince;
        private String adressToCity;
        private String adressToDistrict;
        private String adressToLongitude;
        private String adressToLatitude;
        private String goodsName;
        private double goodsMass;
        private double goodsSize;
        private int goodsQuantity;
        private String freight;
        private String paymentMethod;
        private String createTime;
        private String takeorderTime;
        private String pickTime;
        private String reachwarehouseTime;
        private String arriveTime;
        private String signTime;
        private String status;

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }

        private boolean isCheck = false;


        public String getDriverStatus() {
            return driverStatus;
        }

        public void setDriverStatus(String driverStatus) {
            this.driverStatus = driverStatus;
        }

        public String getUpdateTime() {
            return updateTime;
        }

        public void setUpdateTime(String updateTime) {
            this.updateTime = updateTime;
        }

        public String getToNetworkId() {
            return toNetworkId;
        }

        public void setToNetworkId(String toNetworkId) {
            this.toNetworkId = toNetworkId;
        }

        public String getToNetworkName() {
            return toNetworkName;
        }

        public void setToNetworkName(String toNetworkName) {
            this.toNetworkName = toNetworkName;
        }

        public String getToNetworkAdress() {
            return toNetworkAdress;
        }

        public void setToNetworkAdress(String toNetworkAdress) {
            this.toNetworkAdress = toNetworkAdress;
        }

        public String getToNetworkLatitude() {
            return toNetworkLatitude;
        }

        public void setToNetworkLatitude(String toNetworkLatitude) {
            this.toNetworkLatitude = toNetworkLatitude;
        }

        public String getToNetworkLongitude() {
            return toNetworkLongitude;
        }

        public void setToNetworkLongitude(String toNetworkLongitude) {
            this.toNetworkLongitude = toNetworkLongitude;
        }

        public String getDriverIsFinished() {
            return driverIsFinished;
        }

        public void setDriverIsFinished(String driverIsFinished) {
            this.driverIsFinished = driverIsFinished;
        }

        public String getConfirmTime() {
            return confirmTime;
        }

        public void setConfirmTime(String confirmTime) {
            this.confirmTime = confirmTime;
        }

        public String getWarehouseName() {
            return warehouseName;
        }

        public void setWarehouseName(String warehouseName) {
            this.warehouseName = warehouseName;
        }

        public String getIsConfirm() {
            return isConfirm;
        }

        public void setIsConfirm(String isConfirm) {
            this.isConfirm = isConfirm;
        }

        public String getNetworkName() {
            return networkName;
        }

        public void setNetworkName(String networkName) {
            this.networkName = networkName;
        }

        public String getLogisticsCompanyId() {
            return logisticsCompanyId;
        }

        public void setLogisticsCompanyId(String logisticsCompanyId) {
            this.logisticsCompanyId = logisticsCompanyId;
        }

        public String getLogisticsCompanyName() {
            return logisticsCompanyName;
        }

        public void setLogisticsCompanyName(String logisticsCompanyName) {
            this.logisticsCompanyName = logisticsCompanyName;
        }

        public double getDistanceTotal() {
            return distanceTotal;
        }

        public void setDistanceTotal(double distanceTotal) {
            this.distanceTotal = distanceTotal;
        }

        public double getDistanceDriver() {
            return distanceDriver;
        }

        public void setDistanceDriver(double distanceDriver) {
            this.distanceDriver = distanceDriver;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNetworkId() {
            return networkId;
        }

        public void setNetworkId(String networkId) {
            this.networkId = networkId;
        }

        public String getDriverId() {
            return driverId;
        }

        public void setDriverId(String driverId) {
            this.driverId = driverId;
        }

        public String getDemanderId() {
            return demanderId;
        }

        public void setDemanderId(String demanderId) {
            this.demanderId = demanderId;
        }

        public String getDemanderTelnum() {
            return demanderTelnum;
        }

        public void setDemanderTelnum(String demanderTelnum) {
            this.demanderTelnum = demanderTelnum;
        }

        public String getReceiverTelnum() {
            return receiverTelnum;
        }

        public void setReceiverTelnum(String receiverTelnum) {
            this.receiverTelnum = receiverTelnum;
        }

        public String getOrderNumber() {
            return orderNumber;
        }

        public void setOrderNumber(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        public String getNetworkAdressLatitude() {
            return networkAdressLatitude;
        }

        public void setNetworkAdressLatitude(String networkAdressLatitude) {
            this.networkAdressLatitude = networkAdressLatitude;
        }

        public String getNetworkAdressLongitude() {
            return networkAdressLongitude;
        }

        public void setNetworkAdressLongitude(String networkAdressLongitude) {
            this.networkAdressLongitude = networkAdressLongitude;
        }

        public String getNetworkAdress() {
            return networkAdress;
        }

        public void setNetworkAdress(String networkAdress) {
            this.networkAdress = networkAdress;
        }

        public String getWarehouseAdress() {
            return warehouseAdress;
        }

        public void setWarehouseAdress(String warehouseAdress) {
            this.warehouseAdress = warehouseAdress;
        }

        public String getWarehouseAdressLatitude() {
            return warehouseAdressLatitude;
        }

        public void setWarehouseAdressLatitude(String warehouseAdressLatitude) {
            this.warehouseAdressLatitude = warehouseAdressLatitude;
        }

        public String getWarehouseAdressLongitude() {
            return warehouseAdressLongitude;
        }

        public void setWarehouseAdressLongitude(String warehouseAdressLongitude) {
            this.warehouseAdressLongitude = warehouseAdressLongitude;
        }

        public String getNameFrom() {
            return nameFrom;
        }

        public void setNameFrom(String nameFrom) {
            this.nameFrom = nameFrom;
        }

        public String getAdressFrom() {
            return adressFrom;
        }

        public void setAdressFrom(String adressFrom) {
            this.adressFrom = adressFrom;
        }

        public String getAdressFromProvince() {
            return adressFromProvince;
        }

        public void setAdressFromProvince(String adressFromProvince) {
            this.adressFromProvince = adressFromProvince;
        }

        public String getAdressFromCity() {
            return adressFromCity;
        }

        public void setAdressFromCity(String adressFromCity) {
            this.adressFromCity = adressFromCity;
        }

        public String getAdressFromDistrict() {
            return adressFromDistrict;
        }

        public void setAdressFromDistrict(String adressFromDistrict) {
            this.adressFromDistrict = adressFromDistrict;
        }

        public String getAdressFromLongitude() {
            return adressFromLongitude;
        }

        public void setAdressFromLongitude(String adressFromLongitude) {
            this.adressFromLongitude = adressFromLongitude;
        }

        public String getAdressFromLatitude() {
            return adressFromLatitude;
        }

        public void setAdressFromLatitude(String adressFromLatitude) {
            this.adressFromLatitude = adressFromLatitude;
        }

        public String getNameTo() {
            return nameTo;
        }

        public void setNameTo(String nameTo) {
            this.nameTo = nameTo;
        }

        public String getAdressTo() {
            return adressTo;
        }

        public void setAdressTo(String adressTo) {
            this.adressTo = adressTo;
        }

        public String getAdressToProvince() {
            return adressToProvince;
        }

        public void setAdressToProvince(String adressToProvince) {
            this.adressToProvince = adressToProvince;
        }

        public String getAdressToCity() {
            return adressToCity;
        }

        public void setAdressToCity(String adressToCity) {
            this.adressToCity = adressToCity;
        }

        public String getAdressToDistrict() {
            return adressToDistrict;
        }

        public void setAdressToDistrict(String adressToDistrict) {
            this.adressToDistrict = adressToDistrict;
        }

        public String getAdressToLongitude() {
            return adressToLongitude;
        }

        public void setAdressToLongitude(String adressToLongitude) {
            this.adressToLongitude = adressToLongitude;
        }

        public String getAdressToLatitude() {
            return adressToLatitude;
        }

        public void setAdressToLatitude(String adressToLatitude) {
            this.adressToLatitude = adressToLatitude;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public double getGoodsMass() {
            return goodsMass;
        }

        public void setGoodsMass(double goodsMass) {
            this.goodsMass = goodsMass;
        }

        public double getGoodsSize() {
            return goodsSize;
        }

        public void setGoodsSize(double goodsSize) {
            this.goodsSize = goodsSize;
        }

        public int getGoodsQuantity() {
            return goodsQuantity;
        }

        public void setGoodsQuantity(int goodsQuantity) {
            this.goodsQuantity = goodsQuantity;
        }

        public String getFreight() {
            return freight;
        }

        public void setFreight(String freight) {
            this.freight = freight;
        }

        public String getPaymentMethod() {
            return paymentMethod;
        }

        public void setPaymentMethod(String paymentMethod) {
            this.paymentMethod = paymentMethod;
        }

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getTakeorderTime() {
            return takeorderTime;
        }

        public void setTakeorderTime(String takeorderTime) {
            this.takeorderTime = takeorderTime;
        }

        public String getPickTime() {
            return pickTime;
        }

        public void setPickTime(String pickTime) {
            this.pickTime = pickTime;
        }

        public String getReachwarehouseTime() {
            return reachwarehouseTime;
        }

        public void setReachwarehouseTime(String reachwarehouseTime) {
            this.reachwarehouseTime = reachwarehouseTime;
        }

        public String getArriveTime() {
            return arriveTime;
        }

        public void setArriveTime(String arriveTime) {
            this.arriveTime = arriveTime;
        }

        public String getSignTime() {
            return signTime;
        }

        public void setSignTime(String signTime) {
            this.signTime = signTime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }
}
