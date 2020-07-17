package com.devgd.melonclone.domain.main.model;

import com.devgd.melonclone.domain.main.domain.Album;
import com.devgd.melonclone.domain.main.domain.Artist;
import com.devgd.melonclone.domain.main.domain.Music;

public class MusicModel {
    Music music;

    public Music getMusic() {
        if (music == null) {
            // TODO getting music from server
            music = new Music("", "퇴근버스", new Album(), new Artist());
        }
        return music;
    }
}
