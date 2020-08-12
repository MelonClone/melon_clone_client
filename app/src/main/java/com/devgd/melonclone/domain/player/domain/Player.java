package com.devgd.melonclone.domain.player.domain;

import com.devgd.melonclone.global.model.domain.Domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class Player implements Domain {
    @Setter
    @NonNull
    int playerTheme;
    @Setter
    @NonNull
    int playtime;
    @Setter
    @NonNull
    int currentPlaytime;
    @NonNull
    boolean isPlay;
    boolean isPlayed = false;

    public void setPlay(boolean isPlay) {
        this.isPlay = isPlay;
        this.isPlayed = true;
    }
}
