package com.cctv.ssfs.view.search;

import android.content.Context;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.Gravity;
import android.widget.Toast;

import com.cctv.ssfs.utils.ToastUtils;
import com.cctv.ssfs.view.MainActivity;

/**
 * Created by sanjin on 2018/4/18.
 */

public class MaxTextLengthFilter implements InputFilter {
    private int mMaxLength;
    private Context context;

    public MaxTextLengthFilter(Context context, int max) {
        mMaxLength = max - 1;
        this.context = context;
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end,
                               Spanned dest, int dstart, int dend) {
        int keep = mMaxLength - (dest.length() - (dend - dstart));
        if (keep < (end - start)) {
            ToastUtils.showShortToast(context, "字符不能超过" + mMaxLength + "个");
        }
        if (keep <= 0) {
            return "";
        } else if (keep >= end - start) {
            return null;
        } else {
            return source.subSequence(start, start + keep);
        }
    }
}
