package com.devgd.melonclone.utils.store;

import com.devgd.melonclone.domain.player.domain.Album;
import com.devgd.melonclone.domain.player.domain.Artist;
import com.devgd.melonclone.domain.player.domain.Music;
import com.devgd.melonclone.domain.player.domain.Playlist;

import java.util.ArrayList;
import java.util.List;

public class MusicSample {

    public static Music getSample() {
        return new Music("abc1", "노래제목 테스트",
            new Album("앨범제목 테스트", "https://images.unsplash.com/photo-1494253109108-2e30c049369b?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1050&q=80"),
            new Artist("아티스트 이름"));
    }

    public static List<Music> getSampleList() {
        ArrayList<Music> musicList = new ArrayList<>();
        musicList.add(new Music("abc1", "노래제목 테스트",
                new Album("앨범제목 테스트", "https://images.unsplash.com/photo-1494253109108-2e30c049369b?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1050&q=80"),
                new Artist("아티스트 이름")));
        musicList.add(new Music("abc1", "노래제목 테스트",
                new Album("앨범제목 테스트", "https://images.unsplash.com/photo-1494253109108-2e30c049369b?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1050&q=80"),
                new Artist("아티스트 이름")));
        musicList.add(new Music("abc1", "노래제목 테스트",
                new Album("앨범제목 테스트", "https://images.unsplash.com/photo-1494253109108-2e30c049369b?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1050&q=80"),
                new Artist("아티스트 이름")));
        musicList.add(new Music("abc1", "노래제목 테스트",
                new Album("앨범제목 테스트", "https://images.unsplash.com/photo-1494253109108-2e30c049369b?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1050&q=80"),
                new Artist("아티스트 이름")));
        musicList.add(new Music("abc1", "노래제목 테스트",
                new Album("앨범제목 테스트", "https://images.unsplash.com/photo-1494253109108-2e30c049369b?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1050&q=80"),
                new Artist("아티스트 이름")));
        musicList.add(new Music("abc1", "노래제목 테스트",
                new Album("앨범제목 테스트", "https://images.unsplash.com/photo-1494253109108-2e30c049369b?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1050&q=80"),
                new Artist("아티스트 이름")));
        musicList.add(new Music("abc1", "노래제목 테스트",
                new Album("앨범제목 테스트", "https://images.unsplash.com/photo-1494253109108-2e30c049369b?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1050&q=80"),
                new Artist("아티스트 이름")));
        musicList.add(new Music("abc1", "노래제목 테스트",
                new Album("앨범제목 테스트", "https://images.unsplash.com/photo-1494253109108-2e30c049369b?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1050&q=80"),
                new Artist("아티스트 이름")));
        musicList.add(new Music("abc1", "노래제목 테스트",
                new Album("앨범제목 테스트", "https://images.unsplash.com/photo-1494253109108-2e30c049369b?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1050&q=80"),
                new Artist("아티스트 이름")));
        musicList.add(new Music("abc1", "노래제목 테스트",
                new Album("앨범제목 테스트", "https://images.unsplash.com/photo-1494253109108-2e30c049369b?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1050&q=80"),
                new Artist("아티스트 이름")));
        musicList.add(new Music("abc1", "노래제목 테스트",
                new Album("앨범제목 테스트", "https://images.unsplash.com/photo-1494253109108-2e30c049369b?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1050&q=80"),
                new Artist("아티스트 이름")));
        musicList.add(new Music("abc1", "노래제목 테스트",
                new Album("앨범제목 테스트", "https://images.unsplash.com/photo-1494253109108-2e30c049369b?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1050&q=80"),
                new Artist("아티스트 이름")));

        return musicList;
    }
}
