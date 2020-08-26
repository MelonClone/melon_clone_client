package com.devgd.melonclone.global.media.player;

import android.content.Context;
import android.net.Uri;
import android.view.Surface;

import com.devgd.melonclone.global.consts.Constants;
import com.devgd.melonclone.global.media.player.MusicPlayer;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

// 실 MediaPlayer 클래스
// 플레이 관련된 정보 보유
public class ExoMediaPlayer implements MusicPlayer {
    private SimpleExoPlayer player;

    private String mediaSource = "";
    private float volume = 1.0f;

    public ExoMediaPlayer(String mediaSource, float volume, Context context) {
        super();
        this.mediaSource = mediaSource;
        this.volume = volume;

        player = new SimpleExoPlayer.Builder(context).build();
    }

    private MediaSource buildMediaSource(Context context, Uri uri) {
        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                Util.getUserAgent(context, Constants.SP_NAME));

        // This is the MediaSource representing the media to be played.
        return new ProgressiveMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(uri);
    }

    public void initPlayer() {

    }

    public void setSource(String mediaSource) {
        this.mediaSource = mediaSource;
    }

    @Override
    public void startPlayer() {
        player.pa
    }

    @Override
    public void pausePlayer() {

    }

    @Override
    public void resetPlayer() {

    }

    @Override
    public void stopPlayer() {

    }

    @Override
    public void destroyPlayer() {

    }

    @Override
    public void releasePlayer() {

    }

    @Override
    public void seekTo(int pos) {

    }

    @Override
    public boolean isPlaying() {
        return false;
    }

    @Override
    public boolean isDestroyed() {
        return false;
    }

    @Override
    public void setVolume(float volume) {

    }

    @Override
    public boolean isPrepared() {
        return false;
    }

    @Override
    public int getDuration() {
        return 0;
    }

    @Override
    public int getCurrentPosition() {
        return 0;
    }

    @Override
    public void setDisplay(Surface surface) {
        player.setVideoSurface(surface);
    }
}
