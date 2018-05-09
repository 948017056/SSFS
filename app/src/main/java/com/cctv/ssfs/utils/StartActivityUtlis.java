package com.cctv.ssfs.utils;

import android.content.Context;
import android.content.Intent;

import com.cctv.ssfs.view.home.activity.WebDetailsActivity;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by sanjin on 2018/4/28.
 */

public class StartActivityUtlis {
    public static void startWebDetailsActivity(Context context, Class<?> cls, JSONObject data) {
        Intent intent = new Intent(context, cls);
        intent.putExtra(WebDetailsActivity.ARTICLE_ID, data.optString("article_id"));
        intent.putExtra("code", data.optString("code"));
        intent.putExtra("label", "0");
        intent.putExtra("url", data.optString("url"));
        intent.putExtra("lable_name", data.optString("lable_name"));
        JSONArray jsonArray = data.optJSONArray("cover_pic");
        if (null != jsonArray) {
            intent.putExtra("img", jsonArray.optString(0));
        }
        context.startActivity(intent);
    }
}
