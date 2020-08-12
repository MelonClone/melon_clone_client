package com.devgd.melonclone.domain.player.model;

import com.devgd.melonclone.domain.player.domain.Music;
import com.devgd.melonclone.domain.player.domain.Player;
import com.devgd.melonclone.utils.store.MusicSample;
import com.devgd.melonclone.utils.store.PlayerSample;

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
        init();
    }

    public void init() {
        // TODO get Music from SharedPreference
        lastPlayedMusic = MusicSample.getSample();
    }

    public Player getPlayer() {
        // TODO getting player from DB
        return PlayerSample.getSample();
    }

    public Music getLastPlayedMusic() {
        if (lastPlayedMusic == null) {
            // TODO getting last played music
            return MusicSample.getSample();
        }
        return lastPlayedMusic;
    }
}
