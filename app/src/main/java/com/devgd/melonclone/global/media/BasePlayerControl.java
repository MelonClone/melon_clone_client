package com.devgd.melonclone.global.media;

public interface BasePlayerControl {
    MelonMediaPlayer getMediaPlayer();
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
}
