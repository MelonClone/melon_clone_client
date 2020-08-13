package com.devgd.melonclone.global.media;

import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.view.Surface;

import java.io.IOException;
import java.lang.ref.WeakReference;

import lombok.Getter;
import lombok.Setter;


// 플레이 관련된 정보 할당
// 실제 플레이어 동작
public class PlayerController extends Thread implements
        MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnBufferingUpdateListener,
        MusicPlayerControl, VideoPlayerControl {

    @Getter
    private MelonMediaPlayer mediaPlayer;
    private boolean initiate = false;
    @Getter
    private boolean prepared = false;
    private boolean completion = false;
    private int percent = 0;
    private MediaPlayer.OnPreparedListener preparedListener;

    public PlayerController(MelonMediaPlayer player, MediaPlayer.OnPreparedListener preparedListener) {
        this.mediaPlayer = player;
        this.preparedListener = preparedListener;
    }

    @Override
    public void run() {
        try {
            prepared = false;
            completion = false;
            playerInit();
            playerPrepare();
            mediaPlayer.start();
        } catch (IllegalStateException e) {
            //e.printStackTrace();
        }
    }

    private void playerInit() {
        initiate = true;
        try {
            mediaPlayer.setDataSource(mediaPlayer.getMediaSource());
            mediaPlayer.setVolume(mediaPlayer.getVolume());

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                AudioAttributes aa = new AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_MEDIA)
                        .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                        .build();
                mediaPlayer.setAudioAttributes(aa);
            } else {
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            }

            mediaPlayer.setOnBufferingUpdateListener(this);
            mediaPlayer.setOnPreparedListener(preparedListener);
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setOnErrorListener(this);
        } catch (IllegalArgumentException | SecurityException | IllegalStateException | IOException e) {
            e.printStackTrace();
        }
    }

    private void playerPrepare() {
        try {
            mediaPlayer.prepare();
            mediaPlayer.setLooping(true);
            mediaPlayer.seekTo(0);
            prepared = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    // Implement MediaPlayerControl
    @Override
    public int getBufferPercentage() {
        return this.percent;
    }

    @Override
    public int getCurrentPosition() {
        int position = 0;
        if (mediaPlayer != null && mediaPlayer.getCurrentPosition() > 0) {
            position = mediaPlayer.getCurrentPosition();
        }

        return position;
    }

    @Override
    public int getDuration() {
        return mediaPlayer.getDuration();
    }
    // End MediaPlayerControl

    // Implement MediaBasePlayerControl
    @Override
    public void startPlayer() {
        if (mediaPlayer != null) {
            if (!initiate) {
                start();
            } else {
                mediaPlayer.start();
            }
        }
    }

    @Override
    public void pausePlayer() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
        }
    }

    @Override
    public void resetPlayer() {
        if (mediaPlayer.isPlaying() || mediaPlayer.isLooping()) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            prepared = false;
            completion = false;
            playerInit();
            playerPrepare();
        }
    }

    @Override
    public void releasePlayer() {
        if (mediaPlayer.isPlaying() || mediaPlayer.isLooping()) {
            mediaPlayer.stop();
            mediaPlayer.release();
        }
    }


    @Override
    public void stopPlayer() {
        this.mediaPlayer.stop();
    }

    @Override
    public void destroyPlayer() {
        mediaPlayer.stop();
        mediaPlayer.setDisplay(null);
        mediaPlayer.release();
        mediaPlayer = null;
    }

    @Override
    public void seekTo(int i) {
        if (initiate && mediaPlayer != null && mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(i);
        }
    }

    @Override
    public boolean isPlaying() {
        if (mediaPlayer != null) {
            return mediaPlayer.isPlaying();
        }
        return false;
    }

    @Override
    public boolean isDestroyed() {
        return mediaPlayer == null;
    }

    @Override
    public void setVolume(float volume) {
        mediaPlayer.setVolume(volume);
        mediaPlayer.onVolumeChange();
    }
    // End MediaBasePlayerControl

    // Start VideoPlayerControl
    public void setDisplay(Surface surface) {
        mediaPlayer.setSurface(surface);
    }
    // End VideoPlayerControl
}
