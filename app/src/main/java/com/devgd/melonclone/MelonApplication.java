package com.devgd.melonclone;

import android.app.Application;

import com.devgd.melonclone.utils.db.DBHelper;

public class MelonApplication extends Application {

    @Override
    public void onCreate() {

        DBHelper.getInstance().init(this);
        super.onCreate();
    }
}
