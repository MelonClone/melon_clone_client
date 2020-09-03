package com.devgd.melonclone.domain.player.viewmodel;

import android.content.Context;
import android.view.TextureView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.devgd.melonclone.domain.player.domain.Music;
import com.devgd.melonclone.domain.player.domain.Player;
import com.devgd.melonclone.domain.player.model.MusicModel;
import com.devgd.melonclone.domain.player.model.PlayerModel;
import com.devgd.melonclone.domain.user.view.activity.LoginActivity;
import com.devgd.melonclone.domain.user.view.activity.ProfileActivity;
import com.devgd.melonclone.global.model.viewmodel.MelonCloneBaseViewModel;

import org.watermelon.framework.global.media.PlayManager;
import org.watermelon.framework.global.media.Playable;
import org.watermelon.framework.global.media.player.ExoMediaPlayer;
import org.watermelon.framework.global.media.player.MusicPlayer;
import org.watermelon.framework.global.model.view.states.ViewState;

import static org.watermelon.framework.global.model.view.states.StateCode.ACTIVITY_CHANGE;


public class PlayerViewModel extends MelonCloneBaseViewModel implements Playable<Music> {

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
        if (getLoginState().getValue() != null && getLoginState().getValue().isLogin()) {
            getViewState().postValue(new ViewState(ACTIVITY_CHANGE, ProfileActivity.class, null));
        } else {
            getViewState().postValue(new ViewState(ACTIVITY_CHANGE, LoginActivity.class, null));
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

        if (playerInfo == null) {
            loadPlayer();
            playerInfo = playerModel.getPlayer();
        }

        if (playerInfo.isPlay()) {
            PlayManager.getInstance().pausePlayer();
            playerInfo.setPlay(false);
        } else if (playerInfo.isPlayed()) {
            PlayManager.getInstance().startPlayer();
            playerInfo.setPlay(true);
        } else {
            if (!(PlayManager.getInstance().isPrepared() || PlayManager.getInstance().isPlaying())) {
                MusicPlayer mediaPlayer = new ExoMediaPlayer(music.getMusicUrl(), 1f, context);
                PlayManager.getInstance().setPlayer(mediaPlayer);
                PlayManager.getInstance().addMusicChangedListener(() -> {
                    // TODO MusicChange
                    // TODO callback event to View and execute next
                });
                PlayManager.getInstance().startPlayer();
            }
            playerInfo.setPlay(true);
        }
        this.playerInfo.postValue(playerInfo);
    }

    @Override
    public void mediaStop() {
        // Player 종료
        if (!PlayManager.getInstance().isDestroyed()) {
            PlayManager.getInstance().stopPlayer();
            PlayManager.getInstance().destroyPlayer();
            this.playerInfo.getValue().resetPlayer();
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
