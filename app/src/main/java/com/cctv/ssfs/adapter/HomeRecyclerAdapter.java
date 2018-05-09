package com.cctv.ssfs.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cctv.fancycoverflow.FancyCoverFlow;
import com.cctv.ssfs.R;
import com.cctv.ssfs.base.BaseGroupTypeAdapter;
import com.cctv.ssfs.base.BaseRecyclerViewAdapter;
import com.cctv.ssfs.base.MultipleTypeItemEntity;
import com.cctv.ssfs.base.ViewHolder;
import com.cctv.ssfs.utils.ImageLoader;
import com.cctv.ssfs.view.base.WebActivity;
import com.cctv.ssfs.view.home.activity.StockDetailsActivity;
import com.cctv.ssfs.view.home.activity.WebDetailsActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2018/3/12 0012.
 * 首页多种布局的适配器
 */
public class HomeRecyclerAdapter extends BaseGroupTypeAdapter<MultipleTypeItemEntity<JSONObject>> {
    /**
     * 只有标题
     */
    public static final int TYPE_HOME_ZERO = 0;
    /**
     * 一张图片加一个标题
     */
    public static final int TYPE_HOME_ONE = 1;
    /**
     * 图片列表
     */
    public static final int TYPE_HOME_IMGLIST = 2;
    /**
     * 一张图片加一个标题带描述信息
     */
    public static final int TYPE_HOME_PLAINTEXT = 3;
    /**
     * 追热点
     */
    public static final int TYPE_HOME_FOLLOW_UP = 4;
    /**
     * 知识(画廊)
     */
    public static final int TYPE_HOME_FOLLOW_FIVES = 5;
    /**
     * 短评论
     */
    public static final int TYPE_HOME_SIX = 6;
    /**
     * 风声资讯 一张图
     */
    public static final int TYPE_HOME_SEVEN = 7;
    /**
     * 风声资讯 三张图
     */
    public static final int TYPE_HOME_EIGHT = 8;


    private Context context;

    public HomeRecyclerAdapter(Context context, List<MultipleTypeItemEntity<JSONObject>> data) {
        super(data);
        this.context = context;
        addItemType(TYPE_HOME_ZERO, R.layout.item_home_imglist);
        addItemType(TYPE_HOME_ONE, R.layout.item_home_raphic);
        addItemType(TYPE_HOME_IMGLIST, R.layout.item_home_imglist);
        addItemType(TYPE_HOME_PLAINTEXT, R.layout.item_home_top);
        addItemType(TYPE_HOME_FOLLOW_UP, R.layout.item_home_follw);
        addItemType(TYPE_HOME_FOLLOW_FIVES, R.layout.item_home_fives);
        addItemType(TYPE_HOME_SIX, R.layout.item_home_six);
        addItemType(TYPE_HOME_SEVEN, R.layout.item_home_seven);
        addItemType(TYPE_HOME_EIGHT, R.layout.item_home_eight);
    }

    @Override
    protected void convert(ViewHolder helper, MultipleTypeItemEntity<JSONObject> item) {
        final JSONObject data = item.getData();

        int cover = data.optInt("cover");
        if (cover == TYPE_HOME_ZERO) {
            reuseLayout(helper, data);
            RecyclerView recycler = helper.getView(R.id.recycler_img);
            TextView tv_view = helper.getView(R.id.tv_view);
            recycler.setVisibility(View.GONE);
            tv_view.setVisibility(View.VISIBLE);
            TextView tvTitle = helper.getView(R.id.tv_titlebig);
            tvTitle.setText(data.optString("title"));

        } else if (cover == TYPE_HOME_ONE) {
            reuseLayout(helper, data);
            ImageView ivImage = helper.getView(R.id.iv_image);
            TextView tvInfo = helper.getView(R.id.tv_info);
            ImageLoader.getInstance().loadImageUrl(context, ivImage, data.optJSONArray("cover_pic").optString(0));
            tvInfo.setText(data.optString("title"));

        } else if (cover == TYPE_HOME_IMGLIST) {
            reuseLayout(helper, data);
            RecyclerView recycler = helper.getView(R.id.recycler_img);
            recycler.setVisibility(View.VISIBLE);
            TextView tv_view = helper.getView(R.id.tv_view);
            tv_view.setVisibility(View.GONE);
            TextView tvTitle = helper.getView(R.id.tv_titlebig);
            tvTitle.setText(data.optString("title"));
            JSONArray cover_pic = data.optJSONArray("cover_pic");
            final List<String> imgList = new ArrayList<>();
            for (int i = 0; i < cover_pic.length(); i++) {
                imgList.add(cover_pic.optString(i));
            }

            recycler.setLayoutManager(new GridLayoutManager(context, 3));
            ImgListRecyclerAdapter listRecyclerAdapter = new ImgListRecyclerAdapter(imgList);
            recycler.setAdapter(listRecyclerAdapter);

            listRecyclerAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    Intent intent = new Intent(context, WebDetailsActivity.class);
                    intent.putExtra(WebDetailsActivity.ARTICLE_ID, data.optString("article_id"));
                    intent.putExtra("code", data.optString("code"));
                    intent.putExtra("label", String.valueOf(data.optInt("lable_id")));
                    intent.putExtra("url", data.optString("url"));
                    intent.putExtra("img", imgList.get(0));
                    intent.putExtra("lable_name", data.optString("lable_name"));
                    context.startActivity(intent);
                }
            });

        } else if (cover == TYPE_HOME_PLAINTEXT) {
            reuseLayout(helper, data);
            ImageView ivImage = helper.getView(R.id.iv_image);
            TextView tvInfo = helper.getView(R.id.tv_info);
            JSONArray cover_pic = data.optJSONArray("cover_pic");

            if (cover_pic.length() > 0) {
                ImageLoader.getInstance().loadImageUrl(context, ivImage, cover_pic.optString(0));
            }
            TextView tvTitle = helper.getView(R.id.tv_titlebig);
            tvTitle.setText(data.optString("title"));
            tvInfo.setText(data.optString("art_desc"));

        } else if (cover == TYPE_HOME_FOLLOW_UP) {
            LinearLayout linear_top = helper.getView(R.id.linear_top);
            LinearLayout linear_bottom = helper.getView(R.id.linear_bottom);
            String art_desc = data.optString("art_desc");
            helper.setText(R.id.tv_titlebig, data.optString("title"));

            if (art_desc.equals("")) {
                linear_top.setVisibility(View.GONE);
                linear_bottom.setVisibility(View.VISIBLE);

                helper.setText(R.id.tv_time2, data.optString("time"));
                helper.setText(R.id.tv_source, data.optString("art_from"));

            } else {
                linear_bottom.setVisibility(View.GONE);
                linear_top.setVisibility(View.VISIBLE);
                ImageView ivImage = helper.getView(R.id.iv_image);
                JSONArray cover_pic = data.optJSONArray("cover_pic");
                if (cover_pic.length() > 0) {
                    ImageLoader.getInstance().loadImageUrl(context, ivImage, cover_pic.optString(0));
                }

                helper.setText(R.id.tv_info, art_desc);
                helper.setText(R.id.tv_art_from, String.format("# %s", data.optString("art_from")));
                helper.setText(R.id.tv_time, data.optString("time"));
            }


        } else if (cover == TYPE_HOME_FOLLOW_FIVES) {
            FancyCoverFlow fancyCoverFlow = helper.getView(R.id.fancyCoverFlow);

        } else if (cover == TYPE_HOME_SIX) {
            StringBuffer buffer = new StringBuffer();
            buffer.append(data.optString("name"));
            buffer.append("(");
            buffer.append(data.optString("code"));
            buffer.append(")");
            TextView tv_name_code = helper.getView(R.id.tv_name_code);
            tv_name_code.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
            tv_name_code.setText(buffer.toString());
            tv_name_code.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, StockDetailsActivity.class);
                    intent.putExtra(StockDetailsActivity.CODE, data.optString("code"));
                    context.startActivity(intent);
                }
            });
            helper.setText(R.id.tv_content, data.optString("content"));
            helper.setText(R.id.tv_user_name, String.format("%s   买过", data.optString("user_name")));
            helper.setText(R.id.tv_praise, String.format("·   %s人  赞同", data.optString("praise")));
            helper.setText(R.id.tv_count, String.format("%s分", data.optInt("count") * 2));
        } else if (cover == TYPE_HOME_SEVEN) {

        } else if (cover == TYPE_HOME_EIGHT) {

        }
    }

    @NonNull
    private void reuseLayout(ViewHolder helper, final JSONObject data) {
        TextView tvTag = helper.getView(R.id.tv_flag);
        TextView tvStockname = helper.getView(R.id.tv_stockname);
        TextView tvTime = helper.getView(R.id.tv_time);
        TextView tvSource = helper.getView(R.id.tv_source);

        if (data.optString("name").equals("") && data.optString("code").equals("")) {
            tvStockname.setText("");
        } else {
            StringBuffer buffer = new StringBuffer();
            buffer.append(data.optString("name"));
            buffer.append("(");
            buffer.append(data.optString("code"));
            buffer.append(")");
            tvStockname.setText(buffer.toString());
        }

        int is_top = data.optInt("is_top");
        if (is_top == 2) {
            tvTag.setVisibility(View.VISIBLE);
            tvTag.setText("置顶");
        } else if (is_top == 1) {
            tvTag.setVisibility(View.VISIBLE);
            tvTag.setText("热门");
        } else if (is_top == 0) {
            tvTag.setVisibility(View.GONE);
        }
        tvStockname.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        tvTime.setText(data.optString("time"));
        tvSource.setText(data.optString("art_from"));

        tvStockname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StockDetailsActivity.class);
                intent.putExtra(StockDetailsActivity.CODE, data.optString("code"));
                context.startActivity(intent);
            }
        });
    }


    private class ImgListRecyclerAdapter extends BaseRecyclerViewAdapter<String> {

        public ImgListRecyclerAdapter(@Nullable List<String> data) {
            super(R.layout.item_recycler_img, data);
        }

        @Override
        protected void convert(ViewHolder helper, String item) {
            ImageView img = helper.getView(R.id.img_imgList);
            ImageLoader.getInstance().loadImageUrl(context, img, item);
        }
    }

}
