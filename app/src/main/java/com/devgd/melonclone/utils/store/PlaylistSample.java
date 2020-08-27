package com.devgd.melonclone.utils.store;

import com.devgd.melonclone.domain.player.domain.Playlist;
import com.devgd.melonclone.domain.player.domain.PlaylistItem;

import java.util.ArrayList;
import java.util.LinkedList;

public class PlaylistSample {

    public static Playlist getSample() {
        LinkedList<PlaylistItem> playlistItem = new LinkedList<>();

        for (int i=0; i<13; i++) {
            if (i%2 == 0)
                playlistItem.add(new PlaylistItem(MusicSample.getSample()));
            else
                playlistItem.add(new PlaylistItem(MusicSample.getSample2()));
        }

        return new Playlist("MyPlaylist"+(long)(Math.random()*100), playlistItem);
    }
}
