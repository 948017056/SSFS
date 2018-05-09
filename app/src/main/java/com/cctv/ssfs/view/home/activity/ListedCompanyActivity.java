package com.cctv.ssfs.view.home.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cctv.ratingbarlibrary.BaseRatingBar;
import com.cctv.ssfs.R;
import com.cctv.ssfs.adapter.ListedRecyclerAdapter;
import com.cctv.ssfs.base.BaseUrl;
import com.cctv.ssfs.customview.CircleImageView;
import com.cctv.ssfs.customview.MyLayoutManager;
import com.cctv.ssfs.customview.MyRecycleView;
import com.cctv.ssfs.entity.HomeBean;
import com.cctv.ssfs.netdata.MyObserver;
import com.cctv.ssfs.netdata.ObserverOnNextListener;
import com.cctv.ssfs.netdata.RetrofitManage;
import com.cctv.ssfs.utils.ImageLoader;
import com.cctv.ssfs.utils.SPUtils;
import com.cctv.ssfs.utils.ToastUtils;
import com.cctv.ssfs.utils.UIUtils;
import com.cctv.ssfs.view.base.BaseActivity;
import com.cctv.ssfs.view.search.MaxTextLengthFilter;
import com.cctv.ssfs.view.user.LoginActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

import static com.cctv.ssfs.view.home.activity.StockDetailsActivity.CODE;


/**
 * @author qi
 *         短评详情
 */
public class ListedCompanyActivity extends BaseActivity implements View.OnClickListener, ObserverOnNextListener<ResponseBody> {

    private String code, userId;
    private TextView tvPerson;
    private BaseRatingBar rbStarscore;
    private TextView tvScore, btnSend, tvWebsite, tvStockTime, tvCompanyName, tvStockName, tvPlateName, tvNewcomment;
    private EditText etInfo;
    private List<JSONObject> list;
    private ListedRecyclerAdapter adapter;
    private int haveId;
    private boolean loginStaus;
    public static final int CODE_LISTED = 357;

    @Override
    public int getLayout() {
        return R.layout.activity_listed_company;
    }

    @Override
    public void initView() {
        loginStaus = (boolean) SPUtils.getParam(this, LoginActivity.LOGIN_STATUS, false);
        userId = (String) SPUtils.getParam(this, LoginActivity.USER_ID, "");
        HomeBean.DataBeanX.ArticleBean.DataBean bean = getIntent().getParcelableExtra("bean");
        code = bean.getCode();
        haveId = bean.getHave_id();

        tvStockName = getView(R.id.tv_stock_name);
        tvCompanyName = getView(R.id.tv_company_name);
        tvPlateName = getView(R.id.tv_plate_name);
        tvStockTime = getView(R.id.tv_stock_time);
        tvWebsite = getView(R.id.tv_website);
        tvScore = getView(R.id.tv_score);
        rbStarscore = getView(R.id.rb_starscore);
        tvPerson = getView(R.id.tv_person);
        LinearLayout btnWriteComment = getView(R.id.btn_writecomment);
        TextView tvAllComment = getView(R.id.tv_allcomment);
        ImageView ivBack = getView(R.id.iv_back);
        TextView tvTitle = getView(R.id.tv_title);
        CircleImageView ivHeadportrait = getView(R.id.iv_headportrait);
        TextView tvCommentName = getView(R.id.tv_comment_name);
        TextView tvCommentFraction = getView(R.id.tv_comment_fraction);
        TextView tvCommentContent = getView(R.id.tv_comment_content);
        TextView tvCommentTime = getView(R.id.tv_comment_time);
        TextView tvRichtext = getView(R.id.tv_richtext);
        tvNewcomment = getView(R.id.tv_newcomment);
        MyRecycleView recyclerListed = getView(R.id.recycler_listed);
        MyLayoutManager layoutManager = new MyLayoutManager(this);
        layoutManager.setScrollEnabled(false);
        recyclerListed.setLayoutManager(layoutManager);
        list = new ArrayList<>();
        View footView = View.inflate(this, R.layout.layout_foot_listed, null);
        TextView tvNoData = footView.findViewById(R.id.tv_noData);
        LinearLayout tvLoading = footView.findViewById(R.id.tv_Loading);
        tvNoData.setVisibility(View.VISIBLE);
        tvLoading.setVisibility(View.GONE);
        adapter = new ListedRecyclerAdapter(this, list);
        adapter.addFooterView(footView);
        recyclerListed.setAdapter(adapter);
        etInfo = getView(R.id.et_info);
        etInfo.setFilters(new InputFilter[]{new MaxTextLengthFilter(this, 1000)});
        btnSend = getView(R.id.btn_send);

        btnWriteComment.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        tvAllComment.setOnClickListener(this);
        btnSend.setOnClickListener(this);

        tvTitle.setText("短评详情");
        ImageLoader.getInstance().loadImageUrl(this, ivHeadportrait, bean.getUser_img());
        tvCommentName.setText(bean.getUser_name());
        tvCommentFraction.setText(String.valueOf(bean.getCount() * 2));

        SpannableStringBuilder span = new SpannableStringBuilder(bean.getContent());
        span.setSpan(new ForegroundColorSpan(Color.TRANSPARENT), 0, 2,
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        tvCommentContent.setText("\u3000\u3000" + span);

        tvCommentTime.setText(String.format("编辑于%s", bean.getTime()));
        final String user_name = bean.getUser_name();
        String format = String.format("SPECIAL NOTICE:本短评由 %s 原创，你也可以接受他的邀请，助他获得现金红包", user_name);

        Spannable string = new SpannableString(format);
        // 粗体
        string.setSpan(new StyleSpan(Typeface.BOLD), 0, 14, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        // 字体大小
        string.setSpan(new AbsoluteSizeSpan(UIUtils.sp2px(this, 16)), 0, 14, Spannable.SPAN_INCLUSIVE_INCLUSIVE);
//        // 字体颜色
        string.setSpan(new ForegroundColorSpan(0xFFF5A44F), 20, 21 + user_name.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
//        string.setSpan(new ForegroundColorSpan(0xFFF5A44F), 36, 38, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvRichtext.setText(string);

        recyclerListed.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

            }
        });
    }

    @Override
    public void initData() {
        RetrofitManage
                .getApiService(BaseUrl.BASE_WK_URL)
                .getStockData(code, userId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver<ResponseBody>(this));
        request(new HaveInfoDataListener());

    }

    private void request(ObserverOnNextListener nextListener) {
        RetrofitManage
                .getApiService(BaseUrl.BASE_WK_URL)
                .getHaveInfoData(haveId + "")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver<ResponseBody>(nextListener));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            //写短评
            case R.id.btn_writecomment:
                Intent intent = new Intent(ListedCompanyActivity.this, StockDetailsActivity.class);
                intent.putExtra(CODE, code);
                startActivity(intent);
                break;
            //查看全部评论
            case R.id.tv_allcomment:
                Intent intent2 = new Intent(ListedCompanyActivity.this, AllCommentActivity.class);
                intent2.putExtra(CODE, code);
                startActivity(intent2);
                break;
            //发送
            case R.id.btn_send:
                send();
                break;
            default:
        }
    }

    public void send() {
        if (loginStaus) {
            sendRequest();
        } else {
            Intent intent = new Intent(ListedCompanyActivity.this, LoginActivity.class);
            startActivityForResult(intent, CODE_LISTED);
        }
    }

    private void sendRequest() {
        try {
            String content = etInfo.getText().toString();
            JSONObject json = new JSONObject();
            json.put("user_id", userId);
            json.put("p_have_id", haveId);
            json.put("content", content);
            json.put("num", content.length());

            if (!TextUtils.isEmpty(content)) {
                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json.toString());
                RetrofitManage
                        .getApiService(BaseUrl.BASE_WK_URL)
                        .getSend(body)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new MyObserver<ResponseBody>(new SendLisener()));
            } else {
                ToastUtils.showShortToast(this, "请输入内容");
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CODE_LISTED && resultCode == LoginActivity.CODE) {
            loginStaus = (boolean) SPUtils.getParam(this, LoginActivity.LOGIN_STATUS, false);
            userId = (String) SPUtils.getParam(this, LoginActivity.USER_ID, "");
            sendRequest();
        }
    }

    /**
     * 发送
     */
    private class SendLisener implements ObserverOnNextListener<ResponseBody> {

        @Override
        public void onNext(ResponseBody body) {
            try {
                String string = body.string();
                JSONObject jsonObject = new JSONObject(string);
                if (jsonObject.optBoolean("code")) {
                    etInfo.setText("");
                    ToastUtils.showShortToast(ListedCompanyActivity.this, "发表成功");
                    request(new ObserverOnNextListener<ResponseBody>() {
                        @Override
                        public void onNext(ResponseBody responseBody) {
                            try {
                                String json = responseBody.string();
                                JSONObject jsonObject = new JSONObject(json);
                                if (jsonObject.optBoolean("code")) {
                                    JSONArray jsonArray = jsonObject.optJSONObject("data").optJSONArray("item");
                                    if (jsonArray.length() > 0) {
                                        tvNewcomment.setText(String.format("最新评论%s条", jsonArray.length()));
                                    } else {
                                        tvNewcomment.setText(String.format("最新评论%s条", 0));
                                    }
                                    list.clear();
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
                    });
                } else {
                    ToastUtils.showShortToast(ListedCompanyActivity.this, "评论失败");
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 短评列表数据
     */
    private class HaveInfoDataListener implements ObserverOnNextListener<ResponseBody> {

        @Override
        public void onNext(ResponseBody responseBody) {
            try {
                String json = responseBody.string();
                JSONObject jsonObject = new JSONObject(json);
                if (jsonObject.optBoolean("code")) {
                    JSONArray jsonArray = jsonObject.optJSONObject("data").optJSONArray("item");
                    if (jsonArray.length() > 0) {
                        tvNewcomment.setText(String.format("最新评论%s条", jsonArray.length()));
                    } else {
                        tvNewcomment.setText(String.format("最新评论%s条", 0));
                    }
                    list.clear();
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
    }

    @Override
    public void onNext(ResponseBody responseBody) {
        try {
            String json = responseBody.string();
            JSONObject jsonObject = new JSONObject(json);
            if (jsonObject.optBoolean("code")) {
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

            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
