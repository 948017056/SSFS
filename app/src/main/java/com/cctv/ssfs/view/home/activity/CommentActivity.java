package com.cctv.ssfs.view.home.activity;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.cctv.ssfs.R;
import com.cctv.ssfs.adapter.CommentViewPagerAdapter;
import com.cctv.ssfs.customview.tablayout.PagerSlidingTabStrip;
import com.cctv.ssfs.utils.SPUtils;
import com.cctv.ssfs.view.base.BaseActivity;
import com.cctv.ssfs.view.user.LoginActivity;

/**
 * 评论页
 *
 * @author sanjin
 */
public class CommentActivity extends BaseActivity implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private PagerSlidingTabStrip mTabLayout;
    private int mPosition = -1;
    private String code;
    public static final int COMMENT_CODE = 852;
    private Intent intent;
    private OnBoughtListener onBoughtListener;
    private OnInterstedListener onInterstedListener;
    private String user_id;

    @Override
    public int getLayout() {
//        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        return R.layout.activity_comment;
    }

    @Override
    public void initView() {
        intent = getIntent();
        int position = intent.getIntExtra(StockDetailsActivity.POSITION, -1);
        code = getIntent().getStringExtra(StockDetailsActivity.CODE);
        user_id = (String) SPUtils.getParam(this, LoginActivity.USER_ID, " ");

        mPosition = position;
        ImageView ivCancel = getView(R.id.iv_cancel);
        TextView tvDetermine = getView(R.id.tv_determine);
        mTabLayout = getView(R.id.mtabLayout);
        ViewPager mViewPager = getView(R.id.mViewPager);

        ivCancel.setOnClickListener(this);
        tvDetermine.setOnClickListener(this);
        CommentViewPagerAdapter pagerAdapter = new CommentViewPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(pagerAdapter);
        mTabLayout.setViewPager(mViewPager);
        setTabsValue();
        if (position > -1) {
            mViewPager.setCurrentItem(position);
        }
        mViewPager.setOnPageChangeListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_cancel:
                finish();
                break;
            case R.id.tv_determine:
                if (mPosition == 0) {
                    onInterstedListener.interstedListener(code, user_id);
                } else if (mPosition == 1) {
                    onBoughtListener.boughtListener(code, user_id);
                }
                break;
            default:
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mPosition = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /**
     * 评论后的回调
     *
     * @param commentCode 评论的状态
     * @param data        返回的数据
     * @param isChecked   分享是否选中
     */
    public void setCommentStatus(boolean commentCode, String data, boolean isChecked) {
        intent.putExtra("code", commentCode);
        intent.putExtra("data", data);
        intent.putExtra("isChecked", isChecked);
        setResult(COMMENT_CODE, intent);
        finish();
    }

    public void noticeHide(boolean isHide) {
        /*if (isHide) {
            mTabLayout.setVisibility(View.GONE);
        } else {
            mTabLayout.setVisibility(View.VISIBLE);
        }*/
    }

    /**
     * 买过的回调
     */
    public interface OnBoughtListener {
        /**
         * 股票代码
         *
         * @param code
         */
        void boughtListener(String code, String user_id);
    }

    public void setOnBoughtListener(OnBoughtListener onBoughtListener) {
        this.onBoughtListener = onBoughtListener;
    }


    /**
     * 想买的回调
     */
    public interface OnInterstedListener {
        /**
         * 股票代码
         *
         * @param code
         */
        void interstedListener(String code, String user_id);
    }

    public void setOnInterstedListener(OnInterstedListener onInterstedListener) {
        this.onInterstedListener = onInterstedListener;
    }

    private void setTabsValue() {
        // 设置Tab是自动填充满屏幕的
        mTabLayout.setShouldExpand(true);

        // 设置分割线的上下的间距,传入的是dp
        mTabLayout.setDividerPaddingTopBottom(12);
        //设置Tab底部线的颜色
        mTabLayout.setUnderlineColor(getResources().getColor(R.color.white));
        // 设置Tab标题文字的大小,传入的是sp
        mTabLayout.setTextSize(16);
        // 设置选中Tab文字的颜色
        mTabLayout.setSelectedTextColor(getResources().getColor(R.color.bacak));
        //设置正常Tab文字的颜色
        mTabLayout.setTextColor(getResources().getColor(R.color.gray));
        mTabLayout.setFadeEnabled(true);
        mTabLayout.setZoomMax(0.4F);
    }
}
