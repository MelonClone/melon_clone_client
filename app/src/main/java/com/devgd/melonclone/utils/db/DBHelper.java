package com.devgd.melonclone.utils.db;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import androidx.room.Room;
import androidx.room.migration.Migration;

import com.devgd.melonclone.global.db.AppDatabase;
import com.devgd.melonclone.global.db.version.VersionMigration;

import java.lang.reflect.Method;

import static com.devgd.melonclone.global.consts.Constants.DB_NAME;

public class DBHelper {

    private static DBHelper INSTANCE;
    private AppDatabase database;
    private Context appContext;

    private DBHelper() {
    }

    public static DBHelper getInstance() {
        if (INSTANCE == null) {
            synchronized (DBHelper.class) {
                INSTANCE = new DBHelper();
            }
        }

        return INSTANCE;
    }

    public void init(Context appContext) {
        this.appContext = appContext;
        this.database = Room.databaseBuilder(appContext.getApplicationContext(),
                AppDatabase.class, DB_NAME+".db")
                .addMigrations(VersionMigration.getInstance().getMigrationList().toArray(new Migration[0]))
                .build();
    }

    public AppDatabase getDB() {
        return database;
    }
}
