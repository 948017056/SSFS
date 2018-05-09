package com.cctv.ssfs.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.view.View;

import com.cctv.ssfs.R;
import com.cctv.ssfs.base.BaseRecyclerViewAdapter;
import com.cctv.ssfs.base.ViewHolder;
import com.cctv.ssfs.view.home.activity.StockDetailsActivity;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by sanjin on 2018/4/26.
 */

public class BoughtRecyclerAdapter extends BaseRecyclerViewAdapter<JSONObject> {
    private Context context;

    public BoughtRecyclerAdapter(Context context, @Nullable List<JSONObject> data) {
        super(R.layout.item_bought, data);
        this.context = context;
    }

    @Override
    protected void convert(ViewHolder helper, final JSONObject item) {
        final String code = item.optString("code");

        StringBuffer buffer = new StringBuffer();
        buffer.append(item.optString("name"));
        buffer.append("(");
        buffer.append(code);
        buffer.append(")");
        helper.setText(R.id.tv_code, buffer.toString());
        helper.setText(R.id.tv_havecount,String.format("%s条短评",item.optInt("haveCount")));
        helper.setText(R.id.tv_articleCount, String.format("%s篇文章",item.optInt("articleCount")));
        helper.setText(R.id.tv_menCount, String.format("%s人参与",item.optInt("menCount")));


        helper.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StockDetailsActivity.class);
                intent.putExtra(StockDetailsActivity.CODE, code);
                context.startActivity(intent);
            }
        });
    }
}
