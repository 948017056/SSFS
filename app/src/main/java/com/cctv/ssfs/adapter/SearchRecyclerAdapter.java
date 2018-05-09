package com.cctv.ssfs.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.cctv.ssfs.R;
import com.cctv.ssfs.base.BaseRecyclerViewAdapter;
import com.cctv.ssfs.base.ViewHolder;
import com.cctv.ssfs.entity.HomeBean;
import com.cctv.ssfs.utils.ToastUtils;
import com.cctv.ssfs.view.home.activity.ListedCompanyActivity;
import com.cctv.ssfs.view.home.activity.StockDetailsActivity;

import org.json.JSONObject;

import java.util.List;

/**
 * @author qi
 * @date 2018/4/4
 */

public class SearchRecyclerAdapter extends BaseRecyclerViewAdapter<JSONObject> {
    private Context context;

    public SearchRecyclerAdapter(Context context, @Nullable List<JSONObject> data) {
        super(R.layout.item_home_six2, data);
        this.context = context;
    }

    @Override
    protected void convert(ViewHolder helper, final JSONObject data) {
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
        helper.setText(R.id.tv_content, data.optString("content").replace("\n",""));
        helper.setText(R.id.tv_user_name, data.optString("user_name") + "   买过");
        helper.setText(R.id.tv_praise, data.optString("praise") + "人  赞同");
        helper.setText(R.id.tv_count, (data.optInt("count") * 2) + "分");

        ((TextView) helper.getView(R.id.tv_content)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ListedCompanyActivity.class);
                HomeBean.DataBeanX.ArticleBean.DataBean bean = new HomeBean.DataBeanX.ArticleBean.DataBean();
                bean.setHave_id(data.optInt("have_id"));
                bean.setUser_id(data.optInt("user_id"));
                bean.setCode(data.optString("code"));
                bean.setCount(data.optInt("count"));
                bean.setPraise(data.optInt("praise"));
                bean.setContent(data.optString("content"));
                bean.setTime(data.optString("time"));
                bean.setUser_img(data.optString("user_img"));
                bean.setUser_name(data.optString("user_name"));
                bean.setName(data.optString("name"));
                bean.setCover(data.optInt("cover"));
                intent.putExtra("bean", bean);
                context.startActivity(intent);
            }
        });
    }
}
