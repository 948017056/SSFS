package com.cctv.ssfs.view.user;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.cctv.ssfs.R;
import com.cctv.ssfs.base.BaseUrl;
import com.cctv.ssfs.entity.LoginBean;
import com.cctv.ssfs.entity.WeChatInfo;
import com.cctv.ssfs.netdata.MyObserver;
import com.cctv.ssfs.netdata.ObserverOnNextListener;
import com.cctv.ssfs.netdata.RetrofitManage;
import com.cctv.ssfs.utils.SPUtils;
import com.cctv.ssfs.utils.ToastUtils;
import com.cctv.ssfs.view.base.BaseActivity;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * 登录
 *
 * @author qi
 */
public class LoginActivity extends BaseActivity implements View.OnTouchListener, CompoundButton.OnCheckedChangeListener, View.OnClickListener, ObserverOnNextListener<ResponseBody> {
    public TextView tvBg;
    public TextView tvWeixin;
    public FrameLayout btnLogin;
    public CheckBox chenckBox;
    public static final int CODE = 321;
    private Map<String, Object> map;
    public static final String USER_ID = "userId";
    public static final String LOGIN_STATUS = "loginstatus";
    public static final String LOGIN_IMG = "loginimg";
    private static final String TAG = "LoginActivity";
    public static final int CODE_LOFIN = 123456;

    @Override
    public int getLayout() {
        return R.layout.activity_login;
    }

    @Override
    public void initView() {
        map = new HashMap<>();
        tvBg = getView(R.id.tv_bg);
        btnLogin = getView(R.id.btn_login);
        tvWeixin = getView(R.id.tv_weixin);
        chenckBox = getView(R.id.chenckBox);
        ImageView ivBack = getView(R.id.iv_back);
        ivBack.setOnClickListener(this);
        chenckBox.setChecked(true);
        btnLogin.setOnTouchListener(this);
        chenckBox.setOnCheckedChangeListener(this);
    }

    @Override
    public void initData() {

    }

    public void request(WeChatInfo chatInfo) {
        map.put("nickname", chatInfo.getNickname());
        map.put("province", chatInfo.getProvince());
        map.put("city", chatInfo.getCity());
        map.put("headimgurl", chatInfo.getHeadimgurl());
        map.put("unionid", chatInfo.getUnionid());
        map.put("sex", chatInfo.getSex().equals("男") ? 1 : 2);
        RetrofitManage.getApiService(BaseUrl.BASE_WK_URL)
                .getLoginData(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new MyObserver<ResponseBody>(this));
    }

    @Override
    public void onNext(ResponseBody responseBody) {
        try {
            String json = responseBody.string();
            JSONObject jsonObject = new JSONObject(json);
            boolean code = jsonObject.optBoolean("code");
            if (code) {
                String msg = jsonObject.optString("msg");
                JSONObject data = jsonObject.optJSONObject("data");
                String user_id = data.optString("user_id");
                String user_img = data.optString("user_img");
                SPUtils.setParam(this, USER_ID, user_id);
                SPUtils.setParam(this, LOGIN_STATUS, code);
                SPUtils.setParam(this, LOGIN_IMG, user_img);
                ToastUtils.showShortToast(LoginActivity.this, msg);
                LoginBean bean = new LoginBean();
                bean.setCode(code);
                EventBus.getDefault().post(bean);
                setResult(CODE);
                finish();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if (chenckBox.isChecked()) {
                    tvBg.setVisibility(View.VISIBLE);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                if (chenckBox.isChecked()) {
                    tvBg.setVisibility(View.VISIBLE);
                }
                break;
            case MotionEvent.ACTION_UP:
                //微信登录
                if (chenckBox.isChecked()) {
                    tvBg.setVisibility(View.GONE);
                    authorization(SHARE_MEDIA.WEIXIN);

                }
                break;
            default:
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    //授权
    private void authorization(SHARE_MEDIA share_media) {
        UMShareAPI.get(this).getPlatformInfo(this, share_media, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
//                Log.i(TAG, "onStart " + "授权开始");
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {
//                Log.i(TAG, "onComplete " + "授权完成");
                //sdk是6.4.4的,但是获取值的时候用的是6.2以前的(access_token)才能获取到值,未知原因
                String uid = "";
                if (share_media == SHARE_MEDIA.WEIXIN) {
                    //unionid:（6.2以前用unionid）uid
                    uid = map.get("unionid");
                } else {
                    uid = map.get("uid");
                }
                //微博没有
                String openid = map.get("openid");
                String access_token = map.get("access_token");
                //微信,qq,微博都没有获取到
                String refresh_token = map.get("refresh_token");
                String expires_in = map.get("expires_in");
                String name = map.get("name");
                String province = map.get("province");
                String gender = map.get("gender");

                String iconurl = map.get("profile_image_url");
                String city = map.get("city");
                //拿到信息去请求登录接口。。。
                WeChatInfo chatInfo = new WeChatInfo();
                chatInfo.setNickname(name);
                chatInfo.setProvince(province.equals("") ? " " : province);
                chatInfo.setCity(city.equals("") ? " " : city);
                chatInfo.setHeadimgurl(iconurl);
                chatInfo.setUnionid(uid);
                chatInfo.setSex(gender);
                request(chatInfo);
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                Log.i(TAG, "onError " + "授权失败");
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                Log.i(TAG, "onCancel " + "授权取消");
            }
        });
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked) {
            ObjectAnimator alpha = ObjectAnimator.ofFloat(tvWeixin, "alpha", 0.3f, 1.0f);
            alpha.setDuration(100);
            alpha.start();
        } else {
            ObjectAnimator alpha = ObjectAnimator.ofFloat(tvWeixin, "alpha", 1.0f, 0.3f);
            alpha.setDuration(100);
            alpha.start();
        }
    }

    @Override
    public void onClick(View v) {
        finish();
        setResult(CODE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }

}
