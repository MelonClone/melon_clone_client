package com.devgd.melonclone.domain.user.model;

import com.devgd.melonclone.domain.user.domain.User;
import com.devgd.melonclone.global.db.DatabaseCallback;

public interface UserDataSource {
    int INSERT_USER = 0;
    int GET_USER = 1;

    void insertOrUpdateUser(User user);
    void getUserInfo();
}
