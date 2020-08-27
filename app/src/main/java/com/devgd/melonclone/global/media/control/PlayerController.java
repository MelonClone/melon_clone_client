package com.devgd.melonclone.global.media.control;

import android.view.Surface;

import com.devgd.melonclone.global.media.listener.MusicChangedListener;
import com.devgd.melonclone.global.media.player.ExoMediaPlayer;
import com.devgd.melonclone.global.media.player.MusicPlayer;

import java.util.ArrayList;
import java.util.List;

public class PlayerController extends Thread implements VideoPlayerControl {

    private MusicPlayer mediaPlayer;
    private boolean initiate = false;
    private int percent = 0;

    private List<MusicChangedListener> musicChangedListenerList = new ArrayList<>();

    public PlayerController(MusicPlayer player) {
        this.mediaPlayer = player;
    }

    @Override
    public void run() {
        playerInit();
        startPlayer();
    }

    private void playerInit() {
        initiate = true;

        mediaPlayer.initPlayer();
        mediaPlayer.setCompletionListener(() -> {
            if (mediaPlayer.getRepeatMode() == MusicPlayer.Repeat.ALL_LOOP
            || mediaPlayer.getRepeatMode() == MusicPlayer.Repeat.ONCE_LOOP) {
                for (MusicChangedListener listener : musicChangedListenerList) {
                    listener.onMusicChanged();
                }
            }
        });
    }

    @Override
    public MusicPlayer getPlayer() {
        return mediaPlayer;
    }

    @Override
    public long getBufferPosition() {
        return this.percent;
    }

    @Override
    public long getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    @Override
    public long getDuration() {
        return mediaPlayer.getDuration();
    }

    @Override
    public void startPlayer() {
        if (mediaPlayer != null) {
            if (!initiate) {
                if (mediaPlayer instanceof ExoMediaPlayer) {
                    // ExoPlayer must play in Main Thread
                    playerInit();
                    startPlayer();
                } else {
                    start();
                }
            } else {
                mediaPlayer.startPlayer();
            }
        }
    }

    @Override
    public void pausePlayer() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pausePlayer();
        }
    }

    @Override
    public void resetPlayer() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.resetPlayer();
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
    public void setRepeatMode(MusicPlayer.Repeat repeatMode) {
        if (initiate && mediaPlayer != null) {
            mediaPlayer.setRepeatMode(repeatMode);
        }
    }

    @Override
    public void addMusicChangedListener(MusicChangedListener musicChangedListener) {
        this.musicChangedListenerList.add(musicChangedListener);
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
    public boolean isPrepared() {
        if (mediaPlayer != null) {
            return mediaPlayer.isPrepared();
        }
        return false;
    }

    @Override
    public void setVolume(float volume) {
        mediaPlayer.setVolume(volume);
    }

    public void setDisplay(Surface surface) {
        mediaPlayer.setDisplay(surface);
    }
}
