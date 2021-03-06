package com.devgd.melonclone.domain.player.domain;

import org.watermelon.framework.global.media.player.MusicPlayer;
import org.watermelon.framework.global.model.domain.Domain;

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
    int currentPlaytime;
    @NonNull
    boolean isPlay;
    boolean isPlayed = false;
    @Setter
    Mode playMode = new Mode(MusicPlayer.Repeat.ALL_LOOP, false, 0, 0);

    public void setPlay(boolean isPlay) {
        this.isPlay = isPlay;
        this.isPlayed = true;
    }

    public void resetPlayer() {
        this.isPlay = false;
        this.isPlayed = false;
    }
}
