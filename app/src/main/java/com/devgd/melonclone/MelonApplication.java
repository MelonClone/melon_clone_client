package com.devgd.melonclone;


import androidx.room.RoomDatabase;

import com.devgd.melonclone.global.consts.Constants;
import com.devgd.melonclone.global.db.version.AppDatabase;

import org.watermelon.framework.global.model.application.BaseApplication;


public class MelonApplication extends BaseApplication {

    @Override
    public String getDatabaseName() {
        return Constants.DB_NAME;
    }

    @Override
    public String getSharedPreferenceName() {
        return Constants.SP_NAME;
    }

    @Override
    public <D extends RoomDatabase> Class<D> getDatabaseClass() {
        return (Class<D>) AppDatabase.class;
    }
}
