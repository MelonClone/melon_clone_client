package com.devgd.melonclone.global.media.player;

import android.view.Surface;

public interface MusicPlayer {
    void initPlayer();
    void startPlayer();
    void pausePlayer();
    void resetPlayer();
    void stopPlayer();
    void destroyPlayer();
    void releasePlayer();
    void seekTo(int pos);
    boolean isPlaying();
    boolean isDestroyed();
    void setVolume(float volume);
    boolean isPrepared();
    int getDuration();
    int getCurrentPosition();
    void setDisplay(Surface surface);
    void setReadyListener(ReadyListener readyListener);
}
