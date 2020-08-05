package com.devgd.melonclone.domain.player.model;

import com.devgd.melonclone.domain.player.domain.Album;
import com.devgd.melonclone.domain.player.domain.Artist;
import com.devgd.melonclone.domain.player.domain.Music;
import com.devgd.melonclone.domain.player.domain.Player;
import com.devgd.melonclone.utils.store.MusicSample;

public class PlayerModel {
    private Player player;
    private Music lastPlayedMusic;

    private static PlayerModel instance;

    public static PlayerModel getInstance() {
        if (instance == null) {
            instance = new PlayerModel();
            instance.init();
        }
        return instance;
    }

    private PlayerModel() {
    }

    public void init() {
        // TODO get Music from SharedPreference
        lastPlayedMusic = MusicSample.getSample();
    }

    public Player getPlayer() {
        // TODO getting player from server
        return player;
    }

    public Music getLastPlayedMusic() {
        if (lastPlayedMusic == null) {
            return null;
        }
        return lastPlayedMusic;
    }
}
