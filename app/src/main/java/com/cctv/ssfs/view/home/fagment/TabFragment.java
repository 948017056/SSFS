package com.cctv.ssfs.view.home.fagment;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.cctv.ssfs.R;
import com.cctv.ssfs.adapter.HomeRecyclerAdapter;
import com.cctv.ssfs.base.BaseUrl;
import com.cctv.ssfs.base.MultipleTypeItemEntity;
import com.cctv.ssfs.base.RefreshableView;
import com.cctv.ssfs.entity.HomeBean;
import com.cctv.ssfs.netdata.MyObserver;
import com.cctv.ssfs.netdata.ObserverOnNextListener;
import com.cctv.ssfs.netdata.RetrofitManage;
import com.cctv.ssfs.view.base.BaseFragment;
import com.cctv.ssfs.view.home.activity.ListedCompanyActivity;
import com.cctv.ssfs.view.home.activity.WebDetailsActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment extends BaseFragment implements ObserverOnNextListener<ResponseBody>, BaseQuickAdapter.OnItemClickListener {
    private List<MultipleTypeItemEntity<JSONObject>> jsonList = new ArrayList<>();
    private RecyclerView recycler;
    private HomeRecyclerAdapter adapter;
    private int pager = 1, label, last_page;
    private Map<String, Object> map;
    private JSONObject jsonObject;
    private boolean isPaging;

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_tab;
    }

    @Override
    protected void initFragmentView(View view) {
        pager = 1;
        recycler = getView(R.id.recycler_tab);
        final RefreshableView refreshableView = getView(R.id.refreshable_view);
        adapter = new HomeRecyclerAdapter(getContext(), jsonList);
        adapter.setOnItemClickListener(this);
//        View footView = View.inflate(getContext(), R.layout.layout_foot_tab, null);
//        final FrameLayout tvNoData = footView.findViewById(R.id.tv_nodata);
//        tvLoading = footView.findViewById(R.id.tv_Loading);
//        adapter.addFooterView(footView);
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
        refreshableView.setOnRefreshListener(new RefreshableView.PullToRefreshListener() {
            @Override
            public void onRefresh() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                isPaging = false;
                pager = 1;
                request(label, pager);
                refreshableView.finishRefreshing();
            }
        }, 0);
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                recycler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isPaging = true;
                        pager++;
                        if (pager <= last_page) {
                            request(label, pager);
                            adapter.loadMoreComplete();
                        } else {
                            adapter.loadMoreEnd();
                        }
                    }
                }, 500);
            }
        }, recycler);

    }

    @Override
    protected void onFragmentVisibleChange() {
        jsonList.clear();
        if (jsonObject != null) {
            JSONArray jsonArray = jsonObject.optJSONObject("data").optJSONObject("article").optJSONArray("data");
            for (int i = 0; i < jsonArray.length(); i++) {
                final JSONObject data = jsonArray.optJSONObject(i);
                jsonList.add(new MultipleTypeItemEntity<JSONObject>(data.optInt("cover"), data));
            }
            adapter.notifyDataSetChanged();
        }

    }

    private void request(int label, int page) {
        map = new HashMap<>();
        map.put(BaseUrl.LABEL, label);
        map.put(BaseUrl.PAGE, page);
        RetrofitManage.getApiService(BaseUrl.BASE_WK_URL).getHomeData(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver<ResponseBody>(TabFragment.this));
    }

    /**
     * @param jsonObject 数据
     * @param label      标签对应Id
     * @param last_page  可以加载更多的次数
     */
    public void setData(int label, int last_page, JSONObject jsonObject) {
        this.label = label;
        this.last_page = last_page;
        this.jsonObject = jsonObject;

        if (adapter != null) {
            jsonList.clear();
            if (jsonObject != null) {
                JSONArray jsonArray = jsonObject.optJSONObject("data").optJSONObject("article").optJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    final JSONObject data = jsonArray.optJSONObject(i);
                    jsonList.add(new MultipleTypeItemEntity<JSONObject>(data.optInt("cover"), data));
                }
                adapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public void onNext(ResponseBody body) {
        try {
            String string = body.string();
            JSONObject jsonObject = new JSONObject(string);
            if (jsonObject.optBoolean("code")) {
                JSONArray jsonArray = jsonObject.optJSONObject("data").optJSONObject("article").optJSONArray("data");

                if (isPaging) {
//                    tvLoading.setVisibility(View.VISIBLE);
                    List<MultipleTypeItemEntity<JSONObject>> newList = new ArrayList<>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        final JSONObject data = jsonArray.optJSONObject(i);
                        newList.add(new MultipleTypeItemEntity<JSONObject>(data.optInt("cover"), data));
                    }
                    jsonList.addAll(jsonList.size() - 1, newList);
                } else {
                    jsonList.clear();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        final JSONObject data = jsonArray.optJSONObject(i);
                        jsonList.add(new MultipleTypeItemEntity<JSONObject>(data.optInt("cover"), data));
                    }
                }
                adapter.notifyDataSetChanged();
            } else {
//                tvLoading.setVisibility(View.GONE);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

        final MultipleTypeItemEntity<JSONObject> jsonObject = jsonList.get(position);
        final JSONObject data = jsonObject.getData();
        if (data.optInt("cover") != 6) {
            Intent intent = new Intent(getContext(), WebDetailsActivity.class);
            intent.putExtra(WebDetailsActivity.ARTICLE_ID, data.optString("article_id"));
            intent.putExtra("code", data.optString("code"));
            intent.putExtra("label", String.valueOf(label));
            intent.putExtra("url", String.valueOf(data.optString("url")));
            intent.putExtra("lable_name", data.optString("lable_name"));
            JSONArray jsonArray = data.optJSONArray("cover_pic");
            if (null != jsonArray) {
                intent.putExtra("img", jsonArray.optString(0));
            }
            startActivity(intent);
        } else {
            Intent intent = new Intent(getContext(), ListedCompanyActivity.class);
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
            startActivity(intent);
        }
    }

}
