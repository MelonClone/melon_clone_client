package com.devgd.melonclone.domain.player.model;

import com.devgd.melonclone.domain.player.domain.Album;
import com.devgd.melonclone.domain.player.domain.Artist;
import com.devgd.melonclone.domain.player.domain.Music;
import com.devgd.melonclone.utils.store.MusicSample;

public class MusicModel {
    Music music;

    public Music getMusic() {
        if (music == null) {
            // TODO getting music from server
            music = MusicSample.getSample();
        }
        return music;
    }
}
