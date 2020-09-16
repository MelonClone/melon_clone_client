package com.devgd.melonclone.domain.user.model;

import com.devgd.melonclone.domain.user.domain.User;

import org.watermelon.framework.global.db.DatabaseCallback;

public interface UserDataSource {
    void insertOrUpdateUser(User user, DatabaseCallback daoResultCallback);
    void getUserInfo(DatabaseCallback daoResultCallback);
}
