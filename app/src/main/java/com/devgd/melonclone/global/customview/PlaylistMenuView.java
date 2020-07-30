package com.devgd.melonclone.global.customview;

import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

public class PlaylistMenuView extends View {

    public PlaylistMenuView(Context context) {
        super(context);
    }

    public PlaylistMenuView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public PlaylistMenuView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public PlaylistMenuView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


}
