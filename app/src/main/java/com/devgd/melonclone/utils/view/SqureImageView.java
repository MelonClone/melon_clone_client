package com.devgd.melonclone.utils.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import androidx.annotation.Nullable;

public class SqureImageView extends androidx.appcompat.widget.AppCompatImageView {

    public SqureImageView(Context context) {
        super(context);
    }

    public SqureImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public SqureImageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = getMeasuredWidth();
        setMeasuredDimension(width, width);
    }

}
