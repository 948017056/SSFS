package com.cctv.ssfs.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by qi on 2018/4/9.
 */

public class AllCommentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragmentList;
    private List<String> tabList;

    public AllCommentPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> tabList) {
        super(fm);
        this.fragmentList = fragmentList;
        this.tabList = tabList;
    }

    @Override
    public int getCount() {
        return tabList.size();
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabList.get(position);
    }
}
