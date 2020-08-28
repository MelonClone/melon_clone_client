package com.devgd.melonclone.domain.player.model;

import com.devgd.melonclone.domain.player.domain.Playlist;
import com.devgd.melonclone.domain.player.domain.PlaylistCollection;
import com.devgd.melonclone.utils.store.PlayerSample;
import com.devgd.melonclone.utils.store.PlaylistSample;

public class PlaylistModel {

    private static PlaylistModel instance;

    // TODO remove
    private Playlist lastPlaylist;
    private PlaylistCollection playlistCollection;

    public static PlaylistModel getInstance() {
        if (instance == null) {
            instance = new PlaylistModel();
            instance.init();
        }
        return instance;
    }


    public void init() {
        // TODO getting playlist collections from DB
        playlistCollection = new PlaylistCollection();
        for (int i=0; i<10; i++) {
            Playlist list = PlaylistSample.getSample();
            if (i == 0) list.setPlaylistName("default");
            playlistCollection.addPlaylist(list);
        }
        lastPlaylist = playlistCollection.getDefaultPlaylist();
    }

    public PlaylistCollection getPlaylistCollection() {
        // TODO getting playlist collections from DB

        return playlistCollection;
    }

    public void setLastPlayed(Playlist playlist) {
        lastPlaylist = playlist;
    }

    public Playlist getLastPlaylist() {
        // TODO getting last playlist from db
        return lastPlaylist;
    }
}
