package com.devgd.melonclone.domain.user.model;

import android.os.Message;
import android.util.Log;

import com.devgd.melonclone.domain.user.dao.UserDao;
import com.devgd.melonclone.domain.user.domain.User;
import com.devgd.melonclone.global.db.DatabaseCallback;
import com.devgd.melonclone.global.model.DaoCallback;
import com.devgd.melonclone.global.model.domain.Domain;

public class LocalUserDataSource implements UserDataSource {

    private final UserDao userDao;
    private final DatabaseCallback daoResultCallback;

    public LocalUserDataSource(UserDao userDao, DatabaseCallback daoResultCallback) {
        this.userDao = userDao;
        this.daoResultCallback = daoResultCallback;
    }

    @Override
    public void insertOrUpdateUser(User user) {
        DaoCallback.execute(new DaoCallback() {
            public Domain execute() {
                userDao.insertUser(user);
                return null;
            }

            public void postExecute(Domain d) {
                Message msg = new Message();
                msg.arg1 = INSERT_USER;
                daoResultCallback.callback(msg);
            }
        });
    }

    @Override
    public void getUserInfo() {
        DaoCallback.execute(new DaoCallback() {
            public Domain execute() {
                return userDao.getUser();
            }

            public void postExecute(Domain d) {
                Message msg = new Message();
                msg.arg1 = GET_USER;
                msg.obj = d;
                daoResultCallback.callback(msg);
            }
        });
    }
}
