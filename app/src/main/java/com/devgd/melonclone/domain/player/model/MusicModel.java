package com.devgd.melonclone.domain.player.model;

import com.devgd.melonclone.domain.player.domain.Album;
import com.devgd.melonclone.domain.player.domain.Artist;
import com.devgd.melonclone.domain.player.domain.Music;

public class MusicModel {
    Music music;

    public Music getMusic() {
        if (music == null) {
            // TODO getting music from server
            music = new Music("", "퇴근버스", new Album("앨범 이름"), new Artist("아티스트 이름"));
        }
        return music;
    }
}