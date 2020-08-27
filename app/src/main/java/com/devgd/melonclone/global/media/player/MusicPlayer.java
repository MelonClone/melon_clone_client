package com.devgd.melonclone.global.media.player;

import android.view.Surface;

import com.devgd.melonclone.global.media.listener.CompletionListener;
import com.devgd.melonclone.global.media.listener.ReadyListener;

public interface MusicPlayer {
    enum Repeat {OFF, ONCE_LOOP, ALL_LOOP, LOOP}
    void initPlayer();
    boolean isComplete();
    void setDisplay(Surface surface);
    void setReadyListener(ReadyListener readyListener);
    void setCompletionListener(CompletionListener completionListener);
    void setSource(String mediaSource);

    void startPlayer();
    void pausePlayer();
    void resetPlayer();
    void stopPlayer();
    void destroyPlayer();
    void releasePlayer();
    void seekTo(int pos);
    Repeat getRepeatMode();
    void setRepeatMode(MusicPlayer.Repeat repeatMode);
    boolean isPlaying();
    boolean isDestroyed();
    void setVolume(float volume);
    boolean isPrepared();
    long getDuration();
    long getCurrentPosition();
    long getBufferPosition();
}
