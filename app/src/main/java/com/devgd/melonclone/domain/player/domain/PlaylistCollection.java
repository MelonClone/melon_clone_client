package com.devgd.melonclone.domain.player.domain;

import org.watermelon.framework.global.model.domain.Domain;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PlaylistCollection implements Domain {
    public static final String PLAYLIST_DEFAULT_KEY = "default";
    private Map<String, Playlist> playlistCollection = new HashMap<>();

    public Playlist getDefaultPlaylist() {
        return playlistCollection.get(PLAYLIST_DEFAULT_KEY);
    }

    public Playlist getFirstPlaylist() {
        Set<String> keys = playlistCollection.keySet();
        keys.remove(PLAYLIST_DEFAULT_KEY);
        return playlistCollection.get(keys.iterator().next());
    }

    public Playlist getPlaylist(String playlistName) {
        return playlistCollection.get(playlistName);
    }

    public void addPlaylist(Playlist playlist) {
        playlistCollection.put(playlist.getPlaylistName(), playlist);
    }
}
