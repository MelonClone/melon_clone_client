package com.devgd.melonclone.utils.network;

import java.net.URL;

import okhttp3.OkHttpClient;
import okhttp3.internal.http.HttpMethod;
import retrofit2.Retrofit;

public class HttpManager {

    private static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static Retrofit.Builder getRetrofit(URL url) {
        return new Retrofit.Builder()
                .baseUrl(url.getHost());
    }

    public static <S> S createService(URL url, Class<S> serviceClass) {
        Retrofit retrofit = getRetrofit(url).client(httpClient.build()).build();
        return retrofit.create(serviceClass);
    }
}
