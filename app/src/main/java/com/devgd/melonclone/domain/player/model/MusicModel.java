package com.devgd.melonclone.domain.player.model;

import com.devgd.melonclone.domain.player.domain.Music;
import com.devgd.melonclone.utils.store.MusicSample;

public class MusicModel {
    Music music;
    private Music lastPlayedMusic;

    private static MusicModel instance;

    public static MusicModel getInstance() {
        if (instance == null) {
            instance = new MusicModel();
            instance.init();
        }
        return instance;
    }

    private MusicModel() {
        init();
    }

    public void init() {
        // TODO get Music from SharedPreference
        lastPlayedMusic = MusicSample.getSample();
    }

    public Music getMusic() {
        if (music == null) {
            // TODO getting music from server
            music = MusicSample.getSample();
        }
        return music;
    }

    public Music getLastPlayedMusic() {
        if (lastPlayedMusic == null) {
            // TODO getting last played music
            return MusicSample.getSample();
        }
        return lastPlayedMusic;
    }
}
