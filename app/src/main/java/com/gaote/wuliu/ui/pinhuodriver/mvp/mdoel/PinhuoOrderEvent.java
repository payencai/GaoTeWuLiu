package com.gaote.wuliu.ui.pinhuodriver.mvp.mdoel;

public class PinhuoOrderEvent {
    public int code;
    public int type ;

    public PinhuoOrderEvent(int code, int type) {
        this.code = code;
        this.type = type;
    }
}
