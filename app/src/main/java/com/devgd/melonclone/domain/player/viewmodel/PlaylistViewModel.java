package com.devgd.melonclone.domain.player.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.devgd.melonclone.domain.player.domain.Album;
import com.devgd.melonclone.domain.player.domain.Artist;
import com.devgd.melonclone.domain.player.domain.Lyric;
import com.devgd.melonclone.domain.player.domain.Music;
import com.devgd.melonclone.domain.player.domain.Player;
import com.devgd.melonclone.domain.player.domain.Playlist;
import com.devgd.melonclone.domain.player.model.LyricModel;
import com.devgd.melonclone.domain.player.model.LyricRepository;
import com.devgd.melonclone.domain.player.model.MusicModel;
import com.devgd.melonclone.domain.player.model.MusicRepository;
import com.devgd.melonclone.domain.player.model.PlayerModel;
import com.devgd.melonclone.domain.user.view.activity.LoginActivity;
import com.devgd.melonclone.domain.user.view.activity.ProfileActivity;
import com.devgd.melonclone.global.model.repository.Repository;
import com.devgd.melonclone.global.model.view.states.NetworkState;
import com.devgd.melonclone.global.model.view.states.ViewState;
import com.devgd.melonclone.global.model.viewmodel.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

import static com.devgd.melonclone.global.model.view.states.StateCode.ACTIVITY_CHANGE;

public class PlaylistViewModel extends BaseViewModel {

    private MutableLiveData<List<Playlist>> mPlaylistList;

    // TODO DI or repository to singleton
//    private PlaylistRepository playlistRepository;

    @Override
    protected void init() {
//        playlistRepository = PlaylistRepository.getInstance();
    }

    public LiveData<List<Playlist>> getPlaylistList() {
        if (mPlaylistList == null) {
            mPlaylistList = new MutableLiveData<>();
            loadPlaylist();
        }
        return mPlaylistList;
    }

    private void loadPlaylist() {
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
                new Artist("아티스트 이름"))));playlist.add(new Playlist(new Music("abc1", "노래제목 테스트",
                new Album("앨범제목 테스트"),
                new Artist("아티스트 이름"))));


        mPlaylistList.postValue(playlist);
        /*playlistRepository.getAllPlaylist(new Repository.RepoListCallback<List<Playlist>>() {
            @Override
            public void success(List<Playlist> playlistList) {
                mPlaylistList.postValue(playlistList);
            }

            @Override
            public void fail(NetworkState networkState) {

            }
        });*/
    }
}
