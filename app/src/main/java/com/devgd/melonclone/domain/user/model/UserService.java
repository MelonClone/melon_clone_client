package com.devgd.melonclone.domain.user.model;

import com.devgd.melonclone.domain.user.domain.User;

import retrofit2.Call;
import retrofit2.http.GET;

public interface UserService {
    @GET("/user")
    Call<User> getUser( );
}
