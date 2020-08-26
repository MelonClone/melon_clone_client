package com.devgd.melonclone.global.media.control;

import android.media.MediaPlayer;
import android.util.Log;
import android.view.Surface;

import com.devgd.melonclone.global.media.MelonMediaPlayer;
import com.devgd.melonclone.global.media.control.VideoPlayerControl;
import com.devgd.melonclone.global.media.player.MusicPlayer;

import java.io.IOException;

import lombok.Getter;


// 플레이 관련된 정보 할당
// 실제 플레이어 동작
public class PlayerController extends Thread implements VideoPlayerControl {

    @Getter
    private MusicPlayer mediaPlayer;
    private boolean initiate = false;
    @Getter
    private boolean prepared = false;
    private boolean completion = false;
    private boolean paused = false;
    private int percent = 0;

    public PlayerController(MusicPlayer player) {
        this.mediaPlayer = player;
    }

    @Override
    public void run() {
        prepared = false;
        completion = false;
        playerInit();
        startPlayer();
    }

    private void playerInit() {
        initiate = true;

        mediaPlayer.initPlayer();
    }

    @Override
    public int getBufferPercentage() {
        return this.percent;
    }

    @Override
    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    @Override
    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    @Override
    public void startPlayer() {
        if (mediaPlayer != null) {
            if (!initiate) {
                start();
            } else {
                mediaPlayer.startPlayer();
                paused = false;
            }
        }
    }

    @Override
    public void pausePlayer() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pausePlayer();
            paused = true;
        }
    }

    @Override
    public void resetPlayer() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.resetPlayer();
            prepared = false;
            completion = false;
            playerInit();
        }
    }

    @Override
    public void releasePlayer() {
        mediaPlayer.releasePlayer();
    }


    @Override
    public void stopPlayer() {
        mediaPlayer.stopPlayer();
    }

    @Override
    public void destroyPlayer() {
        mediaPlayer.destroyPlayer();
    }

    @Override
    public void seekTo(int i) {
        if (initiate && mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(i);
        }
    }

    @Override
    public boolean isPlaying() {
        if (mediaPlayer != null) {
            return mediaPlayer.isPlaying();
        }
        return false;
    }

    @Override
    public boolean isDestroyed() {
        return mediaPlayer.isDestroyed();
    }

    @Override
    public void setVolume(float volume) {
        mediaPlayer.setVolume(volume);
    }

    public void setDisplay(Surface surface) {
        mediaPlayer.setDisplay(surface);
    }
}
