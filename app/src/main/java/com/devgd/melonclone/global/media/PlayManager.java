package com.devgd.melonclone.global.media;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.os.Message;
import android.view.Surface;

import com.devgd.melonclone.global.media.control.MixPlayerControl;
import com.devgd.melonclone.global.media.control.MusicPlayerControl;
import com.devgd.melonclone.global.media.control.PlayerController;
import com.devgd.melonclone.global.media.control.VideoPlayerControl;
import com.devgd.melonclone.global.media.player.AndroidMediaPlayer;
import com.devgd.melonclone.global.media.player.MusicPlayer;

import java.io.IOException;
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
        mainPlayerController.setReadyListener(() -> {
            mProgressHandler.sendEmptyMessage(SHOW_PROGRESS);
        });
    }

    public void setDisplay(Surface surface) {
        mainPlayerController.setDisplay(surface);
    }
/*
    public void setSourceUrl(String sourceUrl) {
        mainPlayer.resetPlayer();
        MelonMediaPlayer mediaPlayer = new MelonMediaPlayer();
        mediaPlayer.setMediaSource(sourceUrl);
        mainPlayer = new PlayerController(mediaPlayer);
    }
*/

    public void enableMixPlay() {
        isMixPlay = false;
    }
    public void disableMixPlay() {
        isMixPlay = true;
    }

    /*
    // Implement SurfaceHolder.Callback
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(final SurfaceHolder holder) {
        try {
            mainPlayer.setDisplay(holder);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }
    // End SurfaceHolder.Callback
    */

    // Implement MediaPlayerControl
    @Override
    public boolean isDestroyed() {
        return mainPlayerController.isDestroyed();
    }

    @Override
    public int getBufferPercentage() {
        return mainPlayerController.getBufferPercentage();
    }

    @Override
    public int getCurrentPosition() {
        return mainPlayerController.getCurrentPosition();
    }

    @Override
    public int getDuration() {
        return mainPlayerController.getDuration();
    }

    public boolean isFullScreen() {
        if (mContext == null) return false;
        return mContext.getResources().getConfiguration().orientation==2;
    }

    public void toggleFullScreen() {
        if (mContext == null) return ;

        pausePlayer();

        if(mContext.getResources().getConfiguration().orientation==1){
            ((Activity) mContext).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            ((Activity) mContext).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
    // End MediaPlayerControl

    // Implement MediaBasePlayerControl

    @Override
    public MelonMediaPlayer getMediaPlayer() {
        return mainPlayerController.getMediaPlayer();
    }

    @Override
    public void startPlayer() {
        mainPlayerController.startPlayer();

        for (MusicPlayerControl mixPlayer : mixPlayerControllerList) {
            mixPlayer.startPlayer();
        }

        mProgressHandler.sendEmptyMessage(SHOW_PROGRESS);
//        if (controllerHandler != null)
//            controllerHandler.sendMessage(new Message());
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

    }

    @Override
    public void seekTo(int i) {
        mainPlayerController.seekTo(i);

        for (MusicPlayerControl mixPlayer : mixPlayerControllerList) {
            mixPlayer.seekTo(i);
        }
    }

    @Override
    public boolean isPlaying() {
        return mainPlayerController.isPlaying();
    }

    @Override
    public void setVolume(float volume) {
        mainPlayerController.setVolume(volume);
    }

    @Override
    public boolean isPrepared() {
        return mainPlayerController.isPrepared();
    }

    // End MediaBasePlayerControl

    // Start MediaMixPlayerControl
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

    /**
     *
     * throws IOException
     if (volume > 1) volume = 1;
     MelonMediaPlayer songPlayer = new MelonMediaPlayer(sourceUrl, volume);
     PlayThread playThread = new PlayThread(songPlayer);
     playThread.playerInit();
     * @param mixPlayer
     * @throws IOException
     */
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
    // End MediaMixPlayerControl

    public MelonMediaPlayer getSong(int index) {
        return mixPlayerControllerList.get(index).getMediaPlayer();
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

    public void addMusicChangedListener(MusicChangedListener listener) {
        listener.onMusicChanged();
    }
}
