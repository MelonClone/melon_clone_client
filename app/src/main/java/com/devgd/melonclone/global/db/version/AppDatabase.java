package com.devgd.melonclone.global.db.version;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.devgd.melonclone.domain.user.dao.UserDao;
import com.devgd.melonclone.domain.user.domain.User;

@Database(entities = {
        User.class
}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
