package com.devgd.melonclone.global.media;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;

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

    public void initPlayer() {
        try {
            setDataSource(getMediaSource());
            setVolume(getVolume());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                AudioAttributes aa = new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build();
                setAudioAttributes(aa);
            } else {
                setAudioStreamType(AudioManager.STREAM_MUSIC);
            }
        } catch (IllegalArgumentException | SecurityException | IllegalStateException | IOException e) {
            e.printStackTrace();
        }
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
