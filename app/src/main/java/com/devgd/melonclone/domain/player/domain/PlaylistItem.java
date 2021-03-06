package com.devgd.melonclone.domain.player.domain;

import org.watermelon.framework.global.model.domain.Domain;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PlaylistItem implements Domain {
    private int id;

    @NonNull
    private Music music;
}
