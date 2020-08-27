package com.devgd.melonclone.domain.player.domain;

import com.devgd.melonclone.global.model.domain.Domain;

import java.util.LinkedList;
import java.util.List;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class Playlist implements Domain {
    private int id;
    @Setter
    @NonNull
    private String playlistName;

    @NonNull
    private LinkedList<PlaylistItem> playlistItems;
}
