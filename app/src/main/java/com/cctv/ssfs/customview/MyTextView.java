package com.cctv.ssfs.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.cctv.ssfs.utils.LogUtils;

/**
 * Created by sanjin on 2018/5/2.
 */

public class MyTextView extends android.support.v7.widget.AppCompatTextView {
    private static long lastClickTime;

    public MyTextView(Context context) {
        this(context, null);
    }

    public MyTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private static final String TAG = "WebDetailsActivity";

    public synchronized static boolean isFastClick() {
        long time = System.currentTimeMillis();
        if (time - lastClickTime < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_UP:
                getParent().requestDisallowInterceptTouchEvent(true);
                if (!isFastClick()) {
                    LogUtils.i(TAG, "子View>>>>>>onTouchEvent");
                    clickListener.click();
                }
                break;
            case MotionEvent.ACTION_MOVE:

                break;
            default:
        }
        return true;
    }


    private MyClickListener clickListener;

    public interface MyClickListener {
        void click();
    }

    public void setClickListener(MyClickListener clickListener) {
        this.clickListener = clickListener;
    }
}
