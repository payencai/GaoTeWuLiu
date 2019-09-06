package com.gaote.wuliu.ui.client.wuliu.model;

import java.io.Serializable;

import cn.ittiger.indexlist.entity.BaseEntity;

public class WuliuCompany implements Serializable , BaseEntity {

    /**
     * createTime : 2019-09-06T03:38:12.049Z
     * exName : string
     * exNo : string
     * id : 0
     * isCancel : 0
     */
    private String picUrl;
    private String createTime;
    private String exName;
    private String exNo;
    private int id;
    private int isCancel;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getExName() {
        return exName;
    }

    public void setExName(String exName) {
        this.exName = exName;
    }

    public String getExNo() {
        return exNo;
    }

    public void setExNo(String exNo) {
        this.exNo = exNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsCancel() {
        return isCancel;
    }

    public void setIsCancel(int isCancel) {
        this.isCancel = isCancel;
    }


    @Override
    public String getIndexField() {
        return exName;
    }
}
