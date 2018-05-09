package com.cctv.ssfs.entity;

import java.util.List;

/**
 * Created by qi on 2018/3/26.
 */

public class SearchBean {

    @Override
    public String toString() {
        return "SearchBean{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }

    /**
     * code : 40000
     * message : ok
     * data : [{"stockType":"A","market":"sh","name":"中国平安","state":1,"currcapital":"1083266.4498","profit_four":"722.0400","code":"601318","totalcapital":"1828024.141","mgjzc":"24.522975","pinyin":"zgpa","listing_date":"2007-03-01","ct":"2016-10-16 15:39:26.371"},{"stockType":"A","market":"sz","name":"平安银行","state":1,"currcapital":"1691798.3372","profit_four":"231.8900","code":"000001","totalcapital":"1717041.1366","mgjzc":"11.770588","pinyin":"payh","listing_date":"1991-04-03","ct":"2016-10-16 15:40:36.232"}]
     */

    private String code;
    private String message;
    private List<DataBean> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * stockType : A
         * market : sh
         * name : 中国平安
         * state : 1
         * currcapital : 1083266.4498
         * profit_four : 722.0400
         * code : 601318
         * totalcapital : 1828024.141
         * mgjzc : 24.522975
         * pinyin : zgpa
         * listing_date : 2007-03-01
         * ct : 2016-10-16 15:39:26.371
         */

        private String stockType;
        private String market;
        private String name;
        private int state;
        private String currcapital;
        private String profit_four;
        private String code;
        private String totalcapital;
        private String mgjzc;
        private String pinyin;
        private String listing_date;
        private String ct;

        public String getStockType() {
            return stockType;
        }

        public void setStockType(String stockType) {
            this.stockType = stockType;
        }

        public String getMarket() {
            return market;
        }

        public void setMarket(String market) {
            this.market = market;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getCurrcapital() {
            return currcapital;
        }

        public void setCurrcapital(String currcapital) {
            this.currcapital = currcapital;
        }

        public String getProfit_four() {
            return profit_four;
        }

        public void setProfit_four(String profit_four) {
            this.profit_four = profit_four;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getTotalcapital() {
            return totalcapital;
        }

        public void setTotalcapital(String totalcapital) {
            this.totalcapital = totalcapital;
        }

        public String getMgjzc() {
            return mgjzc;
        }

        public void setMgjzc(String mgjzc) {
            this.mgjzc = mgjzc;
        }

        public String getPinyin() {
            return pinyin;
        }

        public void setPinyin(String pinyin) {
            this.pinyin = pinyin;
        }

        public String getListing_date() {
            return listing_date;
        }

        public void setListing_date(String listing_date) {
            this.listing_date = listing_date;
        }

        public String getCt() {
            return ct;
        }

        public void setCt(String ct) {
            this.ct = ct;
        }
    }
}
