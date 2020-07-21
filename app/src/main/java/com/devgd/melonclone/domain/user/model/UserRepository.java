package com.devgd.melonclone.domain.user.model;

import com.devgd.melonclone.domain.user.domain.User;
import com.devgd.melonclone.global.model.domain.Message;
import com.devgd.melonclone.global.model.repository.Repository;
import com.devgd.melonclone.utils.network.HttpManager;
import com.devgd.melonclone.utils.network.services.UserService;

import java.net.MalformedURLException;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.devgd.melonclone.global.consts.Constants.API_SERVER;

public class UserRepository implements Repository {
    private static UserRepository instance;

    public static UserRepository getInstance() {
        if (instance == null)
            instance = new UserRepository();
        return instance;
    }

    private UserRepository() {
    }


    public void registerUser(User user, final RepoCallback repoCallback) {
        HttpManager.createService(UserService.class)
                .registerUser(user)
                .enqueue(new Callback<Message>() {
                    @Override
                    public void onResponse(Call<Message> call, Response<Message> response) {
                        if (response.isSuccessful())
                            repoCallback.success(new Message());
                        repoCallback.fail();
                    }

                    @Override
                    public void onFailure(Call<Message> call, Throwable t) {
                        repoCallback.fail();
                    }
                });
    }

    public void getUser(final RepoCallback repoCallback) {
        HttpManager.createService(UserService.class)
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
    }
}
