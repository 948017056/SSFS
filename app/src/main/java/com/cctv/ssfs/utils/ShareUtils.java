package com.cctv.ssfs.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.cctv.ssfs.R;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;
import com.umeng.socialize.shareboard.ShareBoardConfig;

/**
 * Author     wildma
 * DATE       2017/07/16
 * Des	      ${友盟分享工具类}
 */
public class ShareUtils {
    static boolean shareResults;

    public static void share(Context context, SHARE_MEDIA shareMedia) {
        Dialog showDialogToClearCache;

        View share_dialog = LayoutInflater.from(context).inflate(
                R.layout.layout_share, null);

        PopupWindow popupWindow = new PopupWindow(share_dialog,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT, false);
        ImageView ivWxRiends = share_dialog.findViewById(R.id.iv_wxriends);
        ImageView ivWxCircle = share_dialog.findViewById(R.id.iv_wxcircle);
        TextView tvCancel = share_dialog.findViewById(R.id.tv_cancel);

    }

    /**
     * 分享链接
     */

    public static boolean shareWeb(final Activity activity, String WebUrl, String title, String description, String imageUrl) {
        UMWeb web = new UMWeb(WebUrl);
        web.setTitle(title);
        web.setDescription(description);
        if (!"".equals(imageUrl)) {
            web.setThumb(new UMImage(activity, imageUrl));
        } else {
            web.setThumb(new UMImage(activity, R.drawable.umeng_socialize_copyurl));
        }
        ShareBoardConfig config = new ShareBoardConfig();
        config.setMenuItemBackgroundShape(ShareBoardConfig.BG_SHAPE_CIRCULAR);
        config.setShareboardBackgroundColor(0xFFFFFFFF);
        config.setCancelButtonBackground(0xFFF5F6FA);
        config.setIndicatorVisibility(false);

        new ShareAction(activity)
                .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .withMedia(web)
                .setCallback(new UMShareListener() {
                    @Override
                    public void onStart(SHARE_MEDIA share_media) {

                    }

                    @Override
                    public void onResult(final SHARE_MEDIA share_media) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (share_media.name().equals("WEIXIN_FAVORITE")) {
                                    Toast.makeText(activity, " 收藏成功", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(activity, " 分享成功", Toast.LENGTH_SHORT).show();
                                    shareResults = true;
                                }
                            }
                        });
                    }

                    @Override
                    public void onError(final SHARE_MEDIA share_media, final Throwable throwable) {
                        if (throwable != null) {
                            Log.d("throw", "throw:" + throwable.getMessage());
                        }
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(activity, " 分享失败", Toast.LENGTH_SHORT).show();
                                shareResults = false;
                            }
                        });
                    }

                    @Override
                    public void onCancel(final SHARE_MEDIA share_media) {
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(activity, " 分享取消", Toast.LENGTH_SHORT).show();
                                shareResults = false;
                            }
                        });
                    }
                })
                .open(config);

        //新浪微博中图文+链接
        /*new ShareAction(activity)
                .setPlatform(platform)
                .withText(description + " " + WebUrl)
                .withMedia(new UMImage(activity,imageID))
                .share();*/
        return shareResults;
    }
}
