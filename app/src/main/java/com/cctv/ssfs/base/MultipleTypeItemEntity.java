package com.cctv.ssfs.base;

import com.chad.library.adapter.base.entity.MultiItemEntity;

/**
 * Created by qi on 2018/3/13.
 *
 */

public class MultipleTypeItemEntity<T> implements MultiItemEntity {
    private final T data;
    private final int itemType;

    public MultipleTypeItemEntity(int itemType, T data) {
        this.data = data;
        this.itemType = itemType;
    }

    @Override
    public int getItemType() {
        return itemType;
    }

    public T getData() {
        return data;
    }
}
