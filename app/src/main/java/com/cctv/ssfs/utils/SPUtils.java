package com.cctv.ssfs.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author qi
 * @date 2018/4/12
 *
 */

public class SPUtils {
    /**
     * 保存在手机里面的文件名
     */
    private static final String FILE_NAME = "share_data";
    private static final String PRAISE_NAME = "praise_data";
    private static final String GUIDE_NAME = "guidepage_data";
    private static final String COLLECTION_NAME = "collection_data";
    private static final String TIMESTAMPS_NAME = "timestamps_data";
    private static final String TIME_KEY = "timestamps_data";

    /**
     * 保存首次进入的状态
     *
     * @param context
     * @param time
     */
    public static void setTimestamps(Context context, long time) {
        SharedPreferences sp = context.getSharedPreferences(TIMESTAMPS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putLong(TIME_KEY, time);
        editor.commit();
    }

    /**
     * 获取进入的状态
     *
     * @param context
     * @param time
     */
    public static long getTimestamps(Context context, long time) {
        SharedPreferences sp = context.getSharedPreferences(TIMESTAMPS_NAME, Context.MODE_PRIVATE);
        return sp.getLong(TIME_KEY, time);
    }

    /**
     * 保存首次进入的状态
     *
     * @param context
     * @param key
     * @param praiseStatus
     */
    public static void setFirst(Context context, String key, boolean praiseStatus) {
        SharedPreferences sp = context.getSharedPreferences(GUIDE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, praiseStatus);
        editor.commit();
    }

    /**
     * 获取进入的状态
     *
     * @param context
     * @param key
     * @param praiseStatus
     */
    public static boolean getFirst(Context context, String key, boolean praiseStatus) {
        SharedPreferences sp = context.getSharedPreferences(GUIDE_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, praiseStatus);
    }

    /**
     * 保存收藏状态
     *
     * @param context
     * @param key
     * @param praiseStatus
     */
    public static void setCollection(Context context, String key, int praiseStatus) {
        SharedPreferences sp = context.getSharedPreferences(COLLECTION_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putInt(key, praiseStatus);
        editor.commit();
    }

    /**
     * 获取收藏状态
     *
     * @param context
     * @param key
     * @param praiseStatus
     */
    public static int getCollection(Context context, String key, int praiseStatus) {
        SharedPreferences sp = context.getSharedPreferences(COLLECTION_NAME, Context.MODE_PRIVATE);
        return sp.getInt(key, praiseStatus);
    }

    /**
     * 保存点赞状态
     *
     * @param context
     * @param key
     * @param praiseStatus
     */
    public static void setPraise(Context context, String key, boolean praiseStatus) {
        SharedPreferences sp = context.getSharedPreferences(PRAISE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.putBoolean(key, praiseStatus);
        editor.commit();
    }

    /**
     * 获取点赞状态
     *
     * @param context
     * @param key
     * @param praiseStatus
     */
    public static boolean getPraise(Context context, String key, boolean praiseStatus) {
        SharedPreferences sp = context.getSharedPreferences(PRAISE_NAME, Context.MODE_PRIVATE);
        return sp.getBoolean(key, praiseStatus);
    }


    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     *
     * @param context
     * @param key
     * @param object
     */
    public static void setParam(Context context, String key, Object object) {

        String type = object.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if ("String".equals(type)) {
            editor.putString(key, (String) object);
        } else if ("Integer".equals(type)) {
            editor.putInt(key, (Integer) object);
        } else if ("Boolean".equals(type)) {
            editor.putBoolean(key, (Boolean) object);
        } else if ("Float".equals(type)) {
            editor.putFloat(key, (Float) object);
        } else if ("Long".equals(type)) {
            editor.putLong(key, (Long) object);
        }

        editor.commit();
    }


    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     *
     * @param context
     * @param key
     * @param defaultObject
     * @return
     */
    public static Object getParam(Context context, String key, Object defaultObject) {
        String type = defaultObject.getClass().getSimpleName();
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

        if ("String".equals(type)) {
            return sp.getString(key, (String) defaultObject);
        } else if ("Integer".equals(type)) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if ("Boolean".equals(type)) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if ("Float".equals(type)) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if ("Long".equals(type)) {
            return sp.getLong(key, (Long) defaultObject);
        }

        return null;
    }

    /**
     * @param context
     * @param key     根据key删除对应内容
     * @return
     */
    public static void remove(Context context, String key) {
        SharedPreferences.Editor edit = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit();
        edit.remove(key);
        edit.commit();
    }

    /**
     * 清空全部
     *
     * @param context
     */
    public static boolean clear(Context context) {
        SharedPreferences.Editor edit = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit();
        edit.clear();
        return edit.commit();
    }
}
