package com.devgd.melonclone.global.media;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.Surface;

import com.devgd.melonclone.global.media.control.MixPlayerControl;
import com.devgd.melonclone.global.media.control.MusicPlayerControl;
import com.devgd.melonclone.global.media.control.PlayerController;
import com.devgd.melonclone.global.media.control.VideoPlayerControl;
import com.devgd.melonclone.global.media.listener.MusicChangedListener;
import com.devgd.melonclone.global.media.listener.PlaytimeListener;
import com.devgd.melonclone.global.media.player.MusicPlayer;

import java.util.ArrayList;
import java.util.List;

import lombok.Setter;

// 컨트롤러 동작에 따른 플레이어 조작
public class PlayManager implements MixPlayerControl, VideoPlayerControl {

    private static PlayManager playManager = new PlayManager();

    private VideoPlayerControl mainPlayerController; // 메인 플레이쓰레드
    ArrayList<MusicPlayerControl> mixPlayerControllerList = new ArrayList<>(); // 동시 플레이쓰레드
//    private MelonMediaController controller; // 전체 컨트롤
//    private Handler controllerHandler; // 플레이 결과 콜백

    ProgressTimerHandler mProgressHandler = new ProgressTimerHandler();
    private List<PlaytimeListener> playtimeListenerList = new ArrayList<>();

    @Setter
    private Context mContext;
    private boolean isMixPlay = false;

    private PlayManager() {
    }

    public static PlayManager getInstance() {
        if (playManager == null) {
            playManager = new PlayManager();
        }

        return playManager;
    }

    public void setPlayer(MusicPlayer musicPlayer) {
        if (mainPlayerController != null && !mainPlayerController.isDestroyed()) {
            mainPlayerController.destroyPlayer();
        }
        mainPlayerController = new PlayerController(musicPlayer);
        mainPlayerController.getPlayer().setReadyListener(() -> {
            mProgressHandler.sendEmptyMessage(SHOW_PROGRESS);
        });
    }

    private boolean isSetPlayer() {
        return mainPlayerController != null;
    }

    @Override
    public MusicPlayer getPlayer() {
        return mainPlayerController.getPlayer();
    }

    public void setDisplay(Surface surface) {
        mainPlayerController.setDisplay(surface);
    }

    public void setSourceUrl(String sourceUrl) {
        mainPlayerController.resetPlayer();
        mainPlayerController.getPlayer().setSource(sourceUrl);
    }


    public void enableMixPlay() {
        isMixPlay = false;
    }
    public void disableMixPlay() {
        isMixPlay = true;
    }

    @Override
    public boolean isDestroyed() {
        if (isSetPlayer()) {
            return mainPlayerController.isDestroyed();
        }
        return true;
    }

    @Override
    public long getBufferPosition() {
        if (!isSetPlayer()) {
            return 0;
        }
        return mainPlayerController.getBufferPosition();
    }

    @Override
    public long getCurrentPosition() {
        if (!isSetPlayer()) {
            return 0;
        }
        return mainPlayerController.getCurrentPosition();
    }

    @Override
    public long getDuration() {
        if (!isSetPlayer()) {
            return 0;
        }
        return mainPlayerController.getDuration();
    }

    @Override
    public void startPlayer() {
        mainPlayerController.startPlayer();

        for (MusicPlayerControl mixPlayer : mixPlayerControllerList) {
            mixPlayer.startPlayer();
        }

        mProgressHandler.sendEmptyMessage(SHOW_PROGRESS);
    }

    @Override
    public void pausePlayer() {
        mainPlayerController.pausePlayer();

        for (MusicPlayerControl mixPlayer : mixPlayerControllerList) {
            mixPlayer.pausePlayer();
        }
    }


    public void resetPlayer() {
        mainPlayerController.resetPlayer();

        for (MusicPlayerControl mixPlayer : mixPlayerControllerList) {
            mixPlayer.resetPlayer();
        }
    }


    public void stopPlayer() {
        mainPlayerController.stopPlayer();

        for (MusicPlayerControl mixPlayer : mixPlayerControllerList) {
            mixPlayer.stopPlayer();
        }
    }

    @Override
    public void destroyPlayer() {
        mainPlayerController.destroyPlayer();

        for (MusicPlayerControl mixPlayer : mixPlayerControllerList) {
            mixPlayer.destroyPlayer();
        }
    }

    @Override
    public void releasePlayer() {
        mainPlayerController.releasePlayer();

        for (MusicPlayerControl mixPlayer : mixPlayerControllerList) {
            mixPlayer.releasePlayer();
        }
    }

    @Override
    public void seekTo(int i) {
        mainPlayerController.seekTo(i);

        for (MusicPlayerControl mixPlayer : mixPlayerControllerList) {
            mixPlayer.seekTo(i);
        }
    }

    @Override
    public void setRepeatMode(MusicPlayer.Repeat repeatMode) {
        if (!isSetPlayer()) {
            return ;
        }
        mainPlayerController.setRepeatMode(repeatMode);
    }

    @Override
    public boolean isPlaying() {
        if (!isSetPlayer()) {
            return false;
        }
        return mainPlayerController.isPlaying();
    }

    @Override
    public void setVolume(float volume) {
        if (!isSetPlayer()) {
            return ;
        }
        mainPlayerController.setVolume(volume);
    }

    @Override
    public boolean isPrepared() {
        if (!isSetPlayer()) {
            return false;
        }
        return mainPlayerController.isPrepared();
    }

    @Override
    public void startMix() {
        if (!isMixPlay) return ;
        for (MusicPlayerControl mixPlayer : mixPlayerControllerList) {
            mixPlayer.startPlayer();
        }
    }

    @Override
    public void stopMix() {
        if (!isMixPlay) return ;
        for (MusicPlayerControl mixPlayer : mixPlayerControllerList) {
            mixPlayer.stopPlayer();
        }
    }

    @Override
    public void pauseMix() {
        if (!isMixPlay) return ;
        for (MusicPlayerControl mixPlayer : mixPlayerControllerList) {
            mixPlayer.pausePlayer();
        }
    }

    @Override
    public void resetMix() {
        if (!isMixPlay) return ;
        for (MusicPlayerControl mixPlayer : mixPlayerControllerList) {
            mixPlayer.resetPlayer();
        }
    }

    @Override
    public void clearMix() {
        if (!isMixPlay) return ;
        mixPlayerControllerList.clear();
    }

    @Override
    public void addMix(MusicPlayerControl mixPlayer) {
        if (!isMixPlay) return ;
        mixPlayerControllerList.add(mixPlayer);
    }

    @Override
    public void setMixVolume(int index, float volume) {
        if (!isMixPlay) return ;
        mixPlayerControllerList.get(index).setVolume(volume);
    }

    public MusicPlayer getMixPlayer(int index) {
        return mixPlayerControllerList.get(index).getPlayer();
    }


    private static final int SHOW_PROGRESS = 1;
    private static class ProgressTimerHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            if (PlayManager.getInstance().isDestroyed()) {
                return;
            }

            switch (msg.what) {
                case SHOW_PROGRESS:
                    if (PlayManager.getInstance().playtimeListenerList.size() > 0 && PlayManager.getInstance().getDuration() > 0) {
                        int progress = (int) (((float) PlayManager.getInstance().getCurrentPosition() / (float) PlayManager.getInstance().getDuration()) * 100);
                        for (PlaytimeListener listener : PlayManager.getInstance().playtimeListenerList) {
                            listener.onPlaytimeListen(progress);
                        }
                    }
                    if (PlayManager.getInstance().isPrepared()) {
                        msg = obtainMessage(SHOW_PROGRESS);
                        sendMessageDelayed(msg, 500);
                    }
                    break;
            }
        }
    }

    public void addPlaytimeListener(PlaytimeListener listener) {
        playtimeListenerList.add(listener);
    }

    public void removePlaytimeListener(PlaytimeListener listener) {
        playtimeListenerList.remove(listener);
    }

    public void addMusicChangedListener(MusicChangedListener musicChangedListener) {
        mainPlayerController.addMusicChangedListener(musicChangedListener);
    }
}
