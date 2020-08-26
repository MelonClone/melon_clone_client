package com.devgd.melonclone.global.media.player;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.view.Surface;

import java.io.IOException;

// 실 MediaPlayer 클래스
// 플레이 관련된 정보 보유
public class AndroidMediaPlayer implements MusicPlayer, MediaPlayer.OnCompletionListener,
        MediaPlayer.OnErrorListener, MediaPlayer.OnBufferingUpdateListener {
    private MediaPlayer player;

    private String mediaSource = "";
    private float volume = 1.0f;

    private boolean prepared = false;
    private MediaPlayer.OnPreparedListener preparedListener;

    public AndroidMediaPlayer() {
        super();
    }

    public AndroidMediaPlayer(String mediaSource, float volume, Context context) {
        this(mediaSource, volume, context, null);
    }

    public AndroidMediaPlayer(String mediaSource, float volume, Context context, MediaPlayer.OnPreparedListener preparedListener) {
        super();
        this.mediaSource = mediaSource;
        this.volume = volume;

        player = new MediaPlayer();
    }

    private String buildMediaSource(Context context, Uri uri) {

        return mediaSource;
    }

    public void initPlayer() {

        player.setOnBufferingUpdateListener(this);
        if (preparedListener != null) {
            player.setOnPreparedListener(preparedListener);
        }
        player.setOnCompletionListener(this);
        player.setOnErrorListener(this);
        try {
            player.setDataSource(mediaSource);
            player.setVolume(volume, volume);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                AudioAttributes aa = new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build();
                player.setAudioAttributes(aa);
            } else {
                player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            }
        } catch (IllegalArgumentException | SecurityException | IllegalStateException | IOException e) {
            e.printStackTrace();
        }
    }


    public void setSource(String mediaSource) {
        this.mediaSource = mediaSource;
    }

    @Override
    public void startPlayer() {
        if (isPrepared()) {
            player.start();
        } else {
            try {
                player.prepare();
                player.setLooping(true);
                player.seekTo(0);
                prepared = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void pausePlayer() {
        if (player != null && player.isPlaying()) {
            player.pause();
        }
    }

    @Override
    public void resetPlayer() {
        if (player.isPlaying() || player.isLooping()) {
            player.stop();
            player.reset();
        }
    }

    @Override
    public void stopPlayer() {
        player.stop();
    }

    @Override
    public void destroyPlayer() {
        player.stop();
        player.setDisplay(null);
        player.release();
        player = null;
    }

    @Override
    public void releasePlayer() {
        if (player.isPlaying() || player.isLooping()) {
            player.stop();
        }
        player.release();
    }

    @Override
    public void seekTo(int pos) {
        player.seekTo(pos);
    }

    @Override
    public boolean isPlaying() {
        if (player != null) {
            return player.isPlaying();
        }
        return false;
    }

    @Override
    public boolean isDestroyed() {
        return player == null;
    }

    @Override
    public void setVolume(float volume) {
        player.setVolume(volume, volume);
    }

    @Override
    public boolean isPrepared() {
        return prepared;
    }

    @Override
    public int getDuration() {
        return player.getDuration();
    }

    @Override
    public int getCurrentPosition() {
        int position = 0;
        if (player != null && player.getCurrentPosition() > 0) {
            position = player.getCurrentPosition();
        }

        return position;
    }

    @Override
    public void setDisplay(Surface surface) {
        player.setSurface(surface);
    }

    public void setReadyListener(ReadyListener readyListener) {
        this.preparedListener = preparedListener;
    }


    // Implement MediaPlayer.OnCompletionListener
    @Override
    public void onCompletion(MediaPlayer mp) {
        completion = true;
    }
    // End MediaPlayer.OnCompletionListener

    // Implement MediaPlayer.OnErrorListener
    @Override
    public boolean onError(MediaPlayer mediaPlayer, int what, int extra) {
        if (what == MediaPlayer.MEDIA_ERROR_UNKNOWN && getMediaPlayer().getMediaSource().equals("")) {
            destroyPlayer();
        }

        return false;
    }
    // End MediaPlayer.OnErrorListener

    // Implement MediaPlayer.OnBufferingUpdateListener
    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {
        this.percent = percent;
    }
    // End MediaPlayer.OnBufferingUpdateListener
}
