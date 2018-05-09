package com.cctv.ssfs.view.user;

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
import com.cctv.ssfs.utils.RecyclerViewScrollUtils;
import com.cctv.ssfs.utils.SPUtils;
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
 * 干货收藏
 *
 * @author qi
 */
public class CollectionActivity extends BaseActivity implements View.OnClickListener, BaseQuickAdapter.OnItemClickListener, ObserverOnNextListener<ResponseBody> {

    private OrignalRecyclerAdapter adapter;
    private List<JSONObject> list = new ArrayList<>();
    private String userId;
    private TextView tvNotCollection;
    private RecyclerView recycler;
    private int lastPage, pager = 1;
    private boolean isPaging;

    @Override
    public int getLayout() {
        return R.layout.activity_collection;
    }

    @Override
    public void initView() {
        userId = (String) SPUtils.getParam(this, LoginActivity.USER_ID, "");

        ImageView iv_back = getView(R.id.iv_back);
        TextView tvTitle = getView(R.id.tv_title);
        tvNotCollection = getView(R.id.tv_notCollection);
        recycler = getView(R.id.recycler_collection);
        tvTitle.setText("干货收藏");
        iv_back.setOnClickListener(this);
        adapter = new OrignalRecyclerAdapter(this, list);
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

        recycler.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (RecyclerViewScrollUtils.isSlideToBottom(recyclerView, newState)) {
                    pager++;
                    if (pager <= lastPage) {
                        request(pager, true);
                    }
                }
            }
        });
    }

    @Override
    public void initData() {
        request(pager, false);

    }

    private void request(int page, boolean isPaging) {
        this.isPaging = isPaging;
        RetrofitManage.getApiService(BaseUrl.BASE_WK_URL)
                .getCollect(userId, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver<ResponseBody>(this));
    }

    @Override
    public void onClick(View v) {
        finish();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        JSONObject data = list.get(position);
        StartActivityUtlis.startWebDetailsActivity(this, WebDetailsActivity.class, data);
//        Intent intent = new Intent(this, WebDetailsActivity.class);
//        intent.putExtra(WebDetailsActivity.ARTICLE_ID, data.optString("article_id"));
//        intent.putExtra("code", data.optString("code"));
//        intent.putExtra("label", "0");
//        intent.putExtra("url", String.valueOf(data.optString("url")));
//        intent.putExtra("lable_name", data.optString("lable_name"));
//        JSONArray jsonArray = data.optJSONArray("cover_pic");
//        if (null != jsonArray) {
//            intent.putExtra("img", jsonArray.optString(0));
//        }
//        startActivity(intent);
    }

    @Override
    public void onNext(ResponseBody responseBody) {
        try {
            String json = responseBody.string();
            JSONObject jsonObject = new JSONObject(json);
            if (jsonObject.optBoolean("code")) {
                final JSONObject jsonData = jsonObject.optJSONObject("data");
                JSONArray jsonArray = jsonData.optJSONArray("data");
                lastPage = jsonData.optInt("last_page");
                if (jsonArray.length() > 0) {
                    if (isPaging) {
                        for (int i = 0; i < jsonArray.length(); i++) {
                            adapter.addData(list.size() - 1, jsonArray.optJSONObject(i).optJSONObject("article"));
                        }
                    } else {
                        tvNotCollection.setVisibility(View.GONE);
                        recycler.setVisibility(View.VISIBLE);
                        list.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            list.add(jsonArray.optJSONObject(i).optJSONObject("article"));
                        }
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    recycler.setVisibility(View.GONE);
                    tvNotCollection.setVisibility(View.VISIBLE);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
