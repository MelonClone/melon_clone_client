package com.devgd.melonclone.global.media.player;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.view.Surface;

import com.devgd.melonclone.global.media.listener.CompletionListener;
import com.devgd.melonclone.global.media.listener.ReadyListener;
import com.google.android.exoplayer2.Player;

import java.io.IOException;

public class AndroidMediaPlayer implements MusicPlayer {
    private MediaPlayer player;

    private String mediaSource;
    private float volume;
    private Repeat repeatMode;

    private boolean prepared = false;
    private boolean completion = false;
    private int bufferPercent = 0;

    private MediaPlayer.OnPreparedListener preparedListener = new MediaPlayer.OnPreparedListener() {
        @Override
        public void onPrepared(MediaPlayer mp) {
            prepared = true;
            if (readyListener != null) {
                readyListener.onReady();
            }
            if (!isPlaying()) {
                startPlayer();
            }
        }
    };
    private ReadyListener readyListener;
    private CompletionListener completionListener;

    public AndroidMediaPlayer(String mediaSource, float volume, Context context) {
        this.mediaSource = mediaSource;
        this.volume = volume;
    }

    @Override
    public void initPlayer() {
        player = new MediaPlayer();
        player.setOnBufferingUpdateListener((mediaPlayer, percent) -> {
            this.bufferPercent = percent;
        });
        player.setOnPreparedListener(preparedListener);
        player.setOnCompletionListener(mediaPlayer -> {
            this.completion = true;
            completionListener.onComplete();
        });
        player.setOnErrorListener((mediaPlayer, what, extra) -> {
            if (what == MediaPlayer.MEDIA_ERROR_UNKNOWN && mediaSource.equals("")) {
                destroyPlayer();
            }

            return false;
        });
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

    @Override
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
    public Repeat getRepeatMode() {
        return repeatMode;
    }

    @Override
    public void setRepeatMode(Repeat repeatMode) {
        this.repeatMode = repeatMode;
        switch (repeatMode) {
            case LOOP:
                player.setLooping(true);
                break;
            case ONCE_LOOP:
            case ALL_LOOP:
            case OFF:
            default:
                player.setLooping(false);
        }
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
    public boolean isComplete() {
        return completion;
    }

    @Override
    public long getDuration() {
        return player.getDuration();
    }

    @Override
    public long getCurrentPosition() {
        int position = 0;
        if (player != null && player.getCurrentPosition() > 0) {
            position = player.getCurrentPosition();
        }

        return position;
    }

    @Override
    public long getBufferPosition() {
        return bufferPercent / getDuration() * 100;
    }

    @Override
    public void setDisplay(Surface surface) {
        player.setSurface(surface);
    }

    @Override
    public void setReadyListener(ReadyListener readyListener) {
        this.readyListener = readyListener;
    }

    @Override
    public void setCompletionListener(CompletionListener completionListener) {
        this.completionListener = completionListener;
    }
}
