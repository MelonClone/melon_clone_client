package com.devgd.melonclone.domain.player.model;

import com.devgd.melonclone.domain.player.domain.Music;
import com.devgd.melonclone.domain.model.Repository;
import com.devgd.melonclone.utils.network.HttpManager;
import com.devgd.melonclone.utils.network.services.MusicService;

import java.net.MalformedURLException;
import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.devgd.melonclone.global.consts.Constants.API_SERVER;

public class MusicRepository implements Repository {

    private static MusicRepository instance = new MusicRepository();

    public static MusicRepository getInstance() {
        if (instance == null)
            instance = new MusicRepository();
        return instance;
    }

    private MusicRepository() {
    }

    public void getMusicInfo(Music music, final RepoCallback repoCallback) {
        try {
            HttpManager.createService(new URL(API_SERVER), MusicService.class)
                    .getMusic(music.getMusicId())
                    .enqueue(new Callback<Music>() {
                        @Override
                        public void onResponse(Call<Music> call, Response<Music> response) {
                            if (response.isSuccessful())
                                repoCallback.success(response.body());
                            repoCallback.fail();
                        }

                        @Override
                        public void onFailure(Call<Music> call, Throwable t) {
                            repoCallback.fail();
                        }
                    });

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
