package com.devgd.melonclone.domain.player.model;

import com.devgd.melonclone.domain.player.domain.Album;
import com.devgd.melonclone.domain.player.domain.Artist;
import com.devgd.melonclone.domain.player.domain.Music;
import com.devgd.melonclone.domain.player.domain.Player;

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
        lastPlayedMusic = new Music("", "퇴근버스", new Album("앨범 이름"), new Artist("아티스트 이름"));
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
