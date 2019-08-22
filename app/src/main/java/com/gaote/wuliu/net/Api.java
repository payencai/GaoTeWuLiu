package com.gaote.wuliu.net;

/**
 * 作者：凌涛 on 2019/5/5 11:17
 * 邮箱：771548229@qq..com
 */
public class Api {
    public static final String BASE_URL = "http://120.79.176.228:8080/hightde";
    public static final String SERVER_TYPE_CODE = "1";//服务方类型
    public static final String DEMAND_TYPE_CODE = "2";//需求方类型
    public static final String LOGIN_CODE = "1";
    public static final String REGISTER_CODE = "2";
    public static final int ROLE_TYPE_DEMAND = 2;
    public static final int ROLE_TYPE_LCL_DRIVER = 3;
    public static final int ROLE_TYPE_LOGISTICS_DRIVER = 4;//物流司机
    public static final int ROLE_TYPE_NET = 5;//网点
    public static final int ROLE_TYPE_DEMAND_AND_LCL = 6;//需求方变成拼货司机，就是6

    public static class PinhuoDriver {

        public static final String[] LCL_DRIVER_ORDER_NAMES = {"全部","待接货","待送货","待签收","已完成"};

        public static final String URL_GET_AVAILABLE_ORDER = "/pdriverOrder/getPdriverOrderByDriver";

        public static final String URL_GET_ALL_ORDER = "/pdriverOrder/getPdriverOrderAllByDriver";

        public static final String URL_DRIVER_UPDATE_ORDER_STATUS = "/pdriverOrder/updatePdriverOrderByDriver";

        public static final String URL_USER_UPDATE_ORDER_STATUS = "/pdriverOrder/updatePdriverOrderCancelByUser";

        public static final String URL_FINISH_ORDER_BY_USER = "/pdriverOrder/updatePdriverOrderByUser";
    }
    public static class Manage {
        public static final String URL_GET_VERIFY_CODE = "/manage/getVerificationCode.do";

        public static final String URL_REGISTER = "/manage/register.do";

        public static final String URL_LOGIN = "/manage/login.do";
    }

    public static class Address {
        public static final String URL_ADD_NEW_ADDRESS = "/address/addReceiverAddress";

        public static final String URL_DELETE_ADDRESS = "/address/deleteAddress";

        public static final String URL_GET_DEFAULT_ADDRESS = "/address/getDefaultAddress";

        public static final String URL_GET_ALL_ADDRESS = "/address/getReceiverAddress";

        public static final String URL_UPDATE_ADDRESS = "/address/updateAddress";

        public static final String URL_UPDATE_DEFAULT_ADDRESS = "/address/updateDefaultAddress";
    }

    public static class Client {
        public static final String getUserInfo = "/demander/info/get";
    }
    public static class Wuliu{
        public static final String getAllWuliu = "/logisticsCompany/getAll";
        public static final String getWuliuNetworks = "/wdriver/order/getNetworks";
    }
    public static class Order {
        public static final String URL_DEMAND_GET_LCL_ORDER = "/pdriverOrder/getPdriverOrderByUser";

        public static final String URL_DEMAND_GET_LOG_ORDER = "/demander/worder/getMyOrders";
        public static final String URL_GET_AVAILABLE_ORDER = "/pdriverOrder/getPdriverOrderByDriver";

        public static final String URL_GET_ALL_ORDER = "/pdriverOrder/getPdriverOrderAllByDriver";

        public static final String URL_DRIVER_UPDATE_ORDER_STATUS = "/pdriverOrder/updatePdriverOrderByDriver";

        public static final String URL_USER_ORDER_CANCEL = "/pdriverOrder/updatePdriverOrderCancelByUser";

        public static final String URL_FINISH_ORDER_BY_USER = "/pdriverOrder/updatePdriverOrderByUser";


    }
    public static final class Net{
        public static final String URL_ADD_ORDER = "/network/order/add";

        public static final String URL_CONFIRM_ORDER = "/network/order/confirm";

        public static final String URL_GET_CONFIRMED_ORDER = "/network/order/getConfirmedOrders";

        public static final String URL_GET_NOT_CONFIRMED_ORDER = "/network/order/getNotConfirmOrders";

        public static final String URL_GET_UNARRIVED_ORDERS = "/network/order/getUnArrivedOrders";

        public static final String URL_TAKE_ON_ORDER = "/network/order/takeOn";

        public static final String URL_UPLOAD_IMAGE = "/image/uploadImage";

        public static final String URL_GET_INFO = "/network/info/get";
        public static final String URL_UPLOAD_PORTRAINT = "/network/info/uploadPortrait";
        public static final String URL_UPDATE_NAME = "/network/info/updateName";
        public static final String URL_UPDATE_ADDRESS = "/network/info/updateAdress";
    }
    public static final String URL_UPLOAD_IMAGE = "/image/uploadImage";

    public static final String URL_COMMIT_LCL_DRIVER_INFORM = "/driverApply/commit";
    public static final String URL_GET_INFO = "/demander/info/get";
    public static final String URL_UPLOAD_PORTRAINT = "/demander/info/uploadPortraint";
    public static final String URL_UPDATE_NAME = "/demander/info/updateName";
    public static final String URL_UPDATE_SEX = "/demander/info/updateSex";
    public static final String URL_GET_NOTICE = "/demander/info/getNotice";
    public static final String URL_FEEDBACK_APPLY = "/demander/feedback/apply";
    public static class WuliuDriver {
        public static final String[] LOGISTICS_DRIVER_ORDER_NAMES = {"全部","待确认","待送达","已完成"};

        public static final String URL_GET_NET_LIST = "/logistics/network/getList";

        public static final String URL_GET_NET_ORDER_LIST = "/wdriver/order/getNetworks";

        public static final String URL_GET_MY_ORDER = "/wdriver/order/getMyOrders";

//    public static final String URL_GET_MY_FINISHED_ORDER = "/wdriver/order/getMyFinishedOrders";

        public static final String URL_CONFIRM_ORDER = "/wdriver/order/pick";

        public static final String URL_DILIVER = "/wdriver/order/arrive";

        public static final String URL_GET_NET_DETAIL_LIST = "/wdriver/order/getNetworkOrder";

        public static final String URL_TAKE_ORDER = "/wdriver/order/takeorder";
        public static final String URL_LOGISTICS_DRIVER_INFORM = "/wdriver/info/get";

        public static final int TYPE_ALL = 1;
        public static final int TYPE_TO_CONFIRM = 2;//待确认
        public static final int TYPE_TO_DELEVER = 3;
        public static final int TYPE_FINISHED = 4;


    }
}
