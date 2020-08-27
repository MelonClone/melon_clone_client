package com.devgd.melonclone.domain.player.viewmodel;

import android.content.Context;
import android.view.TextureView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.devgd.melonclone.domain.player.domain.Music;
import com.devgd.melonclone.domain.player.domain.Player;
import com.devgd.melonclone.domain.player.domain.Playlist;
import com.devgd.melonclone.domain.player.domain.PlaylistItem;
import com.devgd.melonclone.domain.player.model.MusicModel;
import com.devgd.melonclone.domain.player.model.PlayerModel;
import com.devgd.melonclone.domain.user.view.activity.LoginActivity;
import com.devgd.melonclone.domain.user.view.activity.ProfileActivity;
import com.devgd.melonclone.global.media.PlayManager;
import com.devgd.melonclone.global.media.Playable;
import com.devgd.melonclone.global.media.player.ExoMediaPlayer;
import com.devgd.melonclone.global.media.player.MusicPlayer;
import com.devgd.melonclone.global.model.view.states.ViewState;
import com.devgd.melonclone.global.model.viewmodel.BaseViewModel;

import java.util.LinkedList;
import java.util.Map;

import static com.devgd.melonclone.global.model.view.states.StateCode.ACTIVITY_CHANGE;

public class PlayerViewModel extends BaseViewModel implements Playable {

    private MutableLiveData<Player> playerInfo;

    // TODO DI or repository to singleton
    private PlayerModel playerModel;
    private MusicModel musicModel;



    @Override
    protected void init() {
        playerModel = PlayerModel.getInstance();
        musicModel = MusicModel.getInstance();

        loadPlayer();
    }

    public LiveData<Player> getPlayer() {
        if (playerInfo == null) {
            playerInfo = new MutableLiveData<>();
            loadPlayer();
        }

        return playerInfo;
    }

    private void loadPlayer() {
        playerInfo = new MutableLiveData<>();
        if (playerModel.getPlayer() != null) {
            playerInfo.postValue(playerModel.getPlayer());
        }
    }
    public void changeUserPage() {
        if (loginState.getValue() != null && loginState.getValue().isLogin()) {
            state.postValue(new ViewState(ACTIVITY_CHANGE, ProfileActivity.class, null));
        } else {
            state.postValue(new ViewState(ACTIVITY_CHANGE, LoginActivity.class, null));
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
//        musicStop();
    }

    @Override
    public void mediaPlay(Context context, Music music, TextureView view) {
        Player playerInfo = getPlayer().getValue();

        if (playerInfo != null) {
            if (playerInfo.isPlay()) {
                PlayManager.getInstance().pausePlayer();
                playerInfo.setPlay(false);
            } else if (playerInfo.isPlayed()) {
                PlayManager.getInstance().startPlayer();
                playerInfo.setPlay(true);
            } else {
                if (!(PlayManager.getInstance().isPrepared() || PlayManager.getInstance().isPlaying())) {
                    MusicPlayer mediaPlayer = new ExoMediaPlayer(musicModel.getMusic().getMusicUrl(), 1f, context);
                    PlayManager.getInstance().setPlayer(mediaPlayer);
                    PlayManager.getInstance().addMusicChangedListener(() -> {
                        // TODO MusicChange
                        // TODO callback event to View and execute next
                    });
                    PlayManager.getInstance().startPlayer();
                }
                playerInfo.setPlay(true);
            }
            this.playerInfo.postValue(getPlayer().getValue());
        }
    }

    @Override
    public void mediaStop() {
        // Player 종료
        if (!PlayManager.getInstance().isDestroyed()) {
            PlayManager.getInstance().destroyPlayer();
        }
    }

    public void changeRepeatMode() {
        Player repeatPlayer = playerInfo.getValue();
        if (repeatPlayer == null ) {
            loadPlayer();
        } else {
            if (repeatPlayer.getPlayMode().getRepeatMode() == MusicPlayer.Repeat.ALL_LOOP) {
                repeatPlayer.getPlayMode().setRepeatMode(MusicPlayer.Repeat.OFF);
            } else {
                repeatPlayer.getPlayMode().setRepeatMode(MusicPlayer.Repeat.ALL_LOOP);
            }
            playerInfo.postValue(repeatPlayer);
            PlayManager.getInstance().setRepeatMode(repeatPlayer.getPlayMode().getRepeatMode());
        }
    }

    @Override
    public boolean isPlay() {
        return PlayManager.getInstance().isPlaying();
    }

    @Override
    public long getCurrentPosition() {
        return PlayManager.getInstance().getCurrentPosition();
    }
}
