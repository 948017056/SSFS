package com.cctv.ssfs.view.home.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cctv.ssfs.R;
import com.cctv.ssfs.adapter.AllCommentPagerAdapter;
import com.cctv.ssfs.base.BaseUrl;
import com.cctv.ssfs.netdata.MyObserver;
import com.cctv.ssfs.netdata.ObserverOnNextListener;
import com.cctv.ssfs.netdata.RetrofitManage;
import com.cctv.ssfs.view.base.BaseActivity;
import com.cctv.ssfs.view.home.fagment.AllCommentFragment;

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
 * 全部评论
 *
 * @author qi
 */
public class AllCommentActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener, ObserverOnNextListener<ResponseBody> {

    private String code;
    private List<Fragment> fragmentList;
    private AllCommentPagerAdapter adapter;

    @Override
    public int getLayout() {
        return R.layout.activity_all_comment;
    }

    @Override
    public void initView() {
        code = getIntent().getStringExtra(StockDetailsActivity.CODE);
        ImageView ivBack = getView(R.id.iv_back);
        TextView tvTitle = getView(R.id.tv_title);
        tvTitle.setText("全部短评");
        TabLayout mtabLayout = getView(R.id.mtabLayout);
        ViewPager mViewPager = getView(R.id.mViewPager);
        ivBack.setOnClickListener(this);
        mViewPager.setOnPageChangeListener(this);

        fragmentList = new ArrayList<>();
        final List<String> tabList = new ArrayList<>();
        tabList.add("精彩短评");
        tabList.add("最新短评");
        fragmentList.add(new AllCommentFragment());
        fragmentList.add(new AllCommentFragment());

        mtabLayout.setupWithViewPager(mViewPager);
        adapter = new AllCommentPagerAdapter(getSupportFragmentManager(), fragmentList, tabList);
        mViewPager.setAdapter(adapter);
    }

    @Override
    public void initData() {
        request(1, this);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (code != null) {
            final AllCommentFragment fragment = (AllCommentFragment) fragmentList.get(position);
            request(position, new ObserverOnNextListener<ResponseBody>() {
                @Override
                public void onNext(ResponseBody responseBody) {
                    try {
                        String json = responseBody.string();
                        JSONObject jsonObject = new JSONObject(json);
                        if (jsonObject.optBoolean("code")) {
                            JSONObject objectData = jsonObject.optJSONObject("data");
                            fragment.setData(code, 1,objectData);
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

    void request(int flag, ObserverOnNextListener nextListener) {
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("flag", flag + 1);
        RetrofitManage
                .getApiService(BaseUrl.BASE_WK_URL)
                .getCommentData(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver<ResponseBody>(nextListener));
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onClick(View v) {
        finish();
    }

    @Override
    public void onNext(ResponseBody responseBody) {
        try {
            String json = responseBody.string();
            JSONObject jsonObject = new JSONObject(json);
            if (jsonObject.optBoolean("code")) {
                JSONObject objectData = jsonObject.optJSONObject("data");
                AllCommentFragment fragment = (AllCommentFragment) fragmentList.get(0);
                fragment.setData(code, 1,objectData);
                adapter.notifyDataSetChanged();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
