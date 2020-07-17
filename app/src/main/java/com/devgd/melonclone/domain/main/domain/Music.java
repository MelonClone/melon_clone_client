package com.devgd.melonclone.domain.main.domain;

import java.util.List;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class Music {
    final String musicId;
    final String musicTitleName;
    final Album album;
    final Artist artist;
    @Setter
    List<Lyric> musicLyricList;

}
