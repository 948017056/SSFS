package com.cctv.ssfs.base;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;

import java.util.List;

/**
 * Created by qi on 2018/3/13.
 *
 */

public abstract class BaseGroupTypeAdapter<T extends MultiItemEntity> extends BaseMultiItemQuickAdapter<T, ViewHolder> {

    public BaseGroupTypeAdapter(List<T> data) {
        super(data);
    }

}
