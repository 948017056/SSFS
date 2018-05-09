package com.cctv.ssfs.view.base;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.cctv.ssfs.R;
import com.cctv.ssfs.utils.CustomToast;
import com.cctv.ssfs.utils.LogUtils;
import com.cctv.ssfs.utils.SPUtils;
import com.cctv.ssfs.utils.ShareUtils;
import com.cctv.ssfs.view.user.LoginActivity;
import com.umeng.socialize.UMShareAPI;

/**
 * 网络内容展示界面
 *
 * @author qi
 */
public class WebActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "WebActivity";
    public WebView webView;
    public static final String KEY_URL = "url";
    public static final String TITLE = "title";
    private TextView tvTitle;
    private boolean isShare;
    private Intent intent;
    private String userId;

    @Override
    public int getLayout() {
        return R.layout.activity_web;
    }

    @Override
    public void initView() {
        super.initView();
        intent = getIntent();

        ImageView iv_back = getView(R.id.iv_back);
        webView = getView(R.id.webView);
        tvTitle = getView(R.id.tv_title);
        iv_back.setOnClickListener(this);
        WebSettings settings = webView.getSettings();
        //设置自适应屏幕，两者合用
        settings.setUseWideViewPort(true);
        //将图片调整到适合webview的大小
        // 缩放至屏幕的大小
        settings.setLoadWithOverviewMode(true);
        //设置js可以直接打开窗口，如window.open()，默认为false
//        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        //是否允许执行js，默认为false。设置true时，会提醒可能造成XSS漏洞
        settings.setJavaScriptEnabled(true);
        //是否可以缩放，默认true
//        settings.setSupportZoom(true);
        //是否显示缩放按钮，默认false
//        settings.setBuiltInZoomControls(true);
        //设置此属性，可任意比例缩放。大视图模式
//        settings.setUseWideViewPort(true);
        //和setUseWideViewPort(true)一起解决网页自适应问题
//        settings.setLoadWithOverviewMode(true);
        //是否使用缓存
//        settings.setAppCacheEnabled(true);
        // DOM Storage
        settings.setDomStorageEnabled(true);
    }

    @Override
    public void initData() {
        super.initData();
        userId = (String) SPUtils.getParam(this, LoginActivity.USER_ID, "-1");
        String url = intent.getStringExtra(KEY_URL);
        String title = intent.getStringExtra(TITLE);
        isShare = intent.getBooleanExtra("isShare", false);
        tvTitle.setText(title);
        if (!TextUtils.isEmpty(url)) {
            webView.loadUrl(url);
            webView.setWebChromeClient(new CustomWebChromeClient());
            webView.setWebViewClient(new CustomWebViewClient());
        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    private class CustomWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            webView.loadUrl(url);
            return true;
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            LogUtils.i(TAG, String.format("onPageFinished url = %s", url));

            if (isShare) {
                String title = intent.getStringExtra("titleinfo");
                String desc = intent.getStringExtra("desc");
                String pic = intent.getStringExtra("pic");
                String url1 = intent.getStringExtra("urls");
                ShareUtils.shareWeb(WebActivity.this, url1 + "?user_id=" + userId,
                        title, desc, pic);
            }
            /*//编写 javaScript方法
            String javascript = "javascript:function hideOther() {" +
                    "document.getElementsByTagName('header')[0].style.display='none';}";
            //创建方法
            view.loadUrl(javascript);
            //加载方法
            view.loadUrl("javascript:hideOther();");*/
        }
    }

    private class CustomWebChromeClient extends WebChromeClient {

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            LogUtils.i(TAG, "onReceivedTitle");
            //在这里可以在本地设置网页标题
            setTitle(TextUtils.isEmpty(title) ? getResources().getString(R.string.app_name) : title);
        }

        @Override
        public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
            LogUtils.i(TAG, "onJsAlert");
            CustomToast.info(WebActivity.this, message);
            result.confirm();
            return true;
        }

        @TargetApi(Build.VERSION_CODES.LOLLIPOP)
        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> filePathCallback, FileChooserParams fileChooserParams) {
            LogUtils.i(TAG, "onShowFileChooser");

            return true;
        }

        private void chooseFile(String type) {
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
