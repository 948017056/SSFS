package com.cctv.ssfs.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cctv.ssfs.entity.HomeBean;
import com.cctv.ssfs.view.home.fagment.TabFragment;

import java.util.List;

/**
 * Created by qi on 2018/3/22.
 * 首页TabLayout ViewPager
 */

public class HomeViewPagerAdapter extends FragmentPagerAdapter {
    private List<HomeBean.DataBeanX.LabelBean> tabList;
    private List<TabFragment> fragmentList;

    public HomeViewPagerAdapter(FragmentManager fm, List<HomeBean.DataBeanX.LabelBean> tabList, List<TabFragment> fragmentList) {
        super(fm);
        this.tabList = tabList;
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return tabList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabList.get(position).getName();
    }
}
