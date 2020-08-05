package com.devgd.melonclone.domain.search.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.devgd.melonclone.domain.search.domain.Ads;
import com.devgd.melonclone.global.model.viewmodel.BaseViewModel;
import com.devgd.melonclone.global.model.viewmodel.ListPagerAdapterViewModel;
import com.devgd.melonclone.utils.store.AdsSample;

import java.util.List;

public class AdsViewModel extends BaseViewModel implements ListPagerAdapterViewModel<Ads> {

    private MutableLiveData<List<Ads>> mAdsList;

    @Override
    protected void init() {
    }

    @Override
    public LiveData<List<Ads>> getList() {
        if (mAdsList == null) {
            mAdsList = new MutableLiveData<>();
            loadAds();
        }
        return mAdsList;
    }

    private void loadAds() {
        mAdsList.postValue(AdsSample.getSampleList());
    }
}