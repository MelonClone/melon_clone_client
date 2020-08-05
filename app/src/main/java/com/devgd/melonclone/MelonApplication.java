package com.devgd.melonclone;

import android.app.Application;
import android.graphics.Typeface;

import androidx.core.graphics.TypefaceCompatUtil;

import com.devgd.melonclone.utils.TypefaceUtil;
import com.devgd.melonclone.utils.db.DBHelper;
import com.devgd.melonclone.utils.db.SPHelper;

public class MelonApplication extends Application {

    @Override
    public void onCreate() {

        DBHelper.getInstance().init(this);
        SPHelper.getInstance().init(this);
        super.onCreate();

        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/NotoSans-Regular.ttf");
    }
}
