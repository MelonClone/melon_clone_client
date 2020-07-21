package com.devgd.melonclone.utils.network;

import com.devgd.melonclone.utils.network.services.UserService;

import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.devgd.melonclone.global.consts.Constants.API_PROTOCOL;
import static com.devgd.melonclone.global.consts.Constants.API_SERVER;

public class HttpManager {

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static Retrofit.Builder getRetrofit(String url) {
        return new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url);
    }

    public static <S> S createService(Class<S> serviceClass) {
        String url = API_PROTOCOL + API_SERVER;

        if (UserService.class.equals(serviceClass)) {
            url = API_PROTOCOL + API_SERVER;
        }

        Retrofit retrofit = getRetrofit(url).client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }
}
