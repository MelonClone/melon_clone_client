package com.devgd.melonclone.global.media;

public interface MusicPlayerControl extends BasePlayerControl {
    int     getDuration();
    int     getCurrentPosition();
    int     getBufferPercentage();
//    boolean canPause();
//    boolean canSeekBackward();
//    boolean canSeekForward();
//    boolean isFullScreen();
//    void    toggleFullScreen();
}
