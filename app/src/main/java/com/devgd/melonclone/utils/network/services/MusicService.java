package com.devgd.melonclone.utils.network.services;

import com.devgd.melonclone.domain.player.domain.Lyric;
import com.devgd.melonclone.domain.player.domain.Music;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

import static com.devgd.melonclone.global.consts.ApiPreset.MUSIC_PRESET;

public interface MusicService {
    @Headers({
            "Accept: application/json",
            "User-Agent: MelonClone-App"
    })
    @GET(MUSIC_PRESET+"/{music_id}")
    Call<Music> getMusic(
            @Path("music_id") String musicId
    );

    @Headers({
            "Accept: application/json",
            "User-Agent: MelonClone-App"
    })
    @GET(MUSIC_PRESET+"/{music_id}/lyrics")
    Call<List<Lyric>> getLyrics(
            @Path("music_id") String musicId
    );

}
