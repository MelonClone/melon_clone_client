package com.devgd.melonclone.domain.player.model;

import com.devgd.melonclone.domain.player.domain.Lyric;
import com.devgd.melonclone.domain.player.domain.Music;
import com.devgd.melonclone.domain.model.Repository;
import com.devgd.melonclone.utils.network.HttpManager;
import com.devgd.melonclone.utils.network.services.MusicService;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.devgd.melonclone.global.consts.Constants.API_SERVER;

public class LyricRepository implements Repository {

    private static LyricRepository instance = new LyricRepository();

    public static LyricRepository getInstance() {
        if (instance == null)
            instance = new LyricRepository();
        return instance;
    }

    private LyricRepository() {
    }


    public void getLyricList(Music music, final RepoListCallback repoCallback) {
        try {
            HttpManager.createService(new URL(API_SERVER), MusicService.class)
                    .getLyrics(music.getMusicId())
                    .enqueue(new Callback<List<Lyric>>() {
                        @Override
                        public void onResponse(Call<List<Lyric>> call, Response<List<Lyric>> response) {
                            if (response.isSuccessful())
                                repoCallback.success(response.body());
                            repoCallback.fail();
                        }

                        @Override
                        public void onFailure(Call<List<Lyric>> call, Throwable t) {
                            repoCallback.fail();
                        }
                    });

        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }
}
