package com.cctv.ssfs.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.cctv.ssfs.R;
import com.cctv.ssfs.base.BaseRecyclerViewAdapter;
import com.cctv.ssfs.base.ViewHolder;
import com.cctv.ssfs.customview.CircleImageView;
import com.cctv.ssfs.utils.ImageLoader;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by sanjin on 2018/4/25.
 */

public class HeadPortraitListAdapter extends BaseRecyclerViewAdapter<JSONObject> {
    private Context context;

    public HeadPortraitListAdapter(Context context, @Nullable List<JSONObject> data) {
        super(R.layout.item_headlist, data);
        this.context = context;
    }

    @Override
    protected void convert(ViewHolder helper, JSONObject item) {
        CircleImageView img = helper.getView(R.id.iv_head);
        ImageLoader.getInstance().loadImageUrl(context, img, item.optString("user_img"));
    }
}
