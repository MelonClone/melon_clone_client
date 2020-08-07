package com.devgd.melonclone.global.media;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.devgd.melonclone.R;

import java.io.IOException;
import java.util.ArrayList;

import lombok.Setter;

public class GlobalPlayer implements SurfaceHolder.Callback, MediaPlayerControl {

    private static GlobalPlayer globalPlayer = new GlobalPlayer();
    private PlayThread mainPlayThread; // 메인 플레이쓰레드
    ArrayList<PlayThread> mixPlayThreadList = new ArrayList<>(); // 동시 플레이쓰레드
    private MelonMediaController controller; // 전체 컨트롤
    private Handler controllerHandler; // 플레이 결과 콜백
    private float volume = 1.0f;
    private String sourceUrl = "";

    @Setter
    private Context mContext;
    private boolean isMixPlay = false;

//    Playable soundItem;

    private GlobalPlayer() {
        init();
    }

    public static GlobalPlayer getInstance() {
        if (globalPlayer == null) {
            globalPlayer = new GlobalPlayer();
        }

        return globalPlayer;
    }

    public void init() {
        if (mainPlayThread == null) {
            MelonMediaPlayer mediaPlayer = new MelonMediaPlayer();
            mediaPlayer.setVolume(volume);
            mediaPlayer.setMediaSource(sourceUrl);
            mainPlayThread = new PlayThread(mediaPlayer);
        }

        mainPlayThread.playerInit();
    }

    public void setSourceUrl(String sourceUrl) {
        mainPlayThread.reset();
        MelonMediaPlayer mediaPlayer = new MelonMediaPlayer();
        mediaPlayer.setVolume(volume);
        mediaPlayer.setMediaSource(sourceUrl);
        mainPlayThread = new PlayThread(mediaPlayer);
    }


    // Controller 관련
    // (단순 플레이만 원할경우 생성 안함)

    /**
     *
     *
     controller.setAnchorView(
     (FrameLayout) ((Activity) mContext).findViewById(R.id.videoSurfaceContainer),
     (ViewGroup) ((Activity) mContext).findViewById(R.id.custom_controller));
     controller.setPrevNextListeners(nextListener, prevListener);

     * @param controller
     * @param controllerHandler
     */
    public void setController(MelonMediaController controller, Handler controllerHandler) {
        this.controller = controller;
        this.controllerHandler = controllerHandler;

        controllerInit();
    }

    public void controllerInit() {
        controller.setPlayer(this);
        controller.show();

    }

    public void removeController() {
        this.controller = null;
        this.controllerHandler = null;
    }

    public void controllerShow() {
        if (controller != null) {
            controller.show();
        }
    }

    public void setSurfaceView(SurfaceView videoSurface) {
        SurfaceHolder videoHolder = videoSurface.getHolder();
        videoHolder.addCallback(this);
    }

    public void enableMixPlay() {
        isMixPlay = false;
    }
    public void disableMixPlay() {
        isMixPlay = true;
    }


    // Implement SurfaceHolder.Callback
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceCreated(final SurfaceHolder holder) {
        try {
            mainPlayThread.getMediaPlayer().setDisplay(holder);
            //mediaPlayer.prepareAsync();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }
    // End SurfaceHolder.Callback

    // Implement VideoMediaController.MediaPlayerControl
    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return true;
    }

    @Override
    public boolean canSeekForward() {
        return true;
    }

    @Override
    public int getBufferPercentage() {
        return 0;
    }

    @Override
    public int getCurrentPosition() {
        int position = 0;
        if (mainPlayThread.getMediaPlayer().getCurrentPosition() < 0) {
            position = 0;
        } else {
            position = mainPlayThread.getMediaPlayer().getCurrentPosition();
        }

        return position;
    }

    @Override
    public int getDuration() {
        return mainPlayThread.getMediaPlayer().getDuration();
    }

    @Override
    public boolean isPlaying() {
        if (mainPlayThread.getMediaPlayer() == null) return false;
        return mainPlayThread.isPlaying();
    }

    @Override
    public void playerDestroy() {
        mainPlayThread.playerDestroy();

        for (PlayThread mixPlayer : mixPlayThreadList) {
            mixPlayer.playerDestroy();
        }
    }

    @Override
    public boolean isDestroyed() {
        return mainPlayThread.getMediaPlayer() == null;
    }

    @Override
    public void pause() {
        mainPlayThread.pause();

        for (PlayThread mixPlayer : mixPlayThreadList) {
            mixPlayer.pause();
        }
    }

    @Override
    public void seekTo(int i) {
        mainPlayThread.seekTo(i);

        for (PlayThread mixPlayer : mixPlayThreadList) {
            mixPlayer.seekTo(i);
        }
    }

    @Override
    public void startPlayer() {
        mainPlayThread.startPlayer();

        for (PlayThread mixPlayer : mixPlayThreadList) {
            mixPlayer.startPlayer();
        }

        if (controllerHandler != null)
            controllerHandler.sendMessage(new Message());
    }

    @Override
    public boolean isFullScreen() {
        if (mContext == null) return false;
        return mContext.getResources().getConfiguration().orientation==2;
    }

    @Override
    public void toggleFullScreen() {
        if (mContext == null) return ;

        pause();

        if(mContext.getResources().getConfiguration().orientation==1){
            ((Activity) mContext).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else {
            ((Activity) mContext).setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
    }
    // End VideoMediaController.MediaPlayerControl

    public void restart() {
        mainPlayThread.startPlayer();

        for (PlayThread mixPlayer : mixPlayThreadList) {
            mixPlayer.startPlayer();
        }
    }


    public void stop() {
        mainPlayThread.stopPlayer();

        for (PlayThread mixPlayer : mixPlayThreadList) {
            mixPlayer.stopPlayer();
        }
    }

    public void reset() {
        mainPlayThread.reset();
    }

    public void startMix() {
        //Set Media Players there and start play
        for (PlayThread mixPlayer : mixPlayThreadList) {
            mixPlayer.startPlayer();
        }
    }

    public void stopMix() {
        for (PlayThread mixPlayer : mixPlayThreadList) {
            mixPlayer.stopPlayer();
        }
    }

    public void pauseMix() {
        for (PlayThread mixPlayer : mixPlayThreadList) {
            mixPlayer.pause();
        }
    }

    public void resetMix() {
        for (PlayThread mixPlayer : mixPlayThreadList) {
            mixPlayer.resetPlayer();
        }
    }

    // All Player destroy
    public void destroyPlayer() {
        for (PlayThread mixPlayer : mixPlayThreadList) {
            mixPlayer.destroyPlayer();
        }

        mainPlayThread.destroyPlayer();
    }

    public void clearMix() {
        mixPlayThreadList.clear();
    }

    public void addMix(String sourceUrl, float volume) throws IOException {
        if (volume > 1) volume = 1;
        MelonMediaPlayer songPlayer = new MelonMediaPlayer(sourceUrl, volume);
        mixPlayThreadList.add(new PlayThread(songPlayer));
    }

    public MelonMediaPlayer getSong(int index) {
        return mixPlayThreadList.get(index).getMediaPlayer();
    }

    public void setVolume(int index, float volume) {
        mixPlayThreadList.get(index).setVolume(volume);
    }

}
