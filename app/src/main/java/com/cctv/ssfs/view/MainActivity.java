package com.cctv.ssfs.view;

import android.content.Intent;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.cctv.ssfs.R;
import com.cctv.ssfs.base.BaseUrl;
import com.cctv.ssfs.netdata.UpdateAppHttpUtil;
import com.cctv.ssfs.utils.SPUtils;
import com.cctv.ssfs.utils.ToastUtils;
import com.cctv.ssfs.view.base.BaseActivity;
import com.cctv.ssfs.view.base.FragmentBuilder;
import com.cctv.ssfs.view.search.SearchFragment;
import com.cctv.ssfs.view.user.LoginActivity;
import com.umeng.socialize.UMShareAPI;
import com.vector.update_app.UpdateAppBean;
import com.vector.update_app.UpdateAppManager;
import com.vector.update_app.UpdateCallback;
import com.vector.update_app.utils.AppUpdateUtils;
import com.xdandroid.hellodaemon.IntentWrapper;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Qi
 */
public class MainActivity extends BaseActivity implements RadioGroup.OnCheckedChangeListener {

    private static boolean isExit = false;
    private MyHandler mHandler;
    public RadioButton rbMenuHome;
    public RadioButton rbMenuSearch;
    public RadioButton rbMenuUserpager;
    private boolean isHomeSelected, isSearchSelected;
    public static final int CODE = 123;

    @Override
    public int getLayout() {
        setStatusBar(true, 0xFFD53D3C);
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        mHandler = new MyHandler();
        rbMenuHome = getView(R.id.rb_menu_home);
        rbMenuSearch = getView(R.id.rb_menu_search);
        rbMenuUserpager = getView(R.id.rb_menu_userpager);
        RadioGroup rgGroup = getView(R.id.rg_group);
        FragmentBuilder.getInstance().start(R.id.mFrameLayout, HomeFragment.class).buid();
        rbMenuHome.setChecked(true);
        rgGroup.setOnCheckedChangeListener(this);
        initVersion();
    }

    @Override
    public void initData() {

    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rb_menu_home:
                isHomeSelected = true;
                isSearchSelected = false;
                setStatusBar(true, 0xFFD53D3C);
                FragmentBuilder.getInstance().start(R.id.mFrameLayout, HomeFragment.class).buid();

                break;
            case R.id.rb_menu_search:
                isHomeSelected = false;
                isSearchSelected = true;
                setStatusBar(false, 0);
                FragmentBuilder.getInstance().start(R.id.mFrameLayout, SearchFragment.class).buid();
                break;
            case R.id.rb_menu_userpager:
                boolean login_status = (boolean) SPUtils.getParam(this, LoginActivity.LOGIN_STATUS, false);

                if (login_status) {
                    rbMenuUserpager.setChecked(true);
                    setStatusBar(true, 0xFFE02E24);
                    FragmentBuilder.getInstance().start(R.id.mFrameLayout, UserPagerFragment.class).buid();
                } else {
                    startActivityForResult(new Intent(MainActivity.this, LoginActivity.class), CODE);

                    rbMenuUserpager.setChecked(false);
                    if (isHomeSelected) {
                        rbMenuHome.setChecked(true);
                        rbMenuSearch.setChecked(false);
                    } else {
                        if (isSearchSelected) {
                            rbMenuHome.setChecked(false);
                            rbMenuSearch.setChecked(true);
                        }
                    }
                    rbMenuUserpager.setChecked(false);
                }
                break;
            default:
                break;
        }
    }

    private void initVersion() {
        String path = Environment.getExternalStorageDirectory().getAbsolutePath();
        Map<String, String> params = new HashMap<>();
        String versionName = AppUpdateUtils.getVersionName(this);
        params.put("version", versionName);
        new UpdateAppManager
                .Builder()
                //当前Activity
                .setActivity(this)
                //更新地址
                .setUpdateUrl(BaseUrl.UPDATE_URL)
                //以下设置，都是可选
                //设置请求方式，默认get
                .setPost(true)
                //添加自定义参数，默认version=1.0.0（app的versionName）
                .setParams(params)
                //设置apk下砸路径，默认是在下载到sd卡下/Download/1.0.0/test.apk
                .setTargetPath(path)
                //实现httpManager接口的对象
                .setHttpManager(new UpdateAppHttpUtil())
                .build()
                .checkNewApp(new UpdateCallback() {
                    @Override
                    protected UpdateAppBean parseJson(String json) {
                        UpdateAppBean updateAppBean = new UpdateAppBean();
                        try {
                            JSONObject jsonObject = new JSONObject(json);
                            String code = jsonObject.getString("code");
                            if (TextUtils.equals(code, "40000")) {
                                JSONObject data = jsonObject.getJSONObject("data");
                                updateAppBean
                                        //（必须）是否更新Yes,No
                                        .setUpdate(data.optString("update"))
                                        //（必须）新版本号，
                                        .setNewVersion(data.optString("new_version"))
                                        //（必须）下载地址
                                        .setApkFileUrl(data.optString("apk_file_url"))
                                        //（必须）更新内容
                                        .setUpdateLog(data.optString("update_log"))
                                        //大小，不设置不显示大小，可以不设置
                                        .setTargetSize(data.optString("target_size"))
                                        //是否强制更新，可以不设置
                                        .setConstraint(false);
                            }
                        } catch (JSONException e) {

                        }
                        return updateAppBean;
                    }

                    @Override
                    protected void hasNewApp(UpdateAppBean updateApp, UpdateAppManager updateAppManager) {
                        updateAppManager.showDialogFragment();
                    }

                    /**
                     * 网络请求之前
                     */
                    @Override
                    public void onBefore() {

                    }

                    /**
                     * 网路请求之后
                     */
                    @Override
                    public void onAfter() {

                    }

                    /**
                     * 没有新版本
                     */
                    @Override
                    public void noNewApp() {
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        //登录页面返回的回调
        if (requestCode == CODE && resultCode == LoginActivity.CODE) {
            rbMenuUserpager.setChecked(true);
        } else {
            rbMenuHome.setChecked(true);
        }
    }

    /**
     * 防止华为机型未加入白名单时按返回键回到桌面再锁屏后几秒钟进程被杀
     */
    @Override
    public void onBackPressed() {
        IntentWrapper.onBackPressed(this);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        if (!isExit) {
            isExit = true;
            ToastUtils.showShortToast(MainActivity.this, "再按一次退出程序");
            // 利用handler延迟发送更改状态信息
            mHandler.sendEmptyMessageDelayed(0, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }

    static class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            isExit = false;
        }
    }

}
