package com.cctv.ssfs.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;

import com.cctv.ssfs.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.File;
import java.io.IOException;

/**
 * Created by apple on 2018/3/13
 * 图片加载工具类
 * 推荐使用此工具类而不是直接使用Picasso等网络框架，便于业务替换修改
 */

public class ImageLoader{
    private static ImageLoader imageLoader;

    public static ImageLoader getInstance() {
        if (imageLoader == null) {
            imageLoader = new ImageLoader();
        }
        return imageLoader;
    }

    /**
     * 从网络加载用户头像
     *
     * @param imageView 用户头像显示View
     * @param imageUrl  头像网络地址
     */
    public void loadUserIcon(Context context, ImageView imageView, String imageUrl) {
        loadImageUrl(context, imageView, imageUrl, R.mipmap.img_loading, R.mipmap.img_load_error);
    }

    public void loadImageUrl(Context context, ImageView imageView, String imageUrl) {
        loadImageUrl(context, imageView, imageUrl, R.mipmap.img_loading, R.mipmap.img_load_error);
    }

    /**
     * 从网络加载图片到指定View
     *
     * @param imageView     指定显示图片的View
     * @param imageUrl      图片网络地址
     * @param placeHolderId 加载过程显示图片id，如为0则不显示占位图
     * @param errorId       加载出错显示图片id，如为0则不显示
     */
    public void loadImageUrl(Context context, ImageView imageView, String imageUrl, @DrawableRes int placeHolderId, @DrawableRes int errorId) {
        RequestCreator load = Picasso.with(context)
                .load(imageUrl);
        if (placeHolderId != 0) {
            load.placeholder(placeHolderId);
        }
        if (errorId != 0) {
            load.error(errorId);
        }
        load.into(imageView);
    }

    public void loadImageFile(Context context, ImageView imageView, File imageFile) {
        Picasso.with(context)
                .load(imageFile)
                .placeholder(R.mipmap.img_loading)
                .error(R.mipmap.img_loading)
                .into(imageView);
    }

}
