package com.devgd.melonclone.global.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.devgd.melonclone.utils.view.ScreenUtils;
import com.google.android.material.appbar.AppBarLayout;

public class CustomToolbarLayout extends Toolbar {
    public CustomToolbarLayout(Context context) {
        super(context);
    }

    public CustomToolbarLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomToolbarLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
