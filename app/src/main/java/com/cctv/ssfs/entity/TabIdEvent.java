package com.cctv.ssfs.entity;

/**
 * Created by qi on 2018/3/26.
 * EventBus 实体类
 */

public class TabIdEvent {
    private int id;
    private int tag;

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }


    public TabIdEvent() {

    }

    public TabIdEvent(int id, int tag) {
        this.id = id;
        this.tag = tag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
