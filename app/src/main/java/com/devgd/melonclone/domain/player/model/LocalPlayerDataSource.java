package com.devgd.melonclone.domain.player.model;

import com.devgd.melonclone.domain.user.dao.UserDao;
import com.devgd.melonclone.domain.user.domain.User;

public class LocalPlayerDataSource implements PlayerDataSource {

    private final UserDao userDao;

    public LocalPlayerDataSource(UserDao userDao) {
        this.userDao = userDao;
    }


    @Override
    public void insertOrUpdateUser(User user) {
         userDao.insertUser(user);
    }

    @Override
    public User getUserInfo() {
        return userDao.getUser();
    }
}
