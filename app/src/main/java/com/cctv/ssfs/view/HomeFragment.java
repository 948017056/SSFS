package com.cctv.ssfs.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;

import com.cctv.ssfs.R;
import com.cctv.ssfs.adapter.HomeViewPagerAdapter;
import com.cctv.ssfs.base.BaseUrl;
import com.cctv.ssfs.netdata.MyObserver;
import com.cctv.ssfs.netdata.RetrofitManage;
import com.cctv.ssfs.view.base.BaseFragment;
import com.cctv.ssfs.entity.HomeBean;
import com.cctv.ssfs.netdata.ObserverOnNextListener;
import com.cctv.ssfs.view.home.fagment.TabFragment;
import com.cctv.xtablayout.XTabLayout;
import com.google.gson.Gson;

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
import okhttp3.ResponseBody;

/**
 * A simple {@link BaseFragment} subclass.
 * 干货首页
 */
public class HomeFragment extends BaseFragment implements ObserverOnNextListener<ResponseBody>, ViewPager.OnPageChangeListener, View.OnClickListener {

    private List<HomeBean.DataBeanX.LabelBean> tabList;
    private List<TabFragment> fragmentList;
    private HomeViewPagerAdapter viewPagerAdapter;
    private Map<String, Object> map;
    private Timer timer = new Timer();

    public HomeFragment() {
    }

    @Override
    protected int getFragmentLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initFragmentView(View view) {
        final XTabLayout tabLayout = getView(R.id.tab_Layout);
        ViewPager mViewPager = getView(R.id.mViewPager);
        LinearLayout linearHeadSearch = getView(R.id.linear_head_search);
        tabList = new ArrayList<>();
        fragmentList = new ArrayList<>();

        viewPagerAdapter = new HomeViewPagerAdapter(getChildFragmentManager(), tabList, fragmentList);
        mViewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(mViewPager);
        mViewPager.setOnPageChangeListener(this);
        linearHeadSearch.setOnClickListener(this);
    }

    @Override
    protected void initFragmentData() {
        request();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        initFragmentData();
    }

    private void request() {
        map = new HashMap<>();
        RetrofitManage.getApiService(BaseUrl.BASE_WK_URL).getHomeData(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver<ResponseBody>(this));
    }

    @Override
    protected void updateFragmentTitleBar() {

    }

    @Override
    protected void setBundle(Bundle bundle) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    /**
     * 根据滑动得到对应Id请求不同的数据
     *
     * @param position
     */
    @Override
    public void onPageSelected(final int position) {
        updatedData(position);
    }

    private void updatedData(final int position) {
        if (position > 0) {
            final TabFragment fragment = fragmentList.get(position);
            map = new HashMap<>();
            map.put(BaseUrl.LABEL, tabList.get(position).getLable_id());
            RetrofitManage.getApiService(BaseUrl.BASE_WK_URL).getHomeData(map)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new MyObserver<ResponseBody>(new ObserverOnNextListener<ResponseBody>() {
                        @Override
                        public void onNext(ResponseBody responseBody) {
                            try {
                                String string = responseBody.string();
                                if (!TextUtils.isEmpty(string)) {
                                    Gson gson = new Gson();
                                    HomeBean bean = gson.fromJson(string, HomeBean.class);
                                    JSONObject jsonObject = new JSONObject(string);

                                    if (bean.isCode()) {
                                        fragment.setData(bean.getData().getLabel().get(position - 1).getLable_id(), bean.getData().getArticle().getLast_page(), jsonObject);
                                    }
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }));
        } else if (position == 0) {
            request();
        }
    }

    /**
     * @param state state ==1的时辰默示正在滑动，
     *              state ==2的时辰默示滑动完毕了，
     *              state ==0的时辰默示什么都没做。
     */
    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    public void onNext(ResponseBody responseBody) {
        try {
            String string = responseBody.string();
            if (!TextUtils.isEmpty(string)) {
                Gson gson = new Gson();
                HomeBean bean = gson.fromJson(string, HomeBean.class);
                if (bean.isCode()) {
                    HomeBean.DataBeanX data = bean.getData();
                    tabList.clear();
                    HomeBean.DataBeanX.LabelBean labelBean = new HomeBean.DataBeanX.LabelBean();
                    labelBean.setName("推荐");
                    tabList.add(0, labelBean);
                    tabList.addAll(data.getLabel());
                    JSONObject jsonObject = new JSONObject(string);
                    for (int i = 0; i < tabList.size(); i++) {
                        TabFragment tabFragment = new TabFragment();
                        tabFragment.setData(0, data.getArticle().getLast_page(), jsonObject);
                        fragmentList.add(tabFragment);
                    }
                    viewPagerAdapter.notifyDataSetChanged();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View v) {
        ((MainActivity) getContext()).rbMenuSearch.setChecked(true);
        // 软键盘自动弹出
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, 100);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
