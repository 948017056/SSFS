package com.cctv.ssfs.db;

/**
 * Created by qi on 2018/3/27.
 */

public class DBRecordsBean {
    public int id;
    public String name;
    public String code;

    public DBRecordsBean() {
    }

    public DBRecordsBean(int id, String name, String code) {
        this.id = id;
        this.name = name;
        this.code = code;
    }
}
