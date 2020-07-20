package com.devgd.melonclone.domain.main.domain;

import com.devgd.melonclone.domain.model.Domain;

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
