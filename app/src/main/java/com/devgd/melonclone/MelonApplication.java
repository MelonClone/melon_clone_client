package com.devgd.melonclone;


import androidx.room.RoomDatabase;

import org.watermelon.framework.global.model.BaseApplication;

public class MelonApplication extends BaseApplication {

    @Override
    public String getDatabaseName() {
        return null;
    }

    @Override
    public String getSharedPreferenceName() {
        return null;
    }

    @Override
    public Class<RoomDatabase> getRoom() {
        return null;
    }
}
