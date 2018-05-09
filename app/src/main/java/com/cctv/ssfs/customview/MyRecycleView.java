package com.cctv.ssfs.customview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class MyRecycleView extends RecyclerView {

    public MyRecycleView(Context context) {
        super(context,null);
    }

    public MyRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs,0);
    }

    public MyRecycleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }

}