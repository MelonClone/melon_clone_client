package com.devgd.melonclone.domain.main.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Player {
    int playerTheme;
    int playtime;
    int currentPlaytime;
    boolean isPlay;
    boolean isLike;
}
