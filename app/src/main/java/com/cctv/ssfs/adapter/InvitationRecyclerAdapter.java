package com.cctv.ssfs.adapter;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.cctv.ssfs.R;
import com.cctv.ssfs.base.BaseRecyclerViewAdapter;
import com.cctv.ssfs.base.ViewHolder;
import com.cctv.ssfs.customview.CircleImageView;
import com.cctv.ssfs.utils.ImageLoader;

import org.json.JSONObject;

import java.util.List;

/**
 * @author qi
 * @date 2018/4/12
 */

public class InvitationRecyclerAdapter extends BaseRecyclerViewAdapter<JSONObject> {
    private Context context;

    public InvitationRecyclerAdapter(Context context, @Nullable List<JSONObject> data) {
        super(R.layout.item_invitation, data);
        this.context = context;
    }

    @Override
    protected void convert(ViewHolder helper, JSONObject item) {
        int isSuccess = item.optInt("is_success");
        TextView tvSuccess = helper.getView(R.id.tv_success);
        if (isSuccess == 0) {
            tvSuccess.setText("等待确认");
        } else if (isSuccess == 1) {
            tvSuccess.setText("已成功邀请");
        }
        helper.setText(R.id.tv_name, item.optString("user_name"));
        helper.setText(R.id.tv_time, item.optString("time"));
        CircleImageView iv_headimg = helper.getView(R.id.iv_headimg);
        ImageLoader.getInstance().loadImageUrl(context, iv_headimg, item.optString("user_img"));
    }
}
