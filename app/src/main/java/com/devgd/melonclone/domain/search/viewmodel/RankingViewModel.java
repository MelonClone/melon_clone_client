package com.devgd.melonclone.domain.search.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.devgd.melonclone.domain.player.domain.Music;
import com.devgd.melonclone.global.model.viewmodel.BaseViewModel;
import com.devgd.melonclone.global.model.viewmodel.ListViewModel;
import com.devgd.melonclone.utils.store.MusicSample;

import java.util.List;

public class RankingViewModel extends BaseViewModel implements ListViewModel<Music> {

    private MutableLiveData<List<Music>> mRankingList;

    @Override
    protected void init() {
    }

    @Override
    public LiveData<List<Music>> getList() {
        if (mRankingList == null) {
            mRankingList = new MutableLiveData<>();
            loadPlaylist();
        }
        return mRankingList;
    }

    private void loadPlaylist() {
        mRankingList.postValue(MusicSample.getSampleList());
    }
}