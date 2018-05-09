package com.cctv.ssfs.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.cctv.ssfs.R;
import com.cctv.ssfs.base.BaseRecyclerViewAdapter;
import com.cctv.ssfs.base.ViewHolder;
import com.cctv.ssfs.entity.SearchBean;
import com.cctv.ssfs.view.home.activity.StockDetailsActivity;

import java.util.List;

/**
 * Created by qi on 2018/3/26.
 * 搜索页列表
 */

public class SearchListAdapter extends BaseRecyclerViewAdapter<SearchBean.DataBean> {
    private Context context;

    public SearchListAdapter( Context context,@Nullable List<SearchBean.DataBean> data) {
        super(R.layout.item_searchlist, data);
        this.context = context;

    }

    @Override
    protected void convert(ViewHolder helper, final SearchBean.DataBean item) {
        ((TextView) helper.getView(R.id.tv_name)).setText(item.getName());
        ((TextView) helper.getView(R.id.tv_code)).setText(item.getCode());

        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,StockDetailsActivity.class);
                intent.putExtra(StockDetailsActivity.CODE,item.getCode());
                context.startActivity(intent);
            }
        });
    }
}
