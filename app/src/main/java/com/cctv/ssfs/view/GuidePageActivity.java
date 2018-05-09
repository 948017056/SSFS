package com.cctv.ssfs.view;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cctv.ssfs.R;
import com.cctv.ssfs.utils.SPUtils;
import com.cctv.ssfs.view.base.BaseActivity;
import com.squareup.picasso.Picasso;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * @author sanjin
 *         引导页
 */
public class GuidePageActivity extends BaseActivity {
    public static final String FIRST_KEY = "first";

    @Override
    public int getLayout() {
        setStatusBar(false, 0);
        return R.layout.activity_guide_page;
    }

    @Override
    public void initData() {
        List<Integer> list = new ArrayList<>();
        list.add(R.mipmap.guide1);
        list.add(R.mipmap.guide2);
        list.add(R.mipmap.guide3);
        Banner mBanner = getView(R.id.mBanner);
        final TextView btnEnter = getView(R.id.btn_enter);
        mBanner.setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Picasso.with(context).load((int) path).into(imageView);
            }
        });
        mBanner.setImages(list);
        mBanner.isAutoPlay(false);
        mBanner.setBannerStyle(BannerConfig.NOT_INDICATOR);
        mBanner.setBannerAnimation(Transformer.CubeOut);
        mBanner.start();
        mBanner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                if (position == 2) {
                    SPUtils.setFirst(GuidePageActivity.this, FIRST_KEY, true);
                    startActivity(new Intent(GuidePageActivity.this, MainActivity.class));
                    finish();
                }
            }
        });
        mBanner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position != 2) {
                    btnEnter.setVisibility(View.GONE);
                } else {
                    btnEnter.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

}
