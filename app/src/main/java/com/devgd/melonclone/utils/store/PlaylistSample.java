package com.devgd.melonclone.utils.store;

import com.devgd.melonclone.domain.player.domain.Playlist;

import java.util.ArrayList;
import java.util.List;

public class PlaylistSample {

    public static List<Playlist> getSample() {
        ArrayList<Playlist> playlist = new ArrayList<>();
        playlist.add(new Playlist(MusicSample.getSample()));
        playlist.add(new Playlist(MusicSample.getSample()));
        playlist.add(new Playlist(MusicSample.getSample()));
        playlist.add(new Playlist(MusicSample.getSample()));
        playlist.add(new Playlist(MusicSample.getSample()));
        playlist.add(new Playlist(MusicSample.getSample()));
        playlist.add(new Playlist(MusicSample.getSample()));
        playlist.add(new Playlist(MusicSample.getSample()));
        playlist.add(new Playlist(MusicSample.getSample()));
        playlist.add(new Playlist(MusicSample.getSample()));
        playlist.add(new Playlist(MusicSample.getSample()));
        playlist.add(new Playlist(MusicSample.getSample()));
        playlist.add(new Playlist(MusicSample.getSample()));
        playlist.add(new Playlist(MusicSample.getSample()));


        return playlist;
    }
}
