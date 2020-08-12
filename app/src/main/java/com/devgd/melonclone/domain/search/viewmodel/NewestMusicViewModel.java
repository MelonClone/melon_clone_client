package com.devgd.melonclone.domain.search.viewmodel;

import android.view.Surface;
import android.view.TextureView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.devgd.melonclone.domain.player.domain.Music;
import com.devgd.melonclone.domain.player.domain.Player;
import com.devgd.melonclone.domain.player.model.PlayerModel;
import com.devgd.melonclone.global.media.MelonMediaPlayer;
import com.devgd.melonclone.global.media.PlayManager;
import com.devgd.melonclone.global.model.viewmodel.BaseViewModel;
import com.devgd.melonclone.global.model.viewmodel.ListPagerAdapterViewModel;
import com.devgd.melonclone.utils.store.MusicSample;

import java.util.List;

public class NewestMusicViewModel extends BaseViewModel implements ListPagerAdapterViewModel<Music> {

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

    public void musicPlay(TextureView view, Music music) {
        MelonMediaPlayer mediaPlayer = new MelonMediaPlayer(music.getMusicUrl());
        PlayManager.getInstance().setPlayer(mediaPlayer);
        PlayManager.getInstance().setDisplay(new Surface(view.getSurfaceTexture()));
        PlayManager.getInstance().startPlayer();
    }

    public void musicStop() {
        if (!PlayManager.getInstance().isDestroyed()) {
            PlayManager.getInstance().destroyPlayer();
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        musicStop();
    }
}