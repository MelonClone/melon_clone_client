package com.devgd.melonclone.domain.main.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.devgd.melonclone.domain.main.domain.Music;
import com.devgd.melonclone.domain.main.domain.Player;
import com.devgd.melonclone.domain.main.model.LyricModel;
import com.devgd.melonclone.domain.main.model.MusicModel;
import com.devgd.melonclone.domain.main.model.PlayerModel;

public class PlayerViewModel extends ViewModel {

    private MutableLiveData<Player> playerList;
    private MutableLiveData<Music> currentMusic;
    private PlayerModel playerModel = new PlayerModel();
    private MusicModel musicModel = new MusicModel();
    private LyricModel lyricModel = new LyricModel();

    // private MutableLiveData<List<Playlist>> playlistList;

    public LiveData<Player> getPlayer() {
        if (playerList == null) {
            playerList = new MutableLiveData<>();
            loadPlayer();
        }

        return playerList;
    }

    public LiveData<Music> getMusic() {
        if (currentMusic == null) {
            currentMusic = new MutableLiveData<>();
            loadMusic();
        }

        return currentMusic;
    }

    private void loadPlayer() {
        playerList = new MutableLiveData<>(playerModel.getPlayer());
    }

    private void loadMusic() {
        currentMusic = new MutableLiveData<>(musicModel.getMusic());
        setLyric(currentMusic.getValue());
    }

    private void setLyric(Music music) {
//        music.getMusicLyricList(); // .setLyric();
        music.setMusicLyricList(lyricModel.getMusicLyrics(music.getMusicId()));
    }
}
