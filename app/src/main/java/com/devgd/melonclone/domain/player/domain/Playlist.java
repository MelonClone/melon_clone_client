package com.devgd.melonclone.domain.player.domain;

import com.devgd.melonclone.global.model.domain.Domain;

import java.util.LinkedList;
import java.util.ListIterator;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@RequiredArgsConstructor
public class Playlist implements Domain {
    private int id;

    private PlaylistItem currentPlaylistItem;

    @Setter
    @NonNull
    private String playlistName;

    @NonNull
    private LinkedList<PlaylistItem> playlistItems;

    public PlaylistItem getCurrentPlaylistItem() {
        if (currentPlaylistItem == null) {
            currentPlaylistItem = playlistItems.getFirst();
        }
        // || currentPlaylistItem == playlistItems.getLast()
        return currentPlaylistItem;
    }

    public PlaylistItem next() {
        if (currentPlaylistItem == null || currentPlaylistItem == playlistItems.getLast()) {
            currentPlaylistItem = playlistItems.getFirst();
        } else {
            ListIterator<PlaylistItem> listIterator = playlistItems.listIterator(playlistItems.indexOf(currentPlaylistItem));
            listIterator.next(); // currentItem
            currentPlaylistItem = listIterator.next(); // nextItem
        }
        return currentPlaylistItem;
    }

    public PlaylistItem prev() {
        if (currentPlaylistItem == null) {
            currentPlaylistItem = playlistItems.getFirst();
        } else if (currentPlaylistItem == playlistItems.getFirst()) {
            currentPlaylistItem = playlistItems.getLast();
        } else {
            currentPlaylistItem = playlistItems.listIterator(playlistItems.indexOf(currentPlaylistItem)).previous();
        }
        return currentPlaylistItem;
    }
}
