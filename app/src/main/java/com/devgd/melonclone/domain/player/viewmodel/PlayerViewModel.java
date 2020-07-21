package com.devgd.melonclone.domain.player.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devgd.melonclone.domain.player.domain.Lyric;
import com.devgd.melonclone.domain.player.domain.Music;
import com.devgd.melonclone.domain.player.domain.Player;
import com.devgd.melonclone.domain.player.model.LyricModel;
import com.devgd.melonclone.domain.player.model.LyricRepository;
import com.devgd.melonclone.domain.player.model.MusicModel;
import com.devgd.melonclone.domain.player.model.MusicRepository;
import com.devgd.melonclone.domain.player.model.PlayerModel;
import com.devgd.melonclone.domain.model.Repository;

import java.util.List;

public class PlayerViewModel extends ViewModel {

    private MutableLiveData<Player> playerList;
    private MutableLiveData<Music> currentMusic;
    private PlayerModel playerModel;
    private MusicModel musicModel = new MusicModel();
    private LyricModel lyricModel = new LyricModel();

    // TODO DI or repository to singleton
    private MusicRepository musicRepository;
    private LyricRepository lyricRepository;

    // private MutableLiveData<List<Playlist>> playlistList;

    public void init() {
        musicRepository = MusicRepository.getInstance();
        lyricRepository = LyricRepository.getInstance();
        playerModel = PlayerModel.getInstance();
    }

    public LiveData<Player> getPlayer() {
        if (playerList == null) {
            playerList = new MutableLiveData<>();
            loadPlayer();
        }

        return playerList;
    }

    public LiveData<Music> getCurrentMusic() {

        if (playerModel.getLastPlayedMusic() == null) {
            return currentMusic;
        } else if (currentMusic == null) {
            currentMusic = new MutableLiveData<>();
            loadMusic(playerModel.getLastPlayedMusic());
        }

        return currentMusic;
    }

    public LiveData<Music> getMusic(String musicId) {
        if (currentMusic == null) {
            currentMusic = new MutableLiveData<>();
            loadMusic(new Music(musicId,"", null, null));
        }

        return currentMusic;
    }

    private void loadPlayer() {
        playerList = new MutableLiveData<>(playerModel.getPlayer());
    }

    private void loadMusic(Music music) {
        musicRepository.getMusicInfo(music, new Repository.RepoCallback<Music>() {
            @Override
            public void success(Music music) {
                currentMusic.postValue(music);
                loadLyric();
            }

            @Override
            public void fail() {

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
                public void fail() {

                }
            });
        }
    }
}
