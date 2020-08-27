package com.devgd.melonclone.domain.search.viewmodel;

import android.content.Context;
import android.view.Surface;
import android.view.TextureView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.devgd.melonclone.domain.player.domain.Music;
import com.devgd.melonclone.global.media.PlayManager;
import com.devgd.melonclone.global.media.Playable;
import com.devgd.melonclone.global.media.player.AndroidMediaPlayer;
import com.devgd.melonclone.global.media.player.MusicPlayer;
import com.devgd.melonclone.global.model.viewmodel.BaseViewModel;
import com.devgd.melonclone.global.model.viewmodel.ListViewModel;
import com.devgd.melonclone.utils.store.MusicSample;

import java.util.List;

public class NewestMusicViewModel extends BaseViewModel implements ListViewModel<Music>, Playable {

    private MutableLiveData<List<Music>> mMusicList;

    @Override
    protected void init() {
    }

    @Override
    public LiveData<List<Music>> getList() {
        if (mMusicList == null) {
            mMusicList = new MutableLiveData<>();
            loadNewestMusic();
        }
        return mMusicList;
    }

    private void loadNewestMusic() {
        mMusicList.postValue(MusicSample.getSampleList());
    }


    @Override
    protected void onCleared() {
        super.onCleared();
//        musicStop();
    }

    @Override
    public void mediaPlay(Context context, Music music, TextureView view) {
        MusicPlayer mediaPlayer = new AndroidMediaPlayer(music.getMusicUrl(), 1f, context);
        PlayManager.getInstance().setPlayer(mediaPlayer);
        PlayManager.getInstance().setDisplay(new Surface(view.getSurfaceTexture()));
        PlayManager.getInstance().startPlayer();
    }

    @Override
    public void mediaStop() {
        if (!PlayManager.getInstance().isDestroyed()) {
            PlayManager.getInstance().destroyPlayer();
        }
    }

    @Override
    public boolean isPlay() {
        return PlayManager.getInstance().isPlaying();
    }

    @Override
    public long getCurrentPosition() {
        return PlayManager.getInstance().getCurrentPosition();
    }
}