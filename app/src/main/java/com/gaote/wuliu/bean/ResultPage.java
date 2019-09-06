package com.gaote.wuliu.bean;

import java.util.List;

public class ResultPage<T> {

    private DataBean<T> data;
    private String message;
    private int resultCode;

    public DataBean<T> getData() {
        return data;
    }

    public void setData(DataBean<T> data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getResultCode() {
        return resultCode;
    }

    public void setResultCode(int resultCode) {
        this.resultCode = resultCode;
    }

    public static class DataBean<T> {


        private int page;
        private int pageSize;
        private int tp;
        private int tr;
        private List<T> beanList;

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
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

        public List<T> getBeanList() {
            return beanList;
        }

        public void setBeanList(List<T> beanList) {
            this.beanList = beanList;
        }


    }
}
