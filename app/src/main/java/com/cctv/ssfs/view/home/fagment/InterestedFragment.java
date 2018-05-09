package com.cctv.ssfs.view.home.fagment;


import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

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
 * 想买
 */
public class InterestedFragment extends BaseFragment implements TextWatcher, CommentActivity.OnInterstedListener {


    private TextView tvCount;
    private EditText etContent;
    private CheckBox chenckBox;

    public InterestedFragment() {
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_interested;
    }

    @Override
    protected void initFragmentView(View view) {
        etContent = getView(R.id.et_content);
        etContent.addTextChangedListener(this);
        tvCount = getView(R.id.tv_count);
        etContent.setFilters(new InputFilter[]{new MaxTextLengthFilter(getContext(), 2000)});
        chenckBox = getView(R.id.chenckBox);
        chenckBox.setChecked(true);
        ((CommentActivity) getContext()).setOnInterstedListener(this);
    }

    /**
     * 想买
     */
    public void requestIntersted(RequestBody body, ObserverOnNextListener nextListener) {
        RetrofitManage.getApiService(BaseUrl.BASE_WK_URL)
                .getInterstedData(body)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver<ResponseBody>(nextListener));
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
    public void interstedListener(String code, String userId) {
        String content = etContent.getText().toString();
        if (!content.equals("")) {
            try {
                JSONObject json = new JSONObject();
                json.put("user_id", userId);
                json.put("content", content);
                json.put("code", code);
                json.put("num", content.length());
                RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json.toString());

                requestIntersted(body, new ObserverOnNextListener<ResponseBody>() {
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            String jsonString = responseBody.string();
                            JSONObject jsonObject = new JSONObject(jsonString);
                            boolean commentCode = jsonObject.optBoolean("code");
                            if (commentCode) {
                                ((CommentActivity) getContext()).setCommentStatus(commentCode, jsonObject.optString("data"),chenckBox.isChecked());
                            }

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            ToastUtils.showShortToast(getContext(), "请输入内容");
        }
    }
}
