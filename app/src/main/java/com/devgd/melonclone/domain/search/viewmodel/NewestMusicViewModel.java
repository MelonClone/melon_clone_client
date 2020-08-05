package com.devgd.melonclone.domain.search.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.devgd.melonclone.domain.player.domain.Music;
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
}