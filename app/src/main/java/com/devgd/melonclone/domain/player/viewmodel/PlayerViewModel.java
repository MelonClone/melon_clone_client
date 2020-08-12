package com.devgd.melonclone.domain.player.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.devgd.melonclone.domain.player.domain.Lyric;
import com.devgd.melonclone.domain.player.domain.Music;
import com.devgd.melonclone.domain.player.domain.Player;
import com.devgd.melonclone.domain.player.model.LyricModel;
import com.devgd.melonclone.domain.player.model.LyricRepository;
import com.devgd.melonclone.domain.player.model.MusicModel;
import com.devgd.melonclone.domain.player.model.MusicRepository;
import com.devgd.melonclone.domain.player.model.PlayerModel;
import com.devgd.melonclone.domain.user.view.activity.LoginActivity;
import com.devgd.melonclone.domain.user.view.activity.ProfileActivity;
import com.devgd.melonclone.global.media.PlayManager;
import com.devgd.melonclone.global.media.MelonMediaPlayer;
import com.devgd.melonclone.global.model.view.states.NetworkState;
import com.devgd.melonclone.global.model.repository.Repository;
import com.devgd.melonclone.global.model.view.states.ViewState;
import com.devgd.melonclone.global.model.viewmodel.BaseViewModel;

import java.util.List;

import static com.devgd.melonclone.global.model.view.states.StateCode.ACTIVITY_CHANGE;

public class PlayerViewModel extends BaseViewModel {

    private MutableLiveData<Player> playerInfo;
    private MutableLiveData<Music> currentMusic;
    private MusicModel musicModel = new MusicModel();
    private LyricModel lyricModel = new LyricModel();

    // TODO DI or repository to singleton
    private MusicRepository musicRepository;
    private LyricRepository lyricRepository;
    private PlayerModel playerModel;

    // private MutableLiveData<List<Playlist>> playlistList;


    @Override
    protected void init() {
        musicRepository = MusicRepository.getInstance();
        lyricRepository = LyricRepository.getInstance();
        playerModel = PlayerModel.getInstance();
    }

    public LiveData<Player> getPlayer() {
        if (playerInfo == null) {
            playerInfo = new MutableLiveData<>();
            loadPlayer();
        }

        return playerInfo;
    }

    public LiveData<Music> getCurrentMusic() {

        if (currentMusic == null) {
            currentMusic = new MutableLiveData<>();
            if (playerModel.getLastPlayedMusic() != null)
                currentMusic.postValue(playerModel.getLastPlayedMusic());
        }

        return currentMusic;
    }

    private void loadPlayer() {
        playerInfo = new MutableLiveData<>();
        if (playerModel.getPlayer() != null) {
            playerInfo.postValue(playerModel.getPlayer());
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

    public void changeUserPage() {
        if (loginState.getValue() != null && loginState.getValue().isLogin()) {
            state.postValue(new ViewState(ACTIVITY_CHANGE, ProfileActivity.class, null));
        } else {
            state.postValue(new ViewState(ACTIVITY_CHANGE, LoginActivity.class, null));
        }
    }

    public void musicPlay() {
        if (playerInfo.getValue() != null) {
            if (playerInfo.getValue().isPlay()) {
                PlayManager.getInstance().pausePlayer();
                playerInfo.getValue().setPlay(false);
            } else if (playerInfo.getValue().isPlayed()) {
                PlayManager.getInstance().startPlayer();
                playerInfo.getValue().setPlay(true);
            } else {
                if (currentMusic.getValue() != null) {
                    MelonMediaPlayer mediaPlayer = new MelonMediaPlayer(currentMusic.getValue().getMusicUrl());
                    PlayManager.getInstance().setPlayer(mediaPlayer);
                    PlayManager.getInstance().startPlayer();
                }
                playerInfo.getValue().setPlay(true);
            }
            playerInfo.postValue(getPlayer().getValue());
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (!PlayManager.getInstance().isDestroyed()) {
            PlayManager.getInstance().destroyPlayer();
        }
    }
}
