package com.cctv.ssfs.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
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
 * Created by qi on 2018/4/12.
 */

public class OrignalRecyclerAdapter extends BaseRecyclerViewAdapter<JSONObject> {
    private Context context;

    public OrignalRecyclerAdapter(Context context, @Nullable List<JSONObject> data) {
        super(R.layout.item_goods2, data);
        this.context = context;
    }

    @Override
    protected void convert(ViewHolder helper, JSONObject data) {
        TextView tvGoodsTitle = helper.getView(R.id.tv_goods_title);
        TextView tvTime = helper.getView(R.id.tv_time);
        TextView tvSource = helper.getView(R.id.tv_source);
        ImageView ivImage = helper.getView(R.id.iv_image);
        tvTime.setVisibility(View.GONE);
        tvGoodsTitle.setText(data.optString("title"));
        tvSource.setText(data.optString("art_from"));
        JSONArray imgArray = data.optJSONArray("cover_pic");
        if (imgArray != null){
            ImageLoader.getInstance().loadImageUrl(context, ivImage, imgArray.optString(0));
        }
    }
}
