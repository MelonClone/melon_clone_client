package com.devgd.melonclone.domain.player.domain;

import org.watermelon.framework.global.media.player.MusicPlayer;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Mode {
    MusicPlayer.Repeat repeatMode;
    boolean shuffle;
    int aac;
    int equalize;
}
