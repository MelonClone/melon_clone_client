package com.devgd.melonclone.domain.player.domain;

import com.devgd.melonclone.global.model.domain.Domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Album implements Domain {
    private final String albumName;
    private final String albumJacketUrl;
}
