package com.devgd.melonclone;

import android.app.Application;
import android.graphics.Typeface;

import androidx.core.graphics.TypefaceCompatUtil;

import com.devgd.melonclone.utils.TypefaceUtil;
import com.devgd.melonclone.utils.db.DBHelper;

public class MelonApplication extends Application {

    @Override
    public void onCreate() {

        DBHelper.getInstance().init(this);
        super.onCreate();

        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/NotoSans-Regular.ttf");
    }
}
