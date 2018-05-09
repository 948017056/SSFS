package com.cctv.ssfs.adapter;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cctv.ratingbarlibrary.ScaleRatingBar;
import com.cctv.ssfs.R;
import com.cctv.ssfs.base.BaseRecyclerViewAdapter;
import com.cctv.ssfs.base.BaseUrl;
import com.cctv.ssfs.base.ViewHolder;
import com.cctv.ssfs.customview.CircleImageView;
import com.cctv.ssfs.netdata.MyObserver;
import com.cctv.ssfs.netdata.ObserverOnNextListener;
import com.cctv.ssfs.netdata.RetrofitManage;
import com.cctv.ssfs.utils.ImageLoader;
import com.cctv.ssfs.utils.SPUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * Created by sanjin on 2018/4/23.
 */

public class DetailsCommentsRecyclerAdapter extends BaseRecyclerViewAdapter<JSONObject> {
    private Context context;

    public DetailsCommentsRecyclerAdapter(Context context, @Nullable List<JSONObject> data) {
        super(R.layout.item_comment, data);
        this.context = context;
    }

    @Override
    protected void convert(ViewHolder helper, final JSONObject data) {

        TextView tvName = helper.getView(R.id.tv_comment_name);
        final TextView tvAdd = helper.getView(R.id.tv_add);
        TextView tvTime = helper.getView(R.id.tv_comment_time);
        final TextView tvContent = helper.getView(R.id.tv_comment_content);
        final ImageView ivLikeStatus = helper.getView(R.id.iv_like_status);
        final TextView tvLikeCount = helper.getView(R.id.tv_like_count);
        final TextView tvExpand = helper.getView(R.id.tv_expand);
        ScaleRatingBar rb_starscore = helper.getView(R.id.rb_starscore);
        rb_starscore.setVisibility(View.GONE);
        CircleImageView ivHeadPortrait = helper.getView(R.id.iv_headportrait);
        boolean isPraise = SPUtils.getPraise(context, data.optString("comment_id") + "", false);
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
        String content = data.optString("content");
        tvContent.setText(content.replace("\n", ""));
        ImageLoader.getInstance().loadImageUrl(context, ivHeadPortrait, data.optString("user_img"));
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
            final String commentId = data.optString("comment_id");

            @Override
            public void onClick(View v) {
                boolean isPraise = SPUtils.getPraise(context, commentId + "", false);
                if (!isPraise) {
                    final String likeContent = tvLikeCount.getText().toString();
                    request(commentId, new ObserverOnNextListener<ResponseBody>() {
                        @Override
                        public void onNext(ResponseBody responseBody) {
                            try {
                                final String json = responseBody.string();
                                JSONObject jsonObject = new JSONObject(json);
                                boolean code = jsonObject.optBoolean("code");
                                if (code) {
                                    tvAdd.setVisibility(View.VISIBLE);
                                    SPUtils.setPraise(context, commentId + "", true);
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


    private void request(String commentId, ObserverOnNextListener<ResponseBody> nextListener) {
        RetrofitManage
                .getApiService(BaseUrl.BASE_WK_URL)
                .getPraiseData(commentId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver<ResponseBody>(nextListener));
    }

}
