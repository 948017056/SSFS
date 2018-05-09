package com.cctv.ssfs.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.cctv.ssfs.R;

/**
 * Created by qi on 2018/3/23.
 */

public class UserItemLayout extends LinearLayout {
    public UserItemLayout(Context context) {
        this(context,null);
    }

    public UserItemLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public UserItemLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = View.inflate(context, R.layout.layout_user_item, this);
        ImageView ivIcon = view.findViewById(R.id.iv_user_item_icon);
        TextView tvName = view.findViewById(R.id.tv_user_item_name);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.UserItemLayout);
        Drawable icon = typedArray.getDrawable(R.styleable.UserItemLayout_icon);
        String name = typedArray.getString(R.styleable.UserItemLayout_name);
        typedArray.recycle();

        ivIcon.setImageDrawable(icon);
        tvName.setText(name);
    }


}
