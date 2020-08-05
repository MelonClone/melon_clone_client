package com.devgd.melonclone.global.model.viewmodel;

import androidx.lifecycle.LiveData;

import java.util.List;

public interface ListPagerAdapterViewModel<T> {
    LiveData<List<T>> getList();
}
