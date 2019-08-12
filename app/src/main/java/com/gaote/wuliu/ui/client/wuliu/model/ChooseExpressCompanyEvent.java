package com.gaote.wuliu.ui.client.wuliu.model;

public class ChooseExpressCompanyEvent {
    public String companyName;
    public String companyId;

    public ChooseExpressCompanyEvent(String companyName, String companyId) {
        this.companyName = companyName;
        this.companyId = companyId;
    }
}
