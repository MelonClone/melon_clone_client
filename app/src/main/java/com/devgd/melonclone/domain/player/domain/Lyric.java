package com.devgd.melonclone.domain.player.domain;

import com.devgd.melonclone.global.model.domain.Domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class Lyric implements Domain {
    int lyricId;
    String lyricMusicId;
    String lyricTxt;
    @Setter
    int lyricTime;
    @Setter
    boolean isPlay;

    public Lyric(String text) {
        this.lyricTxt = text;
    }

    public Lyric(String text, boolean isPlay) {
        this.lyricTxt = text;
        this.isPlay = isPlay;
    }
}
