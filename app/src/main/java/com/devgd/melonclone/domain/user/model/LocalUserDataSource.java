package com.devgd.melonclone.domain.user.model;

import android.os.Message;

import com.devgd.melonclone.domain.user.dao.UserDao;
import com.devgd.melonclone.domain.user.domain.User;

import org.watermelon.framework.global.db.DatabaseCallback;
import org.watermelon.framework.global.model.dao.DaoCallback;
import org.watermelon.framework.global.model.domain.Domain;

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
