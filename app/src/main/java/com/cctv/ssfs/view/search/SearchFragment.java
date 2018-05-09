package com.cctv.ssfs.view.search;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cctv.ssfs.R;
import com.cctv.ssfs.adapter.SearchListAdapter;
import com.cctv.ssfs.adapter.SearchRecyclerAdapter;
import com.cctv.ssfs.base.BaseRecyclerViewAdapter;
import com.cctv.ssfs.base.BaseUrl;
import com.cctv.ssfs.base.ViewHolder;
import com.cctv.ssfs.customview.flowlayout.FlowLayout;
import com.cctv.ssfs.customview.flowlayout.TagFlowLayout;
import com.cctv.ssfs.entity.SearchBean;
import com.cctv.ssfs.netdata.MyObserver;
import com.cctv.ssfs.netdata.ObserverOnNextListener;
import com.cctv.ssfs.netdata.RetrofitManage;
import com.cctv.ssfs.utils.ToastUtils;
import com.cctv.ssfs.view.MainActivity;
import com.cctv.ssfs.view.base.BaseFragment;
import com.cctv.ssfs.view.home.activity.StockDetailsActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * A simple {@link Fragment} subclass.
 * 搜索页
 */
public class SearchFragment extends BaseFragment implements TextView.OnEditorActionListener, TextWatcher, ObserverOnNextListener<ResponseBody>, View.OnClickListener, View.OnTouchListener, View.OnKeyListener, TagFlowLayout.OnTagClickListener, BaseQuickAdapter.OnItemClickListener {

    public EditText edSearch;
    public LinearLayout linear_bottom;
    public FrameLayout frame_bottom;
    public RecyclerView recycler_search;
    public TextView btnSearch;
    public RecyclerView recycler;
    private Map<String, Object> map;
    public static final String NUMERAL = "[0-9]*";
    private List<SearchBean.DataBean> list = new ArrayList<>();
    private SearchListAdapter searchListAdapter;
    public static final String SUCCESS_CODE = "40000";
    private List<JSONObject> jsonList = new ArrayList<>();
    private List<JSONObject> gridList = new ArrayList<>();
    private SearchRecyclerAdapter searchAdapter;
    private GridRecyclerAdapter gridAdapter;
    private int pager = 1, last_page;

    public SearchFragment() {
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_search;
    }

    @Override
    public void initFragmentView(View view) {
        ((MainActivity) getContext()).rbMenuSearch.setChecked(true);
        edSearch = getView(R.id.ed_search);
        edSearch.requestFocus();
        linear_bottom = getView(R.id.linear_bottom);
        frame_bottom = getView(R.id.frame_bottom);
        recycler_search = getView(R.id.recycler_search);
        btnSearch = getView(R.id.btn_search);
        recycler = getView(R.id.recycler);
        gridList = new ArrayList<>();

        View headView = View.inflate(getContext(), R.layout.layout_head_recycler, null);
        RecyclerView recyclerGrid = headView.findViewById(R.id.recycler_grid);
        recyclerGrid.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        //热搜列表
        gridAdapter = new GridRecyclerAdapter(gridList);
        recyclerGrid.setAdapter(gridAdapter);
        gridAdapter.setOnItemClickListener(this);
        //下面的列表
        searchAdapter = new SearchRecyclerAdapter(getContext(), jsonList);
        searchAdapter.addHeaderView(headView);
        View footView = View.inflate(getContext(), R.layout.layout_foot_tab, null);
        final FrameLayout tvNoData = footView.findViewById(R.id.tv_nodata);
        final LinearLayout tvLoading = footView.findViewById(R.id.tv_Loading);
        searchAdapter.addFooterView(footView);
        recycler.setAdapter(searchAdapter);
        recycler.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (isSlideToBottom(recyclerView, newState)) {
                    if (pager <= last_page) {
                        tvLoading.setVisibility(View.GONE);
                        tvNoData.setVisibility(View.VISIBLE);
                    }
                    pager++;
                    if (pager <= last_page) {
                        tvLoading.setVisibility(View.VISIBLE);
                        tvNoData.setVisibility(View.GONE);
                        request2(pager, new ObserverOnNextListener<ResponseBody>() {
                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    String string = responseBody.string();
                                    JSONObject jsonObject = new JSONObject(string);
                                    if (jsonObject.optBoolean("code")) {
                                        JSONObject jsonObjectData = jsonObject.optJSONObject("data");
                                        last_page = jsonObjectData.optInt("last_page");
                                        JSONArray jsonArray = jsonObjectData.optJSONArray("data");
                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            searchAdapter.addData(jsonList.size() - 1, jsonArray.optJSONObject(i));
                                        }
                                        searchAdapter.notifyDataSetChanged();
                                    }

                                } catch (IOException e) {
                                    e.printStackTrace();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
            }
        });

        edSearch.setOnEditorActionListener(this);
        edSearch.setOnKeyListener(this);
        edSearch.addTextChangedListener(this);
        edSearch.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        searchListAdapter = new SearchListAdapter(getContext(), list);
        recycler_search.setAdapter(searchListAdapter);
        view.setOnTouchListener(this);

    }

    /**
     * 到底部返回true
     *
     * @param recyclerView
     * @return
     */
    public boolean isSlideToBottom(RecyclerView recyclerView, int newState) {
        //当前RecyclerView显示出来的最后一个的item的position
        int lastPosition = -1;
        //当前状态为停止滑动状态SCROLL_STATE_IDLE时
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof GridLayoutManager) {
                //通过LayoutManager找到当前显示的最后的item的position
                lastPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
            } else if (layoutManager instanceof LinearLayoutManager) {
                lastPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                int[] lastPositions = new int[((StaggeredGridLayoutManager) layoutManager).getSpanCount()];
                ((StaggeredGridLayoutManager) layoutManager).findLastVisibleItemPositions(lastPositions);
                lastPosition = findMax(lastPositions);
            }
            //时判断界面显示的最后item的position是否等于itemCount总数-1也就是最后一个item的position
            //如果相等则说明已经滑动到最后了
            if (lastPosition == recyclerView.getLayoutManager().getItemCount() - 1) {
                return true;
            } else {
                return false;
            }

        }
        return false;
    }

    //找到数组中的最大值
    private int findMax(int[] lastPositions) {
        int max = lastPositions[0];
        for (int value : lastPositions) {
            if (value > max) {
                max = value;
            }
        }
        return max;


    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Intent intent = new Intent(getContext(), StockDetailsActivity.class);
        intent.putExtra(StockDetailsActivity.CODE, gridList.get(position).optString("code"));
        startActivity(intent);
    }

    private class GridRecyclerAdapter extends BaseRecyclerViewAdapter<JSONObject> {

        public GridRecyclerAdapter(@Nullable List<JSONObject> data) {
            super(R.layout.item_tag2, data);
        }

        @Override
        protected void convert(ViewHolder helper, JSONObject item) {
            helper.setText(R.id.tv_tag2, item.optString("name"));
        }
    }
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        initFragmentData();
    }

    @Override
    public void initFragmentData() {
        map = new HashMap<>();
        RetrofitManage.getApiService(BaseUrl.BASE_WK_URL).getHomeData(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver<ResponseBody>(new ObserverOnNextListener<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String string = responseBody.string();
                            JSONObject jsonObject = new JSONObject(string);

                            if (jsonObject.optBoolean("code")) {
                                final JSONArray jsonArray = jsonObject.optJSONObject("data").optJSONArray("hot");
                                gridList.clear();
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    gridList.add(jsonArray.optJSONObject(i));
                                }
                                gridAdapter.notifyDataSetChanged();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }));

        request2(pager, new ObserverOnNextListener<ResponseBody>() {
            @Override
            public void onNext(ResponseBody responseBody) {
                try {
                    String string = responseBody.string();
                    JSONObject jsonObject = new JSONObject(string);
                    if (jsonObject.optBoolean("code")) {
                        JSONObject jsonObjectData = jsonObject.optJSONObject("data");
                        last_page = jsonObjectData.optInt("last_page");
                        JSONArray jsonArray = jsonObjectData.optJSONArray("data");
                        jsonList.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            jsonList.add(jsonArray.optJSONObject(i));
                        }
                        searchAdapter.notifyDataSetChanged();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void request2(int page, ObserverOnNextListener nextListener) {
        map.put("page", page);
        RetrofitManage.getApiService(BaseUrl.BASE_WK_URL)
                .getSearchCommentData(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver<ResponseBody>(nextListener));
    }

    @Override
    public boolean onTagClick(View view, int position, FlowLayout parent) {
        //流式标签点击
        /*Intent intent = new Intent(getContext(), StockDetailsActivity.class);
        intent.putExtra(StockDetailsActivity.CODE, tagCodeList.get(position));
        startActivity(intent);*/
        return false;
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            // 当按了搜索之后关闭软键盘
            ((InputMethodManager) edSearch.getContext().getSystemService(
                    Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                    ((MainActivity) getContext()).getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
            return true;
        }
        return false;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }


    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    /**
     * 监听搜索框
     */
    @Override
    public void afterTextChanged(Editable s) {String code = edSearch.getText().toString();
        if (isNumeric(code)) {
            if (s.length() >= 3) {
                recycler_search.setVisibility(View.VISIBLE);
                linear_bottom.setVisibility(View.GONE);
                request(code);
            } else {
                recycler_search.setVisibility(View.GONE);
                linear_bottom.setVisibility(View.VISIBLE);
            }
        } else {
            if (s.length() >= 0) {
                recycler_search.setVisibility(View.VISIBLE);
                linear_bottom.setVisibility(View.GONE);
                request(code);
            } else if (s.length() == 0) {
                recycler_search.setVisibility(View.GONE);
                linear_bottom.setVisibility(View.VISIBLE);
            }
        }

    }

    private void request(String code) {
        RetrofitManage.getApiService(BaseUrl.BASE_WK_URL2)
                .getSearchData(code)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver<ResponseBody>(this));
    }

    public boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile(NUMERAL);
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    @Override
    public void onNext(ResponseBody responseBody) {
        try {
            String json = responseBody.string();
            JSONObject jsonObject = new JSONObject(json);
            if (SUCCESS_CODE.equals(jsonObject.optString("code"))) {
                list.clear();
                JSONArray jsonArray = jsonObject.optJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonData = jsonArray.optJSONObject(i);
                    SearchBean.DataBean dataBean = new SearchBean.DataBean();
                    dataBean.setName(jsonData.optString("name"));
                    dataBean.setCode(jsonData.optString("code"));
                    list.add(dataBean);
                }
                searchListAdapter.notifyDataSetChanged();
            } else {
                ToastUtils.showShortToast(getContext(), "请输入正确的股票/简拼");
                recycler_search.setVisibility(View.GONE);
                linear_bottom.setVisibility(View.VISIBLE);
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ed_search:
                btnSearch.setText("取消");
                break;
            case R.id.btn_search:
                edSearch.setText("");
                btnSearch.setText("搜索");
                recycler_search.setVisibility(View.GONE);
                linear_bottom.setVisibility(View.VISIBLE);
                break;
            default:
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        InputMethodManager manager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (getActivity().getCurrentFocus() != null && getActivity().getCurrentFocus().getWindowToken() != null) {
                btnSearch.setText("搜索");
                manager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
        return false;
    }

    /**
     * 键盘点击搜索的回调
     *
     * @param v
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKey(View v, int keyCode, KeyEvent event) {
        // 修改回车键功能
        if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
            // 隐藏键盘
            ((InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                    getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
        return false;
    }

}

