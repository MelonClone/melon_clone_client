package com.devgd.melonclone.global.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

public class SquareRoundImageView extends AppCompatImageView {

    // 라운드처리 강도 값을 크게하면 라운드 범위가 커짐
    public static float radius = 20.0f;

    public SquareRoundImageView(Context context) {
        super(context);
    }

    public SquareRoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SquareRoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Path clipPath = new Path();
        RectF rect = new RectF(0, 0, this.getWidth(), this.getHeight());
        clipPath.addRoundRect(rect, radius, radius, Path.Direction.CW);
        canvas.clipPath(clipPath);
        super.onDraw(canvas);
    }
}

