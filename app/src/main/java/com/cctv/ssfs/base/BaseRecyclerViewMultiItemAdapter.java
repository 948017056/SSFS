package com.cctv.ssfs.base;

import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;

import java.util.List;

/**
 * Created by qi on 2018/3/13.
 * 多种ItemAdapter
 */

public abstract class BaseRecyclerViewMultiItemAdapter<T> extends BaseMultiItemQuickAdapter<MultipleTypeItemEntity<T>,ViewHolder> {
    public BaseRecyclerViewMultiItemAdapter(List<MultipleTypeItemEntity<T>> data) {
        super(data);
    }

    @Override
    protected abstract void convert(ViewHolder helper, MultipleTypeItemEntity<T> item);
}
