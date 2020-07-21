package com.devgd.melonclone.domain.player.model;

import com.devgd.melonclone.domain.player.domain.Lyric;
import com.devgd.melonclone.utils.store.LyricSample;

import java.util.List;

public class LyricModel {
    Lyric lyric;

    public List<Lyric> getMusicLyrics(String musicId) {
        // TODO Lyric data by musicId from server

        return LyricSample.getSample();
    }
}
