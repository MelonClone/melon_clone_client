package com.devgd.melonclone.domain.player.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.devgd.melonclone.domain.player.domain.Lyric;
import com.devgd.melonclone.domain.player.domain.Music;
import com.devgd.melonclone.domain.player.domain.Playlist;
import com.devgd.melonclone.domain.player.domain.PlaylistCollection;
import com.devgd.melonclone.domain.player.domain.PlaylistItem;
import com.devgd.melonclone.domain.player.model.LyricModel;
import com.devgd.melonclone.domain.player.model.LyricRepository;
import com.devgd.melonclone.domain.player.model.MusicModel;
import com.devgd.melonclone.domain.player.model.MusicRepository;
import com.devgd.melonclone.domain.player.model.PlaylistModel;
import com.devgd.melonclone.global.media.PlayManager;
import com.devgd.melonclone.global.media.player.ExoMediaPlayer;
import com.devgd.melonclone.global.media.player.MusicPlayer;
import com.devgd.melonclone.global.model.repository.Repository;
import com.devgd.melonclone.global.model.view.states.NetworkState;
import com.devgd.melonclone.global.model.viewmodel.BaseViewModel;
import com.devgd.melonclone.utils.store.PlaylistSample;

import java.util.LinkedList;
import java.util.List;

public class MusicViewModel extends BaseViewModel {

    private String currentPlaylistName;
    private MutableLiveData<Music> currentMusic;
    private MutableLiveData<PlaylistCollection> playlistCollection;

    // TODO DI or repository to singleton
    private MusicRepository musicRepository;
    private LyricRepository lyricRepository;
    //    private PlaylistRepository playlistRepository;
    private MusicModel musicModel;
    private PlaylistModel playlistModel;
    private LyricModel lyricModel = new LyricModel();

    @Override
    protected void init() {
        musicRepository = MusicRepository.getInstance();
        lyricRepository = LyricRepository.getInstance();
//        playlistRepository = PlaylistRepository.getInstance();
        musicModel = MusicModel.getInstance();
        playlistModel = PlaylistModel.getInstance();
        loadMusic();
        loadLyric();
        loadPlaylistCollection();
    }

    public LiveData<Music> getCurrentMusic() {

        if (currentMusic == null) {
            currentMusic = new MutableLiveData<>();
            loadMusic();
        }

        return currentMusic;
    }

    public List<PlaylistItem> getCurrentPlaylist() {
        if (playlistCollection.getValue() != null) {
            Playlist playlist = playlistCollection.getValue().getPlaylist(playlistModel.getLastPlaylist().getPlaylistName());
            if (playlist != null) {
                return playlist.getPlaylistItems();
            }
        }
        return new LinkedList<>();
    }

    public LiveData<PlaylistCollection> getPlaylistCollection() {
        if (playlistCollection == null) {
            playlistCollection = new MutableLiveData<>();
            loadPlaylistCollection();
        }
        return playlistCollection;
    }


    private void loadMusic() {
        currentMusic = new MutableLiveData<>();
        if (musicModel.getLastPlayedMusic() != null) {

            currentMusic.postValue(playlistModel.getLastPlaylist().getCurrentPlaylistItem().getMusic());
        }
    }

    private void loadMusic(Music music) {
        musicRepository.getMusicInfo(music, new Repository.RepoCallback<Music>() {
            @Override
            public void success(Music music) {
                currentMusic.postValue(music);
                loadLyric();
            }

            @Override
            public void fail(NetworkState networkState) {

            }
        });

    }

    private void loadLyric() {
        if (currentMusic.getValue() != null) {
            lyricRepository.getLyricList(currentMusic.getValue(), new Repository.RepoListCallback<List<Lyric>>() {
                @Override
                public void success(List<Lyric> lyrics) {
                    currentMusic.getValue().setMusicLyricList(lyrics);
                }

                @Override
                public void fail(NetworkState networkState) {

                }
            });
        }
    }

    private void loadPlaylistCollection() {
        playlistCollection = new MutableLiveData<>();
        playlistCollection.setValue(playlistModel.getPlaylistCollection());

        // TODO get Last played playlist name
        currentPlaylistName = playlistModel.getPlaylistCollection().getDefaultPlaylist().getPlaylistName();


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

    // TODO Open interface
    public void play(Context context) {
        if (currentMusic.getValue() != null &&
                !(PlayManager.getInstance().isPrepared() || PlayManager.getInstance().isPlaying())) {
            MusicPlayer mediaPlayer = new ExoMediaPlayer(currentMusic.getValue().getMusicUrl(), 1f, context);
            PlayManager.getInstance().setPlayer(mediaPlayer);
            PlayManager.getInstance().addMusicChangedListener(this::getNextMusic);
            PlayManager.getInstance().startPlayer();
        }
    }

    public void getNextMusic() {
        if (playlistCollection.getValue() != null) {
            Playlist currentPlaylist = playlistCollection.getValue().getPlaylist(currentPlaylistName);
            playlistModel.setLastPlayed(currentPlaylist);
            currentMusic.postValue(currentPlaylist.next().getMusic());
        }
    }

    public void getPrevMusic() {
        if (PlayManager.getInstance().getCurrentPosition() < 3000) {
            if (playlistCollection.getValue() != null) {
                Playlist currentPlaylist = playlistCollection.getValue()
                        .getPlaylist(currentPlaylistName);
                playlistModel.setLastPlayed(currentPlaylist);
                currentMusic.postValue(currentPlaylist.prev().getMusic());
            }
        } else {
            PlayManager.getInstance().resetPlayer();
            PlayManager.getInstance().startPlayer();
        }
    }
}
