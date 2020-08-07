package com.devgd.melonclone.global.media;

import android.media.MediaPlayer;

import java.io.IOException;

public class MelonMediaPlayer extends MediaPlayer {
    private String mediaSource;
    private float volume = 1;

    public MelonMediaPlayer() {
        super();
    }

    public MelonMediaPlayer(String mediaSource, float volume) {
        super();
        this.mediaSource = mediaSource;
        this.volume = volume;
    }

    public void setPrepare() throws IOException {

        prepare();
        seekTo(0);
        setVolume(volume, volume);
    }

    public void setMediaSource(String mediaSource) {
        this.mediaSource = mediaSource;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public void onVolumeChange() {
        setVolume(volume, volume);
    }

    public String getMediaSource() {
        return mediaSource;
    }

    public float getVolume() {
        return volume;
    }
}
