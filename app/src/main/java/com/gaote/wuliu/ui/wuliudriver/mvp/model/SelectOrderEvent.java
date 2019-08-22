package com.gaote.wuliu.ui.wuliudriver.mvp.model;

public class SelectOrderEvent {
    public boolean isSelected;
    public String orderId;
    public int position;

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public SelectOrderEvent(boolean isSelected, String orderId, int position) {
        this.isSelected = isSelected;
        this.orderId = orderId;
        this.position=position;
    }
}
