package com.cctv.ssfs.view.home.fagment;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cctv.ssfs.R;
import com.cctv.ssfs.base.BaseRecyclerViewAdapter;
import com.cctv.ssfs.base.BaseUrl;
import com.cctv.ssfs.base.ViewHolder;
import com.cctv.ssfs.customview.CircleImageView;
import com.cctv.ssfs.entity.HomeBean;
import com.cctv.ssfs.netdata.MyObserver;
import com.cctv.ssfs.netdata.ObserverOnNextListener;
import com.cctv.ssfs.netdata.RetrofitManage;
import com.cctv.ssfs.utils.ImageLoader;
import com.cctv.ssfs.utils.SPUtils;
import com.cctv.ssfs.view.base.BaseFragment;
import com.cctv.ssfs.view.home.activity.ListedCompanyActivity;
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
 * 精彩评论
 *
 * @author qi
 */
public class AllCommentFragment extends BaseFragment implements BaseQuickAdapter.OnItemClickListener {

    private List<JSONObject> list;
    private AllCommentAdapter adapter;
    private String code;
    private int flag;
    private JSONObject objectData;
    private int last_page, page = 1;
    private FrameLayout tvNoData;
    private LinearLayout tvLoading;

    public AllCommentFragment() {
    }


    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_allcomment;
    }

    @Override
    protected void initFragmentView(View view) {
        list = new ArrayList<>();
        RecyclerView recycler = getView(R.id.recycler_all);
        View footView = View.inflate(getContext(), R.layout.layout_foot_allcomment, null);
        tvNoData = footView.findViewById(R.id.tv_nodata);
        tvLoading = footView.findViewById(R.id.tv_Loading);
        adapter = new AllCommentAdapter(list);
        adapter.addFooterView(footView);
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(this);

        recycler.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (isSlideToBottom(recyclerView, newState)) {
                    page++;
                    if (page <= last_page) {
                        tvLoading.setVisibility(View.VISIBLE);
                        tvNoData.setVisibility(View.GONE);
                        request(code, page);
                    }
                }
            }


        });
    }

    private void request(String code, int page) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("page", page);
        RetrofitManage
                .getApiService(BaseUrl.BASE_WK_URL)
                .getCommentData(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver<ResponseBody>(new ObserverOnNextListener<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String json = responseBody.string();
                            JSONObject jsonObject = new JSONObject(json);
                            if (jsonObject.optBoolean("code")) {
                                JSONObject data = jsonObject.optJSONObject("data");
                                adapter.addData(adapter.getItemCount() - 1, data);
                                adapter.notifyDataSetChanged();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }));
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


    @Override
    protected void onFragmentVisibleChange() {
        if (null != objectData) {
            list.clear();
            JSONArray arrayData = objectData.optJSONArray("data");
            for (int i = 0; i < arrayData.length(); i++) {
                list.add(arrayData.optJSONObject(i));
            }
            adapter.notifyDataSetChanged();
        }
    }

    public void setData(String code, int flag, JSONObject objectData) {
        this.code = code;
        this.flag = flag;
        this.objectData = objectData;
        //最多页数
        last_page = objectData.optInt("last_page");
        if (page == last_page) {
            tvLoading.setVisibility(View.GONE);
            tvNoData.setVisibility(View.VISIBLE);
        } else {
            tvLoading.setVisibility(View.VISIBLE);
            tvNoData.setVisibility(View.GONE);
        }
        if (null != adapter) {
            list.clear();
            JSONArray arrayData = objectData.optJSONArray("data");
            for (int i = 0; i < arrayData.length(); i++) {
                list.add(arrayData.optJSONObject(i));
            }
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        JSONObject data = list.get(position);
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

    private class AllCommentAdapter extends BaseRecyclerViewAdapter<JSONObject> {

        public AllCommentAdapter(@Nullable List<JSONObject> data) {
            super(R.layout.item_comment2, data);
        }

        @Override
        protected void convert(ViewHolder helper, final JSONObject data) {
            TextView tvName = helper.getView(R.id.tv_comment_name);
            final TextView tvAdd = helper.getView(R.id.tv_add);
            TextView tvTime = helper.getView(R.id.tv_comment_time);
            final TextView tvContent = helper.getView(R.id.tv_comment_content);
            final TextView tvLikeCount = helper.getView(R.id.tv_like_count);
            final ImageView ivLikeStatus = helper.getView(R.id.iv_like_status);
            final TextView tvExpand = helper.getView(R.id.tv_expand);
            TextView tvCount = helper.getView(R.id.tv_count);
            CircleImageView ivHeadPortrait = helper.getView(R.id.iv_headportrait);
            boolean isPraise = SPUtils.getPraise(getContext(), data.optString("have_id") + "", false);
            if (isPraise) {
                ivLikeStatus.setImageResource(R.mipmap.statuslike);
            } else {
                ivLikeStatus.setImageResource(R.mipmap.like);
            }
            tvContent.post(new Runnable() {
                @Override
                public void run() {
                    int txtPart = tvContent.getLineCount();
                    if (txtPart > 5) {
                        tvContent.setMaxLines(5);
                        tvExpand.setVisibility(View.VISIBLE);
                    } else {
                        tvExpand.setVisibility(View.GONE);
                    }
                }
            });

            tvName.setText(data.optString("user_name"));
            tvTime.setText(data.optString("time"));
            final String content = data.optString("content");
            tvContent.setText(content.replace("\n", ""));
            tvCount.setText((data.optInt("count") * 2) + "");
            ImageLoader.getInstance().loadImageUrl(getContext(), ivHeadPortrait, data.optString("user_img"));
            tvLikeCount.setText(String.valueOf(data.optInt("praise")));

            tvExpand.setOnClickListener(new View.OnClickListener() {
                boolean isExpand = false;

                @Override
                public void onClick(View v) {
                    if (isExpand) {
                        tvExpand.setText("展开");
                        tvContent.setMaxLines(5);
                        isExpand = false;
                    } else {
                        tvExpand.setText("收起");
                        tvContent.setMaxLines(100);
                        isExpand = true;
                    }
                }
            });

            ivLikeStatus.setOnClickListener(new View.OnClickListener() {
                final String haveId = data.optString("have_id");

                @Override
                public void onClick(View v) {
                    boolean isPraise = SPUtils.getPraise(getContext(), haveId + "", false);
                    if (!isPraise) {
                        final String likeContent = tvLikeCount.getText().toString();
                        request(haveId, new ObserverOnNextListener<ResponseBody>() {
                            @Override
                            public void onNext(ResponseBody responseBody) {
                                try {
                                    final String json = responseBody.string();
                                    JSONObject jsonObject = new JSONObject(json);
                                    boolean code = jsonObject.optBoolean("code");
                                    if (code) {
                                        tvAdd.setVisibility(View.VISIBLE);
                                        SPUtils.setPraise(getContext(), haveId + "", true);
                                        ivLikeStatus.setImageResource(R.mipmap.statuslike);
                                        tvLikeCount.setText(String.valueOf(Integer.parseInt(likeContent) + 1));
                                        ObjectAnimator alphaDisplay = ObjectAnimator.ofFloat(tvAdd, "alpha", 0.0f, 1.0f);
                                        alphaDisplay.setDuration(2000);
                                        alphaDisplay.start();
                                        alphaDisplay.addListener(new AnimatorListenerAdapter() {
                                            @Override
                                            public void onAnimationEnd(Animator animation) {
                                                super.onAnimationEnd(animation);
                                                tvAdd.setVisibility(View.GONE);
                                            }
                                        });
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
            });
        }
    }

    private void request(String haveId, ObserverOnNextListener<ResponseBody> nextListener) {
        RetrofitManage
                .getApiService(BaseUrl.BASE_WK_URL)
                .getHavePraiseData(haveId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver<ResponseBody>(nextListener));
    }
}
