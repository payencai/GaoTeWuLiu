package com.gaote.wuliu.ui.client.mine.bean;

public class Coupon {

    /**
     * coupon : {"createTime":"2019-09-05T09:53:46.574Z","endTime":"2019-09-05T09:53:46.574Z","getNum":0,"getUseDay":0,"id":"string","isDel":0,"name":"string","num":0,"price":0,"startTime":"2019-09-05T09:53:46.574Z","type":0}
     * couponId : string
     * createTime : 2019-09-05T09:53:46.574Z
     * getUseDay : 0
     * id : string
     * isDel : 0
     * isUse : 0
     * name : string
     * overTime : 2019-09-05T09:53:46.574Z
     * price : 0
     * type : 0
     * useTime : 2019-09-05T09:53:46.574Z
     * userId : string
     */

    private CouponBean coupon;
    private String couponId;
    private String createTime;
    private int getUseDay;
    private String id;
    private int isDel;
    private int isUse;
    private String name;
    private String overTime;
    private double price;
    private int type;
    private String useTime;
    private String userId;

    public CouponBean getCoupon() {
        return coupon;
    }

    public void setCoupon(CouponBean coupon) {
        this.coupon = coupon;
    }

    public String getCouponId() {
        return couponId;
    }

    public void setCouponId(String couponId) {
        this.couponId = couponId;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getGetUseDay() {
        return getUseDay;
    }

    public void setGetUseDay(int getUseDay) {
        this.getUseDay = getUseDay;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getIsDel() {
        return isDel;
    }

    public void setIsDel(int isDel) {
        this.isDel = isDel;
    }

    public int getIsUse() {
        return isUse;
    }

    public void setIsUse(int isUse) {
        this.isUse = isUse;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOverTime() {
        return overTime;
    }

    public void setOverTime(String overTime) {
        this.overTime = overTime;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getUseTime() {
        return useTime;
    }

    public void setUseTime(String useTime) {
        this.useTime = useTime;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public static class CouponBean {
        /**
         * createTime : 2019-09-05T09:53:46.574Z
         * endTime : 2019-09-05T09:53:46.574Z
         * getNum : 0
         * getUseDay : 0
         * id : string
         * isDel : 0
         * name : string
         * num : 0
         * price : 0
         * startTime : 2019-09-05T09:53:46.574Z
         * type : 0
         */

        private String createTime;
        private String endTime;
        private int getNum;
        private int getUseDay;
        private String id;
        private int isDel;
        private String name;
        private int num;
        private double price;
        private String startTime;
        private int type;

        public String getCreateTime() {
            return createTime;
        }

        public void setCreateTime(String createTime) {
            this.createTime = createTime;
        }

        public String getEndTime() {
            return endTime;
        }

        public void setEndTime(String endTime) {
            this.endTime = endTime;
        }

        public int getGetNum() {
            return getNum;
        }

        public void setGetNum(int getNum) {
            this.getNum = getNum;
        }

        public int getGetUseDay() {
            return getUseDay;
        }

        public void setGetUseDay(int getUseDay) {
            this.getUseDay = getUseDay;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getIsDel() {
            return isDel;
        }

        public void setIsDel(int isDel) {
            this.isDel = isDel;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public String getStartTime() {
            return startTime;
        }

        public void setStartTime(String startTime) {
            this.startTime = startTime;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }
    }
}
