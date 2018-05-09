package com.cctv.ssfs.adapter;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cctv.ratingbarlibrary.ScaleRatingBar;
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
import com.cctv.ssfs.view.home.activity.ListedCompanyActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by qi on 2018/3/27.
 * 股吧详情页
 */

public class StockDetailsRecyclerAdapter extends BaseRecyclerViewAdapter<JSONObject> {
    private Context context;
    private boolean tag;

    public StockDetailsRecyclerAdapter(Context context, List<JSONObject> data) {
        super(R.layout.item_comment, data);
        this.context = context;
    }

    @Override
    protected void convert(ViewHolder helper, final JSONObject data) {
        if (tag) {
            final TextView tvAdd = helper.getView(R.id.tv_add);
            TextView tvName = helper.getView(R.id.tv_comment_name);
            TextView tvTime = helper.getView(R.id.tv_comment_time);
            final TextView tvContent = helper.getView(R.id.tv_comment_content);
            final ImageView ivLikeStatus = helper.getView(R.id.iv_like_status);
            final TextView tvLikeCount = helper.getView(R.id.tv_like_count);
            final TextView tvExpand = helper.getView(R.id.tv_expand);
            CircleImageView ivHeadPortrait = helper.getView(R.id.iv_headportrait);
            ScaleRatingBar rb_starscore = helper.getView(R.id.rb_starscore);

            boolean isPraise = SPUtils.getPraise(context, data.optString("have_id") + "", false);
            if (isPraise) {
                ivLikeStatus.setImageResource(R.mipmap.statuslike);
            } else {
                ivLikeStatus.setImageResource(R.mipmap.like);
            }

            tvContent.post(new Runnable() {
                @Override
                public void run() {
                    int txtPart = tvContent.getLineCount();
                    if (txtPart > 7) {
                        tvContent.setMaxLines(7);
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
            rb_starscore.setRating((float) data.optDouble("count"));
            ImageLoader.getInstance().loadImageUrl(context, ivHeadPortrait, data.optString("user_img"));
            tvLikeCount.setText(String.valueOf(data.optInt("praise")));

            tvExpand.setOnClickListener(new View.OnClickListener() {
                boolean isExpand = false;

                @Override
                public void onClick(View v) {
                    if (isExpand) {
                        tvExpand.setText("展开");
                        tvContent.setMaxLines(7);
                        isExpand = false;
                    } else {
                        tvExpand.setText("收起");
                        tvContent.setMaxLines(100);
                        isExpand = true;
                    }
                }
            });

            tvContent.setOnClickListener(new View.OnClickListener() {
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

            ivLikeStatus.setOnClickListener(new View.OnClickListener() {
                final String haveId = data.optString("have_id");

                @Override
                public void onClick(View v) {
                    boolean isPraise = SPUtils.getPraise(context, haveId + "", false);
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
                                        SPUtils.setPraise(context, haveId + "", true);
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

    /**
     * 标识是否有数据
     *
     * @param tag
     */
    public void setTag(boolean tag) {
        this.tag = tag;
    }
}
