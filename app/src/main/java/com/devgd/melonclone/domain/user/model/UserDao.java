package com.devgd.melonclone.domain.user.model;

import com.devgd.melonclone.domain.model.Dao;
import com.devgd.melonclone.domain.user.domain.User;
import com.devgd.melonclone.utils.network.HttpManager;

import java.net.MalformedURLException;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.devgd.melonclone.utils.Constants.API_SERVER;

public class UserDao implements Dao {
    private DaoCallback callback;



    public void getUser(final Dao.DaoCallback callback) {
        try {
            HttpManager.createService(new URL(API_SERVER), UserService.class)
                    .getUser()
                    .enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.isSuccessful())
                                callback.success(response.body());
                            callback.fail();
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            callback.fail();
                        }
                    });

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
