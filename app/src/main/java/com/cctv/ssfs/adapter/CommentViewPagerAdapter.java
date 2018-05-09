package com.cctv.ssfs.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cctv.ssfs.view.home.fagment.BoughtFragment;
import com.cctv.ssfs.view.home.fagment.InterestedFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qi on 2018/4/2.
 */

public class CommentViewPagerAdapter extends FragmentPagerAdapter {
    private List<String> tabList;
    private List<Fragment> fragmentList;

    public CommentViewPagerAdapter(FragmentManager fm) {
        super(fm);
        tabList = new ArrayList<>();
        fragmentList = new ArrayList<>();
        tabList.add("想买");
        tabList.add("买过");
        fragmentList.add(new InterestedFragment());
        fragmentList.add(new BoughtFragment());

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
        return tabList.get(position);
    }
}
