package com.devgd.melonclone.utils.store;

import com.devgd.melonclone.domain.player.domain.Album;
import com.devgd.melonclone.domain.player.domain.Artist;
import com.devgd.melonclone.domain.player.domain.Music;
import com.devgd.melonclone.domain.player.domain.Playlist;

import java.util.ArrayList;
import java.util.List;

public class MusicSample {

    private static final String tempMusic1 =
//             "https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8";
            "https://content.jwplatform.com/manifests/yp34SRmf.m3u8";
//             "https://d2zihajmogu5jn.cloudfront.net/bipbop-advanced/bipbop_16x9_variant.m3u8";
//             "http://184.72.239.149/vod/smil:BigBuckBunny.smil/playlist.m3u8";
    
    public static Music getSample() {
        return new Music("abc1", "노래제목 테스트", tempMusic1,
            new Album("앨범제목 테스트", "https://images.unsplash.com/photo-1494253109108-2e30c049369b?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1050&q=80"),
            new Artist("아티스트 이름"), false, false);
    }

    public static List<Music> getSampleList() {
        ArrayList<Music> musicList = new ArrayList<>();

        for (int i=0; i<20; i++) {
            musicList.add(new Music("abc1", "노래제목 테스트", tempMusic1,
                    new Album("앨범제목 테스트", "https://images.unsplash.com/photo-1494253109108-2e30c049369b?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1050&q=80"),
                    new Artist("아티스트 이름"), false, false));
        }

        return musicList;
    }
}
