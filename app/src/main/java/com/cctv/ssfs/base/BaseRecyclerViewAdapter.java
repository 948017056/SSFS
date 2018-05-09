package com.cctv.ssfs.base;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.List;

/**
 * Created by qi on 2018/3/13.
 *  集成三方库的可快速实现的Adapter
 * {@link #BaseQuickAdapter}
 */

public abstract class BaseRecyclerViewAdapter <T> extends BaseQuickAdapter<T, ViewHolder> {
    public BaseRecyclerViewAdapter(@LayoutRes int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
    }

}
