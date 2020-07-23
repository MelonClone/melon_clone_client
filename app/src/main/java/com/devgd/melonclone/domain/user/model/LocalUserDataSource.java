package com.devgd.melonclone.domain.user.model;

import android.os.Handler;

import com.devgd.melonclone.domain.user.dao.UserDao;
import com.devgd.melonclone.domain.user.domain.User;

public class LocalUserDataSource implements UserDataSource {

    private final UserDao userDao;

    public LocalUserDataSource(UserDao userDao) {
        this.userDao = userDao;
    }


    @Override
    public void insertOrUpdateUser(User user, Handler daoResultHandler) {
        new Thread(() -> {
            userDao.insertUser(user);
            daoResultHandler.sendMessage(new android.os.Message());
        }).start();
    }

    @Override
    public User getUserInfo() {
        return userDao.getUser();
    }
}
