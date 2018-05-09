package com.cctv.ssfs.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

import com.cctv.ssfs.R;
import com.cctv.ssfs.base.BaseRecyclerViewAdapter;
import com.cctv.ssfs.base.ViewHolder;
import com.cctv.ssfs.utils.ImageLoader;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by sanjin on 2018/4/23.
 */

public class PostsDetailsRecyclerAdapter extends BaseRecyclerViewAdapter<JSONObject> {
    private Context context;

    public PostsDetailsRecyclerAdapter(Context context, @Nullable List<JSONObject> data) {
        super(R.layout.item_postsdetails, data);
        this.context = context;
    }

    @Override
    protected void convert(ViewHolder helper, JSONObject data) {
        TextView tvGoodsTitle = helper.getView(R.id.tv_goods_title);
        TextView tvSource = helper.getView(R.id.tv_source);
        ImageView ivImage = helper.getView(R.id.iv_image);
        tvGoodsTitle.setText(data.optString("title"));
        tvSource.setText(data.optString("art_from"));
        JSONArray imgArray = data.optJSONArray("cover_pic");
        ImageLoader.getInstance().loadImageUrl(context, ivImage, imgArray.optString(0));
    }
}
