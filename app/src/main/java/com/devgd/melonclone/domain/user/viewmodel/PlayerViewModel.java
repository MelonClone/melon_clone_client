package com.devgd.melonclone.domain.user.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devgd.melonclone.domain.model.Domain;
import com.devgd.melonclone.domain.model.Repository;
import com.devgd.melonclone.domain.user.domain.User;
import com.devgd.melonclone.domain.user.model.UserRepository;

public class PlayerViewModel extends ViewModel {

    private MutableLiveData<User> user;
//    private UserModel lyricModel = new UserModel();
    private UserRepository userDao = new UserRepository();

    public LiveData<User> refreshProfile() {
        if (user == null) {
            user = new MutableLiveData<>();
            loadProfile();
        }

        return user;
    }

    private void loadProfile() {
        userDao.getUser(new Repository.RepoCallback() {
            @Override
            public void success(Domain domain) {
                user = new MutableLiveData<>();
            }

            @Override
            public void fail() {

            }
        });
    }
}
