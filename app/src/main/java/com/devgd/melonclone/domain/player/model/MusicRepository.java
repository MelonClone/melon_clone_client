package com.devgd.melonclone.domain.player.model;

import androidx.annotation.NonNull;

import com.devgd.melonclone.domain.player.domain.Music;
import com.devgd.melonclone.utils.network.services.MusicService;
import com.devgd.melonclone.utils.network.services.SampleService;

import org.watermelon.framework.global.model.domain.Domain;
import org.watermelon.framework.global.model.repository.Repository;
import org.watermelon.framework.global.model.view.states.NetworkState;
import org.watermelon.framework.utils.network.HttpManager;
import org.watermelon.framework.utils.network.httpconnection.HttpSender;

import java.net.HttpURLConnection;

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

    public void getMusicInfo(Music music, final RepoCallback<Music> repoCallback) {
        HttpManager.createService(MusicService.class)
                .getMusic(music.getMusicId())
                .enqueue(new Callback<Music>() {
                    @Override
                    public void onResponse(@NonNull Call<Music> call, @NonNull Response<Music> response) {
                        if (response.isSuccessful()) {
                            repoCallback.success(response.body());
                        } else {
                            repoCallback.fail(new NetworkState(response.code(), response.message()));
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<Music> call, @NonNull Throwable t) {
                        repoCallback.fail(new NetworkState(0, t.toString()));
                    }
                });
    }

    public void getMusicList(Music music, final RepoCallback<Music> repoCallback) {
        HttpManager.createService(SampleService.class)
                .getMusic()
                .execute(new HttpSender.Callback<Music>() {
                    @Override
                    public void onResponse(HttpURLConnection response, Music data) {
                        repoCallback.success(data);
                    }
                });
    }
}
