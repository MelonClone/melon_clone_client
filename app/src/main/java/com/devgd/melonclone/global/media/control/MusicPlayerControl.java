package com.devgd.melonclone.global.media.control;

import com.devgd.melonclone.global.media.listener.MusicChangedListener;
import com.devgd.melonclone.global.media.player.MusicPlayer;

public interface MusicPlayerControl {
    MusicPlayer getPlayer();






    void startPlayer();
    void pausePlayer();
    void resetPlayer();
    void stopPlayer();
    void destroyPlayer();
    void releasePlayer();
    void seekTo(int pos);
    void setRepeatMode(MusicPlayer.Repeat repeatMode);
    void addMusicChangedListener(MusicChangedListener musicChangedListener);
    boolean isPlaying();
    boolean isDestroyed();
    void setVolume(float volume);
    boolean isPrepared();
    long getDuration();
    long getCurrentPosition();
    long getBufferPosition();
}
