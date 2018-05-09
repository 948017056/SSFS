package com.cctv.ssfs.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.NotificationCompat;

import com.cctv.ssfs.R;
import com.cctv.ssfs.view.user.LoginActivity;
import com.umeng.message.UTrack;
import com.umeng.message.UmengMessageService;
import com.umeng.message.common.UmLog;
import com.umeng.message.entity.UMessage;

import org.android.agoo.common.AgooConstants;
import org.json.JSONObject;

import java.util.Map;

public class MyPushIntentService extends UmengMessageService {
    private static final String TAG = MyPushIntentService.class.getName();
    private String messageType = "";

    public MyPushIntentService() {
    }

    @Override
    public void onMessage(Context context, Intent intent) {
        try {
            String message = intent.getStringExtra(AgooConstants.MESSAGE_BODY);
            UMessage msg = new UMessage(new JSONObject(message));

            Map<String, String> extra = msg.extra;
            Intent intentAct = new Intent();
            if (extra != null) {
                messageType = extra.get("MT");
            }

            /*
             * 强制下线
             */
            if (messageType.equals("1000")) {
                intentAct.setClass(context, LoginActivity.class);
            }

            intentAct.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            //展示通知
            showNotifications(context, msg, intentAct);

            // 对完全自定义消息的处理方式，点击或者忽略
            boolean isClickOrDismissed = true;
            if (isClickOrDismissed) {
                //完全自定义消息的点击统计
                UTrack.getInstance(getApplicationContext()).trackMsgClick(msg);
            } else {
                //完全自定义消息的忽略统计
                UTrack.getInstance(getApplicationContext()).trackMsgDismissed(msg);
            }

        } catch (Exception e) {
            UmLog.e(TAG, e.getMessage());
        }
    }


    /**
     * 自定义通知布局
     *
     * @param context 上下文
     * @param msg     消息体
     * @param intent  intent
     */
    public void showNotifications(Context context, UMessage msg, Intent intent) {
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel("channel_id", "channel_name", NotificationManager.IMPORTANCE_HIGH);
            if (manager != null) {
                manager.createNotificationChannel(channel);
            }
            Notification.Builder builder = new Notification.Builder(context, "channel_id");
            PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                    intent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setSmallIcon(R.mipmap.ic_launcher)
                    .setWhen(System.currentTimeMillis())
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher))
                    .setContentTitle(msg.title)
                    .setContentText(msg.text)
                    .setContentIntent(contentIntent)
                    .setAutoCancel(true);
            manager.notify(100, builder.build());
        } else {
            NotificationManager mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context);
            PendingIntent contentIntent = PendingIntent.getActivity(context, 0,
                    intent, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentTitle(msg.title)
                    .setContentText(msg.text)
                    .setTicker(msg.ticker)
                    .setWhen(System.currentTimeMillis())
                    .setSmallIcon(R.mipmap.ssfs)
                    .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.mipmap.ssfs))
                    .setColor(Color.parseColor("#41b5ea"))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setContentIntent(contentIntent)
                    .setAutoCancel(true);

            mNotificationManager.notify(100, builder.build());
        }

    }

}
