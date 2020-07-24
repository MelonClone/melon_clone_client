package com.devgd.melonclone.domain.user.model;

import androidx.annotation.NonNull;

import com.devgd.melonclone.domain.user.domain.User;
import com.devgd.melonclone.global.model.domain.Message;
import com.devgd.melonclone.global.model.view.states.NetworkState;
import com.devgd.melonclone.global.model.repository.Repository;
import com.devgd.melonclone.utils.network.HttpManager;
import com.devgd.melonclone.utils.network.services.UserService;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository implements Repository {
    private static UserRepository instance;

    public static UserRepository getInstance() {
        if (instance == null)
            instance = new UserRepository();
        return instance;
    }

    private UserRepository() {
    }

    public void registerUser(User user, final RepoCallback<Message> repoCallback) {
        HttpManager.createService(UserService.class)
                .registerUser(user)
                .enqueue(new Callback<Message>() {
                    @Override
                    public void onResponse(@NonNull Call<Message> call, @NonNull Response<Message> response) {
                        if (response.isSuccessful()) {
                            repoCallback.success(response.body());
                            return;
                        }
                        repoCallback.fail(new NetworkState(response.code(), response.message()));
                    }

                    @Override
                    public void onFailure(@NonNull Call<Message> call, @NonNull Throwable t) {
                        repoCallback.fail(new NetworkState(0, t.toString()));
                    }
                });
    }

    public void loginUser(User user, final RepoCallback<Message> repoCallback) {
        HttpManager.createService(UserService.class)
                .loginUser(user)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        if (response.isSuccessful()
                                && response.body() != null) {
                            try {
                                repoCallback.success(new Message(response.body().string()));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return;
                        }
                        repoCallback.fail(new NetworkState(response.code(), response.message()));
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        repoCallback.fail(new NetworkState(0, t.toString()));
                    }
                });
    }

    public void getUser(final RepoCallback<User> repoCallback) {
        HttpManager.createService(UserService.class)
                .getUser()
                .enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                        if (response.isSuccessful()) {
                            repoCallback.success(response.body());
                            return;
                        }
                        repoCallback.fail(new NetworkState(response.code(), response.message()));
                    }

                    @Override
                    public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                        repoCallback.fail(new NetworkState(0, t.toString()));
                    }
                });
    }
}
