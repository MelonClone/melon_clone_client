package com.devgd.melonclone.domain.player.domain;

import org.watermelon.framework.global.media.Media;
import org.watermelon.framework.global.model.domain.Domain;

import java.util.List;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class Music implements Domain, Media {
    final String musicId;
    final String musicTitleName;
    final String musicUrl;
    final Album album;
    final Artist artist;
    final boolean isLike;
    final boolean isMV;
    @Setter
    @NonNull
    int playtime;
    @Setter
    List<Lyric> musicLyricList;

}
