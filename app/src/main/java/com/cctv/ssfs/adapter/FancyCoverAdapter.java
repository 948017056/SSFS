package com.cctv.ssfs.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cctv.fancycoverflow.FancyCoverFlowAdapter;
import com.cctv.ssfs.R;

import org.json.JSONObject;

import java.util.List;

/**
 * @author qi
 * @date 2018/4/13
 */

public class FancyCoverAdapter extends FancyCoverFlowAdapter {
    private Context context;
    private List<JSONObject> list;

    public FancyCoverAdapter(Context context, List<JSONObject> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public View getCoverFlowItem(int position, View reusableView, ViewGroup parent) {

        if (reusableView == null) {
            reusableView = View.inflate(context, R.layout.item_fancycover, null);

        }
        return null;
    }

    private class ViewHolder {
        private ImageView ivImg;
        private TextView tvTitle;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
