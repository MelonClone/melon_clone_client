package com.devgd.melonclone.domain.user.model;

import android.os.Message;

import com.devgd.melonclone.domain.user.dao.UserDao;
import com.devgd.melonclone.domain.user.domain.User;

import org.watermelon.framework.global.db.DatabaseCallback;
import org.watermelon.framework.global.model.dao.DaoCallback;
import org.watermelon.framework.global.model.domain.Domain;

public class LocalUserDataSource implements UserDataSource {

    private final UserDao userDao;

    public LocalUserDataSource(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void insertOrUpdateUser(User user, DatabaseCallback daoResultCallback) {
        DaoCallback.execute(new DaoCallback() {
            public Domain execute() {
                userDao.insertUser(user);
                return null;
            }

            public void postExecute(Domain d) {
                Message msg = new Message();
                msg.arg1 = 1;
                daoResultCallback.callback(msg);
            }
        });
    }

    @Override
    public void getUserInfo(DatabaseCallback daoResultCallback) {
        DaoCallback.execute(new DaoCallback() {
            public Domain execute() {
                return userDao.getUser();
            }

            public void postExecute(Domain d) {
                Message msg = new Message();
                msg.arg1 = 1;
                msg.obj = d;
                daoResultCallback.callback(msg);
            }
        });
    }
}
