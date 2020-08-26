package com.devgd.melonclone.global.media.control;

import com.devgd.melonclone.global.media.MelonMediaPlayer;

public interface MusicPlayerControl {
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

    boolean isPrepared();

    int getDuration();
    int getCurrentPosition();
    int getBufferPercentage();
//    boolean canPause();
//    boolean canSeekBackward();
//    boolean canSeekForward();
//    boolean isFullScreen();
//    void    toggleFullScreen();
}
