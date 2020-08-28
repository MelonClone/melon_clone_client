package com.devgd.melonclone.global.media.player;

import android.content.Context;
import android.net.Uri;
import android.view.Surface;

import com.devgd.melonclone.global.consts.Constants;
import com.devgd.melonclone.global.media.listener.CompletionListener;
import com.devgd.melonclone.global.media.listener.ReadyListener;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

public class ExoMediaPlayer implements MusicPlayer {
    private SimpleExoPlayer player;
    private Context mContext;

    private String mediaSource;
    private float volume;
    private Repeat repeatMode;

    private boolean prepared = false;
    private boolean completion = false;

    private ReadyListener readyListener;
    private CompletionListener completionListener;

    public ExoMediaPlayer(String mediaSource, float volume, Context context) {
        this.mediaSource = mediaSource;
        this.volume = volume;
        this.mContext = context;
    }

    @Override
    public void initPlayer() {
        player = new SimpleExoPlayer.Builder(mContext).build();
        player.addListener(new Player.EventListener() {
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (playbackState == Player.STATE_ENDED) {
                    completion = true;
                    completionListener.onComplete();
                } else if (playbackState == Player.STATE_READY) {
                    prepared = true;
                    if (readyListener != null) {
                        readyListener.onReady();
                    }
                }
            }
        });
        player.setVolume(volume);
        player.prepare(buildMediaSource(mContext, Uri.parse(mediaSource)));
        player.setRepeatMode(Player.REPEAT_MODE_ALL);
    }

    @Override
    public void setSource(String mediaSource) {
        this.mediaSource = mediaSource;
    }

    @Override
    public void startPlayer() {
        player.setPlayWhenReady(true);
    }

    @Override
    public void pausePlayer() {
        player.setPlayWhenReady(false);
    }

    @Override
    public void resetPlayer() {
        completion = false;
        player.stop(true);
    }

    @Override
    public void stopPlayer() {
        completion = false;
        player.stop(true);
    }

    @Override
    public void destroyPlayer() {
        completion = false;
        prepared = false;
        stopPlayer();
        releasePlayer();
        initPlayer();
    }

    @Override
    public void releasePlayer() {
        completion = false;
        player.release();
    }

    @Override
    public void seekTo(int pos) {
        completion = false;
        player.seekTo(pos);
    }

    @Override
    public Repeat getRepeatMode() {
        return repeatMode;
    }

    @Override
    public void setRepeatMode(Repeat repeatMode) {
        this.repeatMode = repeatMode;
        int mode;
        switch (repeatMode) {
            case LOOP:
                mode = Player.REPEAT_MODE_ALL;
                break;
            case ONCE_LOOP:
            case ALL_LOOP:
            case OFF:
            default:
                mode = Player.REPEAT_MODE_OFF;
        }
        player.setRepeatMode(mode);
    }

    @Override
    public boolean isPlaying() {
        return player.isPlaying();
    }

    @Override
    public boolean isDestroyed() {
        return player == null;
    }

    @Override
    public void setVolume(float volume) {
        player.setVolume(volume);
    }

    @Override
    public boolean isPrepared() {
        return prepared;
    }

    @Override
    public boolean isComplete() {
        return completion;
    }

    @Override
    public long getDuration() {
        return player.getDuration();
    }

    @Override
    public long getCurrentPosition() {
        return player.getCurrentPosition();
    }

    @Override
    public long getBufferPosition() {
        return player.getBufferedPosition();
    }

    @Override
    public void setDisplay(Surface surface) {
        player.setVideoSurface(surface);
    }

    @Override
    public void setReadyListener(ReadyListener readyListener) {
        this.readyListener = readyListener;
    }

    @Override
    public void setCompletionListener(CompletionListener completionListener) {
        this.completionListener = completionListener;
    }


    private MediaSource buildMediaSource(Context context, Uri uri) {
        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                Util.getUserAgent(context, Constants.SP_NAME));
        return new HlsMediaSource.Factory(dataSourceFactory)
                .createMediaSource(uri);
        // This is the MediaSource representing the media to be played.
//        return new ProgressiveMediaSource.Factory(dataSourceFactory)
//                .createMediaSource(uri);
    }
}
