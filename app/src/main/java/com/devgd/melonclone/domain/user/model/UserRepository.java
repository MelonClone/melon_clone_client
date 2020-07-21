package com.devgd.melonclone.domain.user.model;

import com.devgd.melonclone.domain.model.Repository;
import com.devgd.melonclone.domain.user.domain.User;
import com.devgd.melonclone.utils.network.HttpManager;

import java.net.MalformedURLException;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.devgd.melonclone.global.consts.Constants.API_SERVER;

public class UserRepository implements Repository {
    private RepoCallback repoCallback;



    public void getUser(final RepoCallback repoCallback) {
        try {
            HttpManager.createService(new URL(API_SERVER), UserService.class)
                    .getUser()
                    .enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.isSuccessful())
                                repoCallback.success(response.body());
                            repoCallback.fail();
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            repoCallback.fail();
                        }
                    });

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
