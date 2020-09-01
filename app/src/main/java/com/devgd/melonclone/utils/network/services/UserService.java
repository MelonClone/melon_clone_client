package com.devgd.melonclone.utils.network.services;

import com.devgd.melonclone.domain.user.domain.User;

import org.watermelon.framework.global.model.domain.Message;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

import static com.devgd.melonclone.global.consts.ApiPreset.USER_PRESET;

public interface UserService {
    @GET(USER_PRESET)
    Call<User> getUser();

    @POST(USER_PRESET+"/regist")
    Call<Message> registerUser(
            @Body User user
    );

    @POST(USER_PRESET+"/login")
    Call<ResponseBody> loginUser(
            @Body User user
    );
}
