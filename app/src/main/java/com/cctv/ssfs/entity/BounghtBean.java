package com.cctv.ssfs.entity;

/**
 * Created by sanjin on 2018/4/17.
 */

public class BounghtBean {
    private String user_id;
    private String content;
    private String code;
    private int num;
    private int count;

    public BounghtBean() {
    }

    public BounghtBean(String user_id, String content, String code, int num, int count) {
        this.user_id = user_id;
        this.content = content;
        this.code = code;
        this.num = num;
        this.count = count;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
