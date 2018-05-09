package com.cctv.ssfs.view.user;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.cctv.ssfs.R;
import com.cctv.ssfs.utils.SPUtils;
import com.cctv.ssfs.utils.ToastUtils;
import com.cctv.ssfs.view.base.BaseActivity;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;

/**
 * 设置
 *
 * @author qi
 */
public class SettingsActivity extends BaseActivity implements View.OnClickListener {

    public static final int CODE_SETTING = 951;

    @Override
    public int getLayout() {
        return R.layout.activity_settings;
    }

    @Override
    public void initView() {
        ImageView iv_back = getView(R.id.iv_back);
        TextView tvTitle = getView(R.id.tv_title);
        TextView tvSignOut = getView(R.id.tv_signout);
        tvTitle.setText("设置");
        iv_back.setOnClickListener(this);
        tvSignOut.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_signout:
                UMShareAPI.get(this).deleteOauth(this, SHARE_MEDIA.WEIXIN, new MyUMAuthListener());
                SPUtils.remove(SettingsActivity.this, LoginActivity.USER_ID);
                SPUtils.remove(SettingsActivity.this, LoginActivity.LOGIN_STATUS);
                ToastUtils.showShortToast(SettingsActivity.this, "退出成功");
                setResult(CODE_SETTING);
                finish();
                break;
            default:
        }
    }

    private class MyUMAuthListener implements UMAuthListener {

        @Override
        public void onStart(SHARE_MEDIA share_media) {

        }

        @Override
        public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {

        }

        @Override
        public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {

        }

        @Override
        public void onCancel(SHARE_MEDIA share_media, int i) {

        }
    }

}
