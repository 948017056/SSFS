package com.cctv.ssfs.view.home.activity;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cctv.ratingbarlibrary.BaseRatingBar;
import com.cctv.ratingbarlibrary.ScaleRatingBar;
import com.cctv.ssfs.R;
import com.cctv.ssfs.adapter.StockDetailsRecyclerAdapter;
import com.cctv.ssfs.base.BaseRecyclerViewAdapter;
import com.cctv.ssfs.base.BaseUrl;
import com.cctv.ssfs.base.ViewHolder;
import com.cctv.ssfs.netdata.MyObserver;
import com.cctv.ssfs.netdata.ObserverOnNextListener;
import com.cctv.ssfs.netdata.RetrofitManage;
import com.cctv.ssfs.utils.ImageLoader;
import com.cctv.ssfs.utils.LogUtils;
import com.cctv.ssfs.utils.RecycleViewDivider;
import com.cctv.ssfs.utils.SPUtils;
import com.cctv.ssfs.utils.ShareUtils;
import com.cctv.ssfs.view.base.BaseActivity;
import com.cctv.ssfs.view.user.LoginActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.umeng.socialize.UMShareAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/***
 * 股票详情页
 * @author qi
 */

public class StockDetailsActivity extends BaseActivity implements View.OnClickListener {
    public static final String CODE = "code", POSITION = "positotin";
    public static final int CODE_INFO = 369;
    private RecyclerView recycler_stock, recyclerGoods;
    private List<JSONObject> datas = new ArrayList<>();
    private List<JSONObject> list = new ArrayList<>();
    private String code, userId;
    private StockDetailsRecyclerAdapter adapter;
    private GoodsRecyclerAdapter goodsRecyclerAdapter;
    private TextView tvStockName, tvNoComment, tvCompanyName, tvPlateName,
            tvStockTime, tvWebsite, tvScore, tvPerson, tvNoGoods, tvAllComments,
            btnIntersted, tvCommentStatus, tvReturnTime, tvCommentInfo, btnComment;
    private BaseRatingBar rbStarscore;
    private boolean loginStatus;
    private LinearLayout btnbought;
    private RelativeLayout layoutCommentStatus;
    private ImageView ivHeadPortrait;
    private ScaleRatingBar rbStarRating;
    private int mPosition = -1;

    @Override
    public int getLayout() {
        return R.layout.activity_stock_details;
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        if (null != intent) {
            code = intent.getStringExtra(CODE);
        }
        userId = (String) SPUtils.getParam(this, LoginActivity.USER_ID, "-1");
        loginStatus = (boolean) SPUtils.getParam(this, LoginActivity.LOGIN_STATUS, false);
        LogUtils.i("id code ==", code);
        ImageView iv_back = getView(R.id.iv_back);
        TextView tvTitle = getView(R.id.tv_title);
        tvTitle.setText("上市公司");
        iv_back.setOnClickListener(this);
        recycler_stock = getView(R.id.recycler_stock);
        View headView = View.inflate(this, R.layout.layout_head_stock, null);
        View footView = View.inflate(this, R.layout.layout_foot_stock, null);
        tvAllComments = footView.findViewById(R.id.tv_allcomments);
        tvAllComments.setOnClickListener(this);
        initHeadView(headView);
        adapter = new StockDetailsRecyclerAdapter(this, datas);
        adapter.addHeaderView(headView);
        adapter.addFooterView(footView);
        recycler_stock.setAdapter(adapter);

    }

    @Override
    public void initData() {
        if (userId.equals("")) {
            btnIntersted.setVisibility(View.VISIBLE);
            btnbought.setVisibility(View.VISIBLE);
            btnComment.setVisibility(View.VISIBLE);
            layoutCommentStatus.setVisibility(View.GONE);
            request("", new OnStockDataListener());
        } else {
            request(userId, new ReturnDataListener());
        }
    }

    private void request(String userId, ObserverOnNextListener nextListener) {
        RetrofitManage
                .getApiService(BaseUrl.BASE_WK_URL)
                .getStockData(code, userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver<ResponseBody>(nextListener));
    }

    /**
     * 相关干货
     */
    private class GoodsRecyclerAdapter extends BaseRecyclerViewAdapter<JSONObject> {

        public GoodsRecyclerAdapter(@Nullable List<JSONObject> data) {
            super(R.layout.item_goods, data);
        }

        @Override
        protected void convert(ViewHolder helper, JSONObject data) {
            TextView tvGoodsTitle = helper.getView(R.id.tv_goods_title);
            TextView tvTime = helper.getView(R.id.tv_time);
            TextView tvSource = helper.getView(R.id.tv_source);
            ImageView ivImage = helper.getView(R.id.iv_image);

            tvGoodsTitle.setText(data.optString("title"));
            tvTime.setText(data.optString("time"));
            tvSource.setText(data.optString("art_from"));
            String imgUrl = data.optJSONArray("cover_pic").optString(0);
            if (!"".equals(imgUrl)) {
                ivImage.setVisibility(View.VISIBLE);
                ImageLoader.getInstance().loadImageUrl(StockDetailsActivity.this, ivImage, imgUrl);
            } else {
                ivImage.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            //想买
            case R.id.btn_intersted:
                mPosition = 0;
                myStartAntivity(0);
                break;
            //已买
            case R.id.btn_bought:
                mPosition = 1;
                myStartAntivity(1);
                break;
            //写评论
            case R.id.btn_comment:
                mPosition = 1;
                myStartAntivity(1);
                break;
            //全部评论
            case R.id.tv_allcomments:
                Intent intent2 = new Intent(StockDetailsActivity.this, AllCommentActivity.class);
                intent2.putExtra(CODE, code);
                startActivity(intent2);
                break;

            default:
        }
    }

    private void myStartAntivity(int position) {
        if (loginStatus) {
            Intent intent = new Intent(StockDetailsActivity.this, CommentActivity.class);
            intent.putExtra(POSITION, position);
            intent.putExtra(CODE, code);
            startActivityForResult(intent, CODE_INFO);
        } else {
            Intent intent = new Intent(StockDetailsActivity.this, LoginActivity.class);
            startActivityForResult(intent, CODE_INFO);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        //登录的返回处理
        if (requestCode == CODE_INFO && resultCode == LoginActivity.CODE) {
            userId = (String) SPUtils.getParam(this, LoginActivity.USER_ID, "-1");
            loginStatus = (boolean) SPUtils.getParam(this, LoginActivity.LOGIN_STATUS, false);
            if (mPosition > -1) {
                myStartAntivity(mPosition);
            }
        }
        //评论返回处理
        if (requestCode == CODE_INFO && resultCode == CommentActivity.COMMENT_CODE) {
            final boolean commentCode = data.getBooleanExtra("code", false);
            boolean isChecked = data.getBooleanExtra("isChecked", false);
            final String dataString = data.getStringExtra("data");
            if (commentCode) {
                request(userId, new ReturnDataListener());
                if (isChecked) {
//                    ShareUtils.shareWeb(StockDetailsActivity.this, "url", "title", "一篇神奇的干货", "img");
                    RetrofitManage.getApiService(BaseUrl.BASE_WK_URL)
                            .getUserMoneyData()
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new MyObserver<ResponseBody>(new ObserverOnNextListener<ResponseBody>() {
                                @Override
                                public void onNext(ResponseBody responseBody) {
                                    try {
                                        JSONObject jsonObject = new JSONObject(responseBody.string());
                                        final JSONObject commentJson = jsonObject.optJSONObject("data").optJSONObject("share").optJSONObject("comment");

                                        final String desc = commentJson.optString("desc");
                                        final String pic = commentJson.optString("pic");
                                        final String title = tvStockName.getText().toString();
                                        ShareUtils.shareWeb(StockDetailsActivity.this,
                                                String.format(BaseUrl.DISCUSS_URL + "%s?haveid=%s&share=1&code=%s", dataString, dataString, code),
                                                "一条" + title + "的神回复", desc, pic);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }

                                }
                            }));
                }
            }
        }
    }

    private class ReturnDataListener implements ObserverOnNextListener<ResponseBody> {

        @Override
        public void onNext(ResponseBody responseBody) {
            try {
                String json = responseBody.string();
                JSONObject jsonObject = new JSONObject(json);
                if (jsonObject.optBoolean(CODE)) {
                    JSONObject data = jsonObject.optJSONObject("data");
                    JSONObject stock = data.optJSONObject("stock");
                    JSONObject have = data.optJSONObject("have");
                    JSONObject status = data.optJSONObject("status");
                    if (status != null) {
                        ImageLoader.getInstance().loadImageUrl(StockDetailsActivity.this, ivHeadPortrait, status.optString("user_img"));
                        btnIntersted.setVisibility(View.GONE);
                        btnbought.setVisibility(View.GONE);
                        btnComment.setVisibility(View.GONE);
                        layoutCommentStatus.setVisibility(View.VISIBLE);
                        double count1 = status.optDouble("count");
                        if (count1 > 0) {
                            tvCommentStatus.setText("我买过");
                            rbStarRating.setVisibility(View.VISIBLE);
                            rbStarRating.setRating((float) count1);
                        } else {
                            tvCommentStatus.setText("我想买");
                            rbStarRating.setVisibility(View.GONE);
                        }
                        tvReturnTime.setText(status.optString("time"));
                        tvCommentInfo.setText(status.optString("content"));
                    } else {
                        btnIntersted.setVisibility(View.VISIBLE);
                        btnbought.setVisibility(View.VISIBLE);
                        btnComment.setVisibility(View.VISIBLE);
                        layoutCommentStatus.setVisibility(View.GONE);
                    }

                    StringBuffer buffer = new StringBuffer();
                    buffer.append(stock.optString("name"));
                    buffer.append("(");
                    buffer.append(stock.optString("code"));
                    buffer.append(")");

                    tvStockName.setText(buffer.toString());
                    tvPlateName.setText(String.format("%s / %s", stock.optString("area"), stock.optString("plate")));
                    tvCompanyName.setText(String.format("公司名称：%s", stock.optString("company")));
                    tvStockTime.setText(String.format("上市时间：%s", stock.optString("time")));
                    tvWebsite.setText(String.format("公司网址：%s", stock.optString("website")));
                    double count = have.optDouble("count");
                    BigDecimal b = new BigDecimal(String.valueOf(count * 2));
                    tvScore.setText(String.format("%.1f", b));
                    rbStarscore.setRating((float) count);
                    tvPerson.setText(new StringBuffer().append(have.optString("person")).append("人").toString());
                    RetrofitManage
                            .getApiService(BaseUrl.BASE_WK_URL)
                            .getStockArtData(code)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new MyObserver<ResponseBody>(new OnStockArtDataListener()));
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 股票数据
     */
    private class OnStockDataListener implements ObserverOnNextListener<ResponseBody> {
        @Override
        public void onNext(ResponseBody responseBody) {
            try {
                String json = responseBody.string();
                JSONObject jsonObject = new JSONObject(json);
                if (jsonObject.optBoolean(CODE)) {
                    JSONObject data = jsonObject.optJSONObject("data");
                    JSONObject stock = data.optJSONObject("stock");
                    JSONObject have = data.optJSONObject("have");
                    StringBuffer buffer = new StringBuffer();
                    buffer.append(stock.optString("name"));
                    buffer.append("(");
                    buffer.append(stock.optString("code"));
                    buffer.append(")");

                    tvStockName.setText(buffer.toString());
                    tvPlateName.setText(String.format("%s / %s", stock.optString("area"), stock.optString("plate")));
                    tvCompanyName.setText(String.format("公司名称：%s", stock.optString("company")));
                    tvStockTime.setText(String.format("上市时间：%s", stock.optString("time")));
                    tvWebsite.setText(String.format("公司网址：%s", stock.optString("website")));
                    double count = have.optDouble("count");
                    BigDecimal b = new BigDecimal(String.valueOf(count * 2));
                    tvScore.setText(String.format("%.1f", b));
                    rbStarscore.setRating((float) count);
                    tvPerson.setText(new StringBuffer().append(have.optString("person")).append("人").toString());
                    RetrofitManage
                            .getApiService(BaseUrl.BASE_WK_URL)
                            .getStockArtData(code)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new MyObserver<ResponseBody>(new OnStockArtDataListener()));
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 干货数据
     */
    private class OnStockArtDataListener implements ObserverOnNextListener<ResponseBody> {

        @Override
        public void onNext(ResponseBody responseBody) {
            try {
                String json = responseBody.string();
                JSONObject jsonObject = new JSONObject(json);
                list.clear();
                if (jsonObject.optBoolean(CODE)) {
                    tvNoGoods.setVisibility(View.GONE);
                    recyclerGoods.setVisibility(View.VISIBLE);
                    JSONArray jsonArray = jsonObject.optJSONObject("data").optJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        list.add(jsonArray.optJSONObject(i));
                    }
                    goodsRecyclerAdapter.notifyDataSetChanged();
                } else {
                    tvNoGoods.setVisibility(View.VISIBLE);
                    recyclerGoods.setVisibility(View.GONE);
                }
                Map<String, Object> map = new HashMap<>();
                map.put("code", code);
                RetrofitManage
                        .getApiService(BaseUrl.BASE_WK_URL)
                        .getCommentData(map)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new MyObserver<ResponseBody>(new OnCommentDataListener()));

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 短评数据
     */
    public class OnCommentDataListener implements ObserverOnNextListener<ResponseBody> {

        @Override
        public void onNext(ResponseBody responseBody) {
            try {
                String json = responseBody.string();
                JSONObject jsonObject = new JSONObject(json);
                datas.clear();
                if (jsonObject.optBoolean(CODE)) {
                    tvNoComment.setVisibility(View.GONE);
                    adapter.setTag(true);
                    JSONArray jsonArray = jsonObject.optJSONObject("data").optJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        datas.add(jsonArray.optJSONObject(i));
                    }
                    if (datas.size() > 0) {
                        tvAllComments.setVisibility(View.VISIBLE);
                    }
                    adapter.notifyDataSetChanged();
                } else {
                    adapter.setTag(false);
                    tvNoComment.setVisibility(View.VISIBLE);
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void initHeadView(View headView) {
        tvNoComment = headView.findViewById(R.id.tv_nocomment);
        tvStockName = headView.findViewById(R.id.tv_stock_name);
        btnIntersted = headView.findViewById(R.id.btn_intersted);
        btnbought = headView.findViewById(R.id.btn_bought);
        btnComment = headView.findViewById(R.id.btn_comment);
        tvCompanyName = headView.findViewById(R.id.tv_company_name);
        tvPlateName = headView.findViewById(R.id.tv_plate_name);
        tvStockTime = headView.findViewById(R.id.tv_stock_time);
        tvWebsite = headView.findViewById(R.id.tv_website);
        tvScore = headView.findViewById(R.id.tv_score);
        rbStarscore = headView.findViewById(R.id.rb_starscore);
        tvPerson = headView.findViewById(R.id.tv_person);
        tvNoGoods = headView.findViewById(R.id.tv_nogoods);

        layoutCommentStatus = headView.findViewById(R.id.layout_comment_status);
        ivHeadPortrait = headView.findViewById(R.id.iv_headportrait);
        tvCommentStatus = headView.findViewById(R.id.tv_comment_status);
        rbStarRating = headView.findViewById(R.id.rb_starRating);
        tvReturnTime = headView.findViewById(R.id.tv_return_time);
        tvCommentInfo = headView.findViewById(R.id.tv_comment_info);

        btnIntersted.setOnClickListener(this);
        btnbought.setOnClickListener(this);
        btnComment.setOnClickListener(this);

        recyclerGoods = headView.findViewById(R.id.recycler_goods);
        recyclerGoods.addItemDecoration(new RecycleViewDivider(StockDetailsActivity.this, LinearLayoutManager.VERTICAL));

        recyclerGoods.setFocusableInTouchMode(false);
        recyclerGoods.requestFocus();
        goodsRecyclerAdapter = new GoodsRecyclerAdapter(list);
        recyclerGoods.setAdapter(goodsRecyclerAdapter);
        goodsRecyclerAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                JSONObject data = list.get(position);
                Intent intent = new Intent(StockDetailsActivity.this, WebDetailsActivity.class);
                intent.putExtra(WebDetailsActivity.ARTICLE_ID, data.optString("article_id"));
                intent.putExtra("code", code);
                intent.putExtra("label", String.valueOf(data.optInt("lable_id")));
                intent.putExtra("url", String.valueOf(data.optString("url")));
                intent.putExtra("lable_name", data.optString("lable_name"));
                JSONArray jsonArray = data.optJSONArray("cover_pic");
                if (null != jsonArray) {
                    intent.putExtra("img", jsonArray.optString(0));
                }
                startActivity(intent);
            }
        });
    }
}
