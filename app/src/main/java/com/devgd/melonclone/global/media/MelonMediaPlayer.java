package com.devgd.melonclone.global.media;

import android.media.MediaPlayer;

import java.io.IOException;

// 실 MediaPlayer 클래스
// 플레이 관련된 정보 보유
public class MelonMediaPlayer extends MediaPlayer {
    private String mediaSource = "";
    private float volume = 1.0f;

    public MelonMediaPlayer() {
        super();
    }

    public MelonMediaPlayer(String mediaSource) {
        super();
        this.mediaSource = mediaSource;
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
