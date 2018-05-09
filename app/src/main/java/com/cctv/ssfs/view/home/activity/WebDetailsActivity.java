package com.cctv.ssfs.view.home.activity;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cctv.ratingbarlibrary.BaseRatingBar;
import com.cctv.ssfs.R;
import com.cctv.ssfs.adapter.DetailsCommentsRecyclerAdapter;
import com.cctv.ssfs.adapter.HeadPortraitListAdapter;
import com.cctv.ssfs.adapter.PostsDetailsRecyclerAdapter;
import com.cctv.ssfs.base.BaseRecyclerViewAdapter;
import com.cctv.ssfs.base.BaseUrl;
import com.cctv.ssfs.customview.MyTextView;
import com.cctv.ssfs.netdata.MyObserver;
import com.cctv.ssfs.netdata.ObserverOnNextListener;
import com.cctv.ssfs.netdata.RetrofitManage;
import com.cctv.ssfs.utils.CustomToast;
import com.cctv.ssfs.utils.DateUtil;
import com.cctv.ssfs.utils.ImageLoader;
import com.cctv.ssfs.utils.LogUtils;
import com.cctv.ssfs.utils.RecyclerViewScrollUtils;
import com.cctv.ssfs.utils.SPUtils;
import com.cctv.ssfs.utils.ShareUtils;
import com.cctv.ssfs.utils.ToastUtils;
import com.cctv.ssfs.view.base.BaseActivity;
import com.cctv.ssfs.view.user.LoginActivity;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.umeng.socialize.UMShareAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * Web详情页
 *
 * @author sanjin
 */
public class WebDetailsActivity extends BaseActivity implements ObserverOnNextListener<ResponseBody>, View.OnClickListener, BaseQuickAdapter.OnItemClickListener, MyTextView.MyClickListener {

    public static final String ARTICLE_ID = "article_id";
    private String articleId, code, label, url, img, lableName, title, userId, s_money, p_money, pp_money, userImg;
    private List<JSONObject> list = new ArrayList<>();
    private List<JSONObject> listComment = new ArrayList<>();
    private List<JSONObject> listHead = new ArrayList<>();
    private TextView tvTitle, tvTime, tvSource, tvCode, tvShareCount, tvCount, tvBigTitle, tvCommentCount, tvNoData;
    private MyTextView btnSend;
    private LinearLayout layoutShare, linearCancel, linearInput, linearRifht, tvLoading;
    private BaseRecyclerViewAdapter adapter, commentsRecyclerAdapter, headPortraitListAdapter;
    private WebView webView;
    private BaseRatingBar rbStarscore;
    private FrameLayout frameComment;
    private EditText etInfo;
    private Timer timer = new Timer();
    public static final int HEIGHT = 35;
    private ImageView ivImgToast, ivCollection;
    public static final int CODE_DETAILS = 753, CODE_COMMENT = 789, CODE_COLLECTION = 222;
    private boolean loginStatus, inviteStatus, isPrompt;
    private int lastPage, page = 1, pp_count;
    private RecyclerView recyclerButtom;
    private long timestamps;
    private PopupWindow mPopupWindow;

    @Override
    public int getLayout() {
        return R.layout.activity_web_details;
    }

    @Override
    public void initView() {
        Intent intent = getIntent();
        articleId = intent.getStringExtra(ARTICLE_ID);
        code = intent.getStringExtra("code");
        label = intent.getStringExtra("label");
        url = intent.getStringExtra("url");
        img = intent.getStringExtra("img");
        lableName = intent.getStringExtra("lable_name");
        userId = (String) SPUtils.getParam(this, LoginActivity.USER_ID, "-1");
        userImg = (String) SPUtils.getParam(this, LoginActivity.LOGIN_IMG, "");
        loginStatus = (Boolean) SPUtils.getParam(this, LoginActivity.LOGIN_STATUS, false);
        int collectionStatus = SPUtils.getCollection(WebDetailsActivity.this, articleId, -1);
        timestamps = SPUtils.getTimestamps(this, -1);
        isPrompt = DateUtil.getTimeFormatText(timestamps).contains("天前");

        ImageView ivBack = getView(R.id.iv_back);
        etInfo = getView(R.id.et_info);
        tvBigTitle = getView(R.id.tv_title);
        ivImgToast = getView(R.id.iv_imgtoast);
        linearCancel = getView(R.id.linear_cancel);
        recyclerButtom = getView(R.id.recycler_buttom);

        FrameLayout frameCommentCollection = getView(R.id.frame_commentcollection);

        final TextView btnComment = getView(R.id.btn_comment);
        frameComment = getView(R.id.frame_comment);
        linearRifht = getView(R.id.linear_rifht);
        final View viewLine = getView(R.id.view_line);
        tvCommentCount = getView(R.id.tv_comment_count);
        linearInput = getView(R.id.linear_input);
        RelativeLayout frameCommentimg = getView(R.id.frame_commentimg);
        ivCollection = getView(R.id.iv_collection);
        ImageView ivForwarding = getView(R.id.iv_forwarding);
        btnSend = getView(R.id.btn_send);
        btnSend.setClickListener(this);

        View headView = View.inflate(this, R.layout.layout_webhead, null);
        View footView = View.inflate(this, R.layout.layout_foot_listed, null);

        commentsRecyclerAdapter = new DetailsCommentsRecyclerAdapter(this, listComment);
        tvNoData = footView.findViewById(R.id.tv_noData);
        tvLoading = footView.findViewById(R.id.tv_Loading);
        commentsRecyclerAdapter.addHeaderView(headView);
        commentsRecyclerAdapter.addFooterView(footView);
        initHeadView(headView);
        recyclerButtom.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerButtom.setAdapter(commentsRecyclerAdapter);
        if (loginStatus) {
            if (collectionStatus == 2) {
                ivCollection.setImageResource(R.mipmap.collectionred);
            } else {
                ivCollection.setImageResource(R.mipmap.collectiongray);
            }
        } else {
            ivCollection.setImageResource(R.mipmap.collectiongray);
        }

        ivBack.setOnClickListener(this);
        frameCommentCollection.setOnClickListener(this);
        linearInput.setOnClickListener(this);
        frameComment.setOnClickListener(this);
        frameCommentimg.setOnClickListener(this);
        linearCancel.setOnClickListener(this);
        ivForwarding.setOnClickListener(this);
        btnComment.setOnClickListener(this);

        recyclerButtom.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int[] viewTextLocation = new int[2];
                int[] viewLineLocation = new int[2];
                tvCode.getLocationInWindow(viewTextLocation);
                viewLine.getLocationInWindow(viewLineLocation);
                int textY = viewTextLocation[1];
                int viewY = viewLineLocation[1];
                if ((textY + HEIGHT) <= viewY) {
                    tvBigTitle.setText(tvCode.getText().toString());
                    if (tvBigTitle.getText().toString().equals("")) {
                        btnComment.setVisibility(View.GONE);
                        tvBigTitle.setVisibility(View.GONE);
                    } else {
                        btnComment.setVisibility(View.VISIBLE);
                        tvBigTitle.setVisibility(View.VISIBLE);
                    }
                } else {
                    tvBigTitle.setVisibility(View.GONE);
                    btnComment.setVisibility(View.GONE);
                }
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (RecyclerViewScrollUtils.isSlideToBottom(recyclerView, newState)) {
                    if (page <= lastPage) {
                        tvLoading.setVisibility(View.GONE);
                        tvNoData.setVisibility(View.VISIBLE);
                    }
                    page++;
                    if (page <= lastPage) {
                        tvLoading.setVisibility(View.VISIBLE);
                        tvNoData.setVisibility(View.GONE);
                        request(page, true);
                    }
                }
            }
        });

        if (isPrompt) {
            ivImgToast.setVisibility(View.GONE);
            linearCancel.setVisibility(View.GONE);
        } else {
            ivImgToast.setVisibility(View.VISIBLE);
            linearCancel.setVisibility(View.VISIBLE);
            ObjectAnimator alphaDisplay = ObjectAnimator.ofFloat(ivImgToast, "alpha", 0.0f, 1.0f);
            alphaDisplay.setDuration(1000);
            alphaDisplay.start();
            new MyThread().start();
        }

    }


    private void initHeadView(View headView) {
        webView = headView.findViewById(R.id.webView);
        RecyclerView recycler = headView.findViewById(R.id.recycler_web);
        tvCount = headView.findViewById(R.id.tv_newcount);
        tvTitle = headView.findViewById(R.id.tv_title);
        tvTime = headView.findViewById(R.id.tv_time);
        rbStarscore = headView.findViewById(R.id.rb_starscore);
        tvSource = headView.findViewById(R.id.tv_source);
        tvCode = headView.findViewById(R.id.tv_code);
        tvShareCount = headView.findViewById(R.id.tv_sharecount);
        layoutShare = headView.findViewById(R.id.layout_share);
        TextView tv_lableName = headView.findViewById(R.id.tv_lableName);
        tv_lableName.setText(lableName);
        RecyclerView recyclerImg = headView.findViewById(R.id.recycler_img);
        headPortraitListAdapter = new HeadPortraitListAdapter(WebDetailsActivity.this, listHead);
        recyclerImg.setLayoutManager(new GridLayoutManager(WebDetailsActivity.this, 8));
        recyclerImg.setAdapter(headPortraitListAdapter);
        TextView tvStatement = headView.findViewById(R.id.tv_statement);
        Spannable string = new SpannableString(tvStatement.getText().toString());
        string.setSpan(new ForegroundColorSpan(0xFF27A5E6), 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvStatement.setText(string);
        tvCode.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());
        WebSettings settings = webView.getSettings();
        //设置自适应屏幕，两者合用
        settings.setUseWideViewPort(true);
        //将图片调整到适合webview的大小
        // 缩放至屏幕的大小
        settings.setLoadWithOverviewMode(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        settings.setDomStorageEnabled(true);
        settings.setJavaScriptEnabled(true);
        //垂直不显示滚动条
        webView.setVerticalScrollBarEnabled(false);
        settings.setLoadsImagesAutomatically(true);
        settings.setDefaultTextEncodingName("UTF-8");
        settings.setBlockNetworkImage(false);
        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        //注意安卓5.0以上的权限
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(settings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        tvCode.setOnClickListener(this);
        adapter = new PostsDetailsRecyclerAdapter(this, list);
        recycler.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }

    @Override
    public void initData() {
        RetrofitManage.getApiService(BaseUrl.BASE_WK_URL)
                .getUserMoneyData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver<ResponseBody>(new UserDataListener()));

        Map<String, Object> map = new HashMap<>();
        if (userId.equals("-1")) {
            map.put("article_id", articleId);
            map.put("code", code);
            map.put("lable_id", label);
        } else {
            map.put("user_id", userId);
            map.put("article_id", articleId);
            map.put("code", code);
            map.put("lable_id", label);
        }

        RetrofitManage
                .getApiService(BaseUrl.BASE_WK_URL)
                .getDetailData(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver<ResponseBody>(this));

        request(1, false);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_code:
                myStart();
                break;
            //关闭红包提示
            case R.id.linear_cancel:
                SPUtils.setTimestamps(WebDetailsActivity.this, System.currentTimeMillis());
                ivImgToast.setVisibility(View.GONE);
                linearCancel.setVisibility(View.GONE);

                break;
            case R.id.btn_comment:
                myStart();
                break;
            case R.id.frame_comment:
                frameComment.setVisibility(View.GONE);
                linearRifht.setVisibility(View.GONE);
                linearInput.setVisibility(View.VISIBLE);

                etInfo.setFocusable(true);
                etInfo.setFocusableInTouchMode(true);
                etInfo.requestFocus();
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }, 100);
                break;
            //评论
            case R.id.frame_commentimg:
                ((LinearLayoutManager) recyclerButtom.getLayoutManager())
                        .scrollToPositionWithOffset(1, 0);
//                commentsRecyclerAdapter.notifyDataSetChanged();
                break;
            //收藏
            case R.id.frame_commentcollection:
                collection();
                break;
            //转发
            case R.id.iv_forwarding:
                if (loginStatus) {
                    if (inviteStatus) {
                        showPopup();
                    } else {
                        shareArticles();
                    }
                } else {
                    Intent intent = new Intent(WebDetailsActivity.this, LoginActivity.class);
                    startActivityForResult(intent, CODE_DETAILS);
                }
                break;
            case R.id.iv_close:
                mPopupWindow.dismiss();
                break;
            case R.id.linear_bottom:
                mPopupWindow.dismiss();
                shareArticles();
                break;
            default:
        }
    }


    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        JSONObject data = list.get(position);
        Intent intent = new Intent(WebDetailsActivity.this, WebDetailsActivity.class);
        intent.putExtra(WebDetailsActivity.ARTICLE_ID, data.optString("article_id"));
        intent.putExtra("code", data.optString("code"));
        intent.putExtra("label", String.valueOf(data.optInt("lable_id")));
//        intent.putExtra("url", String.valueOf(data.optString("url")));
//        intent.putExtra("lable_name", data.optString("lable_name"));
        JSONArray jsonArray = data.optJSONArray("cover_pic");
        if (null != jsonArray) {
            intent.putExtra("img", jsonArray.optString(0));
        }
        startActivity(intent);
    }

    /**
     * 收藏
     */
    private void collection() {
        if (!userId.equals("-1")) {
            requestCollection();
        } else {
            Intent intent = new Intent(WebDetailsActivity.this, LoginActivity.class);
            startActivityForResult(intent, CODE_COLLECTION);
        }
    }

    private void requestCollection() {
        RetrofitManage.getApiService(BaseUrl.BASE_WK_URL)
                .getArtCollect(articleId, userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver<ResponseBody>(new CollectionListener()));
    }

    /**
     * 发送评论
     */
    private void sendRequest() {
        try {
            String content = etInfo.getText().toString();
            if (!TextUtils.isEmpty(content)) {
                JSONObject json = new JSONObject();
                json.put("user_id", userId);
                json.put("article_id", articleId);
                json.put("content", content);
                json.put("num", content.length());
                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json.toString());
                RetrofitManage
                        .getApiService(BaseUrl.BASE_WK_URL)
                        .getSendComment(body)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new MyObserver<ResponseBody>(new SendCommentLisener()));
            } else {
                ToastUtils.showShortToast(this, "请输入内容");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void request(int page, boolean isPaging) {
        Map<String, Object> map = new HashMap<>();
        map.put("article_id", articleId);
        if (page > 0) {
            map.put("page", page);
        }
        RetrofitManage
                .getApiService(BaseUrl.BASE_WK_URL)
                .getCommentListData(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver<ResponseBody>(new CommentListListener(isPaging)));
    }

    private static final String TAG = "WebDetailsActivity";

    /**
     * 发表评论
     */
    @Override
    public void click() {
        LogUtils.i(TAG, "子View>>>>>onTouchEvent");
        if (loginStatus) {
            sendRequest();
        } else {
            Intent intent = new Intent(WebDetailsActivity.this, LoginActivity.class);
            startActivityForResult(intent, CODE_COMMENT);
        }
    }

    /**
     * 分享
     */
    private void shareArticles() {
        boolean result = ShareUtils.shareWeb(WebDetailsActivity.this,
                String.format("%s?article_id=%s&user_id=%s", url, articleId, userId), title, "一篇神奇的干货", img);
        if (result) {
            RetrofitManage.getApiService(BaseUrl.BASE_WK_URL)
                    .getArtUser(userId, articleId)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new MyObserver<ResponseBody>(new ObserverOnNextListener<ResponseBody>() {
                        @Override
                        public void onNext(ResponseBody responseBody) {
                            try {
                                LogUtils.i("jsonUrl", responseBody.string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }));
        }
    }

    @Override
    public void onNext(ResponseBody responseBody) {
        try {
            String json = responseBody.string();
            JSONObject jsonObject = new JSONObject(json);
            if (jsonObject.optBoolean("code")) {
                JSONObject jsonData = jsonObject.optJSONObject("data");
                JSONObject article = jsonData.optJSONObject("article");
                JSONObject inviteUser = jsonData.optJSONObject("inviteUser");
                JSONArray recommend = jsonData.optJSONArray("recommend");

                String content = article.optString("content");
                title = article.optString("title");
                String time = article.optString("time");
                String artFrom = article.optString("art_from");
                String name = article.optString("name");
                String code = article.optString("code");
                double count = article.optDouble("xing");
                if (name.equals("") && code.equals("")) {
                    tvCode.setText("");
                    rbStarscore.setVisibility(View.GONE);
                } else {
                    rbStarscore.setVisibility(View.VISIBLE);
                    StringBuffer buffer = new StringBuffer();
                    buffer.append(name);
                    buffer.append("(");
                    buffer.append(code);
                    buffer.append(")");
                    tvCode.setText(buffer.toString());
                }
                rbStarscore.setRating((float) count);
                tvTitle.setText(title);
                tvTime.setText(time);
                tvSource.setText(artFrom);

                if (null != inviteUser) {
                    layoutShare.setVisibility(View.VISIBLE);
                    tvShareCount.setText(String.format("%s人", inviteUser.optString("count")));
                    JSONArray userArr = inviteUser.optJSONArray("user");
                    listHead.clear();
                    for (int i = 0; i < userArr.length(); i++) {
                        listHead.add(userArr.optJSONObject(i));
                    }
                    headPortraitListAdapter.notifyLoadMoreToLoading();
                } else {
                    layoutShare.setVisibility(View.GONE);
                }
                webView.loadUrl(content);
                list.clear();
                for (int i = 0; i < recommend.length(); i++) {
                    list.add(recommend.optJSONObject(i));
                }
                adapter.notifyDataSetChanged();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void showPopup() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_radenvelope_dialog, null);
        ImageView iv_close = view.findViewById(R.id.iv_close);
        TextView tvNumber1 = view.findViewById(R.id.tv_number1);
        TextView tvNumber2 = view.findViewById(R.id.tv_number2);
        TextView tvNumber3 = view.findViewById(R.id.tv_number3);
        ImageView ivCircleimg = view.findViewById(R.id.iv_circleimg);
        LinearLayout linearBottom = view.findViewById(R.id.linear_bottom);
        iv_close.setOnClickListener(this);
        linearBottom.setOnClickListener(this);
        if (!userImg.equals("")) {
            ImageLoader.getInstance().loadImageUrl(this, ivCircleimg, userImg);
        }
        tvNumber1.setText(getFormat("1、邀请成功以为好友即获取%s元现金红包。", p_money + ""));
        tvNumber2.setText(getFormat("2、该好友可立即获得%s元现金红包。", s_money));
        if (pp_money != null) {
            if ("00".equals(pp_money.split("\\.")[1])) {
                tvNumber3.setText(String.format("3、如果该好友邀请了%s位火以上的好友，你可\n在得%s现金红包。", pp_count, pp_money.split("\\.")[0]));
            }
        }

        if (mPopupWindow == null) {
            mPopupWindow =
                    new PopupWindow(view,
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT, false);
            mPopupWindow.setOutsideTouchable(false);
            mPopupWindow.setFocusable(false);
            mPopupWindow.showAtLocation(tvBigTitle, Gravity.CENTER, 0, 0);
        } else {
            if (mPopupWindow.isShowing()) {
                mPopupWindow.dismiss();
            } else {
                mPopupWindow.showAtLocation(tvBigTitle, Gravity.CENTER, 0, 0);
            }
        }
    }

    private String getFormat(String originalStr, String placeholderStr) {
        String str = null;
        if (placeholderStr != null) {
            if ("00".equals(placeholderStr.split("\\.")[1])) {
                str = String.format(originalStr, placeholderStr.split("\\.")[0]);
            } else {
                str = String.format(originalStr, placeholderStr);
            }
        }
        return str;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        userId = (String) SPUtils.getParam(this, LoginActivity.USER_ID, "-1");
        loginStatus = (Boolean) SPUtils.getParam(this, LoginActivity.LOGIN_STATUS, false);
        userImg = (String) SPUtils.getParam(this, LoginActivity.LOGIN_IMG, "");
        if (requestCode == CODE_DETAILS && resultCode == LoginActivity.CODE) {

        }
        if (requestCode == CODE_COMMENT && resultCode == LoginActivity.CODE) {
            page = 1;
            sendRequest();
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_UP) {
            LogUtils.i(TAG, "Activity>>>dispatchTouchEvent");
            frameComment.setVisibility(View.VISIBLE);
            linearRifht.setVisibility(View.VISIBLE);
            linearInput.setVisibility(View.GONE);
        }
        return super.dispatchTouchEvent(ev);
    }


    private void myStart() {
        Intent intent = new Intent(WebDetailsActivity.this, StockDetailsActivity.class);
        intent.putExtra(StockDetailsActivity.CODE, code);
        startActivity(intent);
    }

    /**
     * 收藏
     */
    private class CollectionListener implements ObserverOnNextListener<ResponseBody> {

        @Override
        public void onNext(ResponseBody responseBody) {
            try {
                JSONObject jsonObject = new JSONObject(responseBody.string());
                if (jsonObject.optBoolean("code")) {
                    int data = jsonObject.optInt("data");
                    Log.i(TAG, "onNext: " + data);
                    if (data == 2) {
                        CustomToast.success(WebDetailsActivity.this, "收藏成功");
                        SPUtils.setCollection(WebDetailsActivity.this, articleId, data);
                        ivCollection.setImageResource(R.mipmap.collectionred);
                    } else {
                        SPUtils.setCollection(WebDetailsActivity.this, articleId, data);
                        ivCollection.setImageResource(R.mipmap.collectiongray);
                        CustomToast.success(WebDetailsActivity.this, "取消收藏");
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 发表评论
     */
    private class SendCommentLisener implements ObserverOnNextListener<ResponseBody> {

        @Override
        public void onNext(ResponseBody responseBody) {
            try {
                String json = responseBody.string();
                JSONObject jsonObject = new JSONObject(json);
                if (jsonObject.optBoolean("code")) {
                    ((LinearLayoutManager) recyclerButtom.getLayoutManager()).scrollToPositionWithOffset(1, 0);
                    ToastUtils.showShortToast(WebDetailsActivity.this, "评论完成");
                    frameComment.setVisibility(View.VISIBLE);
                    linearRifht.setVisibility(View.VISIBLE);
                    linearInput.setVisibility(View.GONE);
                    etInfo.setText("");
                    request(page, false);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获取活动状态
     */
    private class UserDataListener implements ObserverOnNextListener<ResponseBody> {

        @Override
        public void onNext(ResponseBody responseBody) {
            try {
                String json = responseBody.string();
                JSONObject jsonObject = new JSONObject(json);
                if (jsonObject.optBoolean("code")) {
                    JSONObject data = jsonObject.optJSONObject("data");
                    JSONObject invite = data.optJSONObject("invite");
                    JSONObject bind = data.optJSONObject("bind");
                    JSONObject letter = data.optJSONObject("letter");
                    JSONObject share = data.optJSONObject("share");

                    inviteStatus = invite.optBoolean("status");

                    s_money = invite.optString("s_money");
                    p_money = invite.optString("p_money");
                    pp_money = invite.optString("pp_money");
                    pp_count = invite.optInt("pp_count");

                    if (inviteStatus) {
                        ivImgToast.setVisibility(View.VISIBLE);
                        linearCancel.setVisibility(View.VISIBLE);
                        if (timestamps == -1) {
                            ivImgToast.setVisibility(View.VISIBLE);
                            linearCancel.setVisibility(View.VISIBLE);
                        } else {
                            if (isPrompt) {
                                ivImgToast.setVisibility(View.VISIBLE);
                                linearCancel.setVisibility(View.VISIBLE);
                            } else {
                                ivImgToast.setVisibility(View.GONE);
                                linearCancel.setVisibility(View.GONE);
                            }
                        }
                    } else {
                        ivImgToast.setVisibility(View.GONE);
                        linearCancel.setVisibility(View.GONE);
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 评论列表
     */
    private class CommentListListener implements ObserverOnNextListener<ResponseBody> {
        private boolean isPaging;

        public CommentListListener(boolean isPaging) {
            this.isPaging = isPaging;
        }

        @Override
        public void onNext(ResponseBody responseBody) {
            try {
                String json = responseBody.string();
                JSONObject jsonObject = new JSONObject(json);
                if (jsonObject.optBoolean("code")) {
                    final JSONObject dataJson = jsonObject.optJSONObject("data");
                    JSONObject listJson = dataJson.optJSONObject("list");
                    JSONArray dataList = listJson.optJSONArray("data");

                    lastPage = listJson.optInt("last_page");
                    if (page <= lastPage) {
                        tvLoading.setVisibility(View.GONE);
                        tvNoData.setVisibility(View.VISIBLE);
                    }
                    int total = listJson.optInt("total");
                    if (total > 0) {
                        tvCommentCount.setVisibility(View.VISIBLE);
                        tvCount.setText(String.format("最新评论%s条", total));
                        tvCommentCount.setText(String.valueOf(total));
                    } else {
                        tvCommentCount.setVisibility(View.GONE);
                        tvNoData.setVisibility(View.VISIBLE);
                    }
                    if (isPaging) {
                        for (int i = 0; i < dataList.length(); i++) {
                            commentsRecyclerAdapter.addData(listHead.size() - 1, dataList.optJSONObject(i));
                            commentsRecyclerAdapter.notifyDataSetChanged();
                        }
                    } else {
                        listComment.clear();
                        for (int i = 0; i < dataList.length(); i++) {
                            listComment.add(dataList.optJSONObject(i));
                        }
                        commentsRecyclerAdapter.notifyDataSetChanged();
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class MyThread extends Thread {
        @Override
        public void run() {
            super.run();
            try {
                Thread.sleep(5000);
                final ObjectAnimator alphaDisappear = ObjectAnimator.ofFloat(ivImgToast, "alpha", 1.1f, 0.0f);
                alphaDisappear.setDuration(1000);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        alphaDisappear.start();
                        linearCancel.setVisibility(View.GONE);
                    }
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
