package com.devgd.melonclone.utils.network.services;

import com.devgd.melonclone.domain.user.domain.User;

import retrofit2.Call;
import retrofit2.http.GET;

import static com.devgd.melonclone.global.consts.ApiPreset.USER_PRESET;

public interface UserService {
    @GET(USER_PRESET)
    Call<User> getUser();
}
