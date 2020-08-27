package com.devgd.melonclone.domain.player.viewmodel;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.devgd.melonclone.domain.player.domain.Lyric;
import com.devgd.melonclone.domain.player.domain.Music;
import com.devgd.melonclone.domain.player.domain.Playlist;
import com.devgd.melonclone.domain.player.domain.PlaylistItem;
import com.devgd.melonclone.domain.player.model.LyricModel;
import com.devgd.melonclone.domain.player.model.LyricRepository;
import com.devgd.melonclone.domain.player.model.MusicModel;
import com.devgd.melonclone.domain.player.model.MusicRepository;
import com.devgd.melonclone.global.media.PlayManager;
import com.devgd.melonclone.global.media.player.ExoMediaPlayer;
import com.devgd.melonclone.global.media.player.MusicPlayer;
import com.devgd.melonclone.global.model.repository.Repository;
import com.devgd.melonclone.global.model.view.states.NetworkState;
import com.devgd.melonclone.global.model.viewmodel.BaseViewModel;
import com.devgd.melonclone.utils.store.PlaylistSample;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MusicViewModel extends BaseViewModel {

    private String currentPlaylistName;
    private PlaylistItem currentPlaylistItem;
    private MutableLiveData<Music> currentMusic;
    private MutableLiveData<Map<String, Playlist>> playlistCollection;

    // TODO DI or repository to singleton
    private MusicRepository musicRepository;
    private LyricRepository lyricRepository;
    //    private PlaylistRepository playlistRepository;
    private MusicModel musicModel;
    private LyricModel lyricModel = new LyricModel();

    // private MutableLiveData<List<Playlist>> playlistList;

    @Override
    protected void init() {
        musicRepository = MusicRepository.getInstance();
        lyricRepository = LyricRepository.getInstance();
//        playlistRepository = PlaylistRepository.getInstance();
        musicModel = MusicModel.getInstance();
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
            Playlist playlist = playlistCollection.getValue().get(currentPlaylistName);
            if (playlist != null) {
                return playlist.getPlaylistItems();
            }
        }
        return new LinkedList<>();
    }

    public LiveData<Map<String, Playlist>> getPlaylistCollection() {
        if (playlistCollection == null) {
            playlistCollection = new MutableLiveData<>();
            loadPlaylistCollection();
        }
        return playlistCollection;
    }


    private void loadMusic() {
        currentMusic = new MutableLiveData<>();
        if (musicModel.getLastPlayedMusic() != null)
            currentMusic.postValue(musicModel.getLastPlayedMusic());
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
        Map<String, Playlist> customPlaylist = new HashMap<>();
        for (int i=0; i<10; i++) {
            Playlist list = PlaylistSample.getSample();
            if (i == 0) list.setPlaylistName("default");
            customPlaylist.put(list.getPlaylistName(), list);
        }
        playlistCollection.setValue(customPlaylist);
        currentPlaylistName = "default";
        getLastPlayMusic(customPlaylist);


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
            PlayManager.getInstance().addMusicChangedListener(() -> {
                if (playlistCollection.getValue() != null) {
//                    currentMusic.postValue(getNextMusic(playlistCollection.getValue()));
                }
            });
            PlayManager.getInstance().startPlayer();
        }
    }

    private Music getLastPlayMusic(Map<String, Playlist> playlistCollection) {
        Playlist playlist = playlistCollection.get(currentPlaylistName);
        if (playlist != null) {
            LinkedList<PlaylistItem> playlistItems = playlist.getPlaylistItems();
            if (currentPlaylistItem == null || currentPlaylistItem == playlistItems.getLast()) {
                currentPlaylistItem = playlistItems.getFirst();
            }

            return currentPlaylistItem.getMusic();
        } else {
            return null;
        }
    }

    // TODO Executed by View to change music
    private Music getNextMusic(Map<String, Playlist> playlistCollection) {
        Playlist playlist = playlistCollection.get(currentPlaylistName);
        if (playlist != null) {
            LinkedList<PlaylistItem> playlistItems = playlist.getPlaylistItems();
            PlaylistItem playlistItem;
            if (currentPlaylistItem == null || currentPlaylistItem == playlistItems.getLast()) {
                playlistItem = playlistItems.getFirst();
            } else {
                playlistItem = playlistItems.listIterator(playlistItems.indexOf(currentPlaylistItem)).next();
            }
            currentPlaylistItem = playlistItem;
            currentMusic.postValue(playlistItem.getMusic());
            return currentPlaylistItem.getMusic();
        } else {
            return null;
        }
    }
}
