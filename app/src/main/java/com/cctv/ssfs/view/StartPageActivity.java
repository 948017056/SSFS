package com.cctv.ssfs.view;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;

import com.cctv.ssfs.R;
import com.cctv.ssfs.utils.SPUtils;
import com.cctv.ssfs.view.base.BaseActivity;

/**
 * @author Qi
 *         启动页
 */
public class StartPageActivity extends BaseActivity {

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            getHome();
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public int getLayout() {
        setStatusBar(false, 0);
        return R.layout.activity_start_page;
    }

    @Override
    public void initData() {
        handler.sendEmptyMessageDelayed(0, 2000);
    }

    public void getHome() {
        boolean status = SPUtils.getFirst(this, GuidePageActivity.FIRST_KEY, false);
        if (status) {
            startActivity(new Intent(StartPageActivity.this, MainActivity.class));
        } else {
            startActivity(new Intent(StartPageActivity.this, GuidePageActivity.class));
        }
        finish();
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
