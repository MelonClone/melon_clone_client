package com.devgd.melonclone.domain.player.model;

import com.devgd.melonclone.domain.player.domain.Lyric;
import com.devgd.melonclone.domain.player.domain.Music;
import com.devgd.melonclone.global.model.view.states.NetworkState;
import com.devgd.melonclone.global.model.repository.Repository;
import com.devgd.melonclone.utils.network.HttpManager;
import com.devgd.melonclone.utils.network.services.MusicService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        HttpManager.createService(MusicService.class)
                .getLyrics(music.getMusicId())
                .enqueue(new Callback<List<Lyric>>() {
                    @Override
                    public void onResponse(Call<List<Lyric>> call, Response<List<Lyric>> response) {
                        if (response.isSuccessful())
                            repoCallback.success(response.body());
                        repoCallback.fail(new NetworkState(response.code(), response.message()));
                    }

                    @Override
                    public void onFailure(Call<List<Lyric>> call, Throwable t) {
                        repoCallback.fail(new NetworkState(0, t.toString()));
                    }
                });
    }
}
