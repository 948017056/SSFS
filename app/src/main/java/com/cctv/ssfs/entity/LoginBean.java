package com.cctv.ssfs.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by qi on 2018/4/12.
 */

public class LoginBean implements Parcelable {

    @Override
    public String toString() {
        return "LoginBean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }

    /**
     * code : true
     * msg : 登录成功
     * data : {"user_id":"10045","user_name":"name","user_img":"头像url","sex":"1","area":"省份 城市","bind":0,"user_money":0,"telephone":""}
     */

    private boolean code;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Parcelable {
        @Override
        public String toString() {
            return "DataBean{" +
                    "user_id='" + user_id + '\'' +
                    ", user_name='" + user_name + '\'' +
                    ", user_img='" + user_img + '\'' +
                    ", sex='" + sex + '\'' +
                    ", area='" + area + '\'' +
                    ", bind=" + bind +
                    ", user_money=" + user_money +
                    ", telephone='" + telephone + '\'' +
                    '}';
        }

        /**
         * user_id : 10045
         * user_name : name
         * user_img : 头像url
         * sex : 1
         * area : 省份 城市
         * bind : 0
         * user_money : 0
         * telephone :
         */

        private String user_id;
        private String user_name;
        private String user_img;
        private String invite;
        private String sex;
        private String area;
        private int bind;
        private float user_money;
        private String telephone;
        public String getInvite() {
            return invite;
        }

        public void setInvite(String invite) {
            this.invite = invite;
        }
        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name) {
            this.user_name = user_name;
        }

        public String getUser_img() {
            return user_img;
        }

        public void setUser_img(String user_img) {
            this.user_img = user_img;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public int getBind() {
            return bind;
        }

        public void setBind(int bind) {
            this.bind = bind;
        }

        public float getUser_money() {
            return user_money;
        }

        public void setUser_money(float user_money) {
            this.user_money = user_money;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(this.user_id);
            dest.writeString(this.user_name);
            dest.writeString(this.user_img);
            dest.writeString(this.sex);
            dest.writeString(this.area);
            dest.writeInt(this.bind);
            dest.writeFloat(this.user_money);
            dest.writeString(this.telephone);
        }

        public DataBean() {
        }

        protected DataBean(Parcel in) {
            this.user_id = in.readString();
            this.user_name = in.readString();
            this.user_img = in.readString();
            this.sex = in.readString();
            this.area = in.readString();
            this.bind = in.readInt();
            this.user_money = in.readFloat();
            this.telephone = in.readString();
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
        dest.writeByte(this.code ? (byte) 1 : (byte) 0);
        dest.writeString(this.msg);
        dest.writeParcelable(this.data, flags);
    }

    public LoginBean() {
    }

    protected LoginBean(Parcel in) {
        this.code = in.readByte() != 0;
        this.msg = in.readString();
        this.data = in.readParcelable(DataBean.class.getClassLoader());
    }

    public static final Parcelable.Creator<LoginBean> CREATOR = new Parcelable.Creator<LoginBean>() {
        @Override
        public LoginBean createFromParcel(Parcel source) {
            return new LoginBean(source);
        }

        @Override
        public LoginBean[] newArray(int size) {
            return new LoginBean[size];
        }
    };
}
