package com.cctv.ssfs;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.cctv.ssfs.service.MyPushIntentService;
import com.cctv.ssfs.utils.MyConfig;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengNotificationClickHandler;
import com.umeng.message.entity.UMessage;
import com.umeng.socialize.PlatformConfig;


/**
 *
 * @author Qi
 * @date 2017/9/25
 */
public class App extends Application {
    public static Activity mActivity;
    public static FragmentActivity mBaseActivity;
    public static Fragment mBaseLastFragent;

    @Override
    public void onCreate() {
        super.onCreate();
        //开启debug模式，方便定位错误，具体错误检查方式可以查看http://dev.umeng.com/social/android/quick-integration的报错必看，正式发布，请关闭该模式
        UMConfigure.init(this, "5add9a57f43e482e3300001c"
                , "Umeng", UMConfigure.DEVICE_TYPE_PHONE, "27aa61cd0898c927a127733636f7a8e0");
        UMConfigure.setLogEnabled(true);
//        UMConfigure.setEncryptEnabled(true);

        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {
            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回device token
//                Log.i("token_umeng", deviceToken);
            }

            @Override
            public void onFailure(String s, String s1) {
            }
        });
        UmengNotificationClickHandler notificationClickHandler = new UmengNotificationClickHandler() {
            @Override
            public void launchApp(Context context, UMessage uMessage) {
                super.launchApp(context, uMessage);
            }

            @Override
            public void dealWithCustomAction(Context context, UMessage msg) {
                Toast.makeText(context, msg.custom, Toast.LENGTH_LONG).show();
            }
        };
        mPushAgent.setNotificationClickHandler(notificationClickHandler);
        mPushAgent.setPushIntentServiceClass(MyPushIntentService.class);

    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    {
        //微信
        PlatformConfig.setWeixin(MyConfig.WECHAT_APPID, MyConfig.WECHAT_SECRET);
    }
}
