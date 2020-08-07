package com.devgd.melonclone.global.media;

public interface MediaBasePlayerControl {
    void    startPlayer();
    void    pause();
    void    seekTo(int pos);
    boolean isPlaying();
    void    playerDestroy();
}
