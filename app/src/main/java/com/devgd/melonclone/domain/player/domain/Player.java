package com.devgd.melonclone.domain.player.domain;

import com.devgd.melonclone.global.model.domain.Domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Player implements Domain {
    int playerTheme;
    int playtime;
    int currentPlaytime;
    boolean isPlay;
    boolean isLike;
}
