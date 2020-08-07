package com.devgd.melonclone.global.media;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Message;
import android.util.Log;
import android.view.SurfaceHolder;

import java.io.IOException;

import lombok.Getter;

public class PlayThread extends Thread implements MediaPlayer.OnPreparedListener,
        MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener,
        MediaBasePlayerControl {

    @Getter
    private MelonMediaPlayer mediaPlayer;
    private boolean initiate = false;

    public PlayThread(MelonMediaPlayer player) {
        this.mediaPlayer = player;
    }

    @Override
    public void run() {
        try {
            mediaPlayer.setDataSource(mediaPlayer.getMediaSource());
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
            mediaPlayer.seekTo(0);
            initiate = true;
//                audioPlayer.setPrepare();
        } catch (IllegalStateException | IOException e) {
            e.printStackTrace();
        }
    }

    public void playerInit() {
        try {
            mediaPlayer.setVolume(volume);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setOnErrorListener(this);
    //            mediaPlayer.setOnBufferingUpdateListener(this);
            mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                @Override
                public boolean onInfo(MediaPlayer mp, int what, int extra) {
                    switch(what){
                        case MediaPlayer.MEDIA_INFO_BUFFERING_START:
                            // Progress Diaglog 출력
                            for (PlayThread mixPlayer : mixPlayThreadList) {
                                mixPlayer.pausePlayer();
                            }
                            break;
                        case MediaPlayer.MEDIA_INFO_BUFFERING_END:
                            // Progress Dialog 삭제
                            for (PlayThread mixPlayer : mixPlayThreadList) {
                                mixPlayer.startPlayer();
                            }
                            break;
                    }
                    return false;
                }
            });
            if (!sourceUrl.equals(""))
                mediaPlayer.setDataSource(sourceUrl);
        } catch (IllegalArgumentException | SecurityException | IllegalStateException | IOException e) {
            e.printStackTrace();
        }
    }

    public void setVolume(float volume) {
        mediaPlayer.setVolume(volume);
        mediaPlayer.onVolumeChange();
    }

    public void stopPlayer() {
        this.mediaPlayer.stop();
    }

    public void resetPlayer() {
        if (initiate) {
            mediaPlayer.pause();
            mediaPlayer.seekTo(0);
        }
    }

    public void destroyPlayer() {
        mediaPlayer.stop();
        mediaPlayer.reset();
        mediaPlayer.release();
        mediaPlayer = null;
    }

    public void completeStop() {
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
//                audioPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.prepareAsync();
        }
    }









    // Implement MediaPlayer.OnPreparedListener
    @Override
    public void onPrepared(MediaPlayer mp) {
        if (controller != null) {
            controller.setMediaPlayer(this);
            controllerInit();
        }
    }
    // End MediaPlayer.OnPreparedListener

    // Implement MediaPlayer.OnCompletionListener
    @Override
    public void onCompletion(MediaPlayer mp) {
        if (controller != null) {
            controller.updatePausePlay();
        }

        Log.d("PLAYER_PLAYLIST", (playList == null)+"" );

        if (playList != null)
            playList.completion();
    }
    // End MediaPlayer.OnCompletionListener

    // Implement MediaPlayer.OnErrorListener
    @Override
    public boolean onError(MediaPlayer mediaPlayer, int what, int extra) {
//        if (what == MediaPlayer.MEDIA_ERROR_SERVER_DIED) {
        if (what == MediaPlayer.MEDIA_ERROR_UNKNOWN && sourceUrl != null && !sourceUrl.equals("")) {
            resetMix();

            mediaPlayer.stop();
            mediaPlayer.release();
            this.mediaPlayer = null;

            init();
        }

        return false;
    }
    // End MediaPlayer.OnErrorListener

    // Implement MediaBasePlayerControl
    @Override
    public void startPlayer() {
        if (initiate && mediaPlayer != null && !mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
    }

    @Override
    public void pause() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying() || mediaPlayer.isLooping()) {
                this.mediaPlayer.pause();
            }
        }
    }

    public void reset() {
        if (mediaPlayer.isPlaying() || mediaPlayer.isLooping()) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            try {
                mediaPlayer.prepare();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
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
    public void playerDestroy() {
        mediaPlayer.setDisplay(null);
    }
    // End MediaBasePlayerControl
}
