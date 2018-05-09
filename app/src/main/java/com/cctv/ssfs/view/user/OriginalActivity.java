package com.cctv.ssfs.view.user;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cctv.ssfs.R;
import com.cctv.ssfs.adapter.OrignalRecyclerAdapter;
import com.cctv.ssfs.base.BaseUrl;
import com.cctv.ssfs.netdata.MyObserver;
import com.cctv.ssfs.netdata.ObserverOnNextListener;
import com.cctv.ssfs.netdata.RetrofitManage;
import com.cctv.ssfs.utils.StartActivityUtlis;
import com.cctv.ssfs.view.base.BaseActivity;
import com.cctv.ssfs.view.home.activity.WebDetailsActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * 原创干货
 *
 * @author qi
 */
public class OriginalActivity extends BaseActivity implements View.OnClickListener, ObserverOnNextListener<ResponseBody>, BaseQuickAdapter.OnItemClickListener {

    private List<JSONObject> list;
    private OrignalRecyclerAdapter adapter;

    @Override
    public int getLayout() {
        return R.layout.activity_original;
    }

    @Override
    public void initView() {
        list = new ArrayList<>();

        ImageView iv_back = getView(R.id.iv_back);
        TextView tvTitle = getView(R.id.tv_title);
        RecyclerView recycler = getView(R.id.recycler_original);
        tvTitle.setText("原创干货");
        iv_back.setOnClickListener(this);

        adapter = new OrignalRecyclerAdapter(this, list);
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void initData() {
        request(0);
    }

    void request(int page) {
        RetrofitManage
                .getApiService(BaseUrl.BASE_WK_URL)
                .getOriginalData(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver<ResponseBody>(this));
    }

    @Override
    public void onNext(ResponseBody responseBody) {
        try {
            String json = responseBody.string();
            JSONObject jsonObject = new JSONObject(json);
            if (jsonObject.optBoolean("code")) {
                list.clear();
                JSONArray jsonArray = jsonObject.optJSONObject("data").optJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    list.add(jsonArray.optJSONObject(i));
                }
                adapter.notifyDataSetChanged();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        finish();
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        JSONObject data = list.get(position);
        StartActivityUtlis.startWebDetailsActivity(this,WebDetailsActivity.class,data);

        /*Intent intent = new Intent(this, WebDetailsActivity.class);
        intent.putExtra(WebDetailsActivity.ARTICLE_ID, data.optString("article_id"));
        intent.putExtra("code", data.optString("code"));
        intent.putExtra("label", "0");
        intent.putExtra("url", String.valueOf(data.optString("url")));
        intent.putExtra("lable_name", data.optString("lable_name"));
        JSONArray jsonArray = data.optJSONArray("cover_pic");
        if (null != jsonArray) {
            intent.putExtra("img", jsonArray.optString(0));
        }
        startActivity(intent);*/
    }
}
