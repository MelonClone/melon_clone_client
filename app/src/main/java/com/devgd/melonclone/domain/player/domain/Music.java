package com.devgd.melonclone.domain.player.domain;

import com.devgd.melonclone.global.model.domain.Domain;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class Music implements Domain {
    final String musicId;
    final String musicTitleName;
    final Album album;
    final Artist artist;
    @Setter
    List<Lyric> musicLyricList;

}
