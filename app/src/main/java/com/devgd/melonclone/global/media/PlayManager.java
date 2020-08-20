package com.devgd.melonclone.global.media;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.os.Message;
import android.view.Surface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import lombok.Setter;

// 컨트롤러 동작에 따른 플레이어 조작
public class PlayManager implements MixPlayerControl, VideoPlayerControl {

    private static PlayManager playManager = new PlayManager();

    private VideoPlayerControl mainPlayer; // 메인 플레이쓰레드
    ArrayList<MusicPlayerControl> mixPlayerList = new ArrayList<>(); // 동시 플레이쓰레드
//    private MelonMediaController controller; // 전체 컨트롤
//    private Handler controllerHandler; // 플레이 결과 콜백

    ProgressTimerHandler mProgressHandler = new ProgressTimerHandler();
    private List<PlaytimeListener> playtimeListenerList = new ArrayList<>();

    @Setter
    private Context mContext;
    private boolean isMixPlay = false;

    private PlayManager() {
        mainPlayer = new PlayerController(new MelonMediaPlayer(), null);
    }

    public static PlayManager getInstance() {
        if (playManager == null) {
            playManager = new PlayManager();
        }

        return playManager;
    }

    public void setPlayer(MelonMediaPlayer mediaPlayer) {
        if (!mainPlayer.isDestroyed()) {
            mainPlayer.destroyPlayer();
        }
        mainPlayer = new PlayerController(mediaPlayer, mp -> mProgressHandler.sendEmptyMessage(SHOW_PROGRESS));
    }

    public void setDisplay(Surface surface) {
        mainPlayer.setDisplay(surface);
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
        return mainPlayer.isDestroyed();
    }

    @Override
    public int getBufferPercentage() {
        return mainPlayer.getBufferPercentage();
    }

    @Override
    public int getCurrentPosition() {
        return mainPlayer.getCurrentPosition();
    }

    @Override
    public int getDuration() {
        return mainPlayer.getDuration();
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
        return mainPlayer.getMediaPlayer();
    }

    @Override
    public void startPlayer() {
        mainPlayer.startPlayer();

        for (MusicPlayerControl mixPlayer : mixPlayerList) {
            mixPlayer.startPlayer();
        }

        mProgressHandler.sendEmptyMessage(SHOW_PROGRESS);
//        if (controllerHandler != null)
//            controllerHandler.sendMessage(new Message());
    }

    @Override
    public void pausePlayer() {
        mainPlayer.pausePlayer();

        for (MusicPlayerControl mixPlayer : mixPlayerList) {
            mixPlayer.pausePlayer();
        }
    }


    public void resetPlayer() {
        mainPlayer.resetPlayer();

        for (MusicPlayerControl mixPlayer : mixPlayerList) {
            mixPlayer.resetPlayer();
        }
    }


    public void stopPlayer() {
        mainPlayer.stopPlayer();

        for (MusicPlayerControl mixPlayer : mixPlayerList) {
            mixPlayer.stopPlayer();
        }
    }

    @Override
    public void destroyPlayer() {
        mainPlayer.destroyPlayer();

        for (MusicPlayerControl mixPlayer : mixPlayerList) {
            mixPlayer.destroyPlayer();
        }
    }

    @Override
    public void releasePlayer() {

    }

    @Override
    public void seekTo(int i) {
        mainPlayer.seekTo(i);

        for (MusicPlayerControl mixPlayer : mixPlayerList) {
            mixPlayer.seekTo(i);
        }
    }

    @Override
    public boolean isPlaying() {
        return mainPlayer.isPlaying();
    }

    @Override
    public void setVolume(float volume) {
        mainPlayer.setVolume(volume);
    }

    @Override
    public boolean isPrepared() {
        return mainPlayer.isPrepared();
    }

    // End MediaBasePlayerControl

    // Start MediaMixPlayerControl
    @Override
    public void startMix() {
        if (!isMixPlay) return ;
        for (MusicPlayerControl mixPlayer : mixPlayerList) {
            mixPlayer.startPlayer();
        }
    }

    @Override
    public void stopMix() {
        if (!isMixPlay) return ;
        for (MusicPlayerControl mixPlayer : mixPlayerList) {
            mixPlayer.stopPlayer();
        }
    }

    @Override
    public void pauseMix() {
        if (!isMixPlay) return ;
        for (MusicPlayerControl mixPlayer : mixPlayerList) {
            mixPlayer.pausePlayer();
        }
    }

    @Override
    public void resetMix() {
        if (!isMixPlay) return ;
        for (MusicPlayerControl mixPlayer : mixPlayerList) {
            mixPlayer.resetPlayer();
        }
    }

    @Override
    public void clearMix() {
        if (!isMixPlay) return ;
        mixPlayerList.clear();
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
        mixPlayerList.add(mixPlayer);
    }

    @Override
    public void setMixVolume(int index, float volume) {
        if (!isMixPlay) return ;
        mixPlayerList.get(index).setVolume(volume);
    }
    // End MediaMixPlayerControl

    public MelonMediaPlayer getSong(int index) {
        return mixPlayerList.get(index).getMediaPlayer();
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
