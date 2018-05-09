package com.cctv.ssfs.entity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qi on 2018/3/22.
 */

public class HomeBean implements Parcelable {

    /**
     * code : true
     * msg : 请求成功
     * data : {"article":{"total":91,"per_page":10,"current_page":1,"last_page":10,"data":[{"article_id":788,"title":"一直追风口，总是不赶趟！河南这家上市公司又杀入此红海","art_from":" 鉴闻","code":"002535","cover":1,"name":"林州重机","cover_pic":["http://admin.cctvzxjy.com/uploads/acticle/images/20180329/60f1247e702c3233d9987aef7e18d506.jpg"],"time":"4周前","is_top":0,"lable_id":11,"category":0,"art_desc":"","lable_name":"数字风云","url":"http://wind.cctvzxjy.com/detail/788","kind":1},{"article_id":869,"title":"浅谈信立泰2017年业绩快报和黄金坑机遇","art_from":"牛氓的胜利","code":"002294","cover":1,"name":"信立泰","cover_pic":["http://admin.cctvzxjy.com/uploads/acticle/images/20180330/2d246546940233661ba49424aea7e79b.jpg"],"time":"2月前","is_top":0,"lable_id":11,"category":0,"art_desc":"","lable_name":"数字风云","url":"http://wind.cctvzxjy.com/detail/869","kind":1},{"have_id":35,"user_id":10006,"code":"002512","count":3,"praise":11,"content":"还行","time":"2018-03-23 13:51","num":0,"is_big":0,"status":1,"index_page":1,"index_line":3,"user_img":"http://thirdwx.qlogo.cn/mmopen/vi_32/bdEicweK8eBQR0tdDJFt5z4cichxcs3Ky0naJjGz4QN5cbmNOicgLDzbTicYY4D8y4BW9vGMSTrfBQKCmtkuSpHR6g/132","user_name":"sky许","name":"达华智能","kind":2,"stock":{"stock_id":2579,"code":"002512","name":"达华智能","company":"中山达华智能科技股份有限公司","plate":"电子电气组件与设备","pic":"","website":"www.twh.com.cn","status":1,"time":"2010-12-03","flow":"63961.54","area":"广东","legal":"蔡小如","secret":"韩洋","underwriter":"民生证券股份有限公司","address":"广东省中山市小榄镇泰丰工业区水怡南路9号","postcode":"528415","phone":"0760-22550278","fax":"0760-22130941","email":"8888@twh.com.cn","business":"标签卡,包括非接触IC卡和电子标签","company_desc":"公司系由达华有限全体股东为发起人整体变更设立的股份有限公司。2009年5月5日，达华有限召开股东会，决议以达华有限截至2009年3月31日经鹏城会计师事务所审计的净资产89,261,177.88元为基准，按1.116:1的比例折为股本8,000万股，将达华有限整体变更为股份有限公司，余额9,261,177.88元计入资本公积。2009年5月31日，中山市工商局向公司核发注册号为442000000003929的《企业法人营业执照》，核准发行人成立。"}},{"article_id":284,"title":"中国十大家族盘点！唯一财团、华人\u201c经营之父\u201d..李嘉诚排第二","art_from":" FX168","code":"601998","cover":1,"name":"中信银行","cover_pic":["http://admin.cctvzxjy.com/uploads/acticle/images/20180322/245ad8a40a98e14bfafe43f57460d360.jpg"],"time":"2月前","is_top":0,"lable_id":11,"category":0,"art_desc":"","lable_name":"数字风云","url":"http://wind.cctvzxjy.com/detail/284","kind":1},{"article_id":228,"title":"248家上市公司董事长狗年出生 手握近4万亿总市值","art_from":"金融界","code":"600276","cover":1,"name":"恒瑞医药","cover_pic":["http://admin.cctvzxjy.com/uploads/acticle/images/20180322/f0a061effc34935c961e9cdd3df8dcb4.jpg"],"time":"2月前","is_top":0,"lable_id":11,"category":0,"art_desc":"","lable_name":"数字风云","url":"http://wind.cctvzxjy.com/detail/228","kind":1},{"article_id":261,"title":"鸡年收官！28张图纵览A股全貌！","art_from":"东方财富网","code":"601360","cover":2,"name":"三六零","cover_pic":["http://admin.cctvzxjy.com/uploads/acticle/images/20180322/2658400ac6fc1bea60e9a9c7a73a0685.jpg","http://admin.cctvzxjy.com/uploads/acticle/images/20180322/41d2581a8817ef4b3beb8e2b9036c89b.jpg","http://admin.cctvzxjy.com/uploads/acticle/images/20180322/13e1c6f480422116a5720a9155f9a7c1.jpg"],"time":"2月前","is_top":0,"lable_id":11,"category":0,"art_desc":"","lable_name":"数字风云","url":"http://wind.cctvzxjy.com/detail/261","kind":1},{"article_id":232,"title":"舌尖上的A股：3000多家上市公司 哪些是餐饮股","art_from":"腾讯证券","code":"002330","cover":2,"name":"得利斯","cover_pic":["http://admin.cctvzxjy.com/uploads/acticle/images/20180322/ac957ee2776f60742cfc4efd3beb2310.jpg","http://admin.cctvzxjy.com/uploads/acticle/images/20180322/1655d1375b5ae42b81e6914ae9a63a54.jpg","http://admin.cctvzxjy.com/uploads/acticle/images/20180322/b8c073966c1014cd0c71145def2f833d.jpg"],"time":"2月前","is_top":0,"lable_id":11,"category":0,"art_desc":"","lable_name":"数字风云","url":"http://wind.cctvzxjy.com/detail/232","kind":1},{"article_id":229,"title":"这些上市公司狗年春节坐不住 年三十晚还在\u201c活动\u201d！","art_from":"每日经济新闻","code":"601116","cover":1,"name":"三江购物","cover_pic":["http://admin.cctvzxjy.com/uploads/acticle/images/20180322/4588d88ecd746834ae03316cca770916.jpg"],"time":"2月前","is_top":0,"lable_id":11,"category":0,"art_desc":"","lable_name":"数字风云","url":"http://wind.cctvzxjy.com/detail/229","kind":1},{"article_id":235,"title":"16000多家上市公司数据揭示：你家乡这三年的真实变化","art_from":"华尔街见闻","code":"000928","cover":1,"name":"中钢国际","cover_pic":["http://admin.cctvzxjy.com/uploads/acticle/images/20180322/a3b7e6d6a483e99298808e64098e6bf0.jpg"],"time":"2月前","is_top":0,"lable_id":11,"category":0,"art_desc":"","lable_name":"数字风云","url":"http://wind.cctvzxjy.com/detail/235","kind":1},{"article_id":279,"title":"【传闻鉴证】它25年不分红的铁公鸡，两年跌8成被问询十多次！","art_from":"好股互动","code":"600610","cover":1,"name":"中毅达","cover_pic":["http://admin.cctvzxjy.com/uploads/acticle/images/20180322/354931c131b71c64c90bd6fd3c237340.jpg"],"time":"2月前","is_top":0,"lable_id":11,"category":0,"art_desc":"","lable_name":"数字风云","url":"http://wind.cctvzxjy.com/detail/279","kind":1},{"article_id":250,"title":"从上市公司数据读懂我国新兴产业发展态势","art_from":"新软件","code":"000555","cover":2,"name":"神州信息","cover_pic":["http://admin.cctvzxjy.com/uploads/acticle/images/20180322/4b17ddff4cba334d245e9eb3ab70dbcb.jpg","http://admin.cctvzxjy.com/uploads/acticle/images/20180322/92f00501889358dd731a47832023b1af.jpg","http://admin.cctvzxjy.com/uploads/acticle/images/20180322/31aebe6ef46c4c1293ed29143de014d1.jpg"],"time":"2月前","is_top":0,"lable_id":11,"category":0,"art_desc":"","lable_name":"数字风云","url":"http://wind.cctvzxjy.com/detail/250"}]},"hot":[{"hot_id":4,"code":"600519","name":"贵州茅台"},{"hot_id":5,"code":"002069","name":"獐子岛"},{"hot_id":6,"code":"002271","name":"东方雨虹"},{"hot_id":7,"code":"002512","name":"达华智能"},{"hot_id":8,"code":"601360","name":"三六零"},{"hot_id":9,"code":"603939","name":"益丰药房"}],"label":[{"lable_id":8,"name":"风声背后"},{"lable_id":9,"name":"财富秘史"},{"lable_id":10,"name":"交易真相"},{"lable_id":11,"name":"数字风云"},{"lable_id":12,"name":"一线爆料"},{"lable_id":13,"name":"游戏资本"},{"lable_id":14,"name":"聚焦"}]}
     */

    private boolean code;
    private String msg;
    private DataBeanX data;

    public boolean isCode() {
        return code;
    }

    public void setCode(boolean code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBeanX getData() {
        return data;
    }

    public void setData(DataBeanX data) {
        this.data = data;
    }

    public static class DataBeanX implements Parcelable {
        /**
         * article : {"total":91,"per_page":10,"current_page":1,"last_page":10,"data":[{"article_id":788,"title":"一直追风口，总是不赶趟！河南这家上市公司又杀入此红海","art_from":" 鉴闻","code":"002535","cover":1,"name":"林州重机","cover_pic":["http://admin.cctvzxjy.com/uploads/acticle/images/20180329/60f1247e702c3233d9987aef7e18d506.jpg"],"time":"4周前","is_top":0,"lable_id":11,"category":0,"art_desc":"","lable_name":"数字风云","url":"http://wind.cctvzxjy.com/detail/788","kind":1,"have_id":35,"user_id":10006,"count":3,"praise":11,"content":"还行","num":0,"is_big":0,"status":1,"index_page":1,"index_line":3,"user_img":"http://thirdwx.qlogo.cn/mmopen/vi_32/bdEicweK8eBQR0tdDJFt5z4cichxcs3Ky0naJjGz4QN5cbmNOicgLDzbTicYY4D8y4BW9vGMSTrfBQKCmtkuSpHR6g/132","user_name":"sky许","stock":{"stock_id":2579,"code":"002512","name":"达华智能","company":"中山达华智能科技股份有限公司","plate":"电子电气组件与设备","pic":"","website":"www.twh.com.cn","status":1,"time":"2010-12-03","flow":"63961.54","area":"广东","legal":"蔡小如","secret":"韩洋","underwriter":"民生证券股份有限公司","address":"广东省中山市小榄镇泰丰工业区水怡南路9号","postcode":"528415","phone":"0760-22550278","fax":"0760-22130941","email":"8888@twh.com.cn","business":"标签卡,包括非接触IC卡和电子标签","company_desc":"公司系由达华有限全体股东为发起人整体变更设立的股份有限公司。2009年5月5日，达华有限召开股东会，决议以达华有限截至2009年3月31日经鹏城会计师事务所审计的净资产89,261,177.88元为基准，按1.116:1的比例折为股本8,000万股，将达华有限整体变更为股份有限公司，余额9,261,177.88元计入资本公积。2009年5月31日，中山市工商局向公司核发注册号为442000000003929的《企业法人营业执照》，核准发行人成立。"}},{"article_id":869,"title":"浅谈信立泰2017年业绩快报和黄金坑机遇","art_from":"牛氓的胜利","code":"002294","cover":1,"name":"信立泰","cover_pic":["http://admin.cctvzxjy.com/uploads/acticle/images/20180330/2d246546940233661ba49424aea7e79b.jpg"],"time":"2月前","is_top":0,"lable_id":11,"category":0,"art_desc":"","lable_name":"数字风云","url":"http://wind.cctvzxjy.com/detail/869","kind":1},{"have_id":35,"user_id":10006,"code":"002512","count":3,"praise":11,"content":"还行","time":"2018-03-23 13:51","num":0,"is_big":0,"status":1,"index_page":1,"index_line":3,"user_img":"http://thirdwx.qlogo.cn/mmopen/vi_32/bdEicweK8eBQR0tdDJFt5z4cichxcs3Ky0naJjGz4QN5cbmNOicgLDzbTicYY4D8y4BW9vGMSTrfBQKCmtkuSpHR6g/132","user_name":"sky许","name":"达华智能","kind":2,"stock":{"stock_id":2579,"code":"002512","name":"达华智能","company":"中山达华智能科技股份有限公司","plate":"电子电气组件与设备","pic":"","website":"www.twh.com.cn","status":1,"time":"2010-12-03","flow":"63961.54","area":"广东","legal":"蔡小如","secret":"韩洋","underwriter":"民生证券股份有限公司","address":"广东省中山市小榄镇泰丰工业区水怡南路9号","postcode":"528415","phone":"0760-22550278","fax":"0760-22130941","email":"8888@twh.com.cn","business":"标签卡,包括非接触IC卡和电子标签","company_desc":"公司系由达华有限全体股东为发起人整体变更设立的股份有限公司。2009年5月5日，达华有限召开股东会，决议以达华有限截至2009年3月31日经鹏城会计师事务所审计的净资产89,261,177.88元为基准，按1.116:1的比例折为股本8,000万股，将达华有限整体变更为股份有限公司，余额9,261,177.88元计入资本公积。2009年5月31日，中山市工商局向公司核发注册号为442000000003929的《企业法人营业执照》，核准发行人成立。"}},{"article_id":284,"title":"中国十大家族盘点！唯一财团、华人\u201c经营之父\u201d..李嘉诚排第二","art_from":" FX168","code":"601998","cover":1,"name":"中信银行","cover_pic":["http://admin.cctvzxjy.com/uploads/acticle/images/20180322/245ad8a40a98e14bfafe43f57460d360.jpg"],"time":"2月前","is_top":0,"lable_id":11,"category":0,"art_desc":"","lable_name":"数字风云","url":"http://wind.cctvzxjy.com/detail/284","kind":1},{"article_id":228,"title":"248家上市公司董事长狗年出生 手握近4万亿总市值","art_from":"金融界","code":"600276","cover":1,"name":"恒瑞医药","cover_pic":["http://admin.cctvzxjy.com/uploads/acticle/images/20180322/f0a061effc34935c961e9cdd3df8dcb4.jpg"],"time":"2月前","is_top":0,"lable_id":11,"category":0,"art_desc":"","lable_name":"数字风云","url":"http://wind.cctvzxjy.com/detail/228","kind":1},{"article_id":261,"title":"鸡年收官！28张图纵览A股全貌！","art_from":"东方财富网","code":"601360","cover":2,"name":"三六零","cover_pic":["http://admin.cctvzxjy.com/uploads/acticle/images/20180322/2658400ac6fc1bea60e9a9c7a73a0685.jpg","http://admin.cctvzxjy.com/uploads/acticle/images/20180322/41d2581a8817ef4b3beb8e2b9036c89b.jpg","http://admin.cctvzxjy.com/uploads/acticle/images/20180322/13e1c6f480422116a5720a9155f9a7c1.jpg"],"time":"2月前","is_top":0,"lable_id":11,"category":0,"art_desc":"","lable_name":"数字风云","url":"http://wind.cctvzxjy.com/detail/261","kind":1},{"article_id":232,"title":"舌尖上的A股：3000多家上市公司 哪些是餐饮股","art_from":"腾讯证券","code":"002330","cover":2,"name":"得利斯","cover_pic":["http://admin.cctvzxjy.com/uploads/acticle/images/20180322/ac957ee2776f60742cfc4efd3beb2310.jpg","http://admin.cctvzxjy.com/uploads/acticle/images/20180322/1655d1375b5ae42b81e6914ae9a63a54.jpg","http://admin.cctvzxjy.com/uploads/acticle/images/20180322/b8c073966c1014cd0c71145def2f833d.jpg"],"time":"2月前","is_top":0,"lable_id":11,"category":0,"art_desc":"","lable_name":"数字风云","url":"http://wind.cctvzxjy.com/detail/232","kind":1},{"article_id":229,"title":"这些上市公司狗年春节坐不住 年三十晚还在\u201c活动\u201d！","art_from":"每日经济新闻","code":"601116","cover":1,"name":"三江购物","cover_pic":["http://admin.cctvzxjy.com/uploads/acticle/images/20180322/4588d88ecd746834ae03316cca770916.jpg"],"time":"2月前","is_top":0,"lable_id":11,"category":0,"art_desc":"","lable_name":"数字风云","url":"http://wind.cctvzxjy.com/detail/229","kind":1},{"article_id":235,"title":"16000多家上市公司数据揭示：你家乡这三年的真实变化","art_from":"华尔街见闻","code":"000928","cover":1,"name":"中钢国际","cover_pic":["http://admin.cctvzxjy.com/uploads/acticle/images/20180322/a3b7e6d6a483e99298808e64098e6bf0.jpg"],"time":"2月前","is_top":0,"lable_id":11,"category":0,"art_desc":"","lable_name":"数字风云","url":"http://wind.cctvzxjy.com/detail/235","kind":1},{"article_id":279,"title":"【传闻鉴证】它25年不分红的铁公鸡，两年跌8成被问询十多次！","art_from":"好股互动","code":"600610","cover":1,"name":"中毅达","cover_pic":["http://admin.cctvzxjy.com/uploads/acticle/images/20180322/354931c131b71c64c90bd6fd3c237340.jpg"],"time":"2月前","is_top":0,"lable_id":11,"category":0,"art_desc":"","lable_name":"数字风云","url":"http://wind.cctvzxjy.com/detail/279","kind":1},{"article_id":250,"title":"从上市公司数据读懂我国新兴产业发展态势","art_from":"新软件","code":"000555","cover":2,"name":"神州信息","cover_pic":["http://admin.cctvzxjy.com/uploads/acticle/images/20180322/4b17ddff4cba334d245e9eb3ab70dbcb.jpg","http://admin.cctvzxjy.com/uploads/acticle/images/20180322/92f00501889358dd731a47832023b1af.jpg","http://admin.cctvzxjy.com/uploads/acticle/images/20180322/31aebe6ef46c4c1293ed29143de014d1.jpg"],"time":"2月前","is_top":0,"lable_id":11,"category":0,"art_desc":"","lable_name":"数字风云","url":"http://wind.cctvzxjy.com/detail/250"}]}
         * hot : [{"hot_id":4,"code":"600519","name":"贵州茅台"},{"hot_id":5,"code":"002069","name":"獐子岛"},{"hot_id":6,"code":"002271","name":"东方雨虹"},{"hot_id":7,"code":"002512","name":"达华智能"},{"hot_id":8,"code":"601360","name":"三六零"},{"hot_id":9,"code":"603939","name":"益丰药房"}]
         * label : [{"lable_id":8,"name":"风声背后"},{"lable_id":9,"name":"财富秘史"},{"lable_id":10,"name":"交易真相"},{"lable_id":11,"name":"数字风云"},{"lable_id":12,"name":"一线爆料"},{"lable_id":13,"name":"游戏资本"},{"lable_id":14,"name":"聚焦"}]
         */

        private ArticleBean article;
        private List<HotBean> hot;
        private List<LabelBean> label;

        public ArticleBean getArticle() {
            return article;
        }

        public void setArticle(ArticleBean article) {
            this.article = article;
        }

        public List<HotBean> getHot() {
            return hot;
        }

        public void setHot(List<HotBean> hot) {
            this.hot = hot;
        }

        public List<LabelBean> getLabel() {
            return label;
        }

        public void setLabel(List<LabelBean> label) {
            this.label = label;
        }

        public static class ArticleBean implements Parcelable {
            /**
             * total : 91
             * per_page : 10
             * current_page : 1
             * last_page : 10
             * data : [{"article_id":788,"title":"一直追风口，总是不赶趟！河南这家上市公司又杀入此红海","art_from":" 鉴闻","code":"002535","cover":1,"name":"林州重机","cover_pic":["http://admin.cctvzxjy.com/uploads/acticle/images/20180329/60f1247e702c3233d9987aef7e18d506.jpg"],"time":"4周前","is_top":0,"lable_id":11,"category":0,"art_desc":"","lable_name":"数字风云","url":"http://wind.cctvzxjy.com/detail/788","kind":1},{"article_id":869,"title":"浅谈信立泰2017年业绩快报和黄金坑机遇","art_from":"牛氓的胜利","code":"002294","cover":1,"name":"信立泰","cover_pic":["http://admin.cctvzxjy.com/uploads/acticle/images/20180330/2d246546940233661ba49424aea7e79b.jpg"],"time":"2月前","is_top":0,"lable_id":11,"category":0,"art_desc":"","lable_name":"数字风云","url":"http://wind.cctvzxjy.com/detail/869","kind":1},{"have_id":35,"user_id":10006,"code":"002512","count":3,"praise":11,"content":"还行","time":"2018-03-23 13:51","num":0,"is_big":0,"status":1,"index_page":1,"index_line":3,"user_img":"http://thirdwx.qlogo.cn/mmopen/vi_32/bdEicweK8eBQR0tdDJFt5z4cichxcs3Ky0naJjGz4QN5cbmNOicgLDzbTicYY4D8y4BW9vGMSTrfBQKCmtkuSpHR6g/132","user_name":"sky许","name":"达华智能","kind":2,"stock":{"stock_id":2579,"code":"002512","name":"达华智能","company":"中山达华智能科技股份有限公司","plate":"电子电气组件与设备","pic":"","website":"www.twh.com.cn","status":1,"time":"2010-12-03","flow":"63961.54","area":"广东","legal":"蔡小如","secret":"韩洋","underwriter":"民生证券股份有限公司","address":"广东省中山市小榄镇泰丰工业区水怡南路9号","postcode":"528415","phone":"0760-22550278","fax":"0760-22130941","email":"8888@twh.com.cn","business":"标签卡,包括非接触IC卡和电子标签","company_desc":"公司系由达华有限全体股东为发起人整体变更设立的股份有限公司。2009年5月5日，达华有限召开股东会，决议以达华有限截至2009年3月31日经鹏城会计师事务所审计的净资产89,261,177.88元为基准，按1.116:1的比例折为股本8,000万股，将达华有限整体变更为股份有限公司，余额9,261,177.88元计入资本公积。2009年5月31日，中山市工商局向公司核发注册号为442000000003929的《企业法人营业执照》，核准发行人成立。"}},{"article_id":284,"title":"中国十大家族盘点！唯一财团、华人\u201c经营之父\u201d..李嘉诚排第二","art_from":" FX168","code":"601998","cover":1,"name":"中信银行","cover_pic":["http://admin.cctvzxjy.com/uploads/acticle/images/20180322/245ad8a40a98e14bfafe43f57460d360.jpg"],"time":"2月前","is_top":0,"lable_id":11,"category":0,"art_desc":"","lable_name":"数字风云","url":"http://wind.cctvzxjy.com/detail/284","kind":1},{"article_id":228,"title":"248家上市公司董事长狗年出生 手握近4万亿总市值","art_from":"金融界","code":"600276","cover":1,"name":"恒瑞医药","cover_pic":["http://admin.cctvzxjy.com/uploads/acticle/images/20180322/f0a061effc34935c961e9cdd3df8dcb4.jpg"],"time":"2月前","is_top":0,"lable_id":11,"category":0,"art_desc":"","lable_name":"数字风云","url":"http://wind.cctvzxjy.com/detail/228","kind":1},{"article_id":261,"title":"鸡年收官！28张图纵览A股全貌！","art_from":"东方财富网","code":"601360","cover":2,"name":"三六零","cover_pic":["http://admin.cctvzxjy.com/uploads/acticle/images/20180322/2658400ac6fc1bea60e9a9c7a73a0685.jpg","http://admin.cctvzxjy.com/uploads/acticle/images/20180322/41d2581a8817ef4b3beb8e2b9036c89b.jpg","http://admin.cctvzxjy.com/uploads/acticle/images/20180322/13e1c6f480422116a5720a9155f9a7c1.jpg"],"time":"2月前","is_top":0,"lable_id":11,"category":0,"art_desc":"","lable_name":"数字风云","url":"http://wind.cctvzxjy.com/detail/261","kind":1},{"article_id":232,"title":"舌尖上的A股：3000多家上市公司 哪些是餐饮股","art_from":"腾讯证券","code":"002330","cover":2,"name":"得利斯","cover_pic":["http://admin.cctvzxjy.com/uploads/acticle/images/20180322/ac957ee2776f60742cfc4efd3beb2310.jpg","http://admin.cctvzxjy.com/uploads/acticle/images/20180322/1655d1375b5ae42b81e6914ae9a63a54.jpg","http://admin.cctvzxjy.com/uploads/acticle/images/20180322/b8c073966c1014cd0c71145def2f833d.jpg"],"time":"2月前","is_top":0,"lable_id":11,"category":0,"art_desc":"","lable_name":"数字风云","url":"http://wind.cctvzxjy.com/detail/232","kind":1},{"article_id":229,"title":"这些上市公司狗年春节坐不住 年三十晚还在\u201c活动\u201d！","art_from":"每日经济新闻","code":"601116","cover":1,"name":"三江购物","cover_pic":["http://admin.cctvzxjy.com/uploads/acticle/images/20180322/4588d88ecd746834ae03316cca770916.jpg"],"time":"2月前","is_top":0,"lable_id":11,"category":0,"art_desc":"","lable_name":"数字风云","url":"http://wind.cctvzxjy.com/detail/229","kind":1},{"article_id":235,"title":"16000多家上市公司数据揭示：你家乡这三年的真实变化","art_from":"华尔街见闻","code":"000928","cover":1,"name":"中钢国际","cover_pic":["http://admin.cctvzxjy.com/uploads/acticle/images/20180322/a3b7e6d6a483e99298808e64098e6bf0.jpg"],"time":"2月前","is_top":0,"lable_id":11,"category":0,"art_desc":"","lable_name":"数字风云","url":"http://wind.cctvzxjy.com/detail/235","kind":1},{"article_id":279,"title":"【传闻鉴证】它25年不分红的铁公鸡，两年跌8成被问询十多次！","art_from":"好股互动","code":"600610","cover":1,"name":"中毅达","cover_pic":["http://admin.cctvzxjy.com/uploads/acticle/images/20180322/354931c131b71c64c90bd6fd3c237340.jpg"],"time":"2月前","is_top":0,"lable_id":11,"category":0,"art_desc":"","lable_name":"数字风云","url":"http://wind.cctvzxjy.com/detail/279","kind":1},{"article_id":250,"title":"从上市公司数据读懂我国新兴产业发展态势","art_from":"新软件","code":"000555","cover":2,"name":"神州信息","cover_pic":["http://admin.cctvzxjy.com/uploads/acticle/images/20180322/4b17ddff4cba334d245e9eb3ab70dbcb.jpg","http://admin.cctvzxjy.com/uploads/acticle/images/20180322/92f00501889358dd731a47832023b1af.jpg","http://admin.cctvzxjy.com/uploads/acticle/images/20180322/31aebe6ef46c4c1293ed29143de014d1.jpg"],"time":"2月前","is_top":0,"lable_id":11,"category":0,"art_desc":"","lable_name":"数字风云","url":"http://wind.cctvzxjy.com/detail/250"}]
             */

            private int total;
            private int per_page;
            private int current_page;
            private int last_page;
            private List<DataBean> data;

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public int getPer_page() {
                return per_page;
            }

            public void setPer_page(int per_page) {
                this.per_page = per_page;
            }

            public int getCurrent_page() {
                return current_page;
            }

            public void setCurrent_page(int current_page) {
                this.current_page = current_page;
            }

            public int getLast_page() {
                return last_page;
            }

            public void setLast_page(int last_page) {
                this.last_page = last_page;
            }

            public List<DataBean> getData() {
                return data;
            }

            public void setData(List<DataBean> data) {
                this.data = data;
            }

            public static class DataBean implements Parcelable {
                /**
                 * article_id : 788
                 * title : 一直追风口，总是不赶趟！河南这家上市公司又杀入此红海
                 * art_from :  鉴闻
                 * code : 002535
                 * cover : 1
                 * name : 林州重机
                 * cover_pic : ["http://admin.cctvzxjy.com/uploads/acticle/images/20180329/60f1247e702c3233d9987aef7e18d506.jpg"]
                 * time : 4周前
                 * is_top : 0
                 * lable_id : 11
                 * category : 0
                 * art_desc :
                 * lable_name : 数字风云
                 * url : http://wind.cctvzxjy.com/detail/788
                 * kind : 1
                 * have_id : 35
                 * user_id : 10006
                 * count : 3
                 * praise : 11
                 * content : 还行
                 * num : 0
                 * is_big : 0
                 * status : 1
                 * index_page : 1
                 * index_line : 3
                 * user_img : http://thirdwx.qlogo.cn/mmopen/vi_32/bdEicweK8eBQR0tdDJFt5z4cichxcs3Ky0naJjGz4QN5cbmNOicgLDzbTicYY4D8y4BW9vGMSTrfBQKCmtkuSpHR6g/132
                 * user_name : sky许
                 * stock : {"stock_id":2579,"code":"002512","name":"达华智能","company":"中山达华智能科技股份有限公司","plate":"电子电气组件与设备","pic":"","website":"www.twh.com.cn","status":1,"time":"2010-12-03","flow":"63961.54","area":"广东","legal":"蔡小如","secret":"韩洋","underwriter":"民生证券股份有限公司","address":"广东省中山市小榄镇泰丰工业区水怡南路9号","postcode":"528415","phone":"0760-22550278","fax":"0760-22130941","email":"8888@twh.com.cn","business":"标签卡,包括非接触IC卡和电子标签","company_desc":"公司系由达华有限全体股东为发起人整体变更设立的股份有限公司。2009年5月5日，达华有限召开股东会，决议以达华有限截至2009年3月31日经鹏城会计师事务所审计的净资产89,261,177.88元为基准，按1.116:1的比例折为股本8,000万股，将达华有限整体变更为股份有限公司，余额9,261,177.88元计入资本公积。2009年5月31日，中山市工商局向公司核发注册号为442000000003929的《企业法人营业执照》，核准发行人成立。"}
                 */

                private int article_id;
                private String title;
                private String art_from;
                private String code;
                private int cover;
                private String name;
                private String time;
                private int is_top;
                private int lable_id;
                private int category;
                private String art_desc;
                private String lable_name;
                private String url;
                private int kind;
                private int have_id;
                private int user_id;
                private int count;
                private int praise;
                private String content;
                private int num;
                private int is_big;
                private int status;
                private int index_page;
                private int index_line;
                private String user_img;
                private String user_name;
                private StockBean stock;
                private List<String> cover_pic;

                public int getArticle_id() {
                    return article_id;
                }

                public void setArticle_id(int article_id) {
                    this.article_id = article_id;
                }

                public String getTitle() {
                    return title;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public String getArt_from() {
                    return art_from;
                }

                public void setArt_from(String art_from) {
                    this.art_from = art_from;
                }

                public String getCode() {
                    return code;
                }

                public void setCode(String code) {
                    this.code = code;
                }

                public int getCover() {
                    return cover;
                }

                public void setCover(int cover) {
                    this.cover = cover;
                }

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getTime() {
                    return time;
                }

                public void setTime(String time) {
                    this.time = time;
                }

                public int getIs_top() {
                    return is_top;
                }

                public void setIs_top(int is_top) {
                    this.is_top = is_top;
                }

                public int getLable_id() {
                    return lable_id;
                }

                public void setLable_id(int lable_id) {
                    this.lable_id = lable_id;
                }

                public int getCategory() {
                    return category;
                }

                public void setCategory(int category) {
                    this.category = category;
                }

                public String getArt_desc() {
                    return art_desc;
                }

                public void setArt_desc(String art_desc) {
                    this.art_desc = art_desc;
                }

                public String getLable_name() {
                    return lable_name;
                }

                public void setLable_name(String lable_name) {
                    this.lable_name = lable_name;
                }

                public String getUrl() {
                    return url;
                }

                public void setUrl(String url) {
                    this.url = url;
                }

                public int getKind() {
                    return kind;
                }

                public void setKind(int kind) {
                    this.kind = kind;
                }

                public int getHave_id() {
                    return have_id;
                }

                public void setHave_id(int have_id) {
                    this.have_id = have_id;
                }

                public int getUser_id() {
                    return user_id;
                }

                public void setUser_id(int user_id) {
                    this.user_id = user_id;
                }

                public int getCount() {
                    return count;
                }

                public void setCount(int count) {
                    this.count = count;
                }

                public int getPraise() {
                    return praise;
                }

                public void setPraise(int praise) {
                    this.praise = praise;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public int getNum() {
                    return num;
                }

                public void setNum(int num) {
                    this.num = num;
                }

                public int getIs_big() {
                    return is_big;
                }

                public void setIs_big(int is_big) {
                    this.is_big = is_big;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public int getIndex_page() {
                    return index_page;
                }

                public void setIndex_page(int index_page) {
                    this.index_page = index_page;
                }

                public int getIndex_line() {
                    return index_line;
                }

                public void setIndex_line(int index_line) {
                    this.index_line = index_line;
                }

                public String getUser_img() {
                    return user_img;
                }

                public void setUser_img(String user_img) {
                    this.user_img = user_img;
                }

                public String getUser_name() {
                    return user_name;
                }

                public void setUser_name(String user_name) {
                    this.user_name = user_name;
                }

                public StockBean getStock() {
                    return stock;
                }

                public void setStock(StockBean stock) {
                    this.stock = stock;
                }

                public List<String> getCover_pic() {
                    return cover_pic;
                }

                public void setCover_pic(List<String> cover_pic) {
                    this.cover_pic = cover_pic;
                }

                public static class StockBean implements Parcelable {
                    /**
                     * stock_id : 2579
                     * code : 002512
                     * name : 达华智能
                     * company : 中山达华智能科技股份有限公司
                     * plate : 电子电气组件与设备
                     * pic :
                     * website : www.twh.com.cn
                     * status : 1
                     * time : 2010-12-03
                     * flow : 63961.54
                     * area : 广东
                     * legal : 蔡小如
                     * secret : 韩洋
                     * underwriter : 民生证券股份有限公司
                     * address : 广东省中山市小榄镇泰丰工业区水怡南路9号
                     * postcode : 528415
                     * phone : 0760-22550278
                     * fax : 0760-22130941
                     * email : 8888@twh.com.cn
                     * business : 标签卡,包括非接触IC卡和电子标签
                     * company_desc : 公司系由达华有限全体股东为发起人整体变更设立的股份有限公司。2009年5月5日，达华有限召开股东会，决议以达华有限截至2009年3月31日经鹏城会计师事务所审计的净资产89,261,177.88元为基准，按1.116:1的比例折为股本8,000万股，将达华有限整体变更为股份有限公司，余额9,261,177.88元计入资本公积。2009年5月31日，中山市工商局向公司核发注册号为442000000003929的《企业法人营业执照》，核准发行人成立。
                     */

                    private int stock_id;
                    private String code;
                    private String name;
                    private String company;
                    private String plate;
                    private String pic;
                    private String website;
                    private int status;
                    private String time;
                    private String flow;
                    private String area;
                    private String legal;
                    private String secret;
                    private String underwriter;
                    private String address;
                    private String postcode;
                    private String phone;
                    private String fax;
                    private String email;
                    private String business;
                    private String company_desc;

                    public int getStock_id() {
                        return stock_id;
                    }

                    public void setStock_id(int stock_id) {
                        this.stock_id = stock_id;
                    }

                    public String getCode() {
                        return code;
                    }

                    public void setCode(String code) {
                        this.code = code;
                    }

                    public String getName() {
                        return name;
                    }

                    public void setName(String name) {
                        this.name = name;
                    }

                    public String getCompany() {
                        return company;
                    }

                    public void setCompany(String company) {
                        this.company = company;
                    }

                    public String getPlate() {
                        return plate;
                    }

                    public void setPlate(String plate) {
                        this.plate = plate;
                    }

                    public String getPic() {
                        return pic;
                    }

                    public void setPic(String pic) {
                        this.pic = pic;
                    }

                    public String getWebsite() {
                        return website;
                    }

                    public void setWebsite(String website) {
                        this.website = website;
                    }

                    public int getStatus() {
                        return status;
                    }

                    public void setStatus(int status) {
                        this.status = status;
                    }

                    public String getTime() {
                        return time;
                    }

                    public void setTime(String time) {
                        this.time = time;
                    }

                    public String getFlow() {
                        return flow;
                    }

                    public void setFlow(String flow) {
                        this.flow = flow;
                    }

                    public String getArea() {
                        return area;
                    }

                    public void setArea(String area) {
                        this.area = area;
                    }

                    public String getLegal() {
                        return legal;
                    }

                    public void setLegal(String legal) {
                        this.legal = legal;
                    }

                    public String getSecret() {
                        return secret;
                    }

                    public void setSecret(String secret) {
                        this.secret = secret;
                    }

                    public String getUnderwriter() {
                        return underwriter;
                    }

                    public void setUnderwriter(String underwriter) {
                        this.underwriter = underwriter;
                    }

                    public String getAddress() {
                        return address;
                    }

                    public void setAddress(String address) {
                        this.address = address;
                    }

                    public String getPostcode() {
                        return postcode;
                    }

                    public void setPostcode(String postcode) {
                        this.postcode = postcode;
                    }

                    public String getPhone() {
                        return phone;
                    }

                    public void setPhone(String phone) {
                        this.phone = phone;
                    }

                    public String getFax() {
                        return fax;
                    }

                    public void setFax(String fax) {
                        this.fax = fax;
                    }

                    public String getEmail() {
                        return email;
                    }

                    public void setEmail(String email) {
                        this.email = email;
                    }

                    public String getBusiness() {
                        return business;
                    }

                    public void setBusiness(String business) {
                        this.business = business;
                    }

                    public String getCompany_desc() {
                        return company_desc;
                    }

                    public void setCompany_desc(String company_desc) {
                        this.company_desc = company_desc;
                    }

                    @Override
                    public int describeContents() {
                        return 0;
                    }

                    @Override
                    public void writeToParcel(Parcel dest, int flags) {
                        dest.writeInt(this.stock_id);
                        dest.writeString(this.code);
                        dest.writeString(this.name);
                        dest.writeString(this.company);
                        dest.writeString(this.plate);
                        dest.writeString(this.pic);
                        dest.writeString(this.website);
                        dest.writeInt(this.status);
                        dest.writeString(this.time);
                        dest.writeString(this.flow);
                        dest.writeString(this.area);
                        dest.writeString(this.legal);
                        dest.writeString(this.secret);
                        dest.writeString(this.underwriter);
                        dest.writeString(this.address);
                        dest.writeString(this.postcode);
                        dest.writeString(this.phone);
                        dest.writeString(this.fax);
                        dest.writeString(this.email);
                        dest.writeString(this.business);
                        dest.writeString(this.company_desc);
                    }

                    public StockBean() {
                    }

                    protected StockBean(Parcel in) {
                        this.stock_id = in.readInt();
                        this.code = in.readString();
                        this.name = in.readString();
                        this.company = in.readString();
                        this.plate = in.readString();
                        this.pic = in.readString();
                        this.website = in.readString();
                        this.status = in.readInt();
                        this.time = in.readString();
                        this.flow = in.readString();
                        this.area = in.readString();
                        this.legal = in.readString();
                        this.secret = in.readString();
                        this.underwriter = in.readString();
                        this.address = in.readString();
                        this.postcode = in.readString();
                        this.phone = in.readString();
                        this.fax = in.readString();
                        this.email = in.readString();
                        this.business = in.readString();
                        this.company_desc = in.readString();
                    }

                    public static final Creator<StockBean> CREATOR = new Creator<StockBean>() {
                        @Override
                        public StockBean createFromParcel(Parcel source) {
                            return new StockBean(source);
                        }

                        @Override
                        public StockBean[] newArray(int size) {
                            return new StockBean[size];
                        }
                    };
                }

                @Override
                public int describeContents() {
                    return 0;
                }

                @Override
                public void writeToParcel(Parcel dest, int flags) {
                    dest.writeInt(this.article_id);
                    dest.writeString(this.title);
                    dest.writeString(this.art_from);
                    dest.writeString(this.code);
                    dest.writeInt(this.cover);
                    dest.writeString(this.name);
                    dest.writeString(this.time);
                    dest.writeInt(this.is_top);
                    dest.writeInt(this.lable_id);
                    dest.writeInt(this.category);
                    dest.writeString(this.art_desc);
                    dest.writeString(this.lable_name);
                    dest.writeString(this.url);
                    dest.writeInt(this.kind);
                    dest.writeInt(this.have_id);
                    dest.writeInt(this.user_id);
                    dest.writeInt(this.count);
                    dest.writeInt(this.praise);
                    dest.writeString(this.content);
                    dest.writeInt(this.num);
                    dest.writeInt(this.is_big);
                    dest.writeInt(this.status);
                    dest.writeInt(this.index_page);
                    dest.writeInt(this.index_line);
                    dest.writeString(this.user_img);
                    dest.writeString(this.user_name);
                    dest.writeParcelable(this.stock, flags);
                    dest.writeStringList(this.cover_pic);
                }

                public DataBean() {
                }

                protected DataBean(Parcel in) {
                    this.article_id = in.readInt();
                    this.title = in.readString();
                    this.art_from = in.readString();
                    this.code = in.readString();
                    this.cover = in.readInt();
                    this.name = in.readString();
                    this.time = in.readString();
                    this.is_top = in.readInt();
                    this.lable_id = in.readInt();
                    this.category = in.readInt();
                    this.art_desc = in.readString();
                    this.lable_name = in.readString();
                    this.url = in.readString();
                    this.kind = in.readInt();
                    this.have_id = in.readInt();
                    this.user_id = in.readInt();
                    this.count = in.readInt();
                    this.praise = in.readInt();
                    this.content = in.readString();
                    this.num = in.readInt();
                    this.is_big = in.readInt();
                    this.status = in.readInt();
                    this.index_page = in.readInt();
                    this.index_line = in.readInt();
                    this.user_img = in.readString();
                    this.user_name = in.readString();
                    this.stock = in.readParcelable(StockBean.class.getClassLoader());
                    this.cover_pic = in.createStringArrayList();
                }

                public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
                    @Override
                    public DataBean createFromParcel(Parcel source) {
                        return new DataBean(source);
                    }

                    @Override
                    public DataBean[] newArray(int size) {
                        return new DataBean[size];
                    }
                };
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.total);
                dest.writeInt(this.per_page);
                dest.writeInt(this.current_page);
                dest.writeInt(this.last_page);
                dest.writeList(this.data);
            }

            public ArticleBean() {
            }

            protected ArticleBean(Parcel in) {
                this.total = in.readInt();
                this.per_page = in.readInt();
                this.current_page = in.readInt();
                this.last_page = in.readInt();
                this.data = new ArrayList<DataBean>();
                in.readList(this.data, DataBean.class.getClassLoader());
            }

            public static final Creator<ArticleBean> CREATOR = new Creator<ArticleBean>() {
                @Override
                public ArticleBean createFromParcel(Parcel source) {
                    return new ArticleBean(source);
                }

                @Override
                public ArticleBean[] newArray(int size) {
                    return new ArticleBean[size];
                }
            };
        }

        public static class HotBean implements Parcelable {
            /**
             * hot_id : 4
             * code : 600519
             * name : 贵州茅台
             */

            private int hot_id;
            private String code;
            private String name;

            public int getHot_id() {
                return hot_id;
            }

            public void setHot_id(int hot_id) {
                this.hot_id = hot_id;
            }

            public String getCode() {
                return code;
            }

            public void setCode(String code) {
                this.code = code;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.hot_id);
                dest.writeString(this.code);
                dest.writeString(this.name);
            }

            public HotBean() {
            }

            protected HotBean(Parcel in) {
                this.hot_id = in.readInt();
                this.code = in.readString();
                this.name = in.readString();
            }

            public static final Creator<HotBean> CREATOR = new Creator<HotBean>() {
                @Override
                public HotBean createFromParcel(Parcel source) {
                    return new HotBean(source);
                }

                @Override
                public HotBean[] newArray(int size) {
                    return new HotBean[size];
                }
            };
        }

        public static class LabelBean implements Parcelable {
            /**
             * lable_id : 8
             * name : 风声背后
             */

            private int lable_id;
            private String name;

            public int getLable_id() {
                return lable_id;
            }

            public void setLable_id(int lable_id) {
                this.lable_id = lable_id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel dest, int flags) {
                dest.writeInt(this.lable_id);
                dest.writeString(this.name);
            }

            public LabelBean() {
            }

            protected LabelBean(Parcel in) {
                this.lable_id = in.readInt();
                this.name = in.readString();
            }

            public static final Creator<LabelBean> CREATOR = new Creator<LabelBean>() {
                @Override
                public LabelBean createFromParcel(Parcel source) {
                    return new LabelBean(source);
                }

                @Override
                public LabelBean[] newArray(int size) {
                    return new LabelBean[size];
                }
            };
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(this.article, flags);
            dest.writeList(this.hot);
            dest.writeList(this.label);
        }

        public DataBeanX() {
        }

        protected DataBeanX(Parcel in) {
            this.article = in.readParcelable(ArticleBean.class.getClassLoader());
            this.hot = new ArrayList<HotBean>();
            in.readList(this.hot, HotBean.class.getClassLoader());
            this.label = new ArrayList<LabelBean>();
            in.readList(this.label, LabelBean.class.getClassLoader());
        }

        public static final Creator<DataBeanX> CREATOR = new Creator<DataBeanX>() {
            @Override
            public DataBeanX createFromParcel(Parcel source) {
                return new DataBeanX(source);
            }

            @Override
            public DataBeanX[] newArray(int size) {
                return new DataBeanX[size];
            }
        };
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(this.code ? (byte) 1 : (byte) 0);
        dest.writeString(this.msg);
        dest.writeParcelable(this.data, flags);
    }

    public HomeBean() {
    }

    protected HomeBean(Parcel in) {
        this.code = in.readByte() != 0;
        this.msg = in.readString();
        this.data = in.readParcelable(DataBeanX.class.getClassLoader());
    }

    public static final Parcelable.Creator<HomeBean> CREATOR = new Parcelable.Creator<HomeBean>() {
        @Override
        public HomeBean createFromParcel(Parcel source) {
            return new HomeBean(source);
        }

        @Override
        public HomeBean[] newArray(int size) {
            return new HomeBean[size];
        }
    };
}
