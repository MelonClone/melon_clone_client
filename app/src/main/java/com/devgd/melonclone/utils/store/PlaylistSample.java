package com.devgd.melonclone.utils.store;

import com.devgd.melonclone.domain.player.domain.Album;
import com.devgd.melonclone.domain.player.domain.Artist;
import com.devgd.melonclone.domain.player.domain.Music;
import com.devgd.melonclone.domain.player.domain.Playlist;

import java.util.ArrayList;
import java.util.List;

public class PlaylistSample {

    public static List<Playlist> getSample() {
        ArrayList<Playlist> playlist = new ArrayList<>();
        playlist.add(new Playlist(new Music("abc1", "노래제목 테스트",
                new Album("앨범제목 테스트"),
                new Artist("아티스트 이름"))));
        playlist.add(new Playlist(new Music("abc2", "노래제목 테스트",
                new Album("앨범제목 테스트"),
                new Artist("아티스트 이름"))));
        playlist.add(new Playlist(new Music("abc3", "노래제목 테스트",
                new Album("앨범제목 테스트"),
                new Artist("아티스트 이름"))));
        playlist.add(new Playlist(new Music("abc4", "노래제목 테스트",
                new Album("앨범제목 테스트"),
                new Artist("아티스트 이름"))));
        playlist.add(new Playlist(new Music("abc5", "노래제목 테스트",
                new Album("앨범제목 테스트"),
                new Artist("아티스트 이름"))));
        playlist.add(new Playlist(new Music("abc6", "노래제목 테스트",
                new Album("앨범제목 테스트"),
                new Artist("아티스트 이름"))));
        playlist.add(new Playlist(new Music("abc7", "노래제목 테스트",
                new Album("앨범제목 테스트"),
                new Artist("아티스트 이름"))));
        playlist.add(new Playlist(new Music("abc8", "노래제목 테스트",
                new Album("앨범제목 테스트"),
                new Artist("아티스트 이름"))));
        playlist.add(new Playlist(new Music("abc9", "노래제목 테스트",
                new Album("앨범제목 테스트"),
                new Artist("아티스트 이름"))));
        playlist.add(new Playlist(new Music("abc1", "노래제목 테스트",
                new Album("앨범제목 테스트"),
                new Artist("아티스트 이름"))));

        return playlist;
    }
}
