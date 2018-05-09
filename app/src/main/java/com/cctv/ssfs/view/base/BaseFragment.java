package com.cctv.ssfs.view.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



/**
 * Fragment的基类 所有Fragment都应该继承它
 */
@SuppressLint("NewApi")
public abstract class BaseFragment extends Fragment {

    protected View view;
    /**
     * 控件是否初始化完成
     */
    private boolean isViewCreated;
    /**
     * 数据是否已加载完毕
     */
    private boolean isLoadDataCompleted;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(getFragmentLayoutId(), container, false);
        initFragmentView(view);
        updateFragmentTitleBar();
        isViewCreated = true;
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initFragmentData();
    }


    @SuppressWarnings("unchecked")
    public final <E extends View> E getView(int id) {
        try {
            return (E) view.findViewById(id);
        } catch (ClassCastException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && isViewCreated && !isLoadDataCompleted) {
            isLoadDataCompleted = true;
            onFragmentVisibleChange();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getUserVisibleHint()) {
            isLoadDataCompleted = true;
            onFragmentVisibleChange();
        }
    }

    /**
     * 懒加载使用
     */
    protected void onFragmentVisibleChange() {

    }

    /**
     * 加载不同的Fragment的布局
     *
     * @return
     */
    protected abstract int getFragmentLayoutId();

    /**
     * 初始化Fragment中的控件
     */
    protected abstract void initFragmentView(View view);

    /**
     * 初始化Fragment中数据
     */
    protected void initFragmentData() {
    }

    /**
     * 更新Fragment中的标题栏
     */
    protected void updateFragmentTitleBar() {
    }

    /**
     * Fragment之间传值
     */
    protected void setBundle(Bundle bundle) {
    }

    /**
     * add(),hide(),show();
     * <p>
     * AFragment------->BFragment
     * <p>
     * 当AFragment隐藏的时候会调用这个方法
     */
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden) {
            hideTitleBar();//如果当前fragment显示，就调用该方法，让该方法去调用更新TitleBar
        } else {
            showTitleBar();
        }
    }

    /**
     * 隐藏标题栏
     */
    private void hideTitleBar() {
        updateFragmentTitleBar();
    }

    /**
     * 显示标题栏
     */
    private void showTitleBar() {

    }
}
