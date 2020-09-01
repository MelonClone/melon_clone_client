package com.devgd.melonclone.domain.player.domain;

import org.watermelon.framework.global.model.domain.Domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Artist implements Domain {
    private final String artistName;
}
