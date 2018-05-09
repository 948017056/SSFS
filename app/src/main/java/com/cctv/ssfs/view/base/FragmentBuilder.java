package com.cctv.ssfs.view.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.cctv.ssfs.App;

/**
 * 管理程序里面所有的Fragment
 *
 * @author Administrator
 */
@SuppressLint("NewApi")
public class FragmentBuilder {
    private static FragmentBuilder mFragmentBuilder;
    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    private BaseFragment mBaseNowFragment;

    private FragmentBuilder() {
        init();
    }

    public synchronized static FragmentBuilder getInstance() {
        if (mFragmentBuilder == null)
            mFragmentBuilder = new FragmentBuilder();
        return mFragmentBuilder;

    }

    /**
     * 初始化FragmentManager
     */
    private void init() {
        mFragmentManager = App.mBaseActivity.getSupportFragmentManager();
    }

    /**
     * 切换Fragment用的 管理Fragment(链式调用) getInstance().satrt().buid();
     */   //A碎片 -- 打开--->B 碎片
    public FragmentBuilder start(int containerId, Class<? extends BaseFragment> fragmentClass) {
        mFragmentTransaction = mFragmentManager.beginTransaction();
        String fragmentTagName = fragmentClass.getSimpleName();
        mBaseNowFragment = (BaseFragment) mFragmentManager
                .findFragmentByTag(fragmentTagName);

        try {
            if (mBaseNowFragment == null) {
                mBaseNowFragment = fragmentClass.newInstance();
                mFragmentTransaction.add(containerId, mBaseNowFragment, fragmentTagName);

            }
            if (App.mBaseLastFragent != null) {
                mFragmentTransaction.hide(App.mBaseLastFragent);
            }

            mFragmentTransaction.show(mBaseNowFragment);
            mFragmentTransaction.addToBackStack(fragmentTagName);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return this;
    }

    /**
     * 链式调用的Build()方法
     */
    public FragmentBuilder buid() {
        //吧当前的fragment对象赋值给了上一个fragmet
        App.mBaseLastFragent = mBaseNowFragment;
        //提交事务
        mFragmentTransaction.commitAllowingStateLoss();
        return this;
    }

    public BaseFragment getFragmentBundle(Bundle bundle) {
        if (bundle != null) {
            mBaseNowFragment.setBundle(bundle);
        }
        return mBaseNowFragment;
    }



}
