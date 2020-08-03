package com.devgd.melonclone.domain.player.model;

import com.devgd.melonclone.domain.player.domain.Music;
import com.devgd.melonclone.global.model.view.states.NetworkState;
import com.devgd.melonclone.global.model.repository.Repository;
import com.devgd.melonclone.utils.network.HttpManager;
import com.devgd.melonclone.utils.network.services.MusicService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        HttpManager.createService(MusicService.class)
                .getMusic(music.getMusicId())
                .enqueue(new Callback<Music>() {
                    @Override
                    public void onResponse(Call<Music> call, Response<Music> response) {
                        if (response.isSuccessful())
                            repoCallback.success(response.body());
                        repoCallback.fail(new NetworkState(response.code(), response.message()));
                    }

                    @Override
                    public void onFailure(Call<Music> call, Throwable t) {
                        repoCallback.fail(new NetworkState(0, t.toString()));
                    }
                });
    }
}