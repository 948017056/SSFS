package com.cctv.ssfs.utils;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cctv.ssfs.R;

/**
 * Created by qi on 2018/3/23.
 * 自定义toast样式
 */

public class CustomToast {

    private static Toast toast;

    /**
     * 成功提示信息
     *
     * @param context
     * @param text    提示内容
     */
    public static void success(Context context, String text) {
        setToast(context, text, R.mipmap.toast_success);
    }

    /**
     * 错误提示信息
     *
     * @param context
     * @param text    错误提示内容
     */
    public static void error(Context context, String text) {
        setToast(context, text, R.mipmap.toast_error);
    }

    public static void info(Context context, String text) {
        setToast(context, text, R.mipmap.toast_info);
    }

    /**
     * 警告提示信息
     *
     * @param context
     * @param text    警告提示内容
     */
    public static void warning(Context context, String text) {
        setToast(context, text, R.mipmap.toast_warning);
    }

    private static void setToast(Context context, String text, int toast_success) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_toast, null);
        view.setPadding(UIUtils.dp2px(context,15), UIUtils.dp2px(context,15), UIUtils.dp2px(context,15), UIUtils.dp2px(context,15));
        ImageView imageView = view.findViewById(R.id.iv_toast);
        imageView.setImageResource(toast_success);
        TextView t = view.findViewById(R.id.tv_toast);
        t.setText(text);
        if (toast == null) {
            toast = new Toast(context);
        }
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
}
