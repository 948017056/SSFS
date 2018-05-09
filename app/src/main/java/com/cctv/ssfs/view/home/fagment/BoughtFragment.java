package com.cctv.ssfs.view.home.fagment;


import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.cctv.ratingbarlibrary.BaseRatingBar;
import com.cctv.ratingbarlibrary.ScaleRatingBar;
import com.cctv.ssfs.R;
import com.cctv.ssfs.base.BaseUrl;
import com.cctv.ssfs.netdata.MyObserver;
import com.cctv.ssfs.netdata.ObserverOnNextListener;
import com.cctv.ssfs.netdata.RetrofitManage;
import com.cctv.ssfs.utils.ToastUtils;
import com.cctv.ssfs.view.base.BaseFragment;
import com.cctv.ssfs.view.home.activity.CommentActivity;
import com.cctv.ssfs.view.search.MaxTextLengthFilter;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

/**
 * A simple {@link Fragment} subclass.
 * 买过
 */
public class BoughtFragment extends BaseFragment implements CommentActivity.OnBoughtListener, TextWatcher, View.OnClickListener {
    private int aFloat;
    private EditText etContent;
    private TextView tvCount;
    private CheckBox chenckBox;
    private boolean isHide;

    public BoughtFragment() {
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_bought;
    }

    @Override
    protected void initFragmentView(View view) {

        ((CommentActivity) getContext()).setOnBoughtListener(this);

        final ScaleRatingBar mRatingBar = getView(R.id.rb_starscore);
        tvCount = getView(R.id.tv_count);
        final TextView tvRating = getView(R.id.tv_rating);
        etContent = getView(R.id.et_content);
        etContent.setFilters(new InputFilter[]{new MaxTextLengthFilter(getContext(), 2000)});
        etContent.addTextChangedListener(this);
        etContent.setOnClickListener(this);
        chenckBox = getView(R.id.chenckBox);
        chenckBox.setChecked(true);
        mRatingBar.setOnRatingChangeListener(new BaseRatingBar.OnRatingChangeListener() {
            @Override
            public void onRatingChange(BaseRatingBar ratingBar, float rating) {
                aFloat = (int) rating;
                switch (aFloat) {
                    case 0:
                        tvRating.setText("点击星星评分");
                        break;
                    case 1:
                        tvRating.setText("很差");
                        break;
                    case 2:
                        tvRating.setText("较差");
                        break;
                    case 3:
                        tvRating.setText("还行");
                        break;
                    case 4:
                        tvRating.setText("推荐");
                        break;
                    case 5:
                        tvRating.setText("力荐");
                        break;
                    default:
                }
            }
        });
    }

    /**
     * 买过回调
     *
     * @param code
     */
    @Override
    public void boughtListener(String code, String userId) {
        if (aFloat > 0) {
            String content = etContent.getText().toString();

            if (!content.equals("")) {
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("user_id", userId);
                    jsonObject.put("content", content);
                    jsonObject.put("code", code);
                    jsonObject.put("num", content.length());
                    jsonObject.put("count", aFloat);

                    RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), jsonObject.toString());

                    RetrofitManage.getApiService(BaseUrl.BASE_WK_URL)
                            .getBoughtData(body)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new MyObserver<ResponseBody>(new ObserverOnNextListener<ResponseBody>() {
                                @Override
                                public void onNext(ResponseBody responseBody) {
                                    try {
                                        String json = responseBody.string();
                                        JSONObject jsonObject = new JSONObject(json);
                                        final boolean CommentCode = jsonObject.optBoolean("code");
                                        if (CommentCode) {
                                            ((CommentActivity) getContext()).setCommentStatus(CommentCode, jsonObject.optString("data"), chenckBox.isChecked());
                                        }
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                ToastUtils.showShortToast(getContext(), "请输入内容");
            }

        } else {
            ToastUtils.showShortToast(getContext(), "请评分");
        }


    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        tvCount.setText(String.format("%s/2000", s.length()));
    }

    @Override
    public void onClick(View v) {
        if (isHide) {
            isHide = false;
            ((CommentActivity) getContext()).noticeHide(isHide);
        } else {
            isHide = true;
            ((CommentActivity) getContext()).noticeHide(isHide);
        }
    }
}
