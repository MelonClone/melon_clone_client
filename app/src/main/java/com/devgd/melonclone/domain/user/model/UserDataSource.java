package com.devgd.melonclone.domain.user.model;

import android.os.Handler;

import com.devgd.melonclone.domain.user.domain.User;

public interface UserDataSource {
    void insertOrUpdateUser(User user, Handler daoResultHandler);
    User getUserInfo();
}
