package com.devgd.melonclone.global.media;

public interface MediaPlayerControl extends MediaBasePlayerControl {
    int     getDuration();
    int     getCurrentPosition();
    int     getBufferPercentage();
    boolean canPause();
    boolean canSeekBackward();
    boolean canSeekForward();
    boolean isFullScreen();
    void    toggleFullScreen();
    boolean isDestroyed();
}
